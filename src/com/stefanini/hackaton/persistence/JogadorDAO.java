package com.stefanini.hackaton.persistence;

import java.util.List;

import javax.transaction.Transactional;

import com.stefanini.hackaton.dto.JogadorLoginDto;
import com.stefanini.hackaton.entities.Jogador;

public class JogadorDAO extends GenericDAO<Integer, Jogador> {

    public Jogador findByName(String name){
        return getEntityManager()
                .createNamedQuery("Jogador.getByName", Jogador.class)
                .setParameter("nickname", name)
                .getSingleResult();
    }
    
    @Transactional
	@SuppressWarnings("unchecked")
	public List<Jogador> findMatch(String name) {
		return getEntityManager()
				.createNamedQuery("Jogador.findMatch")
				.setParameter("nickname", name)
				.getResultList();
	}
    
    public Jogador login(JogadorLoginDto jogador){
        return getEntityManager()
                .createNamedQuery("Jogador.login", Jogador.class)
                .setParameter("nickname", jogador.getNickname())
                .setParameter("senha", jogador.getSenha())
                .getSingleResult();
    }
}
