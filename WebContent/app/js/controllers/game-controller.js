app.controller("GameController", function($scope, BatalhaService, $location){

    const self = this;

	self.service = BatalhaService;

	self.findMatch = function(){
		self.ready = !self.ready;
		self.service.findMatch().success(res => {
			self.oponentes = res;
		}).error(data =>{
			alertify.error("Ocorreu um erro ao buscar os oponenes!");
		});
	}

	self.bttPlayer = function(id){
		$location.path("/batalhar/"+id)
	}

	self.voltar = function(){
		self.ready = !self.ready
	}

})