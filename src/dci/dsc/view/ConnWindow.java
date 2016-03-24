package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dci.dsc.controller.ConnController;
import dci.dsc.mainmethods.DBInfoFile;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ConnWindow {

	private JFrame frame;
	private JTable table;
	private JTextField _ip;
	private JTextField _sid;
	private JTextField _port;
	private JTextField _ac;
	private JTextField _pw;
	private JTextField _cname;
	public JTable getJtable(){
		return this.table;
	}
	public JFrame getJframe(){
		return this.frame;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnWindow window = new ConnWindow();
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
	public ConnWindow() {
		initialize();
		ArrayList<String> dbinfos =new DBInfoFile().read();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("連線資訊");
		frame.setBounds(100, 100, 530, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()==0){
					_port.setText("1433");
				}else if(comboBox.getSelectedIndex()==1){
					_port.setText("1521");
				}else if(comboBox.getSelectedIndex()==2){
					_port.setText("3306");
				}
				_ip.setText("localhost");
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"MsSql","Oracle","MySql"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(150, 39, 217, 29);
		frame.getContentPane().add(comboBox);
		
//		String[] columnNames = {"資料庫類型",
//                "資料庫名稱",
//                "資料庫IP",
//                "資料庫帳號",
//                "資料庫密碼","資料庫埠號"};
		DefaultTableModel model = new DefaultTableModel(); 
		model.addColumn("ConnName");
		model.addColumn("Type");
		model.addColumn("Name");
		model.addColumn("IP");
		model.addColumn("Account");
		model.addColumn("Password");
		model.addColumn("Port");
		JButton btnNewButton = new JButton("新增");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				table.add("type", comboBox.getComponent(comboBox.getSelectedIndex()));
				DefaultTableModel  jmodel=(DefaultTableModel) table.getModel();
				String msg="";
				//validate 1:null
				if(_cname.getText().equals("")||_sid.getText().equals("")||
						_ip.getText().equals("")||
						_ac.getText().equals("")||
						_pw.getText().equals("")||
						_port.getText().equals("")){
					msg="請確認欄位都有填寫值。";
					JOptionPane.showMessageDialog(null, msg);
				}else{
					//validate 2:duplicate
					String addrow=_cname.getText()+";"+
					comboBox.getSelectedItem().toString()+";"+
					_sid.getText()+";"+
					_ip.getText()+";"+
					_ac.getText()+";"+
					_pw.getText()+";"+
					_port.getText()+";";
					
					int index=jmodel.getRowCount();
					String[] indexes=new String[index];
					for(int i=0;i<jmodel.getRowCount();i++){
						String rows="";
						for(int c=0;c<jmodel.getColumnCount();c++){
							rows+=jmodel.getValueAt(i,c)+";";
						}
						indexes[i]=rows;
					}
					int flag=1;
					for(int i=0;i<indexes.length;i++){
						if(addrow.equals(indexes[i])){
							flag=0;
							break;
						}else{
							flag=1;
						}
					}
					if(flag==1){
						Object[] rowData={_cname.getText(),
								comboBox.getSelectedItem().toString(),
								_sid.getText(),
								_ip.getText(),
								_ac.getText(),
								_pw.getText(),
								_port.getText()};
						jmodel.addRow(rowData);
					}else{
						msg="請勿輸入重複的資料庫資訊。";
						JOptionPane.showMessageDialog(null, msg);
					}
				}
			}
		});
		btnNewButton.setBounds(382, 172, 111, 31);
		frame.getContentPane().add(btnNewButton);
		
		JButton button_1 = new JButton("刪除");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel  jmodel=(DefaultTableModel) table.getModel();
				jmodel.removeRow(table.getSelectedRow());
			}
		});
		button_1.setBounds(382, 207, 111, 31);
		frame.getContentPane().add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 253, 510, 179);
		frame.getContentPane().add(scrollPane);
		table = new JTable(model);
		new ConnController().init(table);
		scrollPane.setViewportView(table);
		JButton button = new JButton("完成");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConnController().save(table);
				getJframe().setVisible(false);
			}
		});
		button.setBounds(192, 435, 111, 31);
		frame.getContentPane().add(button);
	}
}
