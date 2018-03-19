package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Screwdriver;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class ScrewdriverRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("screw_size		FLOAT(10,2),")
		.append("CONSTRAINT fk_Screwdriver_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Screwdriver";
	}
	
    public Screwdriver get(Tool t) {
    	
    	String sql = String.format("SELECT screw_size FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Screwdriver> screwdrivers = query(sql, (rs, rowNum) -> {
										        		Screwdriver s = new Screwdriver(t);
										        		s.setScrewSize(rs.getDouble("screw_size"));
										     			return s;
									        		});
        
    	return (screwdrivers.size() > 0) ? screwdrivers.get(0) : null;
    }
    
    
    public Screwdriver add(Screwdriver s) {
    	
    	s = (Screwdriver)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, screw_size) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getScrewSize());
    	
    	execute(sql.toString()); 

    	return s;
    }
	
}

