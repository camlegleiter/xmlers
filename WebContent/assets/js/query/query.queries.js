'use strict';

TaskManager.module('Query', function(Module, App, Backbone, Marionette, $, _) {
    Module.QueryView = Backbone.Marionette.ItemView.extend({
        template: '#query-template',

        events: {
        	'input textarea': 'onUpdateQueries',
        	
        	'click .save': 'onSaveQueries',
        	'click .run-all': 'onRunAll',
        	'click .run-selected': 'onRunSelected'
        },
        
        ui: {
            queries: 'textarea',
            
            buttons: '.btn',
            save: '.save',
            runAll: '.run-all',
            runSelected: '.run-selected',
            
            loading: '.loading'
        },
        
        initialize: function() {
        	var self = this;
        	$(document).bind('mouseup', function() {
				if (window.getSelection) {
					self.selectedText = window.getSelection().toString();
				} else if (document.getSelection) {
					self.selectedText = document.getSelection();
				} else if (document.selection) {
					self.selectedText = document.selection.createRange().text;
				} else {
					self.selectedText = '';
				}
				
				self.ui.runSelected.toggleClass('disabled', self.selectedText === '');
        	});
        },
        
        onRender: function() {
        	if (this.model.get('queries') === '')
        		this.toggleButtonsDisabled(true);
        },
        
        onSaveQueries: function(e) {
        	e.preventDefault();
        	this.runQuery(this.ui.queries.val(), true);
        },
        
        onRunSelected: function(e) {
        	e.preventDefault();
        	this.runQuery(this.selectedText, false);
        },
        
        onRunAll: function(e) {
        	e.preventDefault();
        	this.runQuery(this.ui.queries.val(), false);
        },
        
        runQuery: function(query, isSaving) {
        	this.ui.loading.show();
            $.post('/xmlers/app/query', {
            	formId: this.model.get('formID'),
            	query: query,
            	isSaving: isSaving
            })
            .done(function(data, textStatus, jqXHR) {
                if (data.success) {
                    if (!isSaving) {
                    	self.trigger('update:results', data.success);
                    }
                    return true;
                } else if (data.error) {
                	self.trigger('update:results', data.error);
                	return false;
                }
            })
            .error(function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus);
                console.log(errorThrown);
                self.trigger('update:results', errorThrown);
                return false;
            })
            .always(function() {
                self.toggleButtonsDisabled(false);
                self.ui.loading.hide();
            });
        },
        
        onUpdateQueries: function() {
        	var queries = this.ui.queries.val();
        	this.model.set('queries', queries);
        	
    		this.ui.runAll.toggleClass('disabled', queries === '');
        },
        
        toggleButtonsDisabled: function(disabled) {
        	this.ui.buttons.toggleClass('disabled', disabled);
        }
    });
});