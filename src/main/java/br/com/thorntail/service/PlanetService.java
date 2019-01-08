package br.com.thorntail.service;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.hql.ast.origin.hql.parse.HQLParser.relationalExpression_return;

import br.com.thorntail.exception.manager.BusinessException;
import br.com.thorntail.exception.manager.PlanetNotFoundException;
import br.com.thorntail.manager.PlanetManager;
import br.com.thorntail.model.Planet;
import br.com.thorntail.repository.PlanetRepository;

@RequestScoped
public class PlanetService {

	@Inject
	private PlanetManager planetManager;

	@Inject
	private PlanetRepository planetRepository;

	@RequestScoped
	public void save(Planet planet) throws PlanetNotFoundException, BusinessException {
		if (planet != null && planet.getName() != null) {
			Optional<Planet> planetInMemory = planetManager.getInMemoryPlanet(planet.getName());
			if (planetInMemory.isPresent()) {
				planet.setMovies(planetInMemory.get().getMovies());
				planetRepository.save(planet);
			} else {
				throw new PlanetNotFoundException("The planet " + planet.getName() + " does'n exist");
			}
		}
	}

	public void updatePlanet(Planet planet) throws PlanetNotFoundException, BusinessException {
		if (planet != null && planet.getName() != null) {
			Optional<Planet> planetInMemory = planetManager.getInMemoryPlanet(planet.getName());
			if (planetInMemory.isPresent()) {
				planet.setMovies(planetInMemory.get().getMovies());
				planetRepository.update(planet);
			} else {
				throw new PlanetNotFoundException("The planet " + planet.getName() + " does'n exist");
			}
		}
	}

}
