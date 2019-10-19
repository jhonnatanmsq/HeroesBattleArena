app.factory("BatalhaService", function ($http, config) {

	var _findMatch = function () {
		return $http.get(`${config.apiUrl}/batalhar/find`);
    };

    var _botBattle = function(){
        return $http.get(`${config.apiUrl}/batalhar/bot`);
    }

    var _playerBattle = function(id){
        return $http.get(`${config.apiUrl}/batalhar/${id}`)
    }
    
    return {
        findMatch: _findMatch,
        botBattle: _botBattle,
        playerBattle: _playerBattle
	};	
});