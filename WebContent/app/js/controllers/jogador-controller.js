app.controller("JogadorController", function ($scope, JogadorService, AuthService, $location) {
    const self = this;
   
    self.auth = AuthService;

    self.init = function(){
        self.jogadorSelec = {
			nickname : "????",
			heroi : {
				vida : "????",
				ataque : "????",
				forca : "????",
				inteligencia : "????",
				defesa : "????",
				velocidade : "????",
				poder : "????",
				nome : "????"
			}
        }
        JogadorService.getJogadores().success(res =>{
            self.jogadores = res;
        }).error(data =>{
            alertify.error("Ocorreu um erro!");
            $location.path("/");
        });
    }

    self.getJogador = function(id){
        JogadorService.getJogador(id).success(res =>{
            self.jogadorSelec = res;
        }).error(data => {
            alertify.error("Ocorreu um erro!");
            $location.path("/");
        });
    }

});