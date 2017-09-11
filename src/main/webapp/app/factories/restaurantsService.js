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

		menuList: function(menuUrl){
			return $http.get(menuUrl);
		},

		mealList: function(mealUrl){
			return $http.get(mealUrl);
		},

		delete: function(restaurant){
			return $http.delete(restaurant._links.self.href);
		},

		getById: function(id){
			return $http.get(url2+id);
		},
		
		getTables: function(restaurant){
			return $http.get(url+restaurant.name+'/tables');
		}

	}

});