package de.shop.lieferverwaltung.rest;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.CONFLICT;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.shop.lieferverwaltung.service.LieferServiceException;
import de.shop.util.Log;


@Provider
@ApplicationScoped
@Log
public class LieferResourceExceptionMapper implements ExceptionMapper<LieferServiceException> {
	@Override
	public Response toResponse(LieferServiceException e) {
		final String msg = e.getMessage();
		final Response response = Response.status(CONFLICT)
		                                  .type(TEXT_PLAIN)
		                                  .entity(msg)
		                                  .build();
		return response;
	}

}
