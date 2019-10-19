app.controller("HomeController", function ($scope, HomeService, HeroiService) {
	const self = this;
	self.app = "Heroes Battle Arena";

	self.service = HomeService;

	self.jogador = {
		nickname : '',
		senha: ''
	}
	
	self.init = function(){
		self.carregarHerois();
	}

	self.carregarHerois = function () {
		HeroiService.getHerois().success(res => {
			self.herois = res;
		}).error(data =>{
			alertify.error(data.menssagem);
		});
	};	

	self.cadastrar = function(jogador){
		jogador.senha = btoa(jogador.senha);
		self.service.cadastrar(jogador).success(res =>{
			alertify.success(jogador.nickname + " cadastrado com sucesso!");
			
			jogador.nickname = '';
			jogador.senha = '';
			jogador.heroi = '';
			self.logar(jogador);
		}).error(data => {
			alertify.error(data.menssagem);
			jogador.senha = atob(jogador.senha);
		});
	}

	
});