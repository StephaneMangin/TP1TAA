'use strict';

angular.module('todoappApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
