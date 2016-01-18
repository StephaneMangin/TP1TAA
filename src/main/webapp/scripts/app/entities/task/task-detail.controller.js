'use strict';

angular.module('todoappApp')
    .controller('TaskDetailController', function ($scope, $rootScope, $stateParams, entity, Task, Owner) {
        $scope.task = entity;
        $scope.load = function (id) {
            Task.get({id: id}, function(result) {
                $scope.task = result;
            });
        };
        $rootScope.$on('todoappApp:taskUpdate', function(event, result) {
            $scope.task = result;
        });
    });
