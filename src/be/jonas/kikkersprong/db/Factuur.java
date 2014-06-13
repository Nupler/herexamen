package be.jonas.kikkersprong.db;

import java.util.ArrayList;

public class Factuur {
	private boolean betaald;
	private int kindId;
	private int factuurId;


	public int getFactuurId() {
		return factuurId;
	}

	public void setFactuurId(int factuurId) {
		this.factuurId = factuurId;
	}

	private String maand;
	private ArrayList<Dag> dagen;

	public Factuur(int kindId, boolean betaald, String maand, int factuurId) {
		setBetaald(betaald);
		setKindId(kindId);
		setMaand(maand);
		setFactuurId(factuurId);
		dagen = new ArrayList<Dag>();
	}

	public int getTotaalBedrag() {
		int totaal = 0;
		for(Dag d : dagen){
			totaal+= d.getUren() *10;
		}
		return totaal;
	}

	public ArrayList<Dag> getDagen() {
		return dagen;
	}

	public void setDagen(ArrayList<Dag> dagen) {
		this.dagen = dagen;
	}



	public boolean isBetaald() {
		return betaald;
	}

	public void setBetaald(boolean betaald) {
		this.betaald = betaald;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getMaand() {
		return maand;
	}

	public void setMaand(String maand) {
		this.maand = maand;
	}
	
	

}
