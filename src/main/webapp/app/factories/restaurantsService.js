app.service('restaurantsService', function($http){
	var url = '/resources/restaurant/';
	var url2 = 'restaurants/';
	return{
		create: function(restaurant){

		},

		update: function(restaurant){
			
		},

		list: function(){
			return $http.get(url2);
		},

		delete: function(restaurant){
			return $http.delete(restaurant._links.self.href);
		},

		getById: function(id){
			return $http.get(url2+id)
		}

	}

});