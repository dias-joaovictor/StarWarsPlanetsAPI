package br.com.thorntail.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.thorntail.manager.PlanetManager;
import br.com.thorntail.model.Planet;
import br.com.thorntail.repository.PlanetRepository;
import br.com.thorntail.service.PlanetService;
import net.bytebuddy.implementation.bind.ParameterLengthResolver;

@Path("/planets")
@RequestScoped
public class PlanetResource {

	@Inject
	private PlanetRepository planetRepository;
	
	@Inject
	private PlanetService planetService;

	@GET
	@Path("/id={id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlanet(@PathParam("id") Long id) {
		try{
			return Response.ok(planetRepository.findById(id)).build();
		}catch (Exception e) {
			throw new WebApplicationException("Planet not Found", Response.Status.NOT_FOUND);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPlanets() {
		return Response.ok(planetRepository.findAll()).build();
	}

	@GET
	@Path("/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlanetsByName(@PathParam("name") String name) {
		return Response.ok(planetRepository.findByNameNative(name)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAPlanet(Planet planet) {
		planetService.save(planet);
		return Response.ok().build();
	}
	
	@PUT
	@Consumes
	public Response updatePlanet(Planet planet) {
		planetService.updatePlanet(planet);
		return Response.ok().build();
	}

}
