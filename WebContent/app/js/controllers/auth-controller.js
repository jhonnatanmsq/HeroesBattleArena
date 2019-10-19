app.controller("AuthController", function($scope, AuthService, $cookies, $location){

    const self = this;

    self.service = AuthService;

    self.logar = function(jogador){
		jogador.senha = btoa(jogador.senha);
		self.service.login(jogador).success((res, status, headers) => {
			self.service.jogador = res;
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
    
    self.logOut = function(){
		delete sessionStorage.jogador;
		delete sessionStorage.JSESSIONID;
		delete $cookies.JSESSIONID;
		$cookies.JSESSIONID = null;
		self.service.jogador = null;
		$location.path("/");
		alertify.success("Até mais!");
	}

})