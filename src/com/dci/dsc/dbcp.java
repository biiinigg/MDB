package com.dci.dsc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import dci.dsc.dcienum.DBEnum;
import dci.dsc.model.ConnInfo;

public class dbcp {
	private static final String MYSQL = DBEnum.Type.MYSQL.toString();
	private static final String ORACLE = DBEnum.Type.ORACLE.toString();
	private static final String SQLSERVER = DBEnum.Type.SQLSERVER.toString();
	public static DataSource mysqlDataSource;
	public static DataSource oracleDataSource;
	public static DataSource sqlserverDataSource;
	private static String dbtype;
	public dbcp() {
	}
	public static void init(String _type){
		dbtype = _type;
	}
	public static String getUrl(String dbtype, String addr, String port, String dbname) {
		String url = null;

		if (dbtype .equals(SQLSERVER)) {
			url = "jdbc:sqlserver://" + addr + ":" + port + ";DatabaseName=" + dbname;
		} else if (dbtype .equals(ORACLE)) {
			url = "jdbc:oracle:thin:@" + addr + ":" + port + ":" + dbname;
		} else if (dbtype  .equals( MYSQL)) {
			url = "jdbc:oracle:thin:@" + addr + ":" + port + ":" + dbname;
		}
		return url;
	}

	private static String getDriveName() {
		String driverName = null;
		if (dbtype == SQLSERVER) {
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} else if (dbtype == ORACLE) {
			driverName = "oracle.jdbc.driver.OracleDriver";
		} else if (dbtype == MYSQL) {
			driverName = "oracle.jdbc.driver.OracleDriver";
		}
		return driverName;
	}

	public static DataSource createConnectionPool(ConnInfo infos) throws Exception {
		DataSource ds = null;
		Connection conn = null;
		try {
			if (infos != null) {
				Properties dsProperties = new Properties();
				infos.initProperties();
				dsProperties.setProperty("url", getUrl(infos.type(), infos.ip(), infos.port(), infos.name()));
				dsProperties.setProperty("driverClassName", getDriveName());
				dsProperties.setProperty("username", infos.ac());
				dsProperties.setProperty("password", infos.pw());
				dsProperties.setProperty("maxActive", infos.maxActive());
				dsProperties.setProperty("minIdle", infos.minIdle());
				dsProperties.setProperty("maxIdle", infos.maxIdle());
				dsProperties.setProperty("maxWait", infos.maxWait());
				dsProperties.setProperty("RemoveAbandoned", infos.RemoveAbandoned());
				dsProperties.setProperty("RemoveAbandonedTimeout", infos.RemoveAbandonedTimeout());

				ds = BasicDataSourceFactory.createDataSource(dsProperties);
				conn = ds.getConnection();
				System.out.println(infos.cname() + " connected!");
			}
		} catch (Exception ex) {
			ds = null;
			throw new Exception(ex.getClass().getName()+" :\n " + infos.cname()+ " connect fail \n" +ex.getMessage());
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {

			}
		}

		return ds;
	}
	public static boolean isDataSourceExist() {
		boolean exist = false;
		if (dbtype == MYSQL) {
			exist = mysqlDataSource != null;
		} else if (dbtype == ORACLE) {
			exist = oracleDataSource != null;
		}else if (dbtype == SQLSERVER) {
			exist = sqlserverDataSource != null;
		}
		return exist;
	}

	public static void createDataSource(ConnInfo DBInfo)throws Exception {
		if(!dbcp.isDataSourceExist()){
			try {
				if (dbtype == MYSQL) {
					mysqlDataSource = createConnectionPool(DBInfo);
				} else if (dbtype == ORACLE) {
					oracleDataSource = createConnectionPool(DBInfo);
				} else if (dbtype == SQLSERVER) {
					sqlserverDataSource = createConnectionPool(DBInfo);
				} else {
					System.err.println("DataSource: Connection type setting error " + dbtype.toString());
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
				throw new Exception(ex.getClass().getName()+" :\n "+ex.getMessage());
			}
		}
	}

	public static Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			if (dbtype == MYSQL) {
				conn = mysqlDataSource.getConnection();
			} else if (dbtype == ORACLE) {
				conn = oracleDataSource.getConnection();
			} else if (dbtype == SQLSERVER) {
				conn = sqlserverDataSource.getConnection();
			}
		} catch (SQLException ex) {
			//ex.printStackTrace();
			throw new Exception(ex.getClass().getName()+" :\n "+ex.getMessage());
		}
		return conn;
	}

	public static void releaseDataSource(Connection conn, PreparedStatement ps,ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
