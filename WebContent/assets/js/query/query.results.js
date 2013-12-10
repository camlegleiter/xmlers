'use strict';

TaskManager.module('Query', function(Module, App, Backbone, Marionette, $, _) {
    Module.ResultsView = Backbone.Marionette.ItemView.extend({
        template: '#results-template',

        events: {
        	'input textarea': 'onDisableExport',
        	
        	'click .export-xml': 'onExportXML',
        },
        
        ui: {
            results: 'textarea',
            
            buttons: '.btn',
            exportXML: '.export-xml'
        },
        
        onRender: function() {
        	this.toggleButtonsDisabled(true);
        },
        
        onDisableExport: function() {
        	this.toggleButtonsDisabled(this.ui.results.val() === '');
        },
        
        onExportXML: function() {
        	if (this.ui.results.val() === '')
        		return false;
        },
        
        toggleButtonsDisabled: function(disabled) {
        	this.ui.buttons.toggleClass('disabled', disabled);
        },
        
        updateResults: function(results) {
        	this.ui.results.text(results);
        }
    });
});