package it.polito.tdp.rivers.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model();
		River r = model.getAllRivers().get(1);
		System.out.println(model.getFirstMeasurement(r));

	}

}
