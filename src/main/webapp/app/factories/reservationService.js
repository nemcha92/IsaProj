app.service('reservationService', function($http, $log){
	var url = '/resources/reservation';

	return{
		createReservation: function(username, restaurantName, reservation, friends){
            var indata = {reservation:reservation, friends:friends}
			$log.info('USERNAME: ' + username);
			$log.info('RESTAURANT: '+restaurantName);
			var customUrl = url + '/'+username+"/"+restaurantName;
			$log.info(customUrl);

			return  $http.post(customUrl, JSON.stringify({'reservation':reservation, 'friends':friends}));
		},

		update: function(){
			
		}

	}

});