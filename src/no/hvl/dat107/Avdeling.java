package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avdeling", schema = "dat107oblig3")
public class Avdeling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdelingId;
	private String navn;

	@OneToOne
	@JoinColumn(name = "sjef", referencedColumnName = "ansattId")
	private Ansatt sjef;

	public Avdeling() {}
	public Avdeling(String navn, Ansatt sjef) {
		this.navn = navn;
		this.sjef = sjef;
	}

	//Get/Set
	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Ansatt getSjef() {
		return sjef;
	}

	public void setSjef(Ansatt sjef) {
		this.sjef = sjef;
	}
	
	@Override
	public String toString() {
		return "[AvdelingsID = " + avdelingId + ", Navn = " + navn + ", Sjef = " + sjef.getFornavn() +  "]";
	}

}