'use strict';

angular.module('todoappApp').controller('OwnerDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Owner', 'User', 'TODOItem',
        function($scope, $stateParams, $modalInstance, $q, entity, Owner, User, TODOItem) {

        $scope.owner = entity;
        $scope.users = User.query();
        $scope.todoitems = TODOItem.query();
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
