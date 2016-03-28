package dci.dsc.mainmethods;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import dci.dsc.dcienum.DBEnum;
import dci.dsc.model.ConnInfo;

public class Dom4j {
	private final String MYSQL=DBEnum.Type.MYSQL.toString();
	private final String ORACLE=DBEnum.Type.ORACLE.toString();
	private final String SQLSERVER=DBEnum.Type.SQLSERVER.toString();
	private String url=".//Config.xml";
	private File file=new File(url);
	private ConnInfo datas =null;
	private Document doc=null;
	public Dom4j(){
	}
	public void create(ConnInfo datas){
		this.datas= datas;
		if(!this.file.exists()){
			insert();
		}else{
			update();
		}
		save();
	}
	public void insert(){
		try{
			this.doc = DocumentHelper.createDocument();
			Element root = doc.addElement("DBConfig");
			Element MY =root.addElement(MYSQL);
			MY.setText("");
			Element ORA =root.addElement(ORACLE);
			ORA.setText("");
			Element SQL =root.addElement(SQLSERVER);
			SQL.setText("");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public List<Element> parseXML(){
		List<Element> list=null;
		try{
			SAXReader saxReader = new SAXReader(); 
			this.doc = saxReader.read(this.file);
			Element root=doc.getRootElement();
			list=root.elements();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public HashMap<String ,ConnInfo> read(){
		HashMap<String ,ConnInfo> result =new HashMap<String ,ConnInfo>();
		if(this.file.exists()){
			try{
				List<Element> list=parseXML();
				for(Element child:list){
					boolean con2=!child.getText().trim().equals("");
					if(child.getName().equals(MYSQL)&&con2){
						result.put(MYSQL, new ConnInfo(child.getText()));
					}else if(child.getName().equals(ORACLE)&&con2){
						result.put(ORACLE, new ConnInfo(child.getText()));
					}else if(child.getName().equals(SQLSERVER)&&con2){
						result.put(SQLSERVER, new ConnInfo(child.getText()));
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
	public void update(){
		try{
			SAXReader saxReader = new SAXReader(); 
			this.doc = saxReader.read(this.file);
			Element root=doc.getRootElement();
			List<Element> list=parseXML();
			for(Element child:list){
				if(child.getName().equals(MYSQL)&&this.datas.type().equals(MYSQL)){
					child.setText(this.datas.getRow());
				}else if(child.getName().equals(ORACLE)&&this.datas.type().equals(ORACLE)){
					child.setText(this.datas.getRow());
				}else if(child.getName().equals(SQLSERVER)&&this.datas.type().equals(SQLSERVER)){
					child.setText(this.datas.getRow());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void save(){
		try{
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(this.file), format);
			writer.write(this.doc);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
