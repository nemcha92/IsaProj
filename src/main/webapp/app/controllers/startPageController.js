app.controller('startPageController', ['$http','$log', '$scope', '$location','$mdDialog', '$mdToast', 'restaurantsService', 'userService', 'reservationService', function($http, $log, $scope, $location, $mdDialog, $mdToast, restaurantsService, userService, reservationService) {

	$scope.restaurants = [];
	$scope.loggedUser = [];
	$scope.nonFriends = [];
	$scope.friends = [];
	$scope.tables = [];

	$scope.propertyName = 'name';
	$scope.reverse = true;


	$scope.init = function(){

		getRestaurants();

		userService.getLoggedUser().success(function(data){
			$scope.loggedUser = data;
			$scope.role = $scope.loggedUser.role;
			$log.info($scope.role);

			getReservationsForUser()
			getFriends();
			getNonFriends();
		});

	};

	var getReservationsForUser = function(){
		reservationService.getReservations($scope.loggedUser.username).success(function(data){
			$log.info('RESERVATIONS FOR: '+$scope.loggedUser.username);
			$log.info(data);
		});
	}

	//SORT
	$scope.sortBy = function(propertyName) {
		$scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
		$scope.propertyName = propertyName;
	  };


	//GET RESTAURANTS
	var getRestaurants = function(){
		restaurantsService.list().success(function(data){
			if(data._embedded != undefined){
				$scope.restaurants = data._embedded.restaurants;
				angular.forEach($scope.restaurants, function(res){
					restaurantsService.menuList(res._links.menus.href).success(function(data){
						if(data._embedded != undefined) {
							res.menus = data._embedded.menus;
							angular.forEach(res.menus, function(menu){
									restaurantsService.mealList(menu._links.meals.href).success(function(data){
										$log.info(menu._links.meals.href);
										$log.info(data._embedded.meals);
										menu.meals = data._embedded.meals;
									});
							})
						}

					}); 
				});
			}
		});
	};


	//GET FRIENDS
	var getFriends = function(){
		userService.getFriends().success(function(data){
			$scope.friends = data;
		});
	};


	//GET NONFRIENDS
	var getNonFriends = function(){
		userService.getNonFriends().success(function(data){
			$scope.nonFriends = data;
		});
	};

	//LOGOUT
	$scope.logout = function(){
		userService.logout().success(function(data){
			$location.path('/');
		});
	};


	//DELETE RESTAURANT
	$scope.deleteRestaurant = function(restaurant){
		var confirm = $scope.showConfirm('Are you sure you want to delete '+restaurant.name+ '?', 'NOTE: This cannot be undone', '', 'Yes', 'No');

		$mdDialog.show(confirm).then(function() {
				restaurantsService.delete(restaurant).success(function(data){
					for(var i = 0; i < $scope.restaurants.length; ++i){
						if($scope.restaurants[i].name === restaurant.name){
							$scope.restaurants.splice(i, 1);
							break;
						}
					}

					$scope.showSimpleToast(restaurant.name+' successfully deleted');
				});
				
			}, function() {
				$log.info('You decided No');
			});
	};

	//ADD FRIEND
	$scope.addFriend = function(userToAdd){
		userService.addFriend(userToAdd).success(function(data){
			$scope.friends.push(userToAdd);

			for(var i = 0; i < $scope.nonFriends.length; ++i){
				if($scope.nonFriends[i].username === userToAdd.username){
					$scope.nonFriends.splice(i, 1);
					break;
				}
			}

			$scope.showSimpleToast(userToAdd.name+' added to friends');

	});
	};

	//REMOVE FRIEND
	$scope.removeFriend = function(frn){
		userService.removeFriend(frn).success(function(data){
			$scope.nonFriends.push(frn);

			for(var i = 0; i < $scope.friends.length; ++i){
				if($scope.friends[i].username === frn.username){
					$scope.friends.splice(i, 1);
					break;
				}
			}

			$scope.showSimpleToast(frn.name+' removed from friends');
			
		});
	};

	//UPDATE USER PROFILE
	$scope.updateProfile = function() {

		if($scope.loggedUser.name == '' || $scope.loggedUser.surname == '' || $scope.loggedUser.address == ''){
			$scope.showAlertDialog('Bad input', 'You must enter all data', 'OK');
		}else{
			userService.updateUserProfile($scope.loggedUser.name, $scope.loggedUser.surname, $scope.loggedUser.address).success(function(data){
				$scope.showSimpleToast('Profile updated');
				$log.info(data);
			});

		}

	};

	$scope.showRestaurant = function(event, res){
		if(res == undefined){
			$scope.selectedRestaurant = null;
			$log.info('new restaurant');
		} else {
			$log.info(res);
			$scope.selectedRestaurant = res;
			$scope.showRestaurantForReal(event);
		}
	};

	$scope.showRestaurantForReal = function(event){

		var showRestDialog = $mdDialog.show({
			controller : 'restaurantController',
			controllerAs : 'restaurantController',
			templateUrl : 'app/partials/restaurantDialog.html',
			parent : angular.element(document.body),
			targetEvent : event,
			locals : {
				res : $scope.selectedRestaurant
			}
		}).then(function(answer){
			
		})

	};

	$scope.reservation = function(event, restaurant){
		$log.info('Reservation for '+ restaurant.name);

		$mdDialog.show({
			controller : 'reservationController',
			controllerAs : 'reservationController',
			templateUrl : 'app/partials/reservationDialog.html',
			parent : angular.element(document.body),
			targetEvent : event,
			locals : {
				res : restaurant,
				loggedUser : $scope.loggedUser,
				friends : $scope.friends
			}
		}).then(function(answer){
			
		})
	}

	//TODO implement
	$scope.showFriend = function(frn){
		$log.info('Show friend: '+frn.name);
	};


	//SHOW CONFIRM
	$scope.showConfirm = function(question, textContent, ariaLabel, ok, cancel) {
    var confirm = $mdDialog.confirm()
          .title(question)
          .content(textContent)
          .ariaLabel(ariaLabel)
          .ok(ok)
          .cancel(cancel);

		  return confirm;
  };

  //SHOW ALERT DIALOG
  $scope.showAlertDialog = function(title, contentText, ok){
		$mdDialog.show($mdDialog.alert()
						.title(title)
						.content(contentText)
						.ok(ok));
	};

	//MD TOAST
	$scope.toastPosition = {
				    bottom: false,
				    top: true,
				    left: false,
				    right: true
				};
	
	$scope.getToastPosition = function() {
	    return Object.keys($scope.toastPosition)
	      .filter(function(pos) { return $scope.toastPosition[pos]; })
	      .join(' ');
	};
		
	$scope.showSimpleToast = function(contentText) {
	    $mdToast.show(
	      $mdToast.simple()
	        .content(contentText)
	        .position($scope.getToastPosition())
	        .hideDelay(5000)
	    );
	};

}]);