package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Ratchet;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class RatchetRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("sae_size		FLOAT(10,2),")
		.append("deep_socket		TINYINT(1),")
		.append("drive_size		FLOAT(10,2),")
		.append("CONSTRAINT fk_Ratchet_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Ratchet";
	}
	
	
    public Ratchet get(Tool t) {
    	
    	String sql = String.format("SELECT sae_size, deep_socket, drive_size FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Ratchet> l = query(sql, (rs, rowNum) -> {
    													Ratchet s = new Ratchet(t);
										        		s.setSaeSize(rs.getDouble("sae_size"));
										        		s.setDeepSocket(rs.getBoolean("deep_socket"));
										        		s.setDriveSize(rs.getDouble("drive_size"));
										        		return s;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
    
    
    public Ratchet add(Ratchet s) {
    	
    	s = (Ratchet)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, sae_size, deep_socket, drive_size) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getSaeSize(),
    								s.isDeepSocket(),
    								s.getDriveSize());
    	
    	execute(sql.toString()); 

    	return s;
    }
	

}
