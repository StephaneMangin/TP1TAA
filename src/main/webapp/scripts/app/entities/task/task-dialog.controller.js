'use strict';

angular.module('todoappApp').controller('TaskDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Task', 'Owner',
        function($scope, $stateParams, $modalInstance, entity, Task, Owner) {

        $scope.task = entity;
        $scope.owners = Owner.query();
        $scope.load = function(id) {
            Task.get({id : id}, function(result) {
                $scope.task = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('todoappApp:taskUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.task.id != null) {
                Task.update($scope.task, onSaveFinished);
            } else {
                Task.save($scope.task, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
