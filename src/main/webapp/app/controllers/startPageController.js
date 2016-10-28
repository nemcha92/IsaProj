app.controller('startPageController', ['$http','$log', '$scope', '$location', 'restaurantsService', 'userService', function($http, $log, $scope, $location, restaurantsService, userService) {

	$scope.init = function(){

		restaurantsService.list().success(function(data){
			$scope.restaurants = data;
		});

		userService.getLoggedUser().success(function(data){
			$scope.loggedUser = data;
			$scope.role = $scope.loggedUser.role;
		});

		userService.getNonFriends().success(function(data){
			$scope.nonFriends = data;
		});

		userService.getFriends().success(function(data){
			$scope.friends = data;
		});
	}

	$scope.logout = function(){
		userService.logout().success(function(data){
			$location.path('/');
		});
	}

	$scope.deleteRestaurant = function(restaurant){
		$log.info(restaurant);
	}


	$scope.addFriend = function(userToAdd){
		$log.info(userToAdd);
		userService.addFriend(userToAdd).success(function(data){
				userService.getNonFriends().success(function(data){
				$scope.nonFriends = data;
			});

		userService.getFriends().success(function(data){
			$scope.friends = data;
		});
		});
	}

	$scope.showRestaurant = function(res){
		if(res === undefined){
			$log.info('new restaurant')
		} else {
			$log.info(res.name);
		}
	}

}]);