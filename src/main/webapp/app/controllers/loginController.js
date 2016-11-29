app.controller('loginController', ['$scope', '$window', 'loginFactory', '$location', '$log', '$mdDialog', function($scope, $window, loginFactory, $location, $log, $mdDialog){
	
	function init(){

	}
	
	$scope.login = function(username, password){

		loginFactory.loginUser(username, password).success(function(data, status){
			if(status=='200'){
				$log.info('login status is 200');
				$location.path('/startPage');
			}
		}).error(function(status, data, headers, config){
			if(data == 404){	//Not acceptable from server
				$scope.showAlertDialog('No match', 'Username not found', 'OK');
			}
			if(data == 400){	//Not acceptable from server
				$scope.showAlertDialog('Bad request', 'Wrog password or not activated', 'OK');
			}
		});

	}


	init();

	$scope.toRegisterPage = function(){
		$location.path('/register');
	};
	

	$scope.showAlertDialog = function(title, contentText, ok){
		$mdDialog.show($mdDialog.alert()
						.title(title)
						.content(contentText)
						.ok(ok));
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