package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Socket;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class SocketRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("drive_size		FLOAT(10,2),")
		.append("CONSTRAINT fk_Socket_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Socket";
	}
	
    public Socket get(Tool t) {
    	
    	String sql = String.format("SELECT drive_size FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Socket> l = query(sql, (rs, rowNum) -> {
    													Socket s = new Socket(t);
										        		s.setDriveSize(rs.getDouble("drive_size"));
										     			return s;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
    
    public Socket add(Socket s) {
    	
    	s = (Socket)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, drive_size) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getDriveSize());
    	
    	execute(sql.toString()); 

    	return s;
    }

}
