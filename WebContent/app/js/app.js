const app = angular.module("heroi", ["ngMessages", "ngRoute", "ngCookies"]);

app.constant('config', {
    apiUrl: 'http://localhost:8080/curso-hackaton-cdi'
});

app.run(function Run($rootScope, $location, AuthService) {
    $rootScope.$on('$routeChangeStart', function (evt, route) {
        if (!AuthService.jogador && route.originalPath !== "/" && route.originalPath !== "/login") {
            $location.path("/");
        } else if(AuthService.jogador && (route.originalPath === "/" || route.originalPath === "/login")){
            $location.path("/game");
        }
    });
});


