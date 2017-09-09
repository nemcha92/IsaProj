app.controller('reservationController', ['$http','$log', '$scope', '$location','$mdDialog', '$mdToast', 'res', 'loggedUser', 'friends', function($http, $log, $scope, $location, $mdDialog, $mdToast, res, loggedUser, friends) {
    

        $scope.init = function(){
            $scope.res = res;
            $scope.loggedUser = loggedUser;
            $scope.friends = friends;
            $scope.date = new Date();
            $scope.reservation = {};
            $scope.reservation.duration = 0.5;
            $scope.reservation.time = "";
            $scope.isNext = true;
            $scope.isFinish = false;
            $scope.nextDisabled = true;
            $scope.finishDisabled = true;
    

            $log.info($scope.res);
            $log.info($scope.loggedUser);
            $log.info($scope.friends);
        }
    
        $scope.closeDialog = function(){
            $mdDialog.hide();
        }
    
        $scope.next = function(){
            var selectedDate = new Date($scope.reservation.time);
            if(!selectedDate || selectedDate== 'Invalid Date' || selectedDate < $scope.date){
                $scope.showSimpleToast("Invalid date")
            }else{
                $scope.isNext = false;
                $scope.isFinish = true;
            }
            $log.info(selectedDate);
            $log.info($scope.date);
        }

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
    