package com.toolsapp.repository;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Accessory;
import com.toolsapp.model.PowerTool;

@Repository
@DependsOn(value = { "powertoolRepository"})
public class AccessoryRepository extends SqlRepository {

	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number 	INT(8),")
    			.append("description 	VARCHAR(64) NOT NULL,")
    			.append("quantity	INT(10) NOT NULL,")
    			.append("PRIMARY KEY (tool_number, description),")
    			.append("CONSTRAINT fk_Accessory_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}

	@Override
	protected String getTableName() {
		return "Accessory";
	}
	
	public List<Accessory> get(int toolNumber) {
		
    	String sql = String.format("SELECT tool_number, description, quantity FROM %s WHERE tool_number=%s", getTableName(), toolNumber);

        List<Accessory> accessories = query(sql, (rs, rowNum) -> {	
		        										Accessory a = new Accessory();
			        									a.setResultSet(rs);
			        									return a;
			      									});
     		
		return accessories;
	}

    public void add(PowerTool t) {
    	
    	List<Accessory> accessories = t.getAccessories();
    	if (accessories == null) return;
    	
    	for (Accessory a : accessories) {
        	String sql = String.format("INSERT INTO %s (tool_number, description, quantity) " +
        								"VALUES (%s, %s, %s)",
        								getTableName(),
        								t.getToolNumber(),
        								toSqlStr(a.getDescription()),
        								a.getQuantity());

        	execute(sql.toString()); 
    	}
    }
	
}

