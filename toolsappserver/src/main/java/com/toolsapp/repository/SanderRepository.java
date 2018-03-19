package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.PowerTool;
import com.toolsapp.model.Sander;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class SanderRepository extends SqlRepository {

    @Autowired
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("dust_bag		TINYINT(1) NOT NULL,")
    			.append("CONSTRAINT fk_Sander_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "Sander";
	}
	
	
    public Sander get(PowerTool t) {
    	
    	String sql = String.format("SELECT dust_bag FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Sander> tools = query(sql, (rs, rowNum) -> {
    									Sander s = new Sander(t);
										s.setDustBag(rs.getBoolean("dust_bag"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public Sander add(Sander s) {
    	
    	s = (Sander)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, dust_bag) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.isDustBag());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
    
}
