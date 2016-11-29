app.controller('startPageController', ['$http','$log', '$scope', '$location','$mdDialog', 'restaurantsService', 'userService', function($http, $log, $scope, $location, $mdDialog, restaurantsService, userService) {

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
		var confirm = $scope.showConfirm('Are you sure you want to delete '+restaurant.name+ '?', 'NOTE: This cannot be undone', '', 'Yes', 'No');
		$log.info(confirm)

		$mdDialog.show(confirm).then(function() {
				$log.info('You decided Yes'); 
				$log.info('DELETE RESTAURANT: '+restaurant.name);
			}, function() {
				$log.info('You decided No');
			});

		
	}

	$scope.addFriend = function(userToAdd){

		$log.info('ADD FRIEND: '+userToAdd.name);
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

	//TODO implement
	$scope.showRestaurant = function(res){
		if(res === undefined){
			$log.info('new restaurant')
		} else {
			$log.info(res);
		}
	}

	//TODO implement
	$scope.showFriend = function(frn){
		$log.info('Show friend: '+frn.name);
	}

	$scope.removeFriend = function(frn){
		$log.info('REMOVE FRIEND: '+frn.name);
	}


	$scope.showConfirm = function(question, textContent, ariaLabel, ok, cancel) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.confirm()
          .title(question)
          .content(textContent)
          .ariaLabel(ariaLabel)
          .ok(ok)
          .cancel(cancel);

		  return confirm;
  };

}]);