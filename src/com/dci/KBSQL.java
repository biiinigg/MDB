package com.dci;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.TreeMap;

public class KBSQL {
	public static void main(String[] agv) {
		String[] data = { "com.dsc.dci.sqlcode.funcs.ekb.sqlPE001", "com.dsc.dci.sqlcode.main.sqlTask" };
		KBSQL sql = new KBSQL();
		sql.parseALLMethods(data);
	}

	public static enum METHOD {
		HasParam, ParamTypes, RETURN, CLASS, PackageClass;
	}

	/**
	 * @param path
	 *            from [KB Project].[copy Qualified name]
	 * @param data
	 * @return parse path to [MDBT].packagePath+className:
	 * @example input [KB Project]:
	 *          /KanBan/src/com/dsc/dci/sqlcode/main/sqlTask.java output [MDBT]:
	 *          com.dsc.dci.sqlcode.main.sqlTask
	 */
	public String[] transPackageClass(String[] data) {
		String[] result = new String[data.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = data[i].replaceAll("/", ".").replace(".KanBan.src.", "").replace(".java", "").trim();
		}
		return result;
	}

	/**
	 * 
	 * @param packageClassPathNames
	 * @return TreeMap<String, HashMap<enum,Object>> method-info HashMap
	 *         <enum,Object> @param hasParam:boolean HashMap
	 *         <enum,Object> @param paramTypes:Class[] HashMap
	 *         <enum,Object> @param return:String HashMap <enum,Object> @param
	 *         packageClass:String HashMap <enum,Object> @param class:String
	 */
	public void parseALLMethods(String[] packageClassPathNames) {
		TreeMap<String, HashMap<METHOD, Object>> map = new TreeMap<String, HashMap<METHOD, Object>>();
		String packageClassName = "";
		for (int j = 0; j < packageClassPathNames.length; j++) {
			packageClassName = packageClassPathNames[j];
			if (packageClassName != null && !"".equals(packageClassName)) {
				System.out.println("*packageClassName:" + packageClassName);
				System.out.println("------------start--------");
				try {
					Class c = Class.forName(packageClassName);
					Method m[] = c.getDeclaredMethods();
					Object obj = c.newInstance();
					HashMap<METHOD, Object> row = null;
					for (int i = 0; i < m.length; i++) {
						row = new HashMap<METHOD, Object>();
						String returnValue = "";
						Class[] paramTypes = m[i].getParameterTypes();
						boolean hasParam = false;
						try {
							if (paramTypes == null || paramTypes.length == 0) {
								returnValue = (String) m[i].invoke(obj, null);
							} else {
								hasParam = true;
								for (int it = 0; it < paramTypes.length; it++) {
									// if(String.class==paramTypes[it]){
									// System.out.println(c.getName()+":"+m[i].getName());
									// returnValue=(String)m[i].invoke(obj,
									// "SqlServer");
									// System.out.println(returnValue);
									// }
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							returnValue = ex.getMessage();
						} finally {
							row.put(METHOD.HasParam, hasParam);
							row.put(METHOD.ParamTypes, paramTypes);
							row.put(METHOD.RETURN, returnValue);
							row.put(METHOD.CLASS, c.getName());
							row.put(METHOD.PackageClass, packageClassName);
							map.put(m[i].getName(), row);
							System.out.println(packageClassName + "_" + m[i].getName() + ":" + hasParam);
							System.out.println("return:\n" + returnValue);
							String pmtypes = "";
							for (Class ccc : paramTypes) {
								pmtypes += ccc.toString() + ";";
							}
							if (pmtypes.length() != 0)
								System.out.println(pmtypes.substring(0, pmtypes.length() - 1));
						}
						System.out.println("=====================");
					}
					System.out.println("******end*********");

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {

				}

			}
		}
		// str2json(map);
		// str2listPython(map);
	}
}
