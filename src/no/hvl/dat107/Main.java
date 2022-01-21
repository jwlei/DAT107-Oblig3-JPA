package no.hvl.dat107;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class Main {

	public static void main(String[] args) {
		System.out.println("OBLIG 3 JPA - Gruppe 15");
		System.out.println("Joachim Leiros, 587728");
		meny();
	}

	public static void meny() {
		AnsattDAO ansattDao = new AnsattDAO();
		AvdelingDAO avdelingDao = new AvdelingDAO();
		ProsjektDeltakereDAO ProsjektDeltakereDAO = new ProsjektDeltakereDAO();
		ProsjektDAO prosjektDao = new ProsjektDAO();
		Scanner sc = new Scanner(System.in);
		
		String userInput, userInput2, brukerNavn, navn, etterNavn, stilling, sjef, rolle = null;
		Integer lonn, avdelingA, ansattId, timer = null;
		LocalDate dato = null;
		boolean running = true;
		boolean erSjef = false;
		Ansatt ansatt = null;
		Avdeling avdeling = null;
		Prosjekt prosjekt = null;
		ProsjektDeltakere prosjektX = null;

		System.out.println("---------------------------------------------------");

		while (running) {
			System.out.println("A = Søke etter ansatt på ansatt-ID");
			System.out.println("B = Søke etter ansatt på brukernavn(initialer)");
			System.out.println("C = Utlisting av alle ansatte");
			System.out.println("D = Oppdatere en ansatt sin stilling");
			System.out.println("E = Oppdatere en ansatt sin lønn");
			System.out.println("F = Legge til en ny ansatt");
			System.out.println("---------------------------------------------------");
			System.out.println("G = Søke etter avdeling på avdeling-id");
			System.out.println("H = Utlisting av alle ansatte på en avdeling inkl. utheving av hvem som er sjef");
			System.out.println("I = Oppdatere hvilken avdeling en ansatt jobber på. Man kan ikke bytte avdeling hvis man er sjef!");
			System.out.println("K = Legge til en ny Avdeling");
			System.out.println("---------------------------------------------------");
			System.out.println("J = Skriv ut prosjekt");
			System.out.println("L = Legge inn et nytt prosjekt");
			System.out.println("M = Registrere prosjektdeltagelse (ansatt med rolle i prosjekt)");
			System.out.println("N = Føre timer for en ansatt på et prosjekt");
			//System.out.println("O = Utskrift av info om prosjekt, inkl. liste av deltagere med rolle og timer, og totalt timetall for prosjektet");
			System.out.println("---------------------------------------------------");
			System.out.println("Q = Avslutt");
			
			userInput = sc.nextLine();

			switch (userInput) {
			case "A":
				System.out.println("Skriv inn Ansatt ID");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedId(Integer.parseInt(userInput));
				
				if (ansatt != null) {	
					System.out.println(ansatt.toString());
				} else {
					System.out.println("Personen med Ansatt-ID du har angitt finnes ikke.");
				}
				break;
			
			case "B":
				System.out.println("Skriv inn brukernavn");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedBrukernavn(userInput);
				
				if (ansatt != null) {
					System.out.println(ansatt.toString());
				} else {
					System.out.println("Personen med brukernavn du har angitt finnes ikke.");
				}
				break;
			
			case "C":
				ansattDao.skrivUtAlle();
				break;
				
			case "D":
				System.out.println("Skriv inn Ansatt ID");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedId(Integer.parseInt(userInput));
				if (ansatt != null) {
					System.out.println("Skriv inn stilling");
					userInput2 = sc.nextLine();
					ansattDao.oppdaterStilling(userInput2, Integer.parseInt(userInput));
					System.out.println("Stillingen er oppdatert for ansatt " + userInput);
				} else {
					System.out.println("Personen med Ansatt-ID du har angitt finnes ikke.");
				}
				break;
				
			case "E":
				System.out.println("Skriv inn Ansatt ID");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedId(Integer.parseInt(userInput));
				
				if (ansatt != null) {
					System.out.println("Skriv inn lønn");
					userInput2 = sc.nextLine();
					ansattDao.oppdaterLonn(Integer.parseInt(userInput2), Integer.parseInt(userInput));
					System.out.println("Lønn er oppdatert for ansatt " + userInput);
				} else {
					System.out.println("Personen med Ansatt ID du har angitt finnes ikke.");
				}
				break;
				
			case "F":
				System.out.println("Skriv inn brukernavn");
				brukerNavn = sc.nextLine();

				System.out.println("Skriv inn fornavn");
				navn = sc.nextLine();

				System.out.println("Skriv inn Etternavn");
				etterNavn = sc.nextLine();

				System.out.println("Skriv inn ansatt dato(ÅÅÅÅ-MM-DD)");
				dato = LocalDate.parse(sc.nextLine());

				System.out.println("Skriv inn stilling");
				stilling = sc.nextLine();

				System.out.println("Skriv inn lønn");
				lonn = Integer.parseInt(sc.nextLine());

				System.out.println("Skriv inn avdelingsID");
				avdelingA = Integer.parseInt(sc.nextLine());
				avdeling = avdelingDao.finnAvdelingMedId(avdelingA);
				Ansatt f = new Ansatt(brukerNavn, navn, etterNavn, dato, stilling, lonn, avdeling);
				ansattDao.leggTil(f);
				System.out.println(brukerNavn + " er lagt til med ansatt ID " + f.getAnsattID());
				break;
				
			// -- Iterasjon 
			// -------------------------------------------------------
				
			case "G":
				System.out.println("Skriv inn Avdelings ID");
			
				userInput = sc.nextLine();
				avdeling = avdelingDao.finnAvdelingMedId(Integer.parseInt(userInput));
				if (avdeling != null) {
					System.out.println(avdeling.toString());
				} else {
					System.out.println("Avdelingen du har angitt finnes ikke.");
				}
				break;
				
			case "H":
				System.out.println("Skriv inn avdelings ID");
				
				userInput = sc.nextLine();
				avdeling = avdelingDao.finnAvdelingMedId(Integer.parseInt(userInput));
				if (avdeling != null) {
					avdelingDao.skrivUtAlle(Integer.parseInt(userInput));
				} else {
					System.out.println("Avdelingen du har angitt finnes ikke.");
				}
				break;
			
			case "I":
				System.out.println("Skriv inn Ansatt ID");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedId(Integer.parseInt(userInput));
				if (ansatt !=null) {
					System.out.println("Skriv inn Avdeling ID");
					userInput2 = sc.nextLine();
					avdeling = avdelingDao.finnAvdelingMedId(Integer.parseInt(userInput2));
					
					erSjef = ansattDao.sjekkSjef(avdeling, Integer.parseInt(userInput));
					
					if (erSjef == true) {
						System.out.println("Brukeren " + userInput + " er sjef for en avdeling og kan ikke overføres");
					 } else {
							ansattDao.oppdaterAvdeling(avdeling, Integer.parseInt(userInput));
							System.out.println("Bruker " + userInput + " har blitt overført til avdeling " + userInput2);
							
					 } if (avdeling == null){
							System.out.println("Avdelingen du har angitt finnes ikke.");
							} 
					} else {
							System.out.println("Brukeren du har angitt finnes ikke");
				}
				break;
			
			case "J":
				System.out.println("Skriv inn Prosjekt ID");
				userInput = sc.nextLine();
				
				if (userInput != null) {
					ProsjektDeltakereDAO.skrivUtProsjekt(Integer.parseInt(userInput));
				} else {
					System.out.println("Prosjektet du har angitt finnes ikke.");
				}
				break;
				
			case "K":
				System.out.println("Skriv inn navn på avdelingen");
				navn = sc.nextLine();

				System.out.println("Velg brukernavn til en ansatt som blir ny sjef for avdelingen");
				sjef = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedBrukernavn(sjef);

					if (ansatt != null) { 
						if (ansatt.getAvdeling().getSjef() != ansatt) {
							avdeling = new Avdeling(navn, ansatt);
							avdelingDao.leggTil(avdeling, ansatt);
						} else {
							System.out.println("Ansatt er allerede sjef i en annen avdeling, velg en annen.");
						}
					} else {
						System.out.println("Fant ikke personen med brukernavn du annga.");
					}
				break;
				
				// -- Iterasjon 2
				// -----------------------------------------------
				
			case "L":
				System.out.println("Skriv inn navn");
				userInput = sc.nextLine();
				System.out.println("Anngi en beskrivelse");
				userInput2 = sc.nextLine();
				Prosjekt p = new Prosjekt(userInput, userInput2);
				prosjektDao.leggTil(p);
				System.out.println("Prosjektet " + userInput + " har blitt lagt til.");
				break;
			
			case "M":
				System.out.println("Skriv inn Ansatt ID");
				ansattId = (Integer.parseInt(sc.nextLine()));
				ansatt = ansattDao.finnAnsattMedId(ansattId);
				
				if (ansatt != null) { 

					System.out.println("Skriv inn Prosjekt ID");
					prosjekt = prosjektDao.finnProsjekt(Integer.parseInt(sc.nextLine()));
						if (prosjekt != null) {
							System.out.println("Skriv inn Rolle");
							rolle = sc.nextLine();

							System.out.println("Anngi antall timer");
							timer = Integer.parseInt(sc.nextLine());

							prosjektX = new ProsjektDeltakere(ansatt, prosjekt, rolle, timer);

							ProsjektDeltakereDAO.leggTilAnsattTilProsjekt(prosjekt.getProsjektId(), prosjektX);

							System.out.println("Ansatt " + ansatt.getFornavn() + " har blitt lagt til prosjekt " + prosjekt.getNavn());	
							
						} else {
							System.out.println("Prosjektet du har anngitt finnes ikke");
						}
				} else {
					System.out.println("Den ansatt ID du har oppgitt finnes ikke");
				}
			break;
				
			
			case "N":
				System.out.println("Skriv inn Ansatt ID");
				userInput = sc.nextLine();
				ansatt = ansattDao.finnAnsattMedId(Integer.parseInt(userInput));
				
				if (ansatt != null) {
					System.out.println("Skriv inn Prosjekt ID");
					userInput2 = sc.nextLine();
					prosjekt = prosjektDao.finnProsjekt(Integer.parseInt(userInput2));
					
					if (prosjekt != null) {
						System.out.println("Anngi antall timer");
						lonn = Integer.parseInt(sc.nextLine());
						prosjektX = ProsjektDeltakereDAO.finnProsjektX(Integer.parseInt(userInput), Integer.parseInt(userInput2));
						
						if(prosjektX != null) {
						ProsjektDeltakereDAO.registrerTimer(lonn, prosjektX);
						System.out.println(lonn + " timer registrert på " + prosjektX.getAnsatt().getFornavn() + " ved  prosjektet " + prosjektX.getProsjekt().getNavn());
						}
					} else {
						System.out.println("Prosjektet du har anngitt finnes ikke");
					}
					
				} else {
					System.out.println("Den ansatt ID du har anngitt finnes ikke");
				}
				break;
			
			//case "O - Ikke implementert":
			//	System.out.println("Skriv inn Prosjekt ID");
			//	TODO Skrive ut timertotal
			//break;
				
			
			case "Q":
				System.out.println("Programmet er avsluttet.");
				running = false;
				sc.close();
				break;
			
			default:
				System.out.println("Ikke gyldig tegn, må være stor bokstav.");
			}
			System.out.println("---------------------------------------------------");
		}
	}
}
