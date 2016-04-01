package com.dci;
import java.util.HashMap;
import java.util.Map;

public class KBClassLoader extends ClassLoader {
	private Map<String, Class<?>> classesMap = new HashMap<String, Class<?>>();
	public KBClassLoader() {
	}
	public KBClassLoader(ClassLoader parent) {
		super(parent);
	}
	public void setClassesMap(){
		
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// load from parent
		Class<?> result = findLoadedClass(name);
		if (result != null) {
			return result;
		}
		try {
			result = findSystemClass(name);
		} catch (Exception e) {
			// Ignore these
		}
		if (result != null) {
			return result;
		}
		result = classesMap.get(name);
		if (result == null) {
			throw new ClassNotFoundException(name);
		}
		return result;
	}
}
