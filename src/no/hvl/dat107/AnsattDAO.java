package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattDAO {
	private static EntityManagerFactory emf;

	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("persistence");
	}

	public Ansatt finnAnsattMedId(int ansattid) {
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;

		try {
			a = em.find(Ansatt.class, ansattid);
		} finally {
			em.close();
		}
		return a;
	}

	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		String queryString = "SELECT a FROM Ansatt a " + "WHERE a.brukernavn = :brukernavn";
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;

		try {
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			query.setParameter("brukernavn", brukernavn);
			a = query.getSingleResult();
		}catch (Throwable e) {
		 //e.printStackTrace();
		} finally {
			em.close();
		}
		return a;
	}

	public void skrivUtAlle() {
		String queryString = "SELECT a FROM Ansatt a";
		EntityManager em = emf.createEntityManager();
		List<Ansatt> ansatte = null;

		try {
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			ansatte = query.getResultList();

			for (Ansatt a : ansatte) {
				System.out.println(a.toString());
			}
		} finally {
			em.close();
		}
	}

	public void oppdaterStilling(String stilling, int ansattId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Ansatt a = em.find(Ansatt.class, ansattId);
			tx.begin();
			
			if (a != null) {
				a.setStilling(stilling);
				em.merge(a);
			}

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	public boolean sjekkSjef (Avdeling Avdeling, int ansattId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
			boolean erSjef = false;
			Ansatt a = em.find(Ansatt.class, ansattId);
			tx.begin();
			
			if (a != null && a.getAvdeling().getSjef() == a) {
				erSjef = true;
		} 
		em.close();
		return erSjef;
	}
	public void oppdaterAvdeling(Avdeling Avdeling, int ansattId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Ansatt a = em.find(Ansatt.class, ansattId);
			tx.begin();
			
			if (a != null && a.getAvdeling().getSjef() != a) {
				a.setAvdeling(Avdeling);
				em.merge(a);
			} else {
				System.out.println("Kan ikke bytte avdeling siden " + a.getFornavn() + " allerede er sjef");
			}

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void oppdaterLonn(Integer lonn, int ansattId) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Ansatt a = em.find(Ansatt.class, ansattId);
			tx.begin();
			
			if (a != null) {
				a.setlonn(lonn);
				em.merge(a);
			}

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void leggTil(Ansatt add) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(add);
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

	}
}
