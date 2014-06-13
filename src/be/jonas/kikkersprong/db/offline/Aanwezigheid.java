package be.jonas.kikkersprong.db.offline;

public class Aanwezigheid {

	private String id;
	private String datum;
	private String uren;

	
	public Aanwezigheid(String id, String datum, String uren){
		setId(id);
		setDatum(datum);
		setUren(uren);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getUren() {
		return uren;
	}
	public void setUren(String uren) {
		this.uren = uren;
	}
	
	
}
