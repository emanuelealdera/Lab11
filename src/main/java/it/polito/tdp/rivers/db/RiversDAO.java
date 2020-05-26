package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public LocalDate getFirstMeasurement(River r) {
		String sql = "select min(day) from flow where river = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			LocalDate result =  rs.getDate("min(day)").toLocalDate();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LocalDate getLastMeasurement(River r) {
		String sql = "select max(day) from flow where river = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			LocalDate result =  rs.getDate("max(day)").toLocalDate();
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getNumberMeasurements (River r) {
		String sql = "select count(*) from flow where river=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int res = rs.getInt("count(*)");
			conn.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public double getAverageMeasurement (River r) {
		String sql = "select avg(flow) from flow where river=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getId());
			ResultSet rs = ps.executeQuery();
			rs.next();
			double res = rs.getDouble("avg(flow)");
			conn.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public Map<LocalDate, Flow> getAllFlowsForRiver(River river, Map <Integer, River> rivers) {
		Map <LocalDate, Flow> result = new HashMap <> ();
		String sql = "select * from flow where river = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, river.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.put(rs.getDate("day").toLocalDate(), 
						new Flow(rs.getDate("day").toLocalDate(), rs.getDouble("flow"), rivers.get(rs.getInt("river"))));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
