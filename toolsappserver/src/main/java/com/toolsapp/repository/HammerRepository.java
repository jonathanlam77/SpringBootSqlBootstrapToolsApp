package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Hammer;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class HammerRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("anti_vibration		TINYINT(1),")
		.append("CONSTRAINT fk_Hammer_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Hammer";
	}
	
	
    public Hammer get(Tool t) {
    	
    	String sql = String.format("SELECT anti_vibration FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Hammer> l = query(sql, (rs, rowNum) -> {
    													Hammer s = new Hammer(t);
										        		s.setAntiVibration(rs.getBoolean("anti_vibration"));
										     			return s;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
    
    
    public Hammer add(Hammer s) {
    	
    	s = (Hammer)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, anti_vibration) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.isAntiVibration());
    	
    	execute(sql.toString()); 

    	return s;
    }
	

}
