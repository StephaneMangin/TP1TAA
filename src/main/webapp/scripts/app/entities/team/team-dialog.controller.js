'use strict';

angular.module('todoappApp').controller('TeamDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Team', 'Task',
        function($scope, $stateParams, $modalInstance, entity, Team, Task) {

        $scope.team = entity;
        $scope.tasks = Task.query();
        $scope.load = function(id) {
            Team.get({id : id}, function(result) {
                $scope.team = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('todoappApp:teamUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.team.id != null) {
                Team.update($scope.team, onSaveFinished);
            } else {
                Team.save($scope.team, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
