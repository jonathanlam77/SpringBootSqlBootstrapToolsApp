package com.toolsapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.toolsapp.model.Clerk;
import com.toolsapp.model.ClerkReport;

@Repository
public class ClerkRepository extends SqlRepository {

	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("user_name 			VARCHAR(64) PRIMARY KEY,")
		.append("email				tinytext NOT NULL,")
		.append("password			VARCHAR(64) NOT NULL,")
		.append("first_name			VARCHAR(64) NOT NULL,")
		.append("mid_name			VARCHAR(64),")
		.append("last_name			VARCHAR(64) NOT NULL,")
		.append("date_of_hire		DATE NOT NULL,")
		.append("employee_number	VARCHAR(64) NOT NULL")
		.append(");")
		.toString();
	}

	@Override
	protected String getTableName() {
		return "Clerk";
	}
	
    public Clerk get(String username) {
    	
    	String sql = String.format("SELECT user_name, email, password, first_name, mid_name, last_name, date_of_hire, employee_number FROM %s WHERE user_name=%s", getTableName(), toSqlStr(username));

        List<Clerk> numbers = query(sql, (rs, rowNum) ->getClerk(rs));
        
        if (numbers.size() > 0) {
            return numbers.get(0);        	
        } else {
        	return null;
        }

    }

    public void add(Clerk c) {
    	
    	String sql = String.format("INSERT INTO %s (user_name, email, password, first_name, mid_name, last_name, date_of_hire, employee_number) " +
    								"VALUES (%s,%s,%s,%s,%s,%s,%s,%s)",
    								getTableName(),
    								toSqlStr(c.getUsername()),
    								toSqlStr(c.getEmail()),
    								toSqlStr(c.getPassword()),
    								toSqlStr(c.getFirstName()),
    								toSqlStr(c.getMidName()),
    								toSqlStr(c.getLastName()),
    								toSqlStr(c.getDateOfHireSql().toString()),
    								toSqlStr(c.getEmployeeNumber()));

    	execute(sql.toString());    				
    }

    public List<ClerkReport> report() {
    	
    	StringBuffer sql = new StringBuffer()
    							.append("SELECT clerk, numpickups, numdropoffs, total, user_name, email, password, first_name, mid_name, last_name, date_of_hire, employee_number ")
    							.append("FROM ")
    							.append("	( ")
    							.append("	SELECT clerk, numpickups, numdropoffs, (numpickups + numdropoffs) AS total ")
    							.append("	FROM ")
    							.append("		(SELECT clerk, numpickups, COALESCE(numdropoffs, 0) AS numdropoffs ")
    							.append("		FROM ")
    							.append("			(SELECT pickup_clerk AS clerk, count(*) AS numpickups ")
    							.append("			FROM Reservation ")
    							.append("           WHERE MONTH(start_date) =  MONTH(NOW()) AND YEAR(start_date) = YEAR(NOW()) ")
    							.append("			GROUP BY pickup_clerk) AS p ")
    							.append("		NATURAL LEFT OUTER JOIN ")
    							.append("			(SELECT dropoff_clerk AS clerk, count(*) AS numdropoffs ")
    							.append("			FROM Reservation ")
    							.append("           WHERE MONTH(end_date) =  MONTH(NOW()) AND YEAR(end_date) = YEAR(NOW()) ")
    							.append("			GROUP BY dropoff_clerk) AS d ")
    							.append("		 ) AS result ")
    							.append("	ORDER BY total DESC ")
    							.append("	) AS NumPickupsDropOffsTotal ")
    							.append("INNER JOIN Clerk ")
    							.append("On Clerk.user_name = NumPickupsDropOffsTotal.clerk ");
   	
        List<ClerkReport> clerks = query(sql.toString(),
     		(rs, rowNum) -> {
     			Clerk clerk = getClerk(rs);
        
        		ClerkReport cr = new ClerkReport();
        		cr.setClerk(clerk);
        		cr.setNumDropoffs(rs.getInt("numdropoffs"));
     			cr.setNumPickups(rs.getInt("numpickups"));
        		cr.setTotal(rs.getInt("total"));
     			return cr;
     		});
    	
    	return clerks;
    	
    }
    
    private Clerk getClerk(ResultSet rs) throws SQLException {
    	return new Clerk(rs.getString("user_name"), 
						rs.getString("email"), 
						rs.getString("password"), 
						rs.getString("first_name"), 
						rs.getString("mid_name"), 
						rs.getString("last_name"), 
						rs.getDate("date_of_hire"), 
						rs.getString("employee_number")
						);
    }
}
