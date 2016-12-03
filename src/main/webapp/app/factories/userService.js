app.service('userService', function($http){
	var url = '/resources/users';
	var url2 ='/users/' 
	return{
		create: function(restaurant){

		},

		update: function(restaurant){
			
		},

		list: function(){
			return $http.get(url);
		},

		delete: function(){
		},

		getLoggedUser: function(){
			return $http.get(url+ '/getLoggedUser');
		},

		logout: function(username){
			return $http.get(url+ '/logout');
		},

		getNonFriends: function(){
			return $http.get(url+'/nonFriends');
		},

		addFriend: function(user){
			return $http.post(url+ '/addFriend', user);
		},

		getFriends: function(){
			return $http.get(url+ '/friends');
		},

		removeFriend: function(frn){
			return $http.post(url+ '/removeFriend', frn);
		}

	}

});