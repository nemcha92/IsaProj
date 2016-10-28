app.service('restaurantsService', function($http){
	var url = '/resources/restaurants';
	return{
		create: function(restaurant){

		},

		update: function(restaurant){
			
		},

		list: function(){
			return $http.get(url);
		}

	}

});