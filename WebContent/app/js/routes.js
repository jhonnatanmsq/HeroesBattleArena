app.config(['$routeProvider',
  function ($routeProvider) {
        $routeProvider
        .when('/', {
            title: 'Home',
            templateUrl: 'app/js/pages/home.html'
        })
        .when('/login', {
            title: 'Login',
            templateUrl: 'app/js/pages/auth.html'
        })
        .when('/game', {
            title: 'Game',
            templateUrl: 'app/js/pages/game.html'
        })
        .when('/batalhar', {
            title: 'Batalha',
            templateUrl: 'app/js/pages/batalha.html'
        })

        .otherwise({
            redirectTo: '/'
        });
}]);
