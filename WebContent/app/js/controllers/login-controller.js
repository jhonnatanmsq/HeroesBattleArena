app.controller("LoginController", function ($scope, LoginService, $cookies, $location, HeroiService) {
	var vm = this;
	vm.app = "Heroes Battle Arena";
	vm.service = LoginService;

	vm.jogador = {
		nickname : '',
		senha: ''
	}
	
	vm.init = function(){
		vm.carregarHerois();
	}

	vm.carregarHerois = function () {
		HeroiService.getHerois().success(function (data) {
			vm.herois = data;
		}).error(function (data, status) {
			alertify.error(data.menssagem);
		});
	};

	vm.logar = function(jogador){
		jogador.senha = btoa(jogador.senha);
		vm.service.login(jogador).success((res, status, headers) => {
			vm.service.jogador = res;
			sessionStorage.jogador = JSON.stringify(res);
			sessionStorage.JSESSIONID = headers("JSESSIONID");
			$cookies.JSESSIONID = sessionStorage.JSESSIONID;
			$location.path("/game");
			alertify.success("Logado com sucesso!");
		}).error(function (data, status) {
			alertify.error(data.menssagem);
			jogador.senha = atob(jogador.senha);
		});
	}

	vm.cadastrar = function(jogador){
		jogador.senha = btoa(jogador.senha);
		vm.service.cadastrar(jogador).success(res =>{
			alertify.success(jogador.nickname + " cadastrado com sucesso!");
			
			jogador.nickname = '';
			jogador.senha = '';
			jogador.heroi = '';
			vm.logar(jogador);
		}).error(function (data, status) {
			alertify.error(data.menssagem);
			jogador.senha = atob(jogador.senha);
		});
	}

	vm.logOut = function(){
		delete sessionStorage.jogador;
		delete sessionStorage.JSESSIONID;
		delete $cookies.JSESSIONID;
		$cookies.JSESSIONID = null;
		vm.service.jogador = null;
		$location.path("/");
		alertify.success("Até mais!");
	}

	vm.batalhar = function(){
		vm.service.batalhar().success(res =>{
			vm.batalha = res;
		});
	}
});