package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import dci.dsc.dcienum.DBEnum;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainProcess {

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		final JButton btnOracle = new JButton(DBEnum.Type.SQLSERVER.toString());
		btnOracle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnWindows cw=new ConnWindows(btnOracle.getText());
				cw.getJframe().setVisible(true);
			}
		});
		btnOracle.setBounds(181, 10, 87, 23);
		frame.getContentPane().add(btnOracle);
		
		final JCheckBox checkMS = new JCheckBox("");
		checkMS.setSelected(true);
		checkMS.setBounds(160, 10, 21, 23);
		frame.getContentPane().add(checkMS);
		
		final JButton button = new JButton(DBEnum.Type.ORACLE.toString());
		button.setBounds(181, 35, 87, 23);
		frame.getContentPane().add(button);
		
		JCheckBox checkOra = new JCheckBox("");
		checkOra.setSelected(true);
		checkOra.setBounds(160, 35, 21, 23);
		frame.getContentPane().add(checkOra);
		
		JButton btnMySql = new JButton(DBEnum.Type.MYSQL.toString());
		btnMySql.setBounds(181, 64, 87, 23);
		frame.getContentPane().add(btnMySql);
		
		JCheckBox checkMY = new JCheckBox("");
		checkMY.setSelected(true);
		checkMY.setBounds(160, 64, 21, 23);
		frame.getContentPane().add(checkMY);
	}
}
