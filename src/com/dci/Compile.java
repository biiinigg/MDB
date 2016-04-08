package com.dci;

public class Compile {
	public static void main(String[] agv) {
		String[] paths = { "D:/MDBT/ClassLoader/KanBan/src/com/dsc/dci/sqlcode/main/sqlTask.java" };
		Compile cc = new Compile();
		String[] pathList=cc.parseCommandLine(paths);
		for(String command :pathList){
			cc.execCommandLine(command);
		}
	}
	public void run(String[] paths){
		String[] pathList=parseCommandLine(paths);
		for(String command :pathList){
			execCommandLine(command);
		}
	}
	private String[] parseCommandLine(String[] paths) {
		String current=System.getProperty("user.dir").replace("\\", "/");
		for (int i = 0; i < paths.length; i++) {
			paths[i] = "javac -encoding utf-8 " +current+paths[i].trim().replace("\n", "")+" & exit";
		}
		for (String path : paths) {
			System.out.println(path);
		}
		return paths;
	}

	public void execCommandLine(String command) {
		try {
			
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
					command);
			builder.redirectErrorStream(true);
			Process p = builder.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
