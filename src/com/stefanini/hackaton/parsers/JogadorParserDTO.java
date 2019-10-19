package com.stefanini.hackaton.parsers;

import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.JogadorInsertDto;
import com.stefanini.hackaton.entities.Jogador;

public class JogadorParserDTO extends AbstractParser<JogadorDto, Jogador> {

	@Override
	public JogadorDto toDTO(Jogador entity) {
		JogadorDto dto = new JogadorDto();
		dto.setId(entity.getId());
		dto.setNickname(entity.getNickname());
		return dto;
	}

	@Override
	public Jogador toEntity(JogadorDto dto) {
		Jogador entity = new Jogador();
		entity.setId(dto.getId());
		entity.setNickname(dto.getNickname());
		return entity;
	}


	public Jogador toEntity(JogadorInsertDto dto) {
		Jogador entity = new Jogador();
		entity.setNickname(dto.getNickname());
		entity.setSenha(dto.getSenha());
		return entity;
	}

}
