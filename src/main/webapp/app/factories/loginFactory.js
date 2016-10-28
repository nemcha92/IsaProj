app.factory('loginFactory', function($http){
	var factory = {};

	factory.loginUser = function(username, password){
		return $http.post('/user/login',
						 JSON.stringify({'username':username, 'password':password}));
	};
	
	return factory;
});