app.controller('restaurantController', ['$http','$log', '$scope', '$location','$mdDialog', '$mdToast', 'res', function($http, $log, $scope, $location, $mdDialog, $mdToast, res) {

	
    init();

    function init(){
        $scope.res = res;
        $log.info('From restaurantController: HELLO');
        $log.info($scope.res);
    }

    $scope.closeDialog = function(){
        $log.info('CLOSE DIALOG');
        $mdDialog.hide();
    }


}]);
