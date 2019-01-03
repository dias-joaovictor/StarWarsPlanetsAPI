package br.com.thorntail.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.thorntail.model.Planet;

@ApplicationScoped
public class PlanetRepository {

	@Inject
	private EntityManager entityManager;
	
	public List<Planet> findAll(){
		String consulta = "SELECT planet FROM Planet planet";
		return entityManager.createQuery(consulta, Planet.class).getResultList();
	}
	
	public Planet findById(Long id){
		String consulta = "SELECT planet FROM Planet planet where planet.id = :id";
		return entityManager.createQuery(consulta, Planet.class).setParameter("id", id).getSingleResult();
	}
	
	public void persistirTest() {
		Planet planet = new Planet();
		planet.setClimate("arid");
		planet.setName("Hoth");
		planet.setTerrain("tundra");
		entityManager.getTransaction().begin();;
		entityManager.persist(planet);
		entityManager.getTransaction().commit();
	}
	
	
}
