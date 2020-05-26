package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map <Integer, River> riverIdMap;
	private Simulation sim;
	
	public Model() {
		this.dao = new RiversDAO();
		riverIdMap = new HashMap <>();
		for (River r : dao.getAllRivers()) {
			riverIdMap.put(r.getId(), r);
		}
	}
	
	public Map <Integer, River> getAllRivers() {
		return this.riverIdMap;
	}
	
	public LocalDate getFirstMeasurement(River r) {
		return this.dao.getFirstMeasurement(r);
	}
	public LocalDate getLastMeasurement(River r) {
		return this.dao.getLastMeasurement(r);
	}
	public int getNumberMeasurements (River r) {
		return this.dao.getNumberMeasurements(r);
	}
	public double getAverageMeasurement (River r) {
		return this.dao.getAverageMeasurement(r);
	}
	public Map<LocalDate, Flow> getAllFlowsForRiver(River river) {
		return this.dao.getAllFlowsForRiver(river, riverIdMap);
	}
	public void simula(River r, double k) {
		this.sim = new Simulation(r, k);
		this.sim.run();
	}
	public double getMedia() {
		return this.sim.getMediaCapacita();
	}
	public int getNumeroGiorni () {
		return this.sim.getGiorniNonErogato();
	}

}
