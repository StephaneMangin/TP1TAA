'use strict';

angular.module('todoappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


