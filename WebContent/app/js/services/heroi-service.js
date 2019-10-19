app.factory("HeroiService", function ($http, config) {

	var _getHerois = function () {
		return $http.get(`${config.apiUrl}/heroi`);
	};
	
	var _getHeroi = function(id) {
		return $http.get(`${config.apiUrl}/heroi/${id}`)
	}
    
    return {
		getHerois: _getHerois,
		getHeroi: _getHeroi
	};	
});