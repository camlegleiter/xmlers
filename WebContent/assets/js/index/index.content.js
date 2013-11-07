'use strict';

TaskManager.module("Index", function(Module, App, Backbone, Marionette, $, _) {
    Module.OwnerFormView = Backbone.Marionette.ItemView.extend({
        template: '#owner-form-template',
        templateHelpers: {
            getFormParticipants: function() {
				return this.formParticipants.length > 0 ? this.formParticipants
						.join(', ') : 'No participants for this form';
            }
        },

        ui: {
            ownerButtons: '.owner-buttons',
        }
    });

    Module.ParticipantFormView = Backbone.Marionette.ItemView.extend({
        template: '#participant-form-template',
    });

    Module.FormContentsView = Backbone.Marionette.CompositeView.extend({
        template: '#form-contents-template',
        itemView: function(options) {
            return (options.model.isOwner) 
                ? new Module.OwnerFormView({ model: options.model }) 
                : new Module.ParticipantFormView({ model: options.model });
        },

        emptyView: function() {
            return new App.EmptyView({
                message: '<h4>Select a form to get started!</h4>'
            });
        },

        initialize: function() {
            this.collection = new App.Collections.Forms();
        },

        appendHtml: function(collectionView, itemView) {
            collectionView.$('.form-content').append(itemView.el);
        },

        showForm: function(options) {
            this.model = options.model;
            this.model.isOwner = options.isOwner;
            this.collection.reset(this.model);
        }
    });
});