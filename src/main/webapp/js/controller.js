var app = angular.module('myApp', []);
app.controller('PhoneListCtrl', function($scope) {
    // $scope.firstName = "John";
    // $scope.lastName = "Doe";
    $scope.phones = [
        {"name": "Nexus S",
            "snippet": "Fast just got faster with Nexus S."},
        {"name": "Motorola XOOM™ with Wi-Fi",
            "snippet": "The Next, Next Generation tablet."},
        {"name": "MOTOROLA XOOM™",
            "snippet": "The Next, Next Generation tablet."}
    ];
    $scope.niu="scope niu"
});