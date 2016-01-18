'use strict';

angular.module('todoappApp')
    .controller('TeamDetailController', function ($scope, $rootScope, $stateParams, entity, Team, Task) {
        $scope.team = entity;
        $scope.load = function (id) {
            Team.get({id: id}, function(result) {
                $scope.team = result;
            });
        };
        $rootScope.$on('todoappApp:teamUpdate', function(event, result) {
            $scope.team = result;
        });
    });
