package com.toolsapp.repository;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Battery;

@Repository
@DependsOn(value = { "powertoolRepository"})
public class BatteryRepository extends SqlRepository {

	private static final String TABLE_NAME = "Battery";
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number  	INT(8),")
    			.append("battery_type 	VARCHAR(10),")
    			.append("volt_rating	FLOAT(10,2) NOT NULL,")
    			.append("quantity		INT(10) NOT NULL,")
    			.append("PRIMARY KEY (tool_number, battery_type),")
    			.append("CONSTRAINT fk_Battery_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) 	REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	public static String getAddBatterySql(Battery b) {
    	String batterySql = String.format("INSERT INTO %s (tool_number, battery_type, volt_rating, quantity) " +
				"VALUES (%s, '%s', %s, %s)",
				TABLE_NAME,
				b.getToolNumber(),
				b.getBatteryType(),
				b.getVoltRating(),
				b.getQuantity());
    	
    	return batterySql;
		
	}
	
}
