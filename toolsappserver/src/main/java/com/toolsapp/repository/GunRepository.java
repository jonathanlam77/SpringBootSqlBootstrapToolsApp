package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Gun;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class GunRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("capacity		int(10),")
		.append("gauge_rating	FLOAT(10,2),")
		.append("CONSTRAINT fk_Gun_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Gun";
	}
	
	
    public Gun get(Tool t) {
    	
    	String sql = String.format("SELECT capacity, gauge_rating FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Gun> l = query(sql, (rs, rowNum) -> {
    													Gun s = new Gun(t);
										        		s.setCapacity(rs.getInt("capacity"));
										        		s.setGaugeRating(rs.getDouble("gauge_rating"));
										     			return s;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
    
    
    public Gun add(Gun s) {
    	
    	s = (Gun)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, capacity, gauge_rating) " +
    								"VALUES (%s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getCapacity(),
    								s.getGaugeRating());
    	
    	execute(sql.toString()); 

    	return s;
    }
	

}
