package com.stefanini.hackaton.api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stefanini.hackaton.rest.exceptions.NegocioException;
import com.stefanini.hackaton.service.HeroiService;

@Path("/heroi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HeroiApi {

	@Inject
	private HeroiService heroiService;

	@GET
	public Response listar() {
		return Response.ok(heroiService.listar()).build();
	}

	@GET
	@Path("/{id}")
	public Response find(@PathParam("id") Integer id) throws NegocioException {
		return Response.ok(heroiService.find(id)).build();
	}
}
