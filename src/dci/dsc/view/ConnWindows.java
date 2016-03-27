package dci.dsc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import dci.dsc.dcienum.DBEnum;
import dci.dsc.mainmethods.DBInfoFile;
import dci.dsc.mainmethods.Dom4j;
import dci.dsc.model.ConnInfo;

public class ConnWindows {
	private final String MYSQL = DBEnum.Type.MYSQL.toString();
	private final String ORACLE = DBEnum.Type.ORACLE.toString();
	private final String SQLSERVER = DBEnum.Type.SQLSERVER.toString();
	private JFrame frame=new JFrame();
	private JTextField _ip;
	private JTextField _sid;
	private JTextField _port;
	private JTextField _ac;
	private JTextField _pw;
	private JTextField _cname;
	private JTextField type;
	private String dbtype;

	public JFrame getJframe() {
		return this.frame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnWindows window = new ConnWindows();
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
	public ConnWindows() {
		initialize();
		//ArrayList<String> dbinfos = new DBInfoFile().read();
	}

	public void initdata(String dbtype) {
		//initialize();
		this.dbtype = dbtype;
		type.setText(this.dbtype);
		if (this.dbtype.equals(MYSQL)) {
			_ip.setText("localhost");
			_port.setText("3306");
		} else if (this.dbtype.equals(ORACLE)) {
			_ip.setText("localhost");
			_port.setText("1521");
		} else if (this.dbtype.equals(SQLSERVER)) {
			_ip.setText("localhost");
			_port.setText("1433");
		}
		HashMap<String ,ConnInfo> dats = new Dom4j().read();
		if (dats != null) {
			ConnInfo row=null;
			for(String db:dats.keySet()){
				row=dats.get(db);
				if (this.dbtype.equals(MYSQL)&&row.type().equals(MYSQL)) {
					if (row != null) {
						_ip.setText(row.ip());
						_sid.setText(row.name());
						_port.setText(row.port());
						_ac.setText(row.ac());
						_pw.setText(row.pw());
						_cname.setText(row.cname());
					}
				} else if (this.dbtype.equals(ORACLE)&&row.type().equals(ORACLE)) {
					if (row != null) {
						_ip.setText(row.ip());
						_sid.setText(row.name());
						_port.setText(row.port());
						_ac.setText(row.ac());
						_pw.setText(row.pw());
						_cname.setText(row.cname());
					}
				} else if (this.dbtype.equals(SQLSERVER)&&row.type().equals(SQLSERVER)) {
					if (row != null) {
						_ip.setText(row.ip());
						_sid.setText(row.name());
						_port.setText(row.port());
						_ac.setText(row.ac());
						_pw.setText(row.pw());
						_cname.setText(row.cname());
					}
				}
			}
		}

	}

	public String getdata() {
		return _cname.getText() + ";" + type.getText() + ";" + _sid.getText() + ";" + _ip.getText() + ";"
				+ _ac.getText() + ";" + _pw.getText() + ";" + _port.getText();
	}

	public boolean validate() {
		boolean resultbool = true;
		String[] array = getdata().split(";");
		for (int i = 0; i < array.length; i++) {
			if ("".equals(array[i])) {
				JOptionPane.showMessageDialog(getJframe(), "不允許空白，請填寫完整資料。", "提示訊息", JOptionPane.PLAIN_MESSAGE);
				resultbool = false;
				break;
			}
		}
		return resultbool;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle("連線資訊");
		frame.setBounds(100, 100, 405, 380);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("連線名稱");
		label.setBounds(15, 9, 120, 23);
		frame.getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("資料庫IP:");
		lblNewLabel.setBounds(15, 75, 120, 23);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("資料庫名稱:");
		lblNewLabel_1.setBounds(15, 110, 120, 23);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("資料庫埠號:");
		lblNewLabel_2.setBounds(15, 145, 120, 23);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("登入帳號:");
		lblNewLabel_3.setBounds(15, 180, 120, 23);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("登入密碼:");
		lblNewLabel_4.setBounds(15, 215, 120, 23);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("資料庫類型:");
		lblNewLabel_5.setBounds(15, 42, 120, 23);
		frame.getContentPane().add(lblNewLabel_5);

		_cname = new JTextField();
		_cname.setColumns(10);
		_cname.setBounds(150, 6, 217, 29);
		frame.getContentPane().add(_cname);

		_ip = new JTextField();
		_ip.setBounds(150, 72, 217, 29);
		frame.getContentPane().add(_ip);
		_ip.setColumns(10);

		_sid = new JTextField();
		_sid.setColumns(10);
		_sid.setBounds(150, 107, 217, 29);
		frame.getContentPane().add(_sid);

		_port = new JTextField();
		_port.setColumns(10);
		_port.setBounds(150, 142, 217, 29);
		frame.getContentPane().add(_port);

		_ac = new JTextField();
		_ac.setColumns(10);
		_ac.setBounds(150, 177, 217, 29);
		frame.getContentPane().add(_ac);

		_pw = new JTextField();
		_pw.setColumns(10);
		_pw.setBounds(150, 212, 217, 29);
		frame.getContentPane().add(_pw);
		type = new JTextField();
		type.setColumns(10);
		type.setBounds(150, 42, 217, 23);
		frame.getContentPane().add(type);
		type.disable();
		// initdata();
		// String[] columnNames = {"資料庫類型",
		// "資料庫名稱",
		// "資料庫IP",
		// "資料庫帳號",
		// "資料庫密碼","資料庫埠號"};
		// DefaultTableModel model = new DefaultTableModel();
		// model.addColumn("ConnName");
		// model.addColumn("Type");
		// model.addColumn("Name");
		// model.addColumn("IP");
		// model.addColumn("Account");
		// model.addColumn("Password");
		// model.addColumn("Port");
		JButton button = new JButton("儲存");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validate()) {
					ConnInfo row = new ConnInfo(getdata());
					new Dom4j().create(row);
					frame.setVisible(false);
				}
			}
		});
		button.setBounds(256, 251, 111, 31);
		frame.getContentPane().add(button);

	}
}
