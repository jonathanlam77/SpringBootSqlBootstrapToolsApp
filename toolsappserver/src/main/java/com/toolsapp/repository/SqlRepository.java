package com.toolsapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SqlRepository {

    protected static final Logger log = LoggerFactory.getLogger(SqlRepository.class);
    protected JdbcTemplate jdbc;

    protected static final String DB_NAME = "cs6400_sfa17_team100";	
    protected static String NULL = "NULL";	
    private static final String TABLE_TYPE = "TABLE";	
    private static boolean initialized = false;

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        init();
    }    
    
    protected void init() {
    	
    	if (!initialized) {
    		try {
    			ResultSet resultSet = jdbc.getDataSource().getConnection().getMetaData().getCatalogs();
    	        boolean dbfound = false;
    			while (resultSet.next()) {
    	        	String databaseName = resultSet.getString(1);
    	            if(databaseName.equals(DB_NAME)){
    					log.info("Database " + DB_NAME + "exists.");
    	            	dbfound = true;
    	            	continue;
    	            }
    	        }
    	        resultSet.close();

    			if (!dbfound) {
    				log.info("Database " + DB_NAME + " doesn't exist.  Creating...");
    				jdbc.execute("DROP DATABASE IF EXISTS " + DB_NAME);
    				jdbc.execute("CREATE DATABASE " + DB_NAME);
    			} else {
    				log.info("Database " + DB_NAME + " found.");
    			}

    		} catch (SQLException e) {
    			e.printStackTrace();
    		}  
    		
    		initialized = true;
    	}

		jdbc.execute("USE " + DB_NAME);
		
		try {
			ResultSet resultSet 
				= jdbc.getDataSource().getConnection().getMetaData().getTables(DB_NAME, null,  getTableName(), new String[] {TABLE_TYPE});

			if (!resultSet.next()) {
				log.info("Table " + getTableName() + " doesn't exist.  Creating...");
				execute(getCreateTableSql());
			} else {
				log.info("Table " + getTableName() + " exists.");
			}
	        resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
    
    abstract protected String getCreateTableSql();
    
    abstract protected String getTableName();
    
    protected void execute(String sql) throws DataAccessException {
		jdbc.execute("USE " + DB_NAME);
		jdbc.execute(sql);
    }
    
    protected <T> java.util.List<T>	query(java.lang.String sql, RowMapper<T> rowMapper) {
		jdbc.execute("USE " + DB_NAME);
    	return jdbc.query(sql, rowMapper);
    }

    /**
     * Contains logic to quote a string to be used in a sql insert/query, or return null if the string is null
     */
    protected String toSqlStr(String s) {
    	return (s == null) ? NULL : "'" + s + "'";
    }
    
    protected String toSqlInt(int i) {
    	return (i == 0) ? NULL : Integer.toString(i);
    }	
    	
    
}
