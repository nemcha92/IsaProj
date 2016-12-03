var app = angular.module('app', ['ngRoute', 'ngMaterial']);

	app.config(function($routeProvider){
		$routeProvider.when('/',
				{
					templateUrl: 'app/partials/login.html'
				}).when('/register',
				{
					templateUrl: 'app/partials/register.html'
				}).when('/startPage', 
				{
					templateUrl: 'app/partials/startPage.html',
					controller: 'startPageController'
				}
				)
	});

app.config(function($logProvider){
    $logProvider.debugEnabled(true);
});