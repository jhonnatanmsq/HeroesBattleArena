const app = angular.module("heroi", ["ngMessages", "ngRoute", "ngCookies"]);

app.constant('config', {
    apiUrl: 'http://localhost:8080/curso-hackaton-cdi'
});

app.run(function Run($rootScope, $location, LoginService) {
    $rootScope.$on('$routeChangeStart', function (evt, route) {
        if (route.originalPath !== "/" && route.originalPath !== "/login") {
            if (!LoginService.jogador) {
                $location.path("/");
            }else{
                $location.path("/game");
            }
        } else {
            if (LoginService.jogador) {
                $location.path("/game");
            }
        }
    });
});


