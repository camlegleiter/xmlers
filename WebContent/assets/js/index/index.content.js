'use strict';

TaskManager.module("Index", function(Module, App, Backbone, Marionette, $, _) {
    Module.FormContentView = Backbone.Marionette.ItemView.extend({
        template: '#form-content-template'
    });
    
    Module.FormContentsView = Backbone.Marionette.CompositeView.extend({
        template: '#form-contents-template',
        itemView: Module.FormContentView,
        emptyView: function() {
            return new App.EmptyView({
                message: '<h4>Select a form to get started!</h4>'
            });
        },
        
        events: {
            
        },
        
        ui: {
            
        },
        
        initialize: function() {
            this.collection = new App.Collections.Forms();
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.form-content').append(itemView.el);
        },
        
        showForm: function(options) {
            this.model = options.model;
            this.collection.reset(this.model);
        }
    });
});