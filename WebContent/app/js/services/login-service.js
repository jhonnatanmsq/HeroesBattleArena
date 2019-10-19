app.factory("LoginService", function ($http, config, $cookies) {

	var jogador = !sessionStorage.jogador ? null : JSON.parse(sessionStorage.jogador);
	
	!sessionStorage.JSESSIONID ? null : $cookies.JSESSIONID = sessionStorage.JSESSIONID;

	var opts = {
		withCredentials : true
	}

	var _login = function(jogador){
		return $http.post(`${config.apiUrl}/jogador/auth`, jogador);
	}

	var _cadastrar = function(jogador){
		return $http.post(`${config.apiUrl}/jogador`, jogador);
	}

	var _batalhar = function(){
		return $http.get(`${config.apiUrl}/batalhar/bot`, opts);
	}

	return {
		login: _login,
		cadastrar: _cadastrar,
		batalhar : _batalhar,
		jogador
	};	
});