package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import dci.dsc.dcienum.DBEnum;
import dci.dsc.mainmethods.Dom4j;
import dci.dsc.model.ConnInfo;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.dci.dsc.dbcp;

import javax.swing.JList;

public class MainProcess {
	private final String MYSQL = DBEnum.Type.MYSQL.toString();
	private final String ORACLE = DBEnum.Type.ORACLE.toString();
	private final String SQLSERVER = DBEnum.Type.SQLSERVER.toString();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProcess window = new MainProcess();
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
	public MainProcess() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("跨資料庫系統");
		frame.setBounds(100, 100, 759, 729);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final ConnWindows cw = new ConnWindows();
		final JButton btnOra = new JButton(ORACLE);
		btnOra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cw.initdata(btnOra.getText());
				cw.getJframe().setVisible(true);
			}
		});

		final JButton btnMy = new JButton(MYSQL);
		btnMy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cw.initdata(btnMy.getText());
				cw.getJframe().setVisible(true);
			}
		});

		final JButton btnSQL = new JButton(SQLSERVER);
		btnSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cw.initdata(btnSQL.getText());
				cw.getJframe().setVisible(true);
			}
		});

		btnSQL.setBounds(32, 93, 127, 23);
		frame.getContentPane().add(btnSQL);
		btnMy.setBounds(32, 15, 127, 23);
		frame.getContentPane().add(btnMy);
		btnOra.setBounds(32, 55, 127, 23);
		frame.getContentPane().add(btnOra);

		final JCheckBox checkMS = new JCheckBox("");
		checkMS.setSelected(true);
		checkMS.setBounds(11, 55, 21, 23);
		frame.getContentPane().add(checkMS);

		final JCheckBox checkMY = new JCheckBox("");
		checkMY.setSelected(true);
		checkMY.setBounds(11, 15, 21, 23);
		frame.getContentPane().add(checkMY);

		final JCheckBox checkOra = new JCheckBox("");
		checkOra.setSelected(true);
		checkOra.setBounds(11, 93, 21, 23);
		frame.getContentPane().add(checkOra);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 400, 707, 253);
		frame.getContentPane().add(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(11, 129, 707, 253);
		frame.getContentPane().add(scrollPane_1);

		final JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);

		JButton button = new JButton("檢核");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Dom4j xml = new Dom4j();
					for(String type:xml.read().keySet()){
						if(!checkMS.isSelected()&&type.equals(SQLSERVER)){
							break;
						}else if(!checkOra.isSelected()&&type.equals(ORACLE)){
							break;
						}else if(!checkMY.isSelected()&&type.equals(MYSQL)){
							break;
						}
//						if(type.equals(SQLSERVER)){
							
							dbcp.init(type);
							ConnInfo DBInfo = xml.read().get(type);
							dbcp.createDataSource(DBInfo);
							Connection conn = dbcp.getConnection();
							PreparedStatement ps = conn.prepareStatement(textArea.getText(), ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
							ResultSet rs = ps.executeQuery();
							String tmp="";
							if (rs.next()) {
								for(int i=1;i<rs.getMetaData().getColumnCount();i++){
									tmp+=rs.getMetaData().getColumnName(i)+";";
								}
								System.out.println(tmp.substring(0,tmp.length()-1));
							}
							dbcp.releaseDataSource(conn,ps,rs);
						}
//					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		button.setBounds(172, 11, 111, 31);
		frame.getContentPane().add(button);

	}
}
