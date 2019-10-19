package com.stefanini.hackaton.service;

import com.stefanini.hackaton.dto.HeroiDto;
import com.stefanini.hackaton.parsers.HeroiParserDTO;
import com.stefanini.hackaton.persistence.HeroiDAO;
import com.stefanini.hackaton.rest.exceptions.NegocioException;

import javax.inject.Inject;
import java.util.List;

public class HeroiService {

	@Inject
	HeroiParserDTO parser;
	
	@Inject
	HeroiDAO heroiDao;


	public List<HeroiDto> listar(){
		return parser.toDTO(heroiDao.list());
	}

	public HeroiDto find(Integer id) throws NegocioException {
		try{
			return parser.toDTO(heroiDao.findById(id));
		}catch (NullPointerException e){
			throw new NegocioException("Heroi não encontrado!");
		}
	}

}
