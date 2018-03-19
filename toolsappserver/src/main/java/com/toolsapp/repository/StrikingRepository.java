package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Striking;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class StrikingRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("handle_material	VARCHAR(64),")
		.append("head_weight		FLOAT(10,2),")
		.append("CONSTRAINT fk_Striking_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Striking";
	}

    public Striking get(Tool t) {
    	
    	String sql = String.format("SELECT handle_material, head_weight FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Striking> l = query(sql, (rs, rowNum) -> {
    													Striking p = new Striking(t);
    													p.setHandleMaterial(rs.getString("handle_material"));
    													p.setHeadWeight(rs.getDouble("head_weight"));
										     			return p;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
	
	
    public Striking add(Striking s) {
    	
    	s = (Striking)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, handle_material, head_weight) " +
    								"VALUES (%s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								toSqlStr(s.getHandleMaterial()),
    								s.getHeadWeight()
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }

}
