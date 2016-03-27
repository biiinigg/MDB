package dci.dsc.mainmethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import dci.dsc.dcienum.DBEnum;
import dci.dsc.model.ConnInfo;

public class DBInfoFile {
	private String url = ".\\DBinfos.txt";

	public void writeFromTable(JTable table) {
		try {
			File file = new File(this.url);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					// System.out.println(table.getModel().getValueAt(i, j));
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

	public void writeFromRows(ConnInfo row) {
		try {
			File file = new File(this.url);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			if (!file.exists()) {
				file.createNewFile();
				if (row.type().equals(DBEnum.Type.MYSQL.toString())) {
					System.out.println(row.getRow());
					bw.write(row.getRow());
					bw.write("\n");
					bw.write("oracle");
					bw.write("\n");
					bw.write("SQLSERVER");
					bw.write("\n");

				} else if (row.type().equals(DBEnum.Type.ORACLE.toString())) {
					bw.write("MYSQL");
					bw.write("\n");
					bw.write(row.getRow());
					bw.write("\n");
					bw.write("SQLSERVER");
					bw.write("\n");
				} else if (row.type().equals(DBEnum.Type.SQLSERVER.toString())) {
					bw.write("MYSQL");
					bw.write("\n");
					bw.write("oracle");
					bw.write("\n");
					bw.write(row.getRow());
					bw.write("\n");
				}
			} else {
				ArrayList<String> list = read();
				if (row.type().equals(DBEnum.Type.MYSQL.toString())) {
					list.set(0, row.getRow());
				} else if (row.type().equals(DBEnum.Type.ORACLE.toString())) {
					list.set(1, row.getRow());
				} else if (row.type().equals(DBEnum.Type.SQLSERVER.toString())) {
					list.set(2, row.getRow());
				}
				for(int i=0;i<list.size();i++){
					bw.write(list.get(i));
					bw.write("\n");
				}
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
