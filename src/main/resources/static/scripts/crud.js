let app = angular.module('bookApp',['ngResource']);

app.factory('Users', function ($resource) {
    return $resource('http://localhost:8080/admin/users/:id', {id: '@uid'},{
        update: {
            method: 'PUT'
        }
    });
});

app.factory('User', function($resource) {
    return $resource('http://localhost:8080/user', {}, {
        update: {
            method: 'PUT'
        },

        enable: {
            method: 'PUT',
            url: 'http://localhost:8080/admin/users/enable'
        }
    });
});

app.controller('UserController', function ($scope, User) {

    $scope.user = User.get();
    console.log($scope.user);

    $scope.edit = function () {
        console.log("edit");
        User.update($scope.user, function() {
            console.log("updating");
        })
    };
});

app.controller('UsersController', function($scope, Users) {

    console.log('controller');

    $scope.users = Users.query();
    $scope.user = {};

    // $scope.update = function() {
    //     $scope.users = Users.query();
    // };

    $scope.edit = function () {
        console.log("edit");
        Users.update($scope.user, function () {
             $scope.users = Users.query();
        })
    };

    $scope.delete = function(user) {
        user.$delete({id: user.id}, function () {
            console.log('deleting');
            $scope.users = Users.query();
        })
    };

    $scope.enable = function() {
        console.log("enabling");
        Users.enable($scope.user, function () {
            $scope.users = Users.query();
        })
    };

    $scope.update = function (user) {
        console.log("update");
        $scope.options = ['M','F'];
        $scope.user = user;
    }

});