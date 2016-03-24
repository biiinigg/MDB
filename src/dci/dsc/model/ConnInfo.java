package dci.dsc.model;

public class ConnInfo {
	private String cname,type,name,ip,ac,pw,port;
	public ConnInfo(String cname,String type,String name,String ip,String ac,String pw,String port){
		this.cname=cname;
		this.type=type;
		this.name=name;
		this.ip=ip;
		this.ac=ac;
		this.pw=pw;
		this.port=port;
	}
	public ConnInfo(String row){
		String[] data=row.split(";");
		this.cname=data[0];
		this.type=data[1];
		this.name=data[2];
		this.ip=data[3];
		this.ac=data[4];
		this.pw=data[5];
		this.port=data[6];
	}
	public String cname(){
		return this.cname;
	}
	public String type(){
		return this.type;
	}
	public String name(){
		return this.name;
	}
	public String ip(){
		return this.ip;
	}
	public String ac(){
		return this.ac;
	}
	public String pw(){
		return this.pw;
	}
	public String port(){
		return this.port;
	}
	
}
