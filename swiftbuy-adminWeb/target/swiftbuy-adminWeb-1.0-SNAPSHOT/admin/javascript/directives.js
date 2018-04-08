'use strict';

angular.module('commonDirectives', []).directive('optionsDisabled', function ($parse) {
    var disableOptions = function (scope, attr, element, data, fnDisableIfTrue) {
        // refresh the disabled options in the select element.
        $("option[value!='?']", element).each(function (i, e) {
            var locals = {};
            locals[attr] = data[i];
            $(this).attr("disabled", fnDisableIfTrue(scope, locals));
        });
    };
    return {
        priority: 0,
        require: 'ngModel',
        link: function (scope, iElement, iAttrs, ctrl) {
            // parse expression and build array of disabled options
            var expElements = iAttrs.optionsDisabled.match(/^\s*(.+)\s+for\s+(.+)\s+in\s+(.+)?\s*/);
            var attrToWatch = expElements[3];
            var fnDisableIfTrue = $parse(expElements[1]);
            scope.$watch(attrToWatch, function (newValue, oldValue) {
                if (newValue)
                    disableOptions(scope, expElements[2], iElement, newValue, fnDisableIfTrue);
            }, true);
            // handle model updates properly
            scope.$watch(iAttrs.ngModel, function (newValue, oldValue) {
                var disOptions = $parse(attrToWatch)(scope);
                if (newValue)
                    disableOptions(scope, expElements[2], iElement, disOptions, fnDisableIfTrue);
            });
        }
    };
}).directive('ngConfirmClick', [
    function () {
        return {
            priority: -1,
            restrict: 'A',
            link: function (scope, element, attrs) {
                element.bind('click', function (e) {
                    var message = attrs.ngConfirmClick;
                    if (message && !confirm(message)) {
                        e.stopImmediatePropagation();
                        e.preventDefault();
                    }
                });
            }
        }
    }
]).directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown, keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter, {'event': event});
                });
                event.preventDefault();
            }
        });
    }
}).directive('intList', function() {
    return {
        require: 'ngModel',
        link: function(scope, ele, attr, ngModel) {
            ngModel.$parsers.push(function(value) {
                if (value) {
                    return value.split(',').map(function(item) {
                        return parseInt(item, 10);
                    });
                } else {
                    return [];
                }
            });
            ngModel.$formatters.push(function (value) {
                return value.join()
            });
        }
    }
}).directive('formatDate', function() {
    return {
        require: 'ngModel',
        link: function(scope, ele, attr, ngModel) {
            ngModel.$parsers.push(function(value) {
                if (!value) {
                    return null;
                }
                return Date.parse(value).getTime();
            });
            ngModel.$formatters.push(function (value) {
                if (!value) {
                    return '';
                }
                return new Date(value).toString('yyyy-MM-dd HH:mm:ss');
            });
        }
    }
});
