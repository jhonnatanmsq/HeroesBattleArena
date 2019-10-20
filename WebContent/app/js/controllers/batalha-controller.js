app.controller("BatalhaController", function($scope, BatalhaService, $location, $routeParams, $cookies, AuthService){

    const self = this;

	self.service = BatalhaService;

	self.id = $routeParams.id;

	self.id === undefined ? self.tipo = "BOT" : self.tipo = "Player";

	self.findPlayer = function(){
		self.service.findPlayer(self.id).success(res =>{
			self.oponente = res;
		}).error(data =>{			
			alertify.error(data.menssagem);
			self.authError();
		});		
	}

	self.findBot = function(){
		self.oponente = {
			nickname : "BOT",
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
	}
	
    self.batalharBot = function(){
		self.ready = !self.ready;
		self.service.botBattle().success(res =>{
			self.batalha = res;
			self.oponente = res.oponente;
			self.oponente.heroi.vida = "10000000";
		}).error(data => {			
			alertify.error("Ocorreu um erro ao realizar a batalha!");
			self.authError();
		});
	}

	self.batalharPlayer = function(){
		self.ready = !self.ready;
		self.service.playerBattle(self.id).success(res =>{
			self.batalha = res;
		}).error(data => {			
			alertify.error("Ocorreu um erro ao realizar a batalha!");
			self.authError();
		});
	}

	self.voltar = function(){

		if(!self.ready){
			$location.path("/game");
		}
		
			
		self.ready = !self.ready;
	}
	
	self.authError = function(){
		delete sessionStorage.jogador;
		delete sessionStorage.JSESSIONID;
		delete $cookies.JSESSIONID;
		$cookies.JSESSIONID = null;
		AuthService.jogador = null;
		$location.path("/");
		alertify.error("Por favor, efetue o login novamente!");
	}
	
})