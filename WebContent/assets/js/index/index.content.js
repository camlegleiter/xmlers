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
            reemailParticipants: '.reemail-participants',
            message: '.message'
        },
        
        events: {
        	'click .reemail-participants': 'onReemailParticipants'
        },
        
        onReemailParticipants: function() {
        	this.ui.message.hide();
        	this.toggleButtonsDisabled(true);
        	
            var self = this;
            $.post('/xmlers/app/index', {
            	formId: this.model.get('formID')
            })
            .done(function(data, textStatus, jqXHR) {
                if (data.success) {
                	self.ui.message.show().text(data.success);
                    return true;
                } else if (data.error) {
                	self.ui.message.show().text(data.error);
                	return false;
                }
            })
            .error(function(jqXHR, textStatus, errorThrown) {
                self.ui.message.show().text(errorThrown);
                return false;
            })
            .always(function() {
            	self.toggleButtonsDisabled(false);
            });
        },
        
        toggleButtonsDisabled: function(disabled) {
        	this.ui.reemailParticipants.toggleClass('disabled', disabled);
        }
    });

    Module.ParticipantFormView = Backbone.Marionette.ItemView.extend({
        template: '#participant-form-template',
    });

    /*
     * The main content view for displaying either an administrator's form
     * or a participant's form.
     */
    Module.FormContentsView = Backbone.Marionette.CompositeView.extend({
        template: '#form-contents-template',
        itemView: function(options) {
            return (options.model.isOwner) 
                ? new Module.OwnerFormView({ model: options.model }) 
                : new Module.ParticipantFormView({ model: options.model });
        },

        emptyView: function() {
            return new App.EmptyView({
            	tagName: 'h4',
                message: 'Select a form to get started!'
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
            this.model.set('userEmail', options.userEmail);
            this.collection.reset(this.model);
        }
    });
});