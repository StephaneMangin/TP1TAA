'use strict';

angular.module('todoappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tODOItem', {
                parent: 'entity',
                url: '/tODOItems',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'todoappApp.tODOItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tODOItem/tODOItems.html',
                        controller: 'TODOItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tODOItem');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tODOItem.detail', {
                parent: 'entity',
                url: '/tODOItem/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'todoappApp.tODOItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tODOItem/tODOItem-detail.html',
                        controller: 'TODOItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tODOItem');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TODOItem', function($stateParams, TODOItem) {
                        return TODOItem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tODOItem.new', {
                parent: 'tODOItem',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tODOItem/tODOItem-dialog.html',
                        controller: 'TODOItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {content: null, endDate: null, done: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tODOItem', null, { reload: true });
                    }, function() {
                        $state.go('tODOItem');
                    })
                }]
            })
            .state('tODOItem.edit', {
                parent: 'tODOItem',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tODOItem/tODOItem-dialog.html',
                        controller: 'TODOItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TODOItem', function(TODOItem) {
                                return TODOItem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tODOItem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
