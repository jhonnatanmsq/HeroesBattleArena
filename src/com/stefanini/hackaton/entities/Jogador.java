package com.stefanini.hackaton.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jogador")
@NamedQueries({@NamedQuery(name = "Jogador.getByName", query = "SELECT j FROM Jogador j WHERE j.nickname = :nickname"),
			@NamedQuery(name = "Jogador.findMatch", query = "SELECT j FROM Jogador j WHERE j.nickname <> :nickname"),
			@NamedQuery(name = "Jogador.login", query = "SELECT j FROM Jogador j WHERE j.nickname = :nickname AND j.senha = :senha")})
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickname;
    private String senha;

    @ManyToOne
    @JoinColumn(name="idHeroi")
    private Heroi heroi;

    public Jogador() {
    }

    public Jogador(String nickname, String senha, Heroi heroi) {
        this.nickname = nickname;
        this.senha = senha;
        this.heroi = heroi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }
}
