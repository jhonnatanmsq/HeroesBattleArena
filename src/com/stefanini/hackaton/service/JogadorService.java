package com.stefanini.hackaton.service;

import com.stefanini.hackaton.api.BaseApi;
import com.stefanini.hackaton.dto.HeroiDto;
import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.JogadorInsertDto;
import com.stefanini.hackaton.dto.JogadorLoginDto;
import com.stefanini.hackaton.entities.Jogador;
import com.stefanini.hackaton.parsers.HeroiParserDTO;
import com.stefanini.hackaton.parsers.JogadorParserDTO;
import com.stefanini.hackaton.persistence.JogadorDAO;
import com.stefanini.hackaton.rest.exceptions.NegocioException;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class JogadorService extends BaseApi {

	@Inject
	JogadorParserDTO parser;

	@Inject
	JogadorDAO jogadorDAO;

	@Inject
    HeroiParserDTO heroiParserDTO;


	public List<JogadorDto> listar() throws NegocioException{

	    List<Jogador> jogadorList = jogadorDAO.list();

	    if(jogadorList == null || jogadorList.isEmpty()){
	        throw new NegocioException("Nenhum usuário encontrado!");
        }

	    return listDto(jogadorList);
	}

	public JogadorDto findById(Integer id)throws NegocioException {
		try{
		    Jogador jogador = jogadorDAO.findById(id);
		    JogadorDto jogadorDto = parser.toDTO(jogadorDAO.findById(id));
            jogadorDto.setHeroi(heroiParserDTO.toDTO(jogador.getHeroi()));
			return jogadorDto;
		}catch (NullPointerException e){
			throw new NegocioException("Jogador não encontrado!");
		}
	}

	@Transactional
	public void salvar(JogadorInsertDto jogador) throws NegocioException {

		if(checkJogador(jogador.getNickname()) != null){
			throw new NegocioException("Este usuário já esta cadastrado no sistema!");
		}

		if (jogador.getNickname() == null || jogador.getNickname().equals("") || jogador.getNickname().length() <= 0 || jogador.getSenha() == null) {
			throw new NegocioException("Dados incorretos!");
		}

		if(jogador.getNickname().contains(" ")){
			throw new NegocioException("Seu nickname não pode conter espaços, utilize - ou _");
		}
		
		String checkSenha = new String(Base64.getDecoder().decode(jogador.getSenha().getBytes()));

		if (checkSenha.length() < 6 || checkSenha.length() > 8) {
			throw new NegocioException("A senha não corresponde ao padrão do sistema. Verifique se a senha possuí no mínimo 6 caracteres e no maximo 8 caracteres.");
		}

		if (jogador.getHeroi() == null) {
			throw new NegocioException("Herói não selecionado, por favor, escolha um herói e tente novamente!");
		}

		Jogador jogadorE = parser.toEntity(jogador);
		jogadorE.setHeroi(heroiParserDTO.toEntity(jogador.getHeroi()));

		jogadorDAO.insert(jogadorE);
	}

	public JogadorDto auth(JogadorLoginDto jogador) throws NegocioException{

		try {
			Jogador jogadorAuth = jogadorDAO.login(jogador);

			JogadorDto jogadorDto = parser.toDTO(jogadorAuth);
			
			jogadorDto.setHeroi(heroiParserDTO.toDTO(jogadorAuth.getHeroi()));

			return jogadorDto;

		}catch (NoResultException e){
			throw  new NegocioException("Ops! Seu nickname ou senha estão incorretos!");
		}
	}

	public JogadorDto getByName(String nick) throws NegocioException{

		try {
			return buscar(nick);
		}catch (NoResultException e){
			throw  new NegocioException("Usuario não encontrado!");
		}
	}

	public JogadorDto checkJogador(String nick){
		try {
            return buscar(nick);
		}catch (NoResultException e){
			return null;
		}
	}

	private JogadorDto buscar(String nick){
        Jogador jogador = jogadorDAO.findByName(nick);

        JogadorDto jDto = parser.toDTO(jogador);

        jDto.setHeroi(heroiParserDTO.toDTO(jogador.getHeroi()));

        return jDto;
    }
	
	public List<JogadorDto> findMatch(){
		JogadorDto jogador = (JogadorDto) getHttpRequest().getSession().getAttribute("USER");
		
		List<Jogador> jogadorList = jogadorDAO.findMatch(jogador.getNickname());
		
		return listDto(jogadorList);
	}
	
	
	private List<JogadorDto> listDto(List<Jogador> jogadorList){
		List<JogadorDto> jogadorDtoList = new ArrayList<>();

        int i = 0;

        for (Jogador jogador : jogadorList) {
            jogadorDtoList.add(parser.toDTO(jogador));
            jogadorDtoList.get(i).setHeroi(heroiParserDTO.toDTO(jogador.getHeroi()));
            i++;
        }
	    return jogadorDtoList;
	}

}
