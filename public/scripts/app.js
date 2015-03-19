'use strict';

angular
	.module('threshold', [])
	.controller('ThresholdCtrl', ['$scope', '$http', function ($scope, $http){
		
		init();

		$scope.createWalletBtn = function () {
			$scope.isCreateWallet = true;
			$scope.isSendMoney = false;
			$scope.isSent = false;
		};

		$scope.sendMoneyBtn = function () {
			$scope.isSent = false;
			$scope.isSendMoney = true;
			$scope.isCreateWallet = false;
		};

		$scope.send = function () {
			$scope.isSent = true;
			$scope.sendMoney = {}
		};

		function init() {
			// show the create wallet form
			$scope.isCreateWallet = false;
			
			// show the send money form	
			$scope.isSendMoney = false;

			// check if the money is being sent
			$scope.isSent = false;

			// bitcoin balance
			$scope.btc = 0.0001;
		}


	}]);
