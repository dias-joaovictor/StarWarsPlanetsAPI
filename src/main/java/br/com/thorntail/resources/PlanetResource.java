package br.com.thorntail.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.thorntail.exception.manager.BusinessException;
import br.com.thorntail.exception.manager.PlanetNotFoundException;
import br.com.thorntail.model.Planet;
import br.com.thorntail.repository.PlanetRepository;
import br.com.thorntail.service.PlanetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Path("/planets")
@RequestScoped
@Api(value = "/planets")
public class PlanetResource {

	@Inject
	private PlanetRepository planetRepository;
	
	@Inject
	private PlanetService planetService;

	@GET
	@Path("/id={id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Find a planet by ID",
			notes = "Returns a planet found by ID",
			response = String.class, 
			httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON)
	public Response getPlanet(@PathParam("id") Long id) throws PlanetNotFoundException {
			return Response.ok(planetRepository.findById(id)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Find all planets",
			notes = "Returns all planets found",
			response = String.class, 
			httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON)
	public Response getAllPlanets() {
		return Response.ok(planetRepository.findAll()).build();
	}

	@GET
	@Path("/name={name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Find a planet by its name",
			notes = "Returns a planet",
			response = String.class, 
			httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON)
	public Response getPlanetsByName(@PathParam("name") String name) throws BusinessException {
		return Response.ok(planetRepository.findByNameNativeQueryIgnoringCase(name)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Add a planet",
			notes = "This method adds a planet and makes a query in a inMemoryBase to find all movies it appered if appered. This method make a merge if the planet is already added",
			response = String.class, 
			httpMethod = "POST",
			consumes = MediaType.APPLICATION_JSON)
	public Response addAPlanet(Planet planet) throws PlanetNotFoundException, BusinessException{
		planetService.save(planet);
		return Response.status(Status.CREATED).build();
	}
	
	@PUT
	@Consumes
	@ApiOperation(
			value = "Update a planet",
			notes = "This endpoint update a planet, but continues finding the planet name in the inMemoryBase to find the total of movies it apperes",
			response = String.class, 
			httpMethod = "PUT",
			consumes = MediaType.APPLICATION_JSON)
	public Response updatePlanet(Planet planet) throws PlanetNotFoundException, BusinessException {
		planetService.updatePlanet(planet);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("id={id}")
	@PUT
	@Consumes
	@ApiOperation(
			value = "Delete a planet by ID",
			notes = "This endpoint delete a planet if its id is found",
			response = String.class, 
			httpMethod = "DELETE",
			consumes = MediaType.APPLICATION_JSON)
	public Response deletePlanet(@PathParam(value="id") Long id) throws PlanetNotFoundException, BusinessException {
		planetRepository.delete(id);
		return Response.ok().build();
	}

}
