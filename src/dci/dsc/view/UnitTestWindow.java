package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dci.dsc.controller.CopyFile;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import com.dci.KBSQL;

public class UnitTestWindow {

	private JFrame frame;
	private JTextField urlField;

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
		frame.setBounds(100, 100, 450, 300);
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
		
		final JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton button = new JButton("擷取檔案");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectCD=urlField.getText();
				String[] fileListArr = textPane.getText().split("\r");
				new CopyFile().copyFile(projectCD, fileListArr);
				JOptionPane.showMessageDialog(frame,"Export ok!");
			}
		});
		button.setBounds(10, 206, 87, 23);
		frame.getContentPane().add(button);
		
		JButton btnNewButton = new JButton("開始測試");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KBSQL sql=new KBSQL();
				String[] fileListArr = textPane.getText().split("\r");
				sql.parseALLMethods(sql.transPackageClass(fileListArr));
				JOptionPane.showMessageDialog(frame,"測試完畢");
			}
		});
		btnNewButton.setBounds(116, 206, 87, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
