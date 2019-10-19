app.factory("BatalhaService", function ($http, config) {

    var opts = {
		withCredentials : true
	}

	var _findMatch = function () {
		return $http.get(`${config.apiUrl}/batalhar/find`, opts);
    };

    var _botBattle = function(){
        return $http.get(`${config.apiUrl}/batalhar/bot`, opts);
    }

    var _playerBattle = function(id){
        return $http.get(`${config.apiUrl}/batalhar/${id}`, opts)
    }
    
    return {
        findMatch: _findMatch,
        botBattle: _botBattle,
        playerBattle: _playerBattle
	};	
});