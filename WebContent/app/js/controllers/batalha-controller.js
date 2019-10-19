app.controller("BatalhaController", function($scope, BatalhaService){

    const self = this;

	self.service = BatalhaService;

    self.batalhar = function(){
		self.service.botBattle().success(res =>{
			self.batalha = res;
		});
	}

	self.bttPlayer = function(){
		self.ready = !self.ready;
		self.service.findMatch().success(res => {
			self.oponentes = res;
			console.log(res)
		})
	}

	self.voltar = function(){
		self.ready = !self.ready
	}
})