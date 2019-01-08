package br.com.thorntail.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.thorntail.exception.manager.PlanetNotFoundException;

@Provider
public class PlanetNotFoundExceptionHandler implements ExceptionMapper<PlanetNotFoundException>{

	@Override
	public Response toResponse(PlanetNotFoundException exception) {
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
	}

}
