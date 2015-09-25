'use strict';

angular.module('todoappApp')
    .controller('OwnerController', function ($scope, Owner, ParseLinks) {
        $scope.owners = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Owner.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.owners.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.owners = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Owner.get({id: id}, function(result) {
                $scope.owner = result;
                $('#deleteOwnerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Owner.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteOwnerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.owner = {name: null, id: null};
        };
    });
