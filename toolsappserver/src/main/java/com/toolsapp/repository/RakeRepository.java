package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Rake;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class RakeRepository extends SqlRepository {

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
		.append("tine_count		INT(10),")
		.append("CONSTRAINT fk_Rake_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Rake";
	}

	
    public Rake get(Tool t) {
    	
    	String sql = String.format("SELECT handle_material, tine_count FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Rake> l = query(sql, (rs, rowNum) -> {
    													Rake p = new Rake(t);
    													p.setHandleMaterial(rs.getString("handle_material"));
    													p.setTineCount(rs.getInt("tine_count")); 
										     			return p;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
	
	
    public Rake add(Rake s) {
    	
    	s = (Rake)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, handle_material, tine_count) " +
    								"VALUES (%s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								toSqlStr(s.getHandleMaterial()),
    								s.getTineCount()
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }
	
}
