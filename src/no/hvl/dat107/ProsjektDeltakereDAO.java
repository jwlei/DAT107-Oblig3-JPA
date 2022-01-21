package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

public class ProsjektDeltakereDAO {

	private static EntityManagerFactory emf;

	public ProsjektDeltakereDAO() {
		emf = Persistence.createEntityManagerFactory("persistence");
	}


	public void leggTilAnsattTilProsjekt(int id, ProsjektDeltakere pd) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Prosjekt p = em.find(Prosjekt.class, id);
		
		try {
			tx.begin();
			pd.setProsjekt(p);
			pd.getAnsatt().addProsjekt(pd);
			em.merge(pd);
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

	}

	public void registrerTimer(int timer, ProsjektDeltakere pd) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			pd.leggTilTimer(timer);
			em.merge(pd);
			tx.commit();
		
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void skrivUtProsjekt(int id) {
		String queryString = 	"SELECT a FROM ProsjektDeltakere a " 
							+ 	"WHERE a.prosjekt = :prosjektid";

		EntityManager em = emf.createEntityManager();
		Prosjekt p = em.find(Prosjekt.class, id);
		List<ProsjektDeltakere> ansatte = null;

		try {
			TypedQuery<ProsjektDeltakere> query = em.createQuery(queryString, ProsjektDeltakere.class);
			query.setParameter("prosjektid", p);
			ansatte = query.getResultList();

			System.out.println(p.toString());
			
			for (ProsjektDeltakere pd : ansatte) {
				System.out.println(pd.toString());
			}
		} catch (Throwable e) {
			//e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public ProsjektDeltakere finnProsjektX(int ansattId, int prosjektId) {
		String queryString = 	"SELECT a FROM ProsjektDeltakere a "
						     +	"WHERE a.prosjekt = :prosjektid "
						     +	"AND a.ansatt = :ansattid";

		EntityManager em = emf.createEntityManager();
		ProsjektDeltakere prosjetkX = null;

		try {
			Ansatt a = em.find(Ansatt.class, ansattId);
			Prosjekt p = em.find(Prosjekt.class, prosjektId);
			
			TypedQuery<ProsjektDeltakere> query = em.createQuery(queryString, ProsjektDeltakere.class);
			query.setParameter("ansattid", a);
			query.setParameter("prosjektid", p);
			prosjetkX = query.getSingleResult();

			} catch (Throwable e) {
				System.out.println("Kan ikke finne prosjektet. du leter etter");
			} finally {
				em.close();
			}
		return prosjetkX;
	}
}
