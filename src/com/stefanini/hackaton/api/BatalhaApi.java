package com.stefanini.hackaton.api;

import com.stefanini.hackaton.rest.exceptions.NegocioException;
import com.stefanini.hackaton.service.BatalhaService;
import com.stefanini.hackaton.service.HeroiService;
import com.stefanini.hackaton.service.JogadorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *  As chamadas para este end-point são negadas se o usuario que requisitar não estiver na sessão do servidor
 */

@Path("/batalhar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BatalhaApi{

	@Inject
	private JogadorService jogadorService;

	@Inject
	private BatalhaService batalhaService;


	@GET
	@Path("/{oponente}")
	public Response playerBattle(@PathParam("oponente") Integer opId) throws NegocioException{

		return Response.ok(batalhaService.playerBattle(opId)).build();
	}

	@GET
	@Path("/bot")
	public Response botBattle() throws NegocioException{

		return Response.ok(batalhaService.botBattle()).build();

	}


	//faz sentido deixar esse metodo aqui pq o metodo da service vai pegar o usuario apartir da sessão
	//Alem do findMatch fazer parte exclusivamente da batalha
	@GET
	@Path("/find")
	public Response findMatch() throws NegocioException{ //busca todos os jogadores no banco, menos o usuario que esta requisitando
		
		return Response.ok(jogadorService.findMatch()).build();
	}
}
