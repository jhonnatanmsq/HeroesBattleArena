package com.stefanini.hackaton.parsers;

import com.stefanini.hackaton.dto.HeroiDto;
import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.JogadorInsertDto;
import com.stefanini.hackaton.entities.Heroi;
import com.stefanini.hackaton.entities.Jogador;

import javax.inject.Inject;

public class JogadorParserDTO extends AbstractParser<JogadorDto, Jogador> {

	@Inject
	private HeroiParserDTO heroiParserDTO;

	@Override
	public JogadorDto toDTO(Jogador entity) {
		JogadorDto dto = new JogadorDto();
		dto.setId(entity.getId());
		dto.setNickname(entity.getNickname());

		HeroiDto heroiDto = heroiParserDTO.toDTO(entity.getHeroi());
		dto.setHeroi(heroiDto);

		return dto;
	}

	@Override
	public Jogador toEntity(JogadorDto dto) {
		Jogador entity = new Jogador();
		entity.setId(dto.getId());
		entity.setNickname(dto.getNickname());

		Heroi heroi = heroiParserDTO.toEntity(dto.getHeroi());
		entity.setHeroi(heroi);

		return entity;
	}


	public Jogador toEntity(JogadorInsertDto dto) {
		Jogador entity = new Jogador();
		entity.setNickname(dto.getNickname());
		entity.setSenha(dto.getSenha());

		Heroi heroi = heroiParserDTO.toEntity(dto.getHeroi());
		entity.setHeroi(heroi);

		return entity;
	}

}
