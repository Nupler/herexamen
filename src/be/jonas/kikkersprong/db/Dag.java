package be.jonas.kikkersprong.db;

public class Dag {

	private String datum;
	private double uren;
	private int factuurId;

	public int getFactuurId() {
		return factuurId;
	}

	public void setFactuurId(int factuurId) {
		this.factuurId = factuurId;
	}

	public Dag(String datum, double uren,int factuurId) {
		setDatum(datum);
		setUren(uren);
		setFactuurId(factuurId);
		
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public double getUren() {
		return uren;
	}

	public void setUren(double uren) {
		this.uren = uren;
	}

}
