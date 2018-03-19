package com.toolsapp.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Clerk;
import com.toolsapp.model.ClerkReport;
import com.toolsapp.model.Reservation;
import com.toolsapp.model.Tool;
import com.toolsapp.model.ToolReport;
import com.toolsapp.model.ToolReservation;

@Repository
@DependsOn(value = { "toolRepository", "reservationRepository" })
public class ReservedToolsRepository extends SqlRepository {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number 	INT(8) NOT NULL,")
    			.append("reservation_id	INT(8) NOT NULL,")
    			.append("UNIQUE uk_tool_reservation(tool_number, reservation_id),")
    			.append("CONSTRAINT fk_ReservedTools_tool_number_Tool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number),")
    			.append("CONSTRAINT fk_ReservedTools_reservation_id_Reservation_reservation_id FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)")
    			.append(");")
    			.toString();
	}

	@Override
	protected String getTableName() {
		return "ReservedTools";
	}

	public void reserve(ToolReservation tr) {
		Reservation reservation = tr.getReservation();
		reservationRepository.add(reservation);
		
		for (Integer toolNumber : tr.getToolIDs()) {
			reserve(toolNumber, reservation.getReservationId());
		}
	}

	public void reserve(int toolNumber, int reservationId) {
		String sql = String.format("INSERT INTO %s (tool_number, reservation_id) VALUES (%s,%s)", 
									getTableName(),
									toSqlInt(toolNumber),
									toSqlInt(reservationId));
		
		execute(sql.toString()); 
	}
	
	public boolean isToolAvailableForRent(int toolNumber) {
		List<Integer> tools = searchToolsAvailableForRent(null, null ,null, null, null, null, toolNumber);
		return (tools != null && tools.size() > 0);
	}
	
	public List<Integer> searchToolsAvailableForRent ( 	String type, 					// all tools, hand, garden, ladder, power
														String subType, 				// saw
														String subOptionKeyword, 		// jig, recipricating
														String powerSource,				// manual, gas, corded, cordless.
														java.sql.Date startDate,
														java.sql.Date endDate,
														int toolNumber) {

		StringBuffer sql =  new StringBuffer()	
				.append("SELECT ")
				.append(toolRepository.getAllAttrForSqlQuery() + ", reservation_id, start_date, end_date")
				.append(" FROM " + toolRepository.getTableName() + " NATURAL LEFT OUTER JOIN " +  getTableName())
				.append(" NATURAL LEFT OUTER JOIN " +  reservationRepository.getTableName())
				.append(" WHERE");

		if (subOptionKeyword == null) {
			subOptionKeyword = "'%'";
		} else {
			subOptionKeyword = "'%" + subOptionKeyword + "%'";
		}
		sql.append(" sub_option LIKE " + subOptionKeyword);

		if (toolNumber != 0) {
			sql.append(" AND tool_number=" + toolNumber);
		}
		
		if (type != null) {
			type = type.toLowerCase();
			if (type.contains("hand") || type.contains("garden") || type.contains("ladder") || type.contains("power")) {
				sql.append(" AND type='" + type + "'");
			}
		}

		if (subType != null) {
			sql.append(" AND sub_type='" + subType + "'");
		}

		if (powerSource != null) {
			sql.append(" AND power_source='" + powerSource + "'");
		}

		log.info("Search tools query: " + sql.toString());
		
		List<Integer> tools = query(sql.toString(),	
				(rs, rowNum) -> {

					boolean toolAvailable = false;

					int reservationId = rs.getInt("reservation_id");
					if (reservationId == 0) {
						toolAvailable = true;
					} else {

						Date reservationStartDate = rs.getDate("start_date");
						Date reservationEndDate = rs.getDate("end_date");

						if (startDate != null && endDate != null) {

							if ((isBetweenInterval(reservationStartDate, reservationEndDate, startDate) || isBetweenInterval(reservationStartDate, reservationEndDate, endDate)) ||
									(reservationStartDate.compareTo(startDate) > 0 && reservationEndDate.compareTo(endDate) < 0)) {
								toolAvailable = false;
							} else {
								toolAvailable = true;
							}


						} else {

							if (startDate != null && isBetweenInterval(reservationStartDate, reservationEndDate, startDate)) {
								toolAvailable = false;
							} else if (endDate != null && isBetweenInterval(reservationStartDate, reservationEndDate, endDate)) {
								toolAvailable = false;
							} else {
								toolAvailable = true;
							}

						}

					}

					if (toolAvailable) {
						return rs.getInt("tool_number");
					} else {
						return null;
					}

				});

		for (int i = 0 ; i < tools.size(); i++) {
			if (tools.get(i) == null) {
				tools.remove(i);
			}
		}

		return tools;
	}

	
    private boolean isBetweenInterval (java.sql.Date start, java.sql.Date end, java.sql.Date d) {
    	if (d.compareTo(start) == 0) {
    		return true;    		
    	}
    	if (d.compareTo(end) == 0) {
    		return true;
    	}
    	if (d.compareTo(start) > 0 && d.compareTo(end) < 0)  {
    		return true;
    	}
    	
    	return false;
    }
    
    public List<ToolReservation> getAll(String customer, boolean pickup, boolean dropoff) {
    	List<Reservation> reservations = reservationRepository.getAll(customer, pickup, dropoff);
    	List<ToolReservation> toolReservations = new ArrayList<>();
    	for (Reservation r : reservations) {
    		ToolReservation tr = new ToolReservation();
    		tr.setReservation(r);
    		tr.setTools(getReservedTools(r.getReservationId()));
    		toolReservations.add(tr);
    	}
    	return toolReservations;
    }
    
    public List<Tool> getReservedTools(int reservationId) {
    	String sql = String.format("SELECT tool_number, reservation_id FROM %s WHERE reservation_id=%s", getTableName(), reservationId);
    	return query(sql, (rs, rowNum) -> toolRepository.get(rs.getInt("reservation_id")));
    }
    
    public List<ToolReport> report() {
    	
    	StringBuffer sql = new StringBuffer()
								.append("SELECT tool_number, sum(rental_profit) as rentalProfit, sum(total_profit) as totalProfit, type, sub_type, sub_option, purchase_price, power_source, width_diameter, length, manufacturer, material ")
								.append("FROM ")
								.append("	(SELECT (purchase_price*0.15) * (end_date-start_date) AS rental_profit, (purchase_price*0.15) * (end_date-start_date) - purchase_price as total_profit, ")
								.append("	tool_number, reservation_id, type, sub_type, sub_option, purchase_price, power_source, width_diameter, length, manufacturer, material, customer, pickup_clerk, dropoff_clerk, credit_number, start_date, end_date ")
								.append("	FROM ")
    							.append("	Tool NATURAL JOIN ReservedTools NATURAL JOIN Reservation) AS rt ")
								.append("GROUP BY tool_number ");
   	
        List<ToolReport> tools = query(sql.toString(),
     		(rs, rowNum) -> {
     			Tool tool = new Tool();
     			tool.setResultSet(rs);
        
     			ToolReport tr = new ToolReport();
     			tr.setTool(tool);
     			tr.setRentalProfit(rs.getDouble("rentalProfit"));
     			tr.setTotalCost(rs.getDouble("purchase_price"));
     			tr.setTotalProfit(rs.getDouble("totalProfit"));
     			tr.setAvailable(isToolAvailableForRent(tool.getToolNumber()));
     			return tr;
     		});
    	
    	return tools;
    	
    }
	
}


