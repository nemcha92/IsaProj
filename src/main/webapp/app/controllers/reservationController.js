app.controller('reservationController', ['$http','$log', '$scope', '$location','$mdDialog', '$mdToast', 'res', 'loggedUser', 'friends','restaurantsService', 'reservationService', function($http, $log, $scope, $location, $mdDialog, $mdToast, res, loggedUser, friends, restaurantsService, reservationService) {
    

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

            restaurantsService.getTables(res).success(function(data){
                $scope.tables = data;
            });

            angular.forEach($scope.tables, function(table) {
                table.reserved = false;   
            });

            angular.forEach($scope.friends, function(frn) {
                frn.called = false;   
            });

            $log.info($scope.res);
            $log.info($scope.loggedUser);
            $log.info($scope.friends);
            $log.info($scope.tables);
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
                $scope.rows = $scope.getNumberList(res.numberOfRows);
                $scope.columns = $scope.getNumberList(res.numberOfColumns);
                $scope.reservation.time = selectedDate;
            }
            $log.info(selectedDate);
            $log.info($scope.date);
        }

        //MAKE RESERVATION
        $scope.finish = function(){
            
            var reservedTables = [];
            var i =0;
            $log.info($scope.tables);

            for(i; i<$scope.tables.length; ++i){
                if($scope.tables[i].reserved){
                    delete $scope.tables[i].reserved;
                    reservedTables.push($scope.tables[i]);
                }
            }

            var calledFriends = [];
            var j=0;
            for(j; j<$scope.friends.length; ++j){
                if($scope.friends[j].called){
                    delete $scope.friends[j].called;
                    calledFriends.push($scope.friends[j]);
                }
            }

            $scope.reservation.user = $scope.loggedUser;
            $scope.reservation.tables = reservedTables;
            $scope.reservation.restaurant = $scope.res;
            $log.info($scope.reservation);

            reservationService.createReservation($scope.loggedUser.username, $scope.res.name, $scope.reservation, calledFriends).success(function(data){
                $scope.showSimpleToast("Reservation successful")
                $log.info('reservation controller : SUCCESS RESERVATION');
                $log.info(data);
                $mdDialog.hide();
            });

        }

        $scope.getNumberList = function(num){
            $log.info(num);

            var i = 1;
            retVal = [];
            for(i ; i<=num; ++i){
                retVal.push(i);
            }

            $log.info(retVal);
            return retVal;
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
    