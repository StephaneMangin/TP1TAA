'use strict';

angular.module('todoappApp')
    .controller('OwnerDetailController', function ($scope, $rootScope, $stateParams, entity, Owner, TODOItem, User) {
        $scope.owner = entity;
        $scope.load = function (id) {
            Owner.get({id: id}, function(result) {
                $scope.owner = result;
            });
        };
        $rootScope.$on('todoappApp:ownerUpdate', function(event, result) {
            $scope.owner = result;
        });
    });
