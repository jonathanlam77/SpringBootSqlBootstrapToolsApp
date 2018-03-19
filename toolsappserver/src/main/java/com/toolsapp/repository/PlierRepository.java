package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Pliers;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class PlierRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("adjustable		TINYINT(1),")
		.append("CONSTRAINT fk_Plier_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Plier";
	}
	
	
    public Pliers get(Tool t) {
    	
    	String sql = String.format("SELECT adjustable FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Pliers> l = query(sql, (rs, rowNum) -> {
    													Pliers s = new Pliers(t);
										        		s.setAdjustable(rs.getBoolean("adjustable"));
										     			return s;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
    
    
    public Pliers add(Pliers s) {
    	
    	s = (Pliers)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, adjustable) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.isAdjustable());
    	
    	execute(sql.toString()); 

    	return s;
    }
	

}
