app.factory("JogadorService", function ($http, config) {

	var _getJogadores = function(){
		return $http.get(`${config.apiUrl}/jogador`);
    }
    
    var _getJogador = function(id){
        return $http.get(`${config.apiUrl}/jogador/${id}`);
    }

	return {
        getJogadores: _getJogadores,
        getJogador: _getJogador
	};	
});