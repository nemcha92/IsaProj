app.factory('registerFactory', function($http){
	var factory={};
	
	factory.registerUser = function(name, surname, username, password, repeatPassword){
		return $http.post('/user/register',
						 JSON.stringify({'name':name, 'surname':surname, 'username':username, 'password':password, 'repeatPassword':repeatPassword}));
	};

	return factory;
});