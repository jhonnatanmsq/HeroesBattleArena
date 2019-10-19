package com.stefanini.hackaton.api;

import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.JogadorInsertDto;
import com.stefanini.hackaton.dto.JogadorLoginDto;
import com.stefanini.hackaton.rest.exceptions.NegocioException;
import com.stefanini.hackaton.service.JogadorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jogador")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogadorApi extends BaseApi{

	@Inject
	private JogadorService jogadorService;

	@GET
	public Response listar() throws NegocioException{
		return Response.ok(jogadorService.listar()).build();
	}

	@GET
	@Path("/nick/{nick}")
	public Response getByName(@PathParam("nick") String nick) throws NegocioException{

		return Response.ok(jogadorService.getByName(nick)).build();

	}

	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") Integer id) throws NegocioException{

		return Response.ok(jogadorService.findById(id)).build();
	}

	@POST
	@Path("/auth")
	public Response auth(JogadorLoginDto jogador) throws NegocioException {

		JogadorDto jogadorDto = jogadorService.auth(jogador);

		getHttpRequest().getSession().setAttribute("USER", jogadorDto);	
		
		
		return Response.ok(jogadorDto).header("JSESSIONID", getHttpRequest().getSession().getId()).build();
	}

	@POST
	public Response salvar(JogadorInsertDto jogador) throws NegocioException {
		jogadorService.salvar(jogador);

		return Response.status(201).build();
	}
}
