app.controller("BatalhaController", function($scope, BatalhaService, $location, $routeParams){

    const self = this;

	self.service = BatalhaService;

	self.id = $routeParams.id;

	self.init = function(){
		self.service.findPlayer(self.id).success(res =>{
			self.oponente = res;
		}).error(data =>{
			alertify.error(data.menssagem);
		});
		
	}
	
    self.batalharBot = function(){
		self.service.botBattle().success(res =>{
			self.batalha = res;
		});
	}

	self.batalharPlayer = function(){
		self.service.playerBattle(self.id).success(res =>{
			self.batalha = res;
		});
	}

	
})