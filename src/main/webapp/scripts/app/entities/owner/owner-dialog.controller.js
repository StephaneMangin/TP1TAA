'use strict';

angular.module('todoappApp').controller('OwnerDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Owner', 'User', 'Task',
        function($scope, $stateParams, $modalInstance, $q, entity, Owner, User, Task) {

        $scope.owner = entity;
        $scope.users = User.query({filter: 'owner-is-null'});
        $q.all([$scope.owner.$promise, $scope.users.$promise]).then(function() {
            if (!$scope.owner.userId) {
                return $q.reject();
            }
            return User.get({id : $scope.owner.userId}).$promise;
        }).then(function(user) {
            $scope.users.push(user);
        });
        $scope.tasks = Task.query();
        $scope.load = function(id) {
            Owner.get({id : id}, function(result) {
                $scope.owner = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('todoappApp:ownerUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.owner.id != null) {
                Owner.update($scope.owner, onSaveFinished);
            } else {
                Owner.save($scope.owner, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
