package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.PowerTool;
import com.toolsapp.model.Saw;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class SawRepository extends SqlRepository {

    @Autowired
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("blade_size		float(10,1) NOT NULL,")
    			.append("CONSTRAINT 	fk_Saw_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "Saw";
	}
	
    public Saw get(PowerTool t) {
    	
    	String sql = String.format("SELECT blade_size FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Saw> tools = query(sql, (rs, rowNum) -> {
    									Saw s = new Saw(t);
										s.setBladeSize(rs.getDouble("blade_size"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public Saw add(Saw s) {
    	
    	s = (Saw)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, blade_size) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getBladeSize());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
    
}
