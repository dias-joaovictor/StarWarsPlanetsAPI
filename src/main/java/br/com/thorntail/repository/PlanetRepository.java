package br.com.thorntail.repository;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.thorntail.model.Planet;

@ApplicationScoped
public class PlanetRepository {

	private static final String FIND_BY_NAME_IGNORING_CASE = "db.Planet.aggregate([{ '$match': {'NAME': { '$regex': '^%s$', '$options': 'i' } } }])";
	@Inject
	private EntityManager entityManager;

	public List<Planet> findAll() {
		String consulta = "SELECT planet FROM Planet planet";
		return entityManager.createQuery(consulta, Planet.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Planet> findByNameNative(String nameLike) {
		if (nameLike != null && !nameLike.isEmpty()) {
			String consulta = String.format(FIND_BY_NAME_IGNORING_CASE, nameLike.toLowerCase());
			return entityManager.createNativeQuery(consulta, Planet.class).getResultList();
		}
		return new ArrayList<Planet>();
	}

	public Planet findById(Long id) throws NoResultException {
		String consulta = "SELECT planet FROM Planet planet where planet.id = :id";
		return entityManager.createQuery(consulta, Planet.class).setParameter("id", id).getSingleResult();
	}


	public void save(Planet planet) {
		if (planet != null) {
			Planet planetFound = null;
			String consulta = String.format(FIND_BY_NAME_IGNORING_CASE, planet.getName());
			Query query = entityManager.createNativeQuery(consulta, Planet.class);
			try {
				planetFound = (Planet) query.getSingleResult();
			} catch (NoResultException ex) {
				//
			}
			if (planetFound == null) {
				saveAndCommit(planet);
			} else {
				merge(planet, planetFound);
			}
		}
	}

	public void update(Planet planet) {
		if (planet != null && planet.getId() != null) {
			Planet planetFound = null;
			try {
				planetFound = findById(planet.getId());
			} catch (NoResultException ex) {
				//
			}
			if (planetFound != null) {
				merge(planet);
			} else {
				// throw handledException
			}
		}
	}

	private void merge(Planet planet, Planet planetFound) {
		planet.setId(planetFound.getId());
		merge(planet);
	}

	private void merge(Planet planet) {
		entityManager.getTransaction().begin();
		entityManager.merge(planet);
		entityManager.getTransaction().commit();
	}

	private void saveAndCommit(Planet planet) {
		entityManager.getTransaction().begin();
		entityManager.persist(planet);
		entityManager.getTransaction().commit();
		;
	}

	public void persistirTest() {
		Planet planet = new Planet();
		planet.setClimate("arid");
		planet.setName("Hoth");
		planet.setTerrain("tundra");
		entityManager.getTransaction().begin();
		entityManager.persist(planet);
		entityManager.getTransaction().commit();
	}

}
