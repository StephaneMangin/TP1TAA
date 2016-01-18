'use strict';

angular.module('todoappApp')
    .controller('TaskController', function ($scope, Task, ParseLinks) {
        $scope.tasks = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Task.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tasks.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.tasks = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Task.get({id: id}, function(result) {
                $scope.task = result;
                $('#deleteTaskConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Task.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteTaskConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.task = {content: null, endDate: null, done: null, id: null};
        };
    });
