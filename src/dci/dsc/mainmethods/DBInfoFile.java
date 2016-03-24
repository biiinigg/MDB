package dci.dsc.mainmethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DBInfoFile {
	private String url = ".\\DBinfos.txt";

	public void write(JTable table) {
		try {
			File file = new File(this.url);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					//System.out.println(table.getModel().getValueAt(i, j));
					bw.write(table.getModel().getValueAt(i, j) + ";");
				}
				bw.write("\n");
			}
			bw.close();
			fw.close();
			JOptionPane.showMessageDialog(null, "Data Save.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public ArrayList<String> read() {
		ArrayList<String> infos = null;
		FileReader fr = null;
		BufferedReader br = null;
		File file = new File(this.url);
		if (file.exists()) {
			try {
				fr = new FileReader(file.getAbsoluteFile());
				br = new BufferedReader(fr);
				infos = new ArrayList<String>();
				while (br.ready()) {
					infos.add(br.readLine());
				}
				fr.close();
				br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "請輸入資料庫連線資訊。");
		}
		return infos;
	}
}
