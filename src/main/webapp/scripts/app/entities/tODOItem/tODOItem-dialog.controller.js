'use strict';

angular.module('todoappApp').controller('TODOItemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TODOItem',
        function($scope, $stateParams, $modalInstance, entity, TODOItem) {

        $scope.tODOItem = entity;
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
