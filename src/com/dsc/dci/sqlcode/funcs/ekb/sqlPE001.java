package com.dsc.dci.sqlcode.funcs.ekb;

public class sqlPE001 {
	public String getSqlColumns() {
		return "select a.*, "
				+ "case when b.lang_value is null or b.lang_value = '' then a.col_lang_key else b.lang_value end col_text "
				+ "from Sql_Column_Info a left join (select * from Multi_Language where lang = ?) b on a.col_lang_key = b.lang_key "
				+ "where sql_id = ? order by col_seq";

	}
	public String getRoleGrantKBID(){
		return " SELECT func_id "
			     + " FROM  Role_Rule_Info "
			     + " WHERE conn_id = ? AND (role_id = (select role_id from Group_Role_Mapping where group_id= (Select group_id from User_Info where user_id=? ))) AND ( func_id<>'PE001' and func_id<>'PE000Config') ";//func_id LIKE 'PE%' and
		
	}
	public String getlangData(){
		return " SELECT lang_value "
				+" FROM Multi_Language_STD "
				+" WHERE lang_key = ? AND lang = ? ";
	}

	public String getSqlCommand() {
		return "select a.*, b.filter_col,b.filter_condi,b.filter_value,b.sort_col,b.sort_type,b.page_size, "
				+ "d.grid_font_size,d.grid_font_color,d.grid_row_height,d.grid_row_color,d.popup_width,"
				+ " case when c.cross_type is not null and c.conn_id is not null and "
				+ " c.sql_context is not null then 1 else 0 "
				+ "        end is_cross, c.conn_id cross_conn_id, c.cross_type, c.group_by cross_group_by, "
				+ "       c.order_by cross_order_by, c.sql_context cross_sql_context, c.join_key_set1, "
				+ "       c.join_key_set2 from (select * from Sql_Info where sql_id = ?)a left join (select * from Sql_Condition "
				+ "              where user_id = ? and sql_id = ?) b on a.sql_id = b.sql_id "
				+ "  left join (select * from Sql_Cross_Database "
				+ "              where sql_id = ?) c on a.sql_id = c.sql_id "
				+ "  left join (select * from Sql_Format "
				+ "              where user_id = ? and sql_id = ?) d on a.sql_id = d.sql_id";

	}

	public String getSqlData(String lang){
		String sql = null;
		 sql = " Declare @date as nvarchar(max)     "+
				 "       Declare @sql as nvarchar(max)     "+
				 "            With my_date as (     "+
				 "            select distinct out_date from Process_Exception_History p    "+
				 "            join Process_Exception b on p.report_id=b.report_id        "+
				 "            where out_date > dateadd(m,-6,getdate()) and conn_id=? and is_work='Y'  %grantCon1% "+
				 "            )     "+
				 "            select  "+
				 "    @date=case when (Select Count(*) From my_date) > 0 then left(report_date,len(report_date)-1) else '[1900-01-01]' end  "+
				 "       from (         "+
				 "            select (     "+
				 "            select '['+  convert(char(10),out_date)+  ']'  + ','     "+
				 "            from my_date     "+
				 "            order by out_date     "+
				 "            for XML PATH('') ) as report_date ) T     "+
				 "            set @sql =     "+
				 "            'select b.item_id,b.process_name,b.report_id,c.lang_value as report_name,b.dept_name,b.owner' "+
				 "      if(@date != '[1900-01-01]')                       "+
				 "      begin                       "+
				 "        set @sql=@sql+','+  @date              "+
				 "      end                        "+
				 "      set @sql=@sql+ ' from                         "+
				 "            (     "+
				 "            select  report_id,exc_count,out_date from Process_Exception_History "+
				 "            where conn_id=''%conn_id%''  %grantCon2% "+
				 "      ) p             "+
				 "            PIVOT     "+
				 "            (sum(exc_count)     "+
				 "            FOR out_date in      "+
				 "            (' + @date+  ' )     "+
				 "            ) AS pvt     "+
				 "            join Process_Exception b on pvt.report_id=b.report_id      "+
				 " 			  join Multi_Language_CUS c on lang_key=b.report_id 	"+
				 "            where is_work=''Y'' and lang=''"+lang+"'' "+
				 "            order by b.sequ_num'     "+
				 "            execute sp_executesql @sql ";
		return sql;
	}

	public String getSqlRelation() {
		return "select a.*,b.lang_key target_sql_name_id,c.col_ori_name  from Sql_Column_Relation a left join Menu b on a.target_sql_id = b.func_id join Sql_Column_Info c on a.target_sql_id = c.sql_id and a.target_col_id = c.col_id where a.sql_id = ? order by a.seq,a.col_id";
	}
	public String getConn_id(){
		return "SELECT conn_id FROM Group_Funcs where func_id=? and user_id=?";
	}

	public String getFuncInfo() {
		return "select * from (select func_id, max(can_edit) can_edit "
				+ "           from (select func_id, can_edit from Role_Rule_Info "
				+ "                   where role_id in (select role_id "
				+ "                                      from Group_Role_Mapping "
				+ "                                     where group_id = (select group_id "
				+ "                                                         from User_Info "
				+ "                                                        where user_id = ?)) and "
				+ "                         conn_id = ? and func_id = ? union all "
				+ "                  select func_id, can_edit from User_Rule_Info "
				+ "                   where user_id = ? and conn_id = ? and func_id = ?) data "
				+ "          group by func_id) a join (select * from Menu "
				+ "         where visible = '1') b on a.func_id = b.func_id";

	}
}
