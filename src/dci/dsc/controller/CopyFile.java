package dci.dsc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dci.dsc.view.UnitTestWindow;

public class CopyFile {
	public String[] transFileList(String[] fileListArr){
		if(!UnitTestWindow.userMode){
			for(int i=0;i<fileListArr.length;i++){
				fileListArr[i]=fileListArr[i].replaceFirst("/KanBan/", "/");
			}
		}
		return fileListArr;
	}
	public void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("新建目錄操作出錯");
			e.printStackTrace();
		}
	}

	public String copyFile(String currFolder, String[] fileList) {
		String msg = "";
		String tmp = "";
		String userDir = System.getProperty("user.dir");
		userDir = userDir.replaceAll("\\\\", "/");
		currFolder = currFolder.replaceAll("\\\\", "/");
		if (currFolder.endsWith("/")) {
			currFolder = currFolder.substring(0, currFolder.length() - 1);
		}
		for (int i = 0; i < fileList.length; i++) {
			if (!fileList[i].contains("--")) {
				tmp = currFolder + fileList[i].trim();
				tmp = tmp.replaceAll("\\s", "");
				System.out.println(tmp);
				System.out.println("copy from src:"+tmp);
				File src = new File(tmp);
				System.out.println("copy to dest:"+userDir + fileList[i].trim());
				File dst = new File(userDir + fileList[i].trim());
				copy(src, dst);
			}
		}

		return msg;
	}

	public void copy(File src, File dst) {
		try {
			dst.getParentFile().mkdirs();// create folders
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();

		} catch (Exception ex) {

		}
	}
}
