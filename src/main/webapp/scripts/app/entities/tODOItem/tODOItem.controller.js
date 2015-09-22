'use strict';

angular.module('todoappApp')
    .controller('TODOItemController', function ($scope, TODOItem, ParseLinks) {
        $scope.tODOItems = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TODOItem.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tODOItems.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.tODOItems = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TODOItem.get({id: id}, function(result) {
                $scope.tODOItem = result;
                $('#deleteTODOItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TODOItem.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteTODOItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tODOItem = {content: null, endDate: null, done: null, id: null};
        };
    });
