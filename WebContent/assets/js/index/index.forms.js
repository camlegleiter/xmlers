'use strict';

TaskManager.module("Index", function(Module, App, Backbone, Marionette, $, _) {
    Module.FormsLayout = Backbone.Marionette.Layout.extend({
        template: '#forms-layout',
        
        regions: {
            ownerForms: '#owner-forms',
            participantForms: '#participant-forms' 
        },
        
        events: {
            'click .delete': 'onDeleteForm'
        },
        
        ui: {
            addBtn: '.add',
            deleteBtn: '.delete'
        },
        
        initialize: function(options) {
            this.ownerCollection = options.ownerCollection;
            this.participantCollection = options.participantCollection;
        },
        
        onRender: function() {
            this.toggleDeleteDisabled(true);
            
            var _ownerForms = new Module.OwnerFormsView({
                collection: this.ownerCollection
            });
            this.listenTo(_ownerForms, 'select:form', this.selectOwnerForm);
            
            var _participantForms = new Module.ParticipantFormsView({
                collection: this.participantCollection
            });
            this.listenTo(_participantForms, 'select:form', this.selectParticipantForm);
            
            this.ownerForms.show(_ownerForms);
            this.participantForms.show(_participantForms);
        },
        
        selectOwnerForm: function(model) {
            this.toggleDeleteDisabled(false);
            this.ui.deleteBtn.text('Delete form');
            this.trigger('select:form', {
                isOwner: true,
                model: model
            });
        },
        
        selectParticipantForm: function(model) {
            this.toggleDeleteDisabled(false);
            this.ui.deleteBtn.text('Unparticipate');
            this.trigger('select:form', {
                isOwner: false,
                model: model
            });
        },
        
        toggleDeleteDisabled: function(isDisabled) {
            this.ui.deleteBtn.toggleClass('disabled', isDisabled);
        }
    });
    
    /*
     * 
     */
    Module.AbstractFormView = Backbone.Marionette.ItemView.extend({
        template: '#form-item-template',
        tagName: 'a',
        className: 'list-group-item',
        
        events: {
            'change :radio': 'onRadioChange'
        },
        
        onRadioChange: function() {
            this.model.trigger('select:form', this.model);
        },
        
        serializeData: function() {
            return {
                id: this.model.cid,
                form: this.model.attributes
            };
        }
    });
    
    Module.AbstractFormsView = Backbone.Marionette.CompositeView.extend({
    	template: '#form-list-template',
        className: 'panel panel-default',
        itemViewContainer: '.list-group',
        
        events: {
        	'click .panel-heading': 'onPanelClick'
        },
        
        ui: {
        	listGroup: '.list-group',
        	caretSpan: 'span'
        },
        
        onSelectForm: function(model) {
            this.trigger('select:form', model);
        },
        
        onPanelClick: function() {
        	this.ui.listGroup.toggle();
        	this.ui.caretSpan.toggleClass('caret').toggleClass('caret-right');
        }
    });
    
    /*
     * 
     */
    Module.OwnerFormView = Module.AbstractFormView.extend({});
    
    Module.OwnerFormsView = Module.AbstractFormsView.extend({
    	templateHelpers: {
    		getPanelHeading: function() {
    			return 'Forms I Own';
    		}
    	},
        itemView: Module.OwnerFormView,
//        emptyView: function() {
//            return new App.EmptyView({
//            	tagName: 'li',
//            	className: 'list-group-item',
//                message: 'No forms to manage!'
//            });
//        },
        
        collectionEvents: {
            'select:form': 'onSelectForm'
        }
    });
    
    /*
     * 
     */
    Module.ParticipantFormView = Module.AbstractFormView.extend({});
    
    Module.ParticipantFormsView = Module.AbstractFormsView.extend({
    	templateHelpers: {
    		getPanelHeading: function() {
    			return 'Forms I Need to Complete';
    		}
    	},
        itemView: Module.ParticipantFormView,
//        emptyView: function() {
//            return new App.EmptyView({
//            	tagName: 'li',
//            	className: 'list-group-item',
//                message: 'No forms to fill!'
//            });
//        },
        
        collectionEvents: {
            'select:form': 'onSelectForm'
        }
    });
});