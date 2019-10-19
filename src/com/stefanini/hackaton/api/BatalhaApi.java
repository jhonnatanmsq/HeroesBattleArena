package com.stefanini.hackaton.api;

import com.stefanini.hackaton.dto.BatalhaDto;
import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.entities.Jogador;
import com.stefanini.hackaton.rest.exceptions.NegocioException;
import com.stefanini.hackaton.service.BatalhaService;
import com.stefanini.hackaton.service.HeroiService;
import com.stefanini.hackaton.service.JogadorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/batalhar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BatalhaApi{

	@Inject
	private JogadorService jogadorService;

	@Inject
	private BatalhaService batalhaService;

	@Inject
	private HeroiService heroiService;

	@GET
	@Path("/{oponente}")
	//@Produces(MediaType.TEXT_PLAIN)
	public Response playerBattle(@PathParam("oponente") Integer opId) throws NegocioException{

		return Response.ok(batalhaService.playerBattle(opId)).build();

	}

	@GET
	@Path("/bot")
	//@Produces(MediaType.TEXT_PLAIN)
	public Response botBattle() throws NegocioException{

		return Response.ok(batalhaService.botBattle()).build();

	}
	
	@GET
	@Path("/find")
	public Response findMatch() throws NegocioException{
		
		return Response.ok(jogadorService.findMatch()).build();
	}
}
