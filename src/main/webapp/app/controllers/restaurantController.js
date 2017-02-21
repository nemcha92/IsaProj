app.controller('restaurantController', ['$http','$log', '$scope', '$location','$mdDialog', '$mdToast', 'res', function($http, $log, $scope, $location, $mdDialog, $mdToast, res) {

	
    init();

    function init(){
        $scope.res = res;
        $log.info('From restaurantController:');
        $log.info($scope.res);
    }


}]);
