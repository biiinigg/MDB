package dci.dsc.controller;

import java.io.PrintWriter;

public class ConnectionController {
	public boolean isValidate(String srcStr,String desStr){
		boolean result=true;
		String[] srcArr=srcStr.split(";"),desArr=desStr.split(";");
		//check1:src DB info 不可空白
		for(String str:srcArr)
		{
			//System.out.println(str);
			if(str==null||str.trim().equals(""))
				result=false;
		}
		//check2:dest DB info 不可空白
		for(String str:desArr)
		{
			//System.out.println(str);
			if(str==null||str.trim().equals(""))
				result=false;
		}
		//check3:src DB name 不可重複
		if(srcArr[0].equals(desArr[0]))
		{
			result=false;
		}
		return result;
	}
	public String saveData(String srcStr,String desStr){
		String path=System.getProperty("user.dir")+"\\DBInfos.txt";
		String res="";
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(srcStr);
			writer.println(desStr);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			res=e.getMessage();
		}
		return res;
	}
}
