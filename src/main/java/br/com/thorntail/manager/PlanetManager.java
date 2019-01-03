package br.com.thorntail.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.enterprise.context.ApplicationScoped;

import br.com.thorntail.model.Planet;


@ApplicationScoped
public class PlanetManager {
	
	private ConcurrentMap<Integer, Planet> inMemoryStore = new ConcurrentHashMap<>();
	private int planetSequence = 0;
	
	
//	public PlanetManager() {
//		Planet planet = new Planet();
//		planet.setId(++planetSequence);
//		planet.setClimate("arid");
//		planet.setName("Tatooine");
//		planet.setTerrain("desert");
//		
//		inMemoryStore.put(planetSequence, planet);
//	}
//	
//	public Integer add(Planet planet) {
//		planet.setId(++planetSequence);
//		inMemoryStore.put(planet.getId(), planet);
//		return planet.getId();
//	}
//	
//	public Planet getPlanetById(Integer id) {
//		return inMemoryStore.get(id);
//	}
//	
//	public List<Planet> findAll(){
//		return (List<Planet>) inMemoryStore.values().stream().collect(Collectors.toList());
//	}
	
	
	

}
