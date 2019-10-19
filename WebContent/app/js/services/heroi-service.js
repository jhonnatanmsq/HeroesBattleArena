app.factory("HeroiService", function ($http, config) {

	var _getHerois = function () {
		return $http.get(`${config.apiUrl}/heroi`);
    };
    
    return {
		getHerois: _getHerois
	};	
});