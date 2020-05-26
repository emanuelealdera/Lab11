package it.polito.tdp.rivers.model;

public class Simulation {

	private int Q;
	private River river;
	private int giorniNonErogato;
	private Model model;
	private double capacitaTot;
	
	public double getMediaCapacita () {
		double d = this.capacitaTot/this.model.getNumberMeasurements(river);
		return d;
	}
	
	public int getGiorniNonErogato () {
		return this.giorniNonErogato;
	}
	
	public Simulation(River river, double k) {
		this.river = river;
		model = new Model();
		this.Q = (int) ((3600*24*model.getAverageMeasurement(river))*k*30);
	}
	
	public void init() {
		this.giorniNonErogato = 0;
		this.capacitaTot = 0;
	}
	
	public void run() {
		this.init();
		int C = Q/2;
		double fOut =0.8*this.model.getAverageMeasurement(river)*3600*24;
		
		for (Flow f : model.getAllFlowsForRiver(river).values()) {
			if (Math.random()>0.95) {
				if (10*fOut - f.getFlow()*86400 > C) {
					C += 86400*f.getFlow();
					this.giorniNonErogato++;
				}
				else
					C += 86400*f.getFlow() - 10*fOut;
			}

			else {
				if (fOut - f.getFlow()*86400 > C) {
					this.giorniNonErogato++;
					C += 86400*f.getFlow();
				}
				else
					C += 86400*f.getFlow() -fOut;
			}

			if (C>Q) 
				C=Q;

			capacitaTot += C;
		}
	}
	
	
}
