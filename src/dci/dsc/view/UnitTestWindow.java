package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dci.dsc.controller.CopyFile;
import dci.dsc.dcienum.DBEnum;
import dci.dsc.mainmethods.Dom4j;
import dci.dsc.model.ConnInfo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import com.dci.KBSQL;
import com.dci.KBSQL.METHOD;
import com.dci.dsc.dbcp;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class UnitTestWindow {
	private final String MYSQL = DBEnum.Type.MYSQL.toString();
	private final String ORACLE = DBEnum.Type.ORACLE.toString();
	private final String SQLSERVER = DBEnum.Type.SQLSERVER.toString();
	private static TreeMap<String, HashMap<METHOD, Object>> classInfo = null;
	private JFrame frame;
	private JTextField urlField;
	private JTextPane textPane;
	private JCheckBox cmssql;
	private JCheckBox coracle;
	private JCheckBox cmysql;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnitTestWindow window = new UnitTestWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UnitTestWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("單元測試");
		frame.setBounds(100, 100, 450, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		urlField = new JTextField();
		urlField.setText("D:\\PEKBTEST");
		urlField.setBounds(116, 10, 256, 21);
		frame.getContentPane().add(urlField);
		urlField.setColumns(10);

		JLabel lblNewLabel = new JLabel("KB專案主目錄");
		lblNewLabel.setBounds(23, 13, 83, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("測試檔案清單");
		lblNewLabel_1.setBounds(23, 38, 83, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(116, 41, 256, 153);
		frame.getContentPane().add(scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		JButton button = new JButton("擷取檔案");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectCD = urlField.getText();
				String[] fileListArr = textPane.getText().split("\r");
				new CopyFile().copyFile(projectCD, fileListArr);
				JOptionPane.showMessageDialog(frame, "Export ok!");
			}
		});
		button.setBounds(10, 206, 87, 23);
		frame.getContentPane().add(button);

		cmysql = new JCheckBox(MYSQL);
		cmysql.setBounds(10, 235, 97, 23);
		frame.getContentPane().add(cmysql);

		coracle = new JCheckBox(ORACLE);
		coracle.setBounds(116, 235, 97, 23);
		frame.getContentPane().add(coracle);

		cmssql = new JCheckBox(SQLSERVER);
		cmssql.setBounds(215, 235, 97, 23);
		frame.getContentPane().add(cmssql);

		JButton btnNewButton = new JButton("開始測試");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (testValidation()) {
					classInfo = getClassInfo();
					initConnect();
					JOptionPane.showMessageDialog(frame, "測試完畢");
				}
			}
		});
		btnNewButton.setBounds(325, 235, 87, 23);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 274, 414, 175);
		frame.getContentPane().add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JButton btnlog = new JButton("清除LOG");
		btnlog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnlog.setBounds(173, 459, 87, 23);
		frame.getContentPane().add(btnlog);

	}

	private ArrayList<String> startup() {
		ArrayList<String> data = new ArrayList<String>();
		if (cmssql.isSelected()) {
			data.add(cmssql.getText());
		}
		if (coracle.isSelected()) {
			data.add(coracle.getText());
		}
		if (cmysql.isSelected()) {
			data.add(cmysql.getText());
		}
		return data;
	}

	private boolean testValidation() {
		boolean boolResult = false;
		if (!"".equals(textPane.getText()) && !"".equals(urlField.getText())) {
			boolResult = true;
		} else {
			JOptionPane.showMessageDialog(frame, "不允許空白，請填寫完整資料。", "提示訊息", JOptionPane.ERROR_MESSAGE);
		}
		return boolResult;
	}
	private void log(String msg){
		textArea.append(getTime()+"\n"+msg+"\n");
		textArea.paintImmediately(textArea.getBounds());
	}
	public static String getTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date current = new Date();
		return sdFormat.format(current);
	}
	private void initConnect() {
		Dom4j xml = new Dom4j();
		for (String dbtype : startup()) {
			System.out.println("[" + dbtype + "]");
			ConnInfo DBInfo = xml.read().get(dbtype);
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				dbcp.createDataSource(DBInfo, dbtype);
				// conn = dbcp.getConnection();
				// ps = conn.prepareStatement("select 1 as col",
				// ResultSet.TYPE_SCROLL_INSENSITIVE,
				// ResultSet.CONCUR_READ_ONLY);
				// rs = ps.executeQuery();
				// if (rs.next()) {
				// System.out.println(rs.getString("col"));
				// }
				// dbcp.releaseDataSource(conn, ps, rs);
				startTest(dbtype);
				log(getResultList(dbtype));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	private void startTest(String dbtype) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dbcp.getConnection();
			for (String methodName : classInfo.keySet()) {
				boolean hasMethodParam = (boolean) classInfo.get(methodName).get(METHOD.HasParam);
				if (!hasMethodParam) {
					try {
						String sql = (String) classInfo.get(methodName).get(METHOD.RETURN);
						sql = sql.replaceAll("\\?", "''");
						ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = ps.executeQuery();
						if (rs != null) {
							if (dbtype.equals(SQLSERVER)) {
								classInfo.get(methodName).put(METHOD.SQL, true);
							} else if (dbtype.equals(MYSQL)) {
								classInfo.get(methodName).put(METHOD.MY, true);
							} else if (dbtype.equals(ORACLE)) {
								classInfo.get(methodName).put(METHOD.ORA, true);
							}
						}
					} catch (Exception ex) {
						// ex.printStackTrace();
						if (dbtype.equals(SQLSERVER)) {
							classInfo.get(methodName).put(METHOD.SQL, false);
							classInfo.get(methodName).put(METHOD.SQLEXC, ex.getMessage());
						} else if (dbtype.equals(MYSQL)) {
							classInfo.get(methodName).put(METHOD.MY, false);
							classInfo.get(methodName).put(METHOD.MYEXC, ex.getMessage());
						} else if (dbtype.equals(ORACLE)) {
							classInfo.get(methodName).put(METHOD.ORA, false);
							classInfo.get(methodName).put(METHOD.ORAEXC, ex.getMessage());
						}
					} finally {
						ps = null;
						rs = null;
					}
				}
			}
			dbcp.releaseDataSource(conn, ps, rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getResultList(String dbtype) {
		String successMethod = "";
		String failMethod = "";
		String hasParamMethodNoTest = "";
		METHOD type = null, exception = null;
		int totalCount = classInfo.size(), successCount = 0, failCount = 0, hasParamCount = 0;
		String result = "["+dbtype+"] has " + totalCount + " Unit test result by :\n";
		if (dbtype.equals(MYSQL)) {
			type = METHOD.MY;
			exception = METHOD.MYEXC;
		} else if (dbtype.equals(SQLSERVER)) {
			type = METHOD.SQL;
			exception = METHOD.SQLEXC;
		} else if (dbtype.equals(ORACLE)) {
			type = METHOD.ORA;
			exception = METHOD.ORAEXC;
		}
		for (String methodName : classInfo.keySet()) {
			if (classInfo.get(methodName).get(type) != null && (boolean) classInfo.get(methodName).get(METHOD.SQL)) {
				successCount++;
				successMethod += (String) classInfo.get(methodName).get(METHOD.CLASS) + "." + methodName + "\n";
			} else if (classInfo.get(methodName).get(type) == null) {
				hasParamCount++;
				hasParamMethodNoTest += (String) classInfo.get(methodName).get(METHOD.CLASS) + "." + methodName + "\n";
			} else if (!(boolean) classInfo.get(methodName).get(type)) {
				failCount++;
				failMethod += (String) classInfo.get(methodName).get(METHOD.CLASS) + "." + methodName + "\n";
				failMethod += "\t" + (String) classInfo.get(methodName).get(exception) + "\n";
			}
		}
		if (totalCount == successCount + hasParamCount + failCount) {
			result += successCount + " Test Success Method:\n" + successMethod + failCount + " Test fail Method:\n"
					+ failMethod + hasParamCount + " No Test Method:\n" + hasParamMethodNoTest;
			System.out.println(result);
		}
		return result;
	}

	private TreeMap<String, HashMap<METHOD, Object>> getClassInfo() {
		String[] fileListArr = textPane.getText().split("\r");
		return new KBSQL().parseALLMethods(new KBSQL().transPackageClass(fileListArr));
	}
}
