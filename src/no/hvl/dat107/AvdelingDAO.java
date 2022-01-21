package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AvdelingDAO {
	private static EntityManagerFactory emf;
	
	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("persistence");
	}
	
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		Avdeling a = null;
		
		try {
			a = em.find(Avdeling.class, id);
			
		} finally {
			em.close();
		}
		return a;
	}
	
	public void skrivUtAlle(int id) {
		EntityManager em = emf.createEntityManager();
		String queryString1 = 	"SELECT a FROM Ansatt a " + 
								"WHERE a.avdeling = :avdeling";

		Avdeling avd = em.find(Avdeling.class, id);
		List<Ansatt> ansatte = null;

		try {
			TypedQuery<Ansatt> query = em.createQuery(queryString1, Ansatt.class);
			query.setParameter("avdeling", avd);

			ansatte = query.getResultList();

			for (Ansatt ans : ansatte) {
				if( ans == avd.getSjef()) {
					System.out.println("SJEF er " + ans.toString());
				} else {
				System.out.println(ans.toString());
				}
			}
		} finally {
			em.close();
		}
	}
	
	public void leggTil(Avdeling a, Ansatt sjef) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {	
			tx.begin();
			em.persist(a);
			sjef.setAvdeling(a);
			em.merge(sjef);
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
