package no.hvl.dat107;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prosjektDeltakere", schema = "dat107oblig3")
public class ProsjektDeltakere {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektDiD;
	
	@ManyToOne
	@JoinColumn(name = "ansattId", referencedColumnName="ansattId")
	private Ansatt ansatt;
	
	@ManyToOne
	@JoinColumn(name = "prosjektId", referencedColumnName = "prosjektId")
	private Prosjekt prosjekt;
	
	
	private String rolle;
	private Integer timer;
	
	
	public ProsjektDeltakere() {}
	public ProsjektDeltakere(Ansatt ansatt, Prosjekt prosjekt, String rolle, Integer timer) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.rolle = rolle;
		this.timer = timer;	
	}
	
	//Get/set
	public Ansatt getAnsatt() {
		return ansatt;
	}
	public void setAnsattId(Ansatt ansatt) {
		this.ansatt = ansatt;
	}
	
	public Prosjekt getProsjekt() {
		return prosjekt;
	}
	public void setProsjekt(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}
	public String getRolle() {
		return rolle;
	}
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
	public Integer getArbeidstimer() {
		return timer;
	}
	public void setArbeidstimer(Integer timer) {
		this.timer = timer;
	}
	public void leggTilTimer(int tillegsTimer){
		this.timer = this.timer + tillegsTimer;
	}
	
	@Override
	public String toString() {
		return "Prosjekt Ansatte: Ansatt = " + ansatt.getBrukernavn() 
				+ ", Prosjekt = " + prosjekt.getNavn() 
				+ ", Rolle = " + rolle 
				+ ", Timer = " + timer + "";
	}
}

