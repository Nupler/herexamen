package be.jonas.kikkersprong.db;


public class Kind {

	private String naam;
	private String voornaam;
	private int id;

	
	public Kind(int id, String voornaam, String naam){
		setId(id);
		setVoornaam(voornaam);
		setNaam(naam);
		
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
