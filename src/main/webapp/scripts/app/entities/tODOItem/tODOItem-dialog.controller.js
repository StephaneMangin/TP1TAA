'use strict';

angular.module('todoappApp').controller('TODOItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TODOItem', 'Owner',
        function($scope, $stateParams, $modalInstance, entity, TODOItem, Owner) {

        $scope.tODOItem = entity;
        $scope.owners = Owner.query();
        $scope.load = function(id) {
            TODOItem.get({id : id}, function(result) {
                $scope.tODOItem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('todoappApp:tODOItemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.tODOItem.id != null) {
                TODOItem.update($scope.tODOItem, onSaveFinished);
            } else {
                TODOItem.save($scope.tODOItem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
