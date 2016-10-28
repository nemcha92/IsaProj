app.controller('registerController',[ '$scope', '$window', 'registerFactory', '$log', '$mdToast','$mdDialog', '$mdMedia', function($scope, $window, registerFactory, $log, $mdToast,$mdDialog, $mdMedia){
	
	function init(){
			
	}
	
	$scope.register = function(name, surname, username, password, repeatPassword){

		if(password !== repeatPassword){
			$scope.showAlertDialog('No match', 'Password and Repeat password don\'t match', 'OK');
			return;
		}

		registerFactory.registerUser(name, surname, username, password, repeatPassword).success(function(data, status){
			if(status == 200){
		 		$scope.showSimpleToast('Confirmation email sent at '+ $scope.username);
		 		$scope.restoreForm();
			}
		}).error(function(status, data, headers, config){
			if(data == 406){	//Not acceptable from server
				$scope.showAlertDialog('No match', 'Password and Repeat password don\'t match', 'OK');
			}
			if(data == 405){	//Not allowed
				$scope.showAlertDialog('Alert', 'Email address is not in right format', 'OK' );
			}	
		});
	};
	
	init();


	$scope.restoreForm = function(){
		$scope.name = null;
		$scope.surname = null;
		$scope.username = null;
		$scope.password = null;
		$scope.repeatPassword = null;
	};
	
	$scope.toastPosition = {
		    bottom: false,
		    top: true,
		    left: false,
		    right: true
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