package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Tool;


@Repository
@DependsOn(value = { "reservationRepository" })
public class ToolRepository extends SqlRepository {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number 	INT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,")
    			.append("type			VARCHAR(64) NOT NULL,")
    			.append("sub_type		VARCHAR(64) NOT NULL,")
    			.append("sub_option		VARCHAR(64) NOT NULL,")
    			.append("purchase_price	FLOAT(10,2) NOT NULL,")
    			.append("power_source	VARCHAR(64) NOT NULL,")
    			.append("width_diameter	FLOAT(10,2) NOT NULL,")
    			.append("length			FLOAT(10,2) NOT NULL,")
    			.append("manufacturer	VARCHAR(256) NOT NULL,")
    			.append("material		VARCHAR(64) NOT NULL")
    			.append(");")
    			.toString();
	}

	@Override
	protected String getTableName() {
		return "Tool";
	}
	
	protected String getAllAttrForSqlQuery() {
		return "tool_number, type, sub_type, sub_option, purchase_price, power_source, width_diameter, length, manufacturer, material";
	}
	
    public Tool get(int toolNumber) {
    	
    	String sql = String.format("SELECT " + getAllAttrForSqlQuery() + " FROM %s WHERE tool_number=%s", getTableName(), toolNumber);

        List<Tool> tools = query(sql, (rs, rowNum) -> {	
        									Tool t = new Tool();
        									t.setResultSet(rs);
        									return t;
      									});
     		
        return (tools.size() > 0) ? tools.get(0) : null;
    }
	
	public Tool add(Tool t) {
		
    	String sql = String.format("INSERT INTO %s (type, sub_type, sub_option, purchase_price, power_source, width_diameter, length, manufacturer, material) " +
				"VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)",
				getTableName(),
				toSqlStr(t.getType()),
				toSqlStr(t.getSubType()),
				toSqlStr(t.getSubOption()),
				t.getPurchasePrice(),
				toSqlStr(t.getPowerSource()),
				t.getWidthDiameter(),
				t.getLength(),
				toSqlStr(t.getManufacturer()),
				toSqlStr(t.getMaterial()));
    	
		execute(sql.toString()); 
		
		List<Tool> tools = query("SELECT LAST_INSERT_ID()",
								(rs, rowNum) -> {
									t.setToolNumber(rs.getInt("LAST_INSERT_ID()"));
									return t;
								});
		
        return (tools.size() > 0) ? tools.get(0) : null;
	}
		    
	    
}

