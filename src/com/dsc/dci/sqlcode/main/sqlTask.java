package com.dsc.dci.sqlcode.main;

public class sqlTask {
	public String getAllConns() {
		String sql = null;
		sql = "select * from Conn_Info where visible = '1' order by conn_id";
		return sql;
	}
	
	//日清日結
	public String peTableExist(){
		return " SELECT count(*) 'count' FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'Process_Exception' OR TABLE_NAME =  'Process_Exception_History' ";
	}
	public String dbtype(){
		return "SELECT db_type FROM Conn_Info WHERE (conn_id = 'CSYS')";
	}
	public String schedulingInfo() {
		return " select report_id,schedule_time from Process_Exception where is_work='Y' order by schedule_time asc ";
	}
	public String delPEData(){
		return " declare @ThreeMonthAgo date " +
		" SET @ThreeMonthAgo = CONVERT(VARCHAR, dateadd(month,-3,getdate()) , 120 ); "+
		" delete from Process_Exception_History where @ThreeMonthAgo > out_date " ;
	}
	public String getPEConnid(){
		return "SELECT distinct conn_id "
				+ " FROM Role_Rule_Info "
				+ " WHERE  (conn_id <> 'CSYS') and func_id=? and conn_id in (select distinct conn_id FROM Role_Rule_Info WHERE func_id='PE001' ) ";
	}

	public String updatePEHistory() {
		return "IF NOT EXISTS ( SELECT TOP 1 1 FROM  Process_Exception_History  WHERE conn_id = ? and report_id=? and out_date=?) "
				+ " INSERT  Process_Exception_History (report_id, out_date, exc_count, modi_date, conn_id) VALUES (?,?,?,?,?) "
				+ " ELSE UPDATE  Process_Exception_History SET report_id=?, out_date=?, exc_count=?, modi_date=?, conn_id=?  WHERE conn_id = ? and report_id=? and out_date=?";
	}
}
