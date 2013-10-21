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
        tagName: 'li',
        className: 'form-item clearfix',
        
        events: {
            'change input[type="radio"]': 'onRadioChange'
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
    
    Module.AbstractFormsView = Backbone.Marionette.CollectionView.extend({
        tagName: 'ul',
        className: 'nav nav-list',
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$el.append(itemView.el);
        },
        
        onSelectForm: function(model) {
            this.trigger('select:form', model);
        }
    });
    
    /*
     * 
     */
    Module.OwnerFormView = Module.AbstractFormView.extend({});
    
    Module.OwnerFormsView = Module.AbstractFormsView.extend({
        itemView: Module.OwnerFormView,
        emptyView: function() {
            return new App.EmptyView({
                message: 'No forms to manage!'
            });
        },
        
        collectionEvents: {
            'select:form': 'onSelectForm'
        },
        
        initialize: function() {
            this.$el.append('<li class="nav-header">Forms I Own</li>');
        }
    });
    
    /*
     * 
     */
    Module.ParticipantFormView = Module.AbstractFormView.extend({});
    
    Module.ParticipantFormsView = Module.AbstractFormsView.extend({
        itemView: Module.ParticipantFormView,
        emptyView: function() {
            return new App.EmptyView({
                message: 'No forms to fill!'
            });
        },
        
        collectionEvents: {
            'select:form': 'onSelectForm'
        },
        
        initialize: function() {
            this.$el.append('<li class="nav-header">Forms I Need to Complete</li>');
        }
    });
});