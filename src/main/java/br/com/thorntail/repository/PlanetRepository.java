package br.com.thorntail.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.thorntail.exception.manager.BusinessException;
import br.com.thorntail.exception.manager.PlanetNotFoundException;
import br.com.thorntail.model.Planet;

@ApplicationScoped
public class PlanetRepository {

	private static final String INVALID_PARAM = "Invalid param";
	private static final String FIND_BY_NAME_IGNORING_CASE = "db.Planet.aggregate([{ '$match': {'NAME': { '$regex': '^%s$', '$options': 'i' } } }])";
	@Inject
	private EntityManager entityManager;

	public List<Planet> findAll() {
		String consulta = "SELECT planet FROM Planet planet";
		return entityManager.createQuery(consulta, Planet.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Planet> findByNameNativeQueryIgnoringCase(String nameLike) throws BusinessException{
		if (nameLike != null && !nameLike.isEmpty()) {
			String consulta = String.format(FIND_BY_NAME_IGNORING_CASE, nameLike.toLowerCase());
			return entityManager.createNativeQuery(consulta, Planet.class).getResultList();
		}
		throw new BusinessException(INVALID_PARAM);
	}

	public Planet findById(Long id) throws PlanetNotFoundException {
		String consulta = "SELECT planet FROM Planet planet where planet.id = :id";
		Query query = entityManager.createQuery(consulta, Planet.class).setParameter("id", id);
		try {
			return (Planet) query.getSingleResult();
		} catch (NoResultException e) {
			throw new PlanetNotFoundException("No planet found with id: " + id);
		}
	}


	public void save(Planet planet) {
		if (planet != null && planet.getName() != null) {
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

	public void update(Planet planet) throws PlanetNotFoundException, BusinessException{
		if (planet != null && planet.getId() != null) {
			Planet planetFound = null;
			try {
				planetFound = findById(planet.getId());
			} catch (PlanetNotFoundException ex) {
				//
			}
			if (planetFound != null) {
				mergeAndCommit(planet);
			} else {
				throw new PlanetNotFoundException("No planet found with id: " + planet.getId());
			}
		}else {
			throw new BusinessException(INVALID_PARAM + ": id Param is needed.");
		}
	}

	private void merge(Planet planet, Planet planetFound) {
		planet.setId(planetFound.getId());
		mergeAndCommit(planet);
	}

	private void mergeAndCommit(Planet planet) {
		if(entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(planet);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	private void saveAndCommit(Planet planet) {

		if(entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(planet);
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
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

	public void delete(Long id) throws PlanetNotFoundException, BusinessException{
		if(id != null) {
			Planet planetFound = findById(id);
			deleteAndCommit(planetFound);
		}else{
			throw new BusinessException("id can not be Null");
		}
		
	}

	private void deleteAndCommit(Planet planetFound) {
		if(entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(planetFound);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

}
