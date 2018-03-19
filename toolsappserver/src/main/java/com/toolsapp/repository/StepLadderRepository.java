package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.StepLadder;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class StepLadderRepository extends SqlRepository {

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
		.append("pail_shelf		TINYINT(1),")
		.append("CONSTRAINT fk_StepLadder_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "StepLadder";
	}
	
    public StepLadder get(Tool t) {
    	
    	String sql = String.format("SELECT step_count, weight_capacity, pail_shelf FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<StepLadder> stepLadders = query(sql, (rs, rowNum) -> {
    													StepLadder s = new StepLadder(t);
    													s.setStepCount(rs.getInt("step_count"));
    													s.setWeightCapacity(rs.getInt("weight_capacity"));
    													s.setPailShelf(rs.getBoolean("pail_shelf"));
										     			return s;
									        		});
        
    	return (stepLadders.size() > 0) ? stepLadders.get(0) : null;
    }
	
	
    public StepLadder add(StepLadder s) {
    	
    	s = (StepLadder)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, step_count, weight_capacity, pail_shelf) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getStepCount(),
    								s.getWeightCapacity(),
    								s.isPailShelf() ? "1":"0"
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }
	
}

