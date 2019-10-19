package com.stefanini.hackaton.dto;

import com.stefanini.hackaton.entities.Jogador;

import java.io.Serializable;

public class BatalhaDto implements Serializable {


    private static final long serialVersionUID = 1L;

    private JogadorDto player;
    private JogadorDto oponente;
    private String battleLog;
    private MenssagemDto menssagem;


    public JogadorDto getPlayer() {
        return player;
    }

    public void setPlayer(JogadorDto player) {
        this.player = player;
    }

    public JogadorDto getOponente() {
        return oponente;
    }

    public void setOponente(JogadorDto oponente) {
        this.oponente = oponente;
    }

    public String getBattleLog() {
        return battleLog;
    }

    public void setBattleLog(String battleLog) {
        this.battleLog = battleLog;
    }

    public MenssagemDto getMensagem() {
        return menssagem;
    }

    public void setMensagem(MenssagemDto mensagem) {
        this.menssagem = mensagem;
    }
}
