package br.com.thorntail.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.thorntail.repository.PlanetRepository;

@Path("/planets")
@RequestScoped
public class PlanetResource {

	
	@Inject
	private PlanetRepository planetRepository;
	
	
	@GET
	@Path("/id={id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlanet(@PathParam("id") Long id) {
		return Response.ok(planetRepository.findById(id)).build();
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
	
	
	
	@GET
	@Path("/vamosla")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAPlanet() {
		planetRepository.persistirTest();
		return Response.ok().build();
	}
	
}
