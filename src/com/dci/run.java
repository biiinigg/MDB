package com.dci;
import java.net.URL;
import java.net.URLClassLoader;

public class run {
	public static void type(){
		try{
			System.out.println(System.getProperty("user.dir"));
			URL url = new URL("file:/D:/MDBT/ClassLoader/KanBan/src/");
			ClassLoader urlClassLoader = new URLClassLoader(new URL[] {url});
			Class clazz = urlClassLoader.loadClass("com.dsc.dci.sqlcode.main.sqlTask");
			Object obj=clazz.newInstance();
			System.out.println(clazz.getClassLoader()+"；"+clazz.getName());
			System.out.println(clazz.getDeclaredMethods()[0].invoke(obj, null));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args){
		try{
			type();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
