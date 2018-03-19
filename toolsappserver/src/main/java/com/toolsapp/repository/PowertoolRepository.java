package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Battery;
import com.toolsapp.model.PowerTool;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class PowertoolRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;	    
    
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("amp_rating		FLOAT(6,2) 	NOT NULL,")
    			.append("volt_rating	FLOAT(6,2)	NOT NULL,")
    			.append("max_rpm_rating	INT(6)		NOT NULL,")
    			.append("min_rpm_rating	INT(6)		NOT NULL,")
    			.append("CONSTRAINT fk_PowerTool_tool_number_Tool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
    			.append(");")
    			.toString();
	}

	@Override
	protected String getTableName() {
		return "PowerTool";
	}
		
    public PowerTool get(Tool t) {
    	
    	String sql = String.format("SELECT p.tool_number as tool_number, amp_rating, p.volt_rating, max_rpm_rating, min_rpm_rating, b.tool_number, battery_type, b.volt_rating, quantity FROM %s AS p LEFT OUTER JOIN Battery AS b ON p.tool_number = b.tool_number WHERE p.tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<PowerTool> tools = query(sql, (rs, rowNum) -> {
												PowerTool p = new PowerTool(t);
												p.setAmpRating(rs.getDouble("amp_rating"));
												p.setVoltRating(rs.getDouble("p.volt_rating"));
												p.setMaxRpmRating(rs.getInt("max_rpm_rating"));
												p.setMinRpmRating(rs.getInt("min_rpm_rating"));	
	        									
	        									String batteryType = rs.getString("battery_type");
	        									if (batteryType != null) {
	        										Battery b = new Battery();
	        										b.setBatteryType(batteryType);
	        										b.setQuantity(rs.getInt("quantity"));
	        										b.setVoltRating(rs.getDouble("b.volt_rating"));
	        										p.setBattery(b);
	        									}
												
								     			return p;
							        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }
    
    public PowerTool add(PowerTool p) {
    	
    	p = (PowerTool)toolRepository.add(p);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, amp_rating, volt_rating, max_rpm_rating, min_rpm_rating) " +
    								"VALUES (%s, %s, %s, %s, %s)",
    								getTableName(),
    								p.getToolNumber(),
    								p.getAmpRating(),
    								p.getVoltRating(),
    								p.getMaxRpmRating(),
    								p.getMinRpmRating());
    	
    	execute(sql.toString()); 

    	Battery b = p.getBattery();
    	if (b != null) {
    		b.setToolNumber(p.getToolNumber());
        	String batterySql = BatteryRepository.getAddBatterySql(b);
        	execute(batterySql); 
    	}
    	
    	return p;
    }
    
    
}



