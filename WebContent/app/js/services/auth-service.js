app.factory("AuthService", function ($http, config, $cookies) {

	var jogador = !sessionStorage.jogador ? null : JSON.parse(sessionStorage.jogador);
	
	!sessionStorage.JSESSIONID ? null : $cookies.JSESSIONID = sessionStorage.JSESSIONID;


	var _login = function(jogador){
		return $http.post(`${config.apiUrl}/jogador/auth`, jogador);
    }
	
	var _logout = function(){
		return $http.get(`${config.apiUrl}/jogador/logout`);
	}	

    return{
        login : _login,
        logout : _logout,
        jogador
    };
});