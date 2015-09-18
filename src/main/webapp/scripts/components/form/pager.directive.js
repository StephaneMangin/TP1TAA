/* globals $ */
'use strict';

angular.module('todoappApp')
    .directive('todoappAppPager', function() {
        return {
            templateUrl: 'scripts/components/form/pager.html'
        };
    });
