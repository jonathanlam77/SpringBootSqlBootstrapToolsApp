package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.StraightLadder;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class StraightLadderRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("step_count		INT(10),")
		.append("weight_capacity	INT(10),")
		.append("rubber_feet		TINYINT(1),")
		.append("CONSTRAINT fk_StraightLadder_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "StraightLadder";
	}
	
    public StraightLadder get(Tool t) {
    	
    	String sql = String.format("SELECT step_count, weight_capacity, rubber_feet FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<StraightLadder> ladders = query(sql, (rs, rowNum) -> {
    													StraightLadder s = new StraightLadder(t);
    													s.setStepCount(rs.getInt("step_count"));
    													s.setWeightCapacity(rs.getInt("weight_capacity"));
    													s.setRubberFeet(rs.getBoolean("rubber_feet"));
										     			return s;
									        		});
        
    	return (ladders.size() > 0) ? ladders.get(0) : null;
    }
	
	
    public StraightLadder add(StraightLadder s) {
    	
    	s = (StraightLadder)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, step_count, weight_capacity, rubber_feet) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getStepCount(),
    								s.getWeightCapacity(),
    								s.isRubberFeet() ? "1":"0"
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }

}
