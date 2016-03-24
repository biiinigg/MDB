package dci.dsc.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import dci.dsc.mainmethods.DBInfoFile;
import dci.dsc.model.ConnInfo;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("語法測試");
		frame.setBounds(100, 100, 533, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnNewButton = new JButton("連線設定");
		btnNewButton.setBounds(0, 0, 118, 38);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnWindow cw = new ConnWindow();
				cw.getJframe().setVisible(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 65, 511, 135);
		frame.getContentPane().add(scrollPane);
		DBInfoFile infos=new DBInfoFile();
		ArrayList<String> dblist=infos.read();
		for(int i =0;i<dblist.size();i++){
			System.out.println(i);
			String cname=new ConnInfo(dblist.get(i)).cname();
			JRadioButton rdbtnNewRadioButton = new JRadioButton(cname);
			rdbtnNewRadioButton.setBounds(0, 49+i*30, 151+i*10, 31+i*10);
			scrollPane.add(rdbtnNewRadioButton);
		}
	}
}
