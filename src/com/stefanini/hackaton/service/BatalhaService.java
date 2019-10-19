package com.stefanini.hackaton.service;

import com.stefanini.hackaton.api.BaseApi;
import com.stefanini.hackaton.dto.BatalhaDto;
import com.stefanini.hackaton.dto.HeroiDto;
import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.MenssagemDto;
import com.stefanini.hackaton.parsers.HeroiParserDTO;
import com.stefanini.hackaton.persistence.HeroiDAO;
import com.stefanini.hackaton.rest.exceptions.NegocioException;

import javax.inject.Inject;
import java.util.Random;

public class BatalhaService extends BaseApi {

    @Inject
    HeroiParserDTO heroiParserDTO;

    @Inject
    HeroiDAO heroiDAO;

    @Inject
    JogadorService jogadorService;
    
    MenssagemDto mensagemDto = new MenssagemDto();

    private BatalhaDto batalhaDto = new BatalhaDto();
    private int turno = 1;
    private String espaco = "\n ====================================================== \n";

    public BatalhaDto playerBattle(Integer opId) throws NegocioException {

        JogadorDto jogador = (JogadorDto) getHttpRequest().getSession().getAttribute("USER");

        JogadorDto player = jogadorService.findById(jogador.getId());
        JogadorDto oponente = jogadorService.findById(opId);
        
        if(player.getId().equals(oponente.getId()) || player.getNickname().equals(oponente.getNickname())) {
        	throw new NegocioException("Não é possível batalhar com você mesmo!");
        }

        batalha(player, oponente);

        return batalhaDto;
    }

    public BatalhaDto botBattle() throws NegocioException{

        JogadorDto jogador = (JogadorDto) getHttpRequest().getSession().getAttribute("USER");

        JogadorDto player = jogadorService.findById(jogador.getId());

        Integer idOp = new Random().nextInt(249 - 1 + 1);
        HeroiDto botHeroi = heroiParserDTO.toDTO(heroiDAO.findById(idOp));
        JogadorDto botPlayer = new JogadorDto();
        botPlayer.setNickname("BOT");
        botPlayer.setHeroi(botHeroi);

        batalha(player, botPlayer);

        return batalhaDto;
    }

    private void batalha(JogadorDto player, JogadorDto oponente){
        batalhaDto.setPlayer(player);
        batalhaDto.setOponente(oponente);

        String inicio = "Inicio de batalha\n\n" +  player.getNickname() + " -- " + player.getHeroi().getNome() + "   VS.  " + oponente.getNickname() + " -- " + oponente.getHeroi().getNome();

        batalhaDto.setBattleLog(inicio);

        while (player.getHeroi().getVida() > 0 && oponente.getHeroi().getVida() > 0 && turno <= 100){

            batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n\nTurno:" + turno + "\n"));

            atack(player.getHeroi(), oponente.getHeroi());

            if(oponente.getHeroi().getVida() < 0){
                venceu(player.getHeroi(), player);
                break;
            }

            atack(oponente.getHeroi(), player.getHeroi());

            if(player.getHeroi().getVida() < 0){
                venceu(oponente.getHeroi(), oponente);
                break;
            }

            batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));

            turno++;

            if(turno > 100 && oponente.getHeroi().getVida() > 0 && player.getHeroi().getVida() > 0){
                empate();
            }
        }
    }

    private void atack(HeroiDto atacante, HeroiDto alvo){
    	
    	Integer defesaFisica = (alvo.getDefesa() * alvo.getPoder());
    	Integer defesaMagica = (alvo.getDefesa() * alvo.getInteligencia());
    	
    	Integer atackFisico = (atacante.getAtaque() * atacante.getForca()) * atacante.getForca();
    	Integer atackMagico = (atacante.getInteligencia() * atacante.getPoder()) * atacante.getInteligencia();

        Integer porrada = atackFisico - defesaFisica;
        Integer magia =  atackMagico - defesaMagica;

        if(porrada < 500){porrada = 5000;}
        if(magia < 500){magia = 5000;}

        alvo.setVida(alvo.getVida() - magia);
        alvo.setVida(alvo.getVida() - porrada);

        String danoFísico = "\n O Herói |" + alvo.getNome() + "| recebeu " + atackFisico + " pontos de dano físico e defendeu " + defesaFisica + " pontos";
        String danoMágico = "\n O Herói |" + alvo.getNome() + "| recebeu " + atackMagico + " de dano mágico e defendeu " + defesaMagica + " pontos";

        String vida = "\n O Herói |" + alvo.getNome() + "| está com " + alvo.getVida() + " pontos de vida restantes! \n";

        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(danoFísico));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(danoMágico));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(vida));
    }

    public void venceu(HeroiDto heroi, JogadorDto jogador){
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n\n ========= O Herói |" + heroi.getNome() + "| do Player |" + jogador.getNickname() + "| foi o campeão! ========="));
        mensagemDto.setStatusBatalha("Finalizada");
        mensagemDto.setPlayerVencedor(jogador.getNickname());
        mensagemDto.setHeroiVencedor(heroi.getNome());
        batalhaDto.setMensagem(mensagemDto);
    }

    public void empate(){
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\nEssa batalha não vai acabar nunca!\n"));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n\n ========= A batalha terminou em Empate! ========="));
        mensagemDto.setStatusBatalha("Empate");
        mensagemDto.setPlayerVencedor("Empate");
        mensagemDto.setHeroiVencedor("Empate");
        batalhaDto.setMensagem(mensagemDto);
    }

}
