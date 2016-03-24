package dci.dsc.model;

public class DBinfos {
	public static String sDBName,dDBName;
	public static String sDBPath,dDBPath;
	public static String sDBPort,dDBPort;
	public static String sDBUser,dDBUser;
	public static String sDBPass,dDBPass;
	public static String sDBinfos,dDBinfos;
	public void setData(String srcStr,String desStr){
		String[] src=srcStr.split(";");
		String[] des=desStr.split(";");
		sDBName=src[0];
		sDBPath=src[1];
		sDBPort=src[2];
		sDBUser=src[3];
		sDBPass=src[4];
		dDBName=des[0];
		dDBPath=des[1];
		dDBPort=des[2];
		dDBUser=des[3];
		dDBPass=des[4];
		sDBinfos=srcStr;
		dDBinfos=desStr;
		System.out.println("com.dci.dsc.model.DBubfis.java");
		System.out.println(sDBinfos);
		System.out.println(dDBinfos);
		System.out.println("------------------------------");
	}
}
