app.controller("GameController", function($scope, BatalhaService, $location){

    const self = this;

	self.service = BatalhaService;

	self.findMatch = function(){
		self.ready = !self.ready;
		self.service.findMatch().success(res => {
			self.oponentes = res;
		}).error(data =>{
			alertify.error("Ocorreu um erro ao buscar os oponenes!");
			$location.path("/login");
		});
	}

	self.bttPlayer = function(id){
		$location.path("/batalhar/"+id)
	}

	self.bttBot = function(){
		$location.path("/batalhar/")
	}

	self.voltar = function(){
		self.ready = !self.ready
	}

})