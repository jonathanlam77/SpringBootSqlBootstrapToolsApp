package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Generator;
import com.toolsapp.model.PowerTool;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class GeneratorRepository extends SqlRepository {

    @Autowired	
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("power_rating	FLOAT(10,2) NOT NULL,")
    			.append("CONSTRAINT fk_Generator_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "Generator";
	}
	
	
    public Generator get(PowerTool t) {
    	
    	String sql = String.format("SELECT power_rating FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Generator> tools = query(sql, (rs, rowNum) -> {
    									Generator s = new Generator(t);
    									s.setPowerRating(rs.getDouble("power_rating"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public Generator add(Generator s) {
    	
    	s = (Generator)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, power_rating) " +
    								"VALUES (%s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getPowerRating());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
    
	
}
