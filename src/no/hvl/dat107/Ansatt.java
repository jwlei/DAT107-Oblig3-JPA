package no.hvl.dat107;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;


@Entity
@Table(name = "ansatt", schema = "dat107oblig3")

public class Ansatt {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToMany(mappedBy = "ansatt")
	private int ansattId;
	
	@Column(unique=true)
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate dato;
	private String stilling;
	private Integer lonn;
	
	@OneToOne
	@JoinColumn(name = "avdelingId", referencedColumnName = "avdelingId")
    private Avdeling avdeling;
	
	@OneToMany
	(mappedBy="ansatt",fetch = FetchType.EAGER)
	private List<ProsjektDeltakere> prosjekt;
	
	public Ansatt() {}
	public Ansatt(String brukernavn, String navn, String etternavn, 
			LocalDate dato, String stilling, Integer lonn, Avdeling avdeling) {
		this.brukernavn = brukernavn;
		this.fornavn = navn;
		this.etternavn = etternavn;
		this.dato = dato;
		this.stilling = stilling;
		this.lonn = lonn;
		this.avdeling = avdeling;
	}
	
	//Get/set etc
	public Avdeling getAvdeling() {
		return avdeling;
	}
	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}
	public int getAnsattID() {
		return ansattId;
	}
	public String getBrukernavn() {
		return brukernavn;
	}
	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}
	public String getFornavn() {
		return fornavn;
	}
	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}
	public String getEtternavn() {
		return etternavn;
	}
	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}
	public LocalDate getDato() {
		return dato;
	}
	public void setDato(LocalDate dato) {
		this.dato = dato;
	}
	public String getStilling() {
		return stilling;
	}
	public void setStilling(String stilling) {
		this.stilling = stilling;
	}
	public Integer getlonn() {
		return lonn;
	}
	public void setlonn(Integer lonn) {
		this.lonn = lonn;
	}
	public List<ProsjektDeltakere> getProsjekt() {
		return prosjekt;
	}
	public void addProsjekt(ProsjektDeltakere pd) {
		prosjekt.add(pd);
	}
	public void setProsjekt(List<ProsjektDeltakere> prosjekt) {
		this.prosjekt = prosjekt;
	}
	
	@Override
	public String toString() {
		return "Ansatt: \nAnsattId = " + ansattId + ", Brukernavn = " + brukernavn 
				+ "\nFornavn = " + fornavn + ", Etternavn = " + etternavn + ", Ansatt Dato = " + dato 
				+ "\nStilling = " + stilling + ", Lønn = " + lonn
				+ "\nAvdelingsInformasjon = " + avdeling.toString() 
				+ "\nProsjektInformasjon = " + prosjekt.toString() + "\n" + "\n";
	}
}
