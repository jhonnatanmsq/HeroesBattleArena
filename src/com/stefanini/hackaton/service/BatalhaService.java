package com.stefanini.hackaton.service;

import com.stefanini.hackaton.api.BaseApi;
import com.stefanini.hackaton.dto.BatalhaDto;
import com.stefanini.hackaton.dto.HeroiDto;
import com.stefanini.hackaton.dto.JogadorDto;
import com.stefanini.hackaton.dto.MenssagemDto;
import com.stefanini.hackaton.rest.exceptions.NegocioException;

import javax.inject.Inject;
import java.util.Random;

/** A logica por tras dessa batalha se baseia em turnos, onde somente o heroi com a maior
 * velocidade ataca (a velocidade é gerada randomicamente a cada turno)
 *
 *  A 'batalha' acontece em um loop while, onde cada ciclo dele é um turno, é dentro do while
 *  que é verificado quem ataca, os calculos de dano, e o fim da batalha (vitoria/derrota empate)
 *
 *  Verifique tambem o BatalhaDto e MenssagemDto para entender melhor essa logica;
 *
 */

public class BatalhaService extends BaseApi {

    @Inject
    private HeroiService heroiService;

    @Inject
    private JogadorService jogadorService;
    
    MenssagemDto mensagemDto = new MenssagemDto();

    private BatalhaDto batalhaDto = new BatalhaDto();
    private int turno = 1;
    private String espaco = "\n ====================================================== \n";

    public BatalhaDto playerBattle(Integer opId) throws NegocioException {

        //pega o usuario diretamente na sessão do servidor, sem haver a necessidade de ter q informar qual usuario esta fazendo a requisição;
        JogadorDto player = (JogadorDto) getHttpRequest().getSession().getAttribute("USER"); //pega oq esta na sessão com atributo "USER' e da um cast para um JogadorDto

        JogadorDto oponente = jogadorService.findById(opId);
        
        if(player.getId().equals(oponente.getId()) || player.getNickname().equals(oponente.getNickname())) {
        	throw new NegocioException("Nao e possível batalhar com voce mesmo!");
        }

        batalha(player, oponente);

        return batalhaDto;
    }

    public BatalhaDto botBattle() throws NegocioException{

        //pega o usuario diretamente na sessão do servidor, sem haver a necessidade de ter q informar qual usuario esta fazendo a requisição;
        JogadorDto player = (JogadorDto) getHttpRequest().getSession().getAttribute("USER");//pega oq esta na sessão com atributo "USER' e da um cast para um JogadorDto

        Integer idOp = new Random().nextInt(249 - 1 + 1);

        HeroiDto botHeroi =  heroiService.find(idOp);//pega um heroi randomicamente

        JogadorDto botPlayer = new JogadorDto();  //Esse botPlayer vai ter apenas o nickname e o heroi
        botPlayer.setNickname("BOT");  //Aqui é setado o nickname
        botPlayer.setHeroi(botHeroi); //E aqui o heroi dele

        batalha(player, botPlayer);

        return batalhaDto;
    }

    private void batalha(JogadorDto player, JogadorDto oponente){
        batalhaDto.setPlayer(player);
        batalhaDto.setOponente(oponente);

        String inicio = "Inicio de batalha\n\n" +  player.getNickname() + " -- " + player.getHeroi().getNome() + "   VS.  " + oponente.getNickname() + " -- " + oponente.getHeroi().getNome();

        batalhaDto.setBattleLog(inicio);
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n" + espaco));

        //Aqui é onde a batalha realmente começa
        while (player.getHeroi().getVida() > 0 && oponente.getHeroi().getVida() > 0 && turno <= 100){

            //Cada turno/ciclo do while a velocidade de cada heroi será randomizada, oq quer dizer q em cada turno ela será diferente
            player.getHeroi().setVelocidade(new Random().nextInt(100 - 1 + 1));
            oponente.getHeroi().setVelocidade(new Random().nextInt(100 - 1 + 1));

            batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\nTurno: " + turno + "\n"));

            if(player.getHeroi().getVelocidade() >= oponente.getHeroi().getVelocidade()){  //Apenas o heroi com a maior velocidade vai atacar
                atack(player.getHeroi(), oponente.getHeroi()); //metodo que faz os calculos de dano

                if(oponente.getHeroi().getVida() < 0){ //se o heroi inimigo tiver com a vida zerada ou negativa a vitoria é decretada
                    venceu(player.getHeroi(), player);
                    break; //quebra o while
                }
            }else{
                atack(oponente.getHeroi(), player.getHeroi());  //metodo que faz os calculos de dano

                if(player.getHeroi().getVida() < 0){  //se o heroi inimigo tiver com a vida zerada ou negativa a vitoria é decretada
                    venceu(oponente.getHeroi(), oponente);
                    break;
                }
            }

            batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));

            turno++;

            if(turno > 50 && oponente.getHeroi().getVida() > 0 && player.getHeroi().getVida() > 0){ //se a batalha se extender demais, o empate é decretado
                empate();
            }
        }
    }

    //metodo que faz os calculos de dano
    private void atack(HeroiDto atacante, HeroiDto alvo){
    	
    	Integer defesaFisica = (alvo.getDefesa() * alvo.getPoder());
    	Integer defesaMagica = (alvo.getDefesa() * alvo.getInteligencia());
    	
    	Integer atackFisico = (atacante.getAtaque() * atacante.getForca()) * atacante.getForca();
    	Integer atackMagico = (atacante.getInteligencia() * atacante.getPoder()) * atacante.getInteligencia();

        Integer porrada = atackFisico - defesaFisica;
        Integer magia =  atackMagico - defesaMagica;

        if(porrada < 0){porrada = 0;} //se o ataque for negativo, ele será setado pra 0; pra não quebrar os calculos e fazer adição de vida aos herois (ex: -5000 de atack com -10000 de defesa iria somar 15000 na vida do heroi que ta recebendo o ataque)
        if(magia < 0){magia = 0;}

        //subtrai a vida do heroi
        alvo.setVida(alvo.getVida() - magia);
        alvo.setVida(alvo.getVida() - porrada);
        
        String hAtacante  = "\n O Heroi |" + atacante.getNome() + "| atacou! \n";

        String danoFísico = "\n O Heroi |" + alvo.getNome() + "| recebeu " + atackFisico + " pontos de dano fisico e defendeu " + defesaFisica + " pontos";
        String danoMágico = "\n O Heroi |" + alvo.getNome() + "| recebeu " + atackMagico + " de dano magico e defendeu " + defesaMagica + " pontos";

        String vida = "\n O Heroi |" + alvo.getNome() + "| esta com " + alvo.getVida() + " pontos de vida restantes! \n";

        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(hAtacante));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(danoFísico));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(danoMágico));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(vida));
    }

    public void venceu(HeroiDto heroi, JogadorDto jogador){
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n\n ========= O Heroi |" + heroi.getNome() + "| do Player |" + jogador.getNickname() + "| foi o campeao! ========="));
        mensagemDto.setStatusBatalha("Finalizada");
        mensagemDto.setPlayerVencedor(jogador.getNickname());
        mensagemDto.setHeroiVencedor(heroi.getNome());
        batalhaDto.setMensagem(mensagemDto);
    }

    public void empate(){
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\nEssa batalha nao vai acabar nunca!\n"));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat(espaco));
        batalhaDto.setBattleLog(batalhaDto.getBattleLog().concat("\n\n ========= A batalha terminou em Empate! ========="));
        mensagemDto.setStatusBatalha("Empate");
        mensagemDto.setPlayerVencedor("Empate");
        mensagemDto.setHeroiVencedor("Empate");
        batalhaDto.setMensagem(mensagemDto);
    }

}
