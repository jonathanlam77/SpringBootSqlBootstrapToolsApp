package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Drill;
import com.toolsapp.model.PowerTool;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class DrillRepository extends SqlRepository {

    @Autowired
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("adjustable_clutch	TINYINT(1) NOT NULL,")
    			.append("min_torque_rating		INT(10)	NOT NULL,")
    			.append("max_torque_rating		INT(10) NOT NULL,")
    			.append("CONSTRAINT fk_Drill_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "Drill";
	}
	
    public Drill get(PowerTool t) {
    	
    	String sql = String.format("SELECT adjustable_clutch, min_torque_rating, max_torque_rating FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Drill> tools = query(sql, (rs, rowNum) -> {
    									Drill s = new Drill(t);
										s.setAdjustableClutch(rs.getBoolean("adjustable_clutch"));
										s.setMinTorqueRating(rs.getInt("min_torque_rating"));
										s.setMaxTorqueRating(rs.getInt("max_torque_rating"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public Drill add(Drill s) {
    	
    	s = (Drill)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, adjustable_clutch, min_torque_rating, max_torque_rating) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.isAdjustableClutch(),
    								s.getMinTorqueRating(),
    								s.getMaxTorqueRating());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
}
