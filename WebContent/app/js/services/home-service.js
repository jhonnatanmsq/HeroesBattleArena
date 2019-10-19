app.factory("HomeService", function ($http, config, $cookies) {

	var _cadastrar = function(jogador){
		return $http.post(`${config.apiUrl}/jogador`, jogador);
	}

	return {
		cadastrar: _cadastrar
	};	
});