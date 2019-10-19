app.controller("HeroiController", function ($scope, HomeService, HeroiService) {
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

        });
    }

    self.getHeroi = function(id){
        HeroiService.getHeroi(id).success(res =>{
            self.heroiSelec = res;
        }).error(data => {
            
        });
    }

});