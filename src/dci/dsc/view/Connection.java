package dci.dsc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dci.dsc.controller.ConnectionController;
//import dci.dsc.controller.dbTableNameTreeController;
import dci.dsc.model.DBinfos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Connection extends JFrame {

	private JPanel contentPane;
	private JTextField srctxtName;
	private JTextField srctxtIp;
	private JTextField srctxtPort;
	private JTextField srctxtAcount;
	private JTextField srctxtPass;
	private JTextField destxtName;
	private JTextField destxtIp;
	private JTextField destxtPort;
	private JTextField destxtAcount;
	private JTextField destxtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection frame = new Connection();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Connection() {
		final JFrame cus=this;
		setTitle("資料庫設定");
		//setDefaultCloseOperation(Connection.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("資料庫名稱：");
		label.setBounds(0, 41, 148, 23);
		contentPane.add(label);
		
		JLabel lblip = new JLabel("資料庫IP：");
		lblip.setBounds(0, 78, 148, 23);
		contentPane.add(lblip);
		
		JLabel label_2 = new JLabel("資料庫埠號：");
		label_2.setBounds(0, 116, 148, 23);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("登入帳號：");
		label_3.setBounds(0, 154, 148, 23);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("登入密碼：");
		label_4.setBounds(0, 196, 148, 23);
		contentPane.add(label_4);
		
		srctxtName = new JTextField();
		srctxtName.setBounds(127, 41, 136, 29);
		contentPane.add(srctxtName);
		srctxtName.setColumns(10);
		
		srctxtIp = new JTextField();
		srctxtIp.setText("localhost");
		srctxtIp.setColumns(10);
		srctxtIp.setBounds(127, 79, 136, 29);
		contentPane.add(srctxtIp);
		
		srctxtPort = new JTextField();
		srctxtPort.setText("1433");
		srctxtPort.setColumns(10);
		srctxtPort.setBounds(127, 116, 136, 29);
		contentPane.add(srctxtPort);
		
		srctxtAcount = new JTextField();
		srctxtAcount.setText("sa");
		srctxtAcount.setColumns(10);
		srctxtAcount.setBounds(127, 154, 136, 29);
		contentPane.add(srctxtAcount);
		
		srctxtPass = new JTextField();
		srctxtPass.setText("dsc");
		srctxtPass.setColumns(10);
		srctxtPass.setBounds(127, 192, 136, 29);
		contentPane.add(srctxtPass);
		JLabel label_1 = new JLabel("資料庫名稱：");
		label_1.setBounds(308, 41, 148, 23);
		contentPane.add(label_1);
		
		destxtName = new JTextField();
		destxtName.setColumns(10);
		destxtName.setBounds(435, 41, 136, 29);
		contentPane.add(destxtName);
		
		JLabel label_5 = new JLabel("資料庫IP：");
		label_5.setBounds(308, 78, 148, 23);
		contentPane.add(label_5);
		
		destxtIp = new JTextField();
		destxtIp.setText("localhost");
		destxtIp.setColumns(10);
		destxtIp.setBounds(435, 79, 136, 29);
		contentPane.add(destxtIp);
		
		JLabel label_6 = new JLabel("資料庫埠號：");
		label_6.setBounds(308, 116, 148, 23);
		contentPane.add(label_6);
		
		destxtPort = new JTextField();
		destxtPort.setText("1433");
		destxtPort.setColumns(10);
		destxtPort.setBounds(435, 116, 136, 29);
		contentPane.add(destxtPort);
		
		JLabel label_7 = new JLabel("登入帳號：");
		label_7.setBounds(308, 154, 148, 23);
		contentPane.add(label_7);
		
		destxtAcount = new JTextField();
		destxtAcount.setText("sa");
		destxtAcount.setColumns(10);
		destxtAcount.setBounds(435, 154, 136, 29);
		contentPane.add(destxtAcount);
		
		JLabel label_8 = new JLabel("登入密碼：");
		label_8.setBounds(308, 196, 148, 23);
		contentPane.add(label_8);
		
		destxtPass = new JTextField();
		destxtPass.setText("dsc");
		destxtPass.setColumns(10);
		destxtPass.setBounds(435, 192, 136, 29);
		contentPane.add(destxtPass);
		//init DB infos
		String path=System.getProperty("user.dir")+"\\DBInfos.txt";
		File dbInfoFile =new File(path);
		if(dbInfoFile.exists()){
			String[] src;
			String[] des;
			try {
				FileInputStream fstream = new FileInputStream(dbInfoFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				ArrayList<String> lineList=new ArrayList<String>();
				String strLine;
				while ((strLine = br.readLine()) != null)   {
					lineList.add(strLine);
				}
				br.close();
				DBinfos infos=new DBinfos();
				infos.setData(lineList.get(0), lineList.get(1));
				
				src=lineList.get(0).split(";");
				des=lineList.get(1).split(";");
//				for(String str : src){
//					System.out.println(str);
//				}
//				for(String str : des){
//					System.out.println(str);
//				}
				srctxtName.setText(src[0]);	
				srctxtIp.setText(src[1]);
				srctxtPort.setText(src[2]);
				srctxtAcount.setText(src[3]);
				srctxtPass.setText(src[4]);
				destxtName.setText(des[0]);	
				destxtIp.setText(des[1]);
				destxtPort.setText(des[2]);
				destxtAcount.setText(des[3]);
				destxtPass.setText(des[4]);
				
			} catch (Exception e) {
			}
			
		}
		JButton btnNewButton = new JButton("連線資訊儲存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String src=srctxtName.getText()+";"+	srctxtIp.getText()+";"+srctxtPort.getText()+";"+srctxtAcount.getText() +";"+srctxtPass.getText();
				String des=destxtName.getText()+";"+	destxtIp.getText()+";"+destxtPort.getText()+";"+destxtAcount.getText() +";"+destxtPass.getText();
				ConnectionController dsw=new ConnectionController();
				try {
					if(dsw.isValidate(src,des)){
						DBinfos infos=new DBinfos();
						infos.setData(src, des);
						setVisible(false);
						dsw.saveData(src,des);
//						dbTableNameTreeController srctree =new dbTableNameTreeController();
//						MainView.scrollPane_2.setViewportView(srctree.createTree(MainView.frame, DBinfos.sDBName,DBinfos.sDBinfos));
//						MainView.scrollPane_2.revalidate();
//						MainView.scrollPane_2.repaint();
//						dbTableNameTreeController destree =new dbTableNameTreeController();
//						MainView.scrollPane.setViewportView(destree.createTree(MainView.frame, DBinfos.dDBName,DBinfos.dDBinfos));
//						MainView.scrollPane.revalidate();
//						MainView.scrollPane.repaint();
						JOptionPane.showMessageDialog(cus,
							    "存檔成功！",
							    "提示訊息",
							    JOptionPane.PLAIN_MESSAGE);
						System.out.println("DB infos update is ok.");
					}
//					else{
//						JOptionPane.showMessageDialog(cus,
//							    "資料欄位驗證失敗！",
//							    "提示訊息",
//							    JOptionPane.ERROR_MESSAGE);
//					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(cus,
						    "資料欄位驗證失敗！",
						    "提示訊息",
						    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnNewButton.setBounds(211, 256, 174, 31);
		contentPane.add(btnNewButton);
		
		
		
		JLabel label_9 = new JLabel("資料來源：");
		label_9.setBounds(0, 3, 148, 23);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("資料目的地：");
		label_10.setBounds(308, 3, 148, 23);
		contentPane.add(label_10);
	}
}
