app.controller("HeroiController", function ($scope, HeroiService, $location) {
    const self = this;


    self.init = function(){

        self.heroiSelec = {
				vida : "????",
				ataque : "????",
				forca : "????",
				inteligencia : "????",
				defesa : "????",
				velocidade : "????",
				poder : "????",
				nome : "????"
		}
        HeroiService.getHerois().success(res => {
            self.herois = res;
        }).error(data =>{
            alertify.error("Ocorreu um erro!");
            $location.path("/");
        });
    }

    self.getHeroi = function(id){
        HeroiService.getHeroi(id).success(res =>{
            self.heroiSelec = res;
        }).error(data => {            
            alertify.error("Ocorreu um erro!");
            $location.path("/");
        });
    }

});