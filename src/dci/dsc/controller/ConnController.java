package dci.dsc.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dci.dsc.mainmethods.DBInfoFile;

public class ConnController {
	private static DBInfoFile dif=new DBInfoFile();
	public void save(JTable table) {
		dif.writeFromTable(table);
	}

	public void init(JTable table) {
		ArrayList<String> infos = dif.read();
		for (int i = 0; i < infos.size(); i++) {
			String[] infoRow = infos.get(i).substring(0, infos.get(i).length() - 1).split(";");
			Object[] rowData = { infoRow[0], infoRow[1], infoRow[2], infoRow[3], infoRow[4], infoRow[5], infoRow[6] };
			DefaultTableModel jmodel = (DefaultTableModel) table.getModel();
			jmodel.addRow(rowData);
		}
	}
}
