'use strict';

TaskManager.module("Index", function(Module, App, Backbone, Marionette, $, _) {
    Module.FormsLayout = Backbone.Marionette.Layout.extend({
        template: '#forms-layout',
        
        regions: {
            ownerForms: '#owner-forms',
            participantForms: '#participant-forms' 
        },
        
        events: {
            'click .delete:not(.disabled)': 'onDeleteForm'
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
        
        onDeleteForm: function() {
        	this.toggleDeleteDisabled(true);
        	
        	var message = (this.isOwner) ? 'This form and all response data will be permanently deleted. Are you sure you wish to delete?'
        			: 'By unparticipating, you cannot respond to this form unless re-added by the form owner. Are you sure you wish to unparticipate?';
        	if (confirm(message)) {
        		var self = this;
        		var postAddr = (this.isOwner) ? 'deleteForm' : 'unparticipateForm';
	            $.post('/xmlers/app/' + postAddr, {
	            	formID: this.selectedModel.get('formID')
	            })
	            .done(function(data, textStatus, jqXHR) {
	                if (data.success) {
	                	self.selectedModel.destroy();
	                    return true;
	                } else if (data.error) {
	                	alert(data.error);
	                	return false;
	                }
	            })
	            .error(function(jqXHR, textStatus, errorThrown) {
	            	console.log(errorThrown);
	                return false;
	            })
	            .always(function() {
	                self.toggleDeleteDisabled(false);
	            });
        	}
        },
        
        selectOwnerForm: function(model) {
            this.toggleDeleteDisabled(false);
            this.ui.deleteBtn.text('Delete form');
            
            this.isOwner = true;
            this.selectedModel = model;
            
            this.trigger('select:form', {
                isOwner: true,
                model: model
            });
        },
        
        selectParticipantForm: function(model) {
            var responseRequired = model.get('participantResponseIsRequired');
            this.toggleDeleteDisabled(responseRequired);
            this.ui.deleteBtn.text(responseRequired ? '' : 'Unparticipate');
            
            this.isOwner = false;
            this.selectedFormId = model.get('formID');
            
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
        
        collectionEvents: {
            'select:form': 'onSelectForm'
        }
    });
});