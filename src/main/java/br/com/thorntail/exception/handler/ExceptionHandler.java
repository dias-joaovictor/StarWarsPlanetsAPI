package br.com.thorntail.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.thorntail.exception.manager.BusinessException;
import br.com.thorntail.exception.manager.PlanetNotFoundException;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception>{

	
	@Override
	public Response toResponse(Exception exception) {
		if(exception instanceof BusinessException) {
			return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
		}else if(exception instanceof PlanetNotFoundException) {
			return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
