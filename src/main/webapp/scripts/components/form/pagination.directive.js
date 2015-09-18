/* globals $ */
'use strict';

angular.module('todoappApp')
    .directive('todoappAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
