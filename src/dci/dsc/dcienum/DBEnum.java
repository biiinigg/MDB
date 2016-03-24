package dci.dsc.dcienum;

public class DBEnum {
	public enum Type {
		ORACLE {
			@Override
			public String toString() {
				return "ORACLE";
			}
		},
		MYSQL {
			@Override
			public String toString() {
				return "MYSQL";
			}
		},
		SQLSERVER {
			@Override
			public String toString() {
				return "SQLSERVER";
			}
		}
	}
}
