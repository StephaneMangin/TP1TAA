'use strict';

angular.module('todoappApp')
    .controller('TODOItemDetailController', function ($scope, $rootScope, $stateParams, entity, TODOItem) {
        $scope.tODOItem = entity;
        $scope.load = function (id) {
            TODOItem.get({id: id}, function(result) {
                $scope.tODOItem = result;
            });
        };
        $rootScope.$on('todoappApp:tODOItemUpdate', function(event, result) {
            $scope.tODOItem = result;
        });
    });
