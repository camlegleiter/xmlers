'use strict';

TaskManager.module('Query', function(Module, App, Backbone, Marionette, $, _) {
    this.startWithParent = false;
    
    App.addRegions({
        queryView: '#queries',
        resultsView: '#results'
    });
    
    Module.addInitializer(function(options) {
        var queryView = new Module.QueryView({
        	model: options.model
        });
        var resultsView = new Module.ResultsView();
        
        Module.listenTo(queryView, 'update:results', function(results) {
            resultsView.updateResults(results);
        });
        
        App.queryView.show(queryView);
        App.resultsView.show(resultsView);
    });
});