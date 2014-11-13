/**
 * Created by George on 10/23/2014.
 */
hrApp.controller('DepartmentsListController', ['$scope', '$http', function($scope, $http){
    $scope.departments = [];
    $http({url: 'http://localhost:8080/app/mvc/department/all', method: 'GET'}).
        success(function(data, status, headers, config) {
            $scope.departments = data;
        }).
        error(function(data, status, headers, config) {
        });
}]);