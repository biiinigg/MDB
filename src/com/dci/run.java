package com.dci;
import java.net.URL;
import java.net.URLClassLoader;

public class run {
	public static void type(){
		try{
			URL url = new URL("file:/D:/MDBT/ClassLoader/KanBan/src/com/dsc/dci/sqlcode/main/");
			ClassLoader urlClassLoader = new URLClassLoader(new URL[] {url});
			//Class c = urlClassLoader.loadClass("sqlTask");
			Class clazz = Class.forName("sqlTask");
			System.out.println(clazz.getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args){
		try{
//			String path=System.getProperty("user.dir");
//			System.out.println(path);
//			KBClassLoader loader=new KBClassLoader();
//			String classname="com.dsc.dci.sqlcode.main.sqlTask";
//			Class clazz = Class.forName(classname);
////			Class clazz = loader.loadClass(classname);
//			System.out.println(clazz.getName());
			//loader.findClass("com.dsc.dci.sqlcode.main.sqlTask");
			type();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
