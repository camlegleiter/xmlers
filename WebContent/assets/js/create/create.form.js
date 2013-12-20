'use strict';

TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    
    /**
     * The main view that represents where the user inputs the form metadata
     * and one or more fields. The ItemView is determined by the type of entry
     * they want to add to the form, which is the "type" field in the model
     * they select.
     */
    Module.FormView = Backbone.Marionette.CompositeView.extend({
        template: '#form-template',
        itemViewContainer: '#form-content',
        getItemView: function(field) {
        	// Use the item view as determined by the "field" model
            return Module[field.get('type') + 'View'];
        },
        emptyView: function() {
            return new App.EmptyView({
                message: 'Select a field to get started!'
            });
        },
        
        events: {
            'input :text': 'onUpdateModel',
            'input textarea': 'onUpdateModel',
            'change :checkbox': 'onUpdateModel',
            
            'click .submit': 'onSubmit',
            'click .cancel': 'onCancel'
        },
        
        ui: {
            formName: '#formName',
            formDesc: '#formDesc',
            formParticipants: '#formParticipants',
            
            participantsCanSeeAll: '#participantsCanSeeAll',
            participantsCanEditResponse: '#participantsCanEditResponse',
            participantResponseIsRequired: '#participantResponseIsRequired',
            
            submit: '.submit',
            cancel: '.cancel',
            
            loading: '.loading',
            errorMessage: '.error-message'
        },
        
        initialize: function() {
        	// Specify this view to store the collection of questions
            this.collection = this.model.get('formQuestions');
        },
        
        onRender: function() {
        	// Initialize the participants UI element with Select2
            this.ui.formParticipants.select2({
                width: '100%',
                placeholder: 'Type an email address',
                
                // Ensures the user can manually add participants
                tags: [],
                // Allows copy-paste of multiple addresses
                tokenSeparators: [',', ' '],                
            });
            
            // Need to set the current participants after initializing select
            this.ui.formParticipants.select2('val', this.model.get('formParticipants'));
            
            var self = this;
            this.ui.formParticipants.on('change', function(e) {
                self.model.set('formParticipants', e.val);
            });
        },
        
        // Update this model with the current information in the form
        onUpdateModel: function() {
            this.model.set({
                formName: this.ui.formName.val(),
                formDescription: _.escape(this.ui.formDesc.val()),
                participantsCanSeeAll: this.ui.participantsCanSeeAll.is(':checked'),
                participantsCanEditResponse: this.ui.participantsCanEditResponse.is(':checked'),
                participantResponseIsRequired: this.ui.participantResponseIsRequired.is(':checked')
            });
        },
        
        onSubmit: function(e) {
        	// Prevent the form from submitting prematurely
        	e.preventDefault();
        	
        	// The user did not add any entries to the form
            if (this.collection.length === 0) {
                alert('A form must have at least one entry for users before submitting.');
                return;
            }
            
            // Verify the inputs are correct
            if (this.inputsAreValid()) {
            	// Inform the user that they did not add any participants initially
                if (this.model.get('formParticipants').length === 0) {
                    if (!confirm('No participants have been added. Are you sure you wish to continue?')) {
                        return;
                    }
                }
                
                // Prevent the user from submitting multiple times
                this.toggleButtonsDisabled(true);
                this.ui.loading.show();
                this.ui.errorMessage.hide();
                
                var self = this;
                $.post('/xmlers/app/upsertForm', {
                	model: JSON.stringify(this.model),
                	isEdit: isEdit
                })
                .done(function(data, textStatus, jqXHR) {
                    if (data.success) {
                    	// If returned success, forward to URI in success property
                        window.location.href = data.success;
                        return true;
                    } else if (data.error) {
                    	// If returned error, display error to user
                    	self.ui.errorMessage.show().text(data.error);
                    	return false;
                    }
                })
                .error(function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus);
                    console.log(errorThrown);
                    return false;
                })
                .always(function() {
                    self.toggleButtonsDisabled(false);
                    self.ui.loading.hide();
                });
            }
            
            return false;
        },
        
        // When the user presses the cancel button
        onCancel: function(e) {
        	// Prevent the form from submitting on accident
        	e.preventDefault();
        	
        	var message = isEdit ? 'Any changes to the current form will not be saved. Are you sure you want to cancel?'
        			: message = 'The current form will not be saved. Are you sure you want to cancel?';
        	
            var isCanceled = confirm(message);
            	
            if (isCanceled)
            	window.location.href = "index.jsp";
            return isCanceled;
        },
        
        // Adds the given question to the collection in this view
        addQuestion: function(question) {
        	// Only add it to the collection if it exists as a model
            if (App.Models[question]) {
                this.collection.add(new App.Models[question]());
            }
        },
        
        // Verifies all inputs are correct
        inputsAreValid: function() {
            var $inputs = this.$('.required-input');
            for (var i = 0; i < $inputs.length; ++i) {
            	// Use the :invalid CSS pseudo-class to check
                if ($($inputs[i]).is(':invalid')) {
                    $($inputs[i]).focus();
                    return false;
                }
            }
            
            return true;
        },
        
        // Disables the form buttons using the Bootstrap "disabled" class
        // and toggling it off/on based on the isDisabled option
        toggleButtonsDisabled: function(isDisabled) {
            this.ui.submit.toggleClass('disabled', isDisabled);
            this.ui.cancel.toggleClass('disabled', isDisabled);
        }
    });
    
    
    
    
    
    /*
     * 
     */
    Module.QuestionView = Backbone.Marionette.ItemView.extend({
        tagName: 'li',
        className: 'form-group',
        
        serializeData: function() {
            return {
                id: this.model.cid,
                data: this.model.attributes
            };
        },
        
        onDeleteField: function() {
            this.model.destroy();
        }
    });
    
    /*
     * 
     */
    Module.QuestionsView = Backbone.Marionette.CompositeView.extend({
        className: 'question',

        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        },
        
        serializeData: function() {
            return {
                id: this.model.cid,
                data: this.model.attributes
            };
        },
        
        onDeleteEntry: function() {
            this.model.destroy();
        },
        
        addItem: function(item) {
            this.collection.add(item);
        }
    });
    
    
    /*
     * 
     */
    Module.CheckboxItemView = Module.QuestionView.extend({
        template: '#checkbox-item-template',
        
        events: {
            'input input': 'onUpdateModel',
            'click .close': 'onDeleteField'
        },
        
        ui: {
            input: 'input[type="text"]'
        },
        
        onUpdateModel: function() {
            this.model.set({
                label: this.ui.input.val()
            });
        }
    });
    
    Module.CheckboxView = Module.QuestionsView.extend({
        template: '#checkbox-template',
        itemView: Module.CheckboxItemView,
        emptyView: function() {
            return new App.EmptyView({
                message: 'Add options for the user to choose from.'
            });
        },
        
        events: {
            'input input': 'onUpdateModel',
            
            'click .add': 'onAddItem',
            'click .close': 'onDeleteEntry'
        },
        
        ui: {
            prompt: 'input[id^="prompt"]'
        },
        
        initialize: function() {
            this.collection = this.model.get('checkboxes');
            if (this.collection.length == 0) {
            	this.addItem(new App.Models.CheckboxItem());
            }
        },

        // Adds a new checkbox entry to the field
        onAddItem: function() {
            this.addItem(new App.Models.CheckboxItem());
        },
        
        onUpdateModel: function() {
            this.model.set({
                prompt: this.ui.prompt.val()
            });
        }
    });
    
    
    /*
     * 
     */
    Module.RadioItemView = Module.QuestionView.extend({
        template: '#radio-item-template',
        
        events: {
            'input input': 'onUpdateModel',
            
            'click .close': 'onDeleteField'
        },
        
        ui: {
            input: ':text'
        },
        
        onUpdateModel: function() {
            this.model.set({
                label: this.ui.input.val()
            });
        }
    });
    
    Module.RadioView = Module.QuestionsView.extend({
        template: '#radio-template',
        itemView: Module.RadioItemView,
        emptyView: function() {
            return new App.EmptyView({
                message: 'Add options for the user to choose from.'
            });
        },
        
        events: {
            'input input': 'onUpdateModel',
            
            'click .add': 'onAddItem',
            'click .close': 'onDeleteEntry'
        },
        
        ui: {
            prompt: ':text'
        },
        
        initialize: function() {
            this.collection = this.model.get('radios');
            if (this.collection.length == 0) {
            	// Add an empty item to get the user started
            	this.addItem(new App.Models.RadioItem());
            }
        },

        // Adds a new checkbox entry to the field
        onAddItem: function() {
            this.addItem(new App.Models.RadioItem());
        },
        
        onUpdateModel: function() {
            this.model.set({
                prompt: this.ui.prompt.val()
            });
        }
    });
    
    
    /*
     * 
     */
    Module.TextboxView = Module.QuestionsView.extend({
        template: '#textbox-template',
        
        events: {
            'input input': 'onUpdateModel',
            
            'click .close': 'onDeleteEntry'
        },
        
        ui: {
            prompt: ':text',
            maxLength: 'input[type="number"]'
        },
        
        onUpdateModel: function() {
            this.model.set('prompt', _.escape(this.ui.prompt.val()));
            
            var l = this.ui.maxLength.val();
            if (!_.isUndefined(l)) {
                var length = parseInt(l, 10);
                if (!_.isNaN(length)) {
                    this.model.set('maxLength', length);
                }
            }
        }
    });
    
    
    /*
     * 
     */
    Module.SelectOptionItemView = Module.QuestionView.extend({
        template: '#select-option-item-template',
        
        events: {
            'input input': 'onUpdateModel',
            
            'click .close': 'onDeleteField'
        },
        
        ui: {
            label: ':text'
        },
        
        onUpdateModel: function() {
            this.model.set({
                value: this.ui.label.val()
            });
        }
    });
    
    Module.SelectView = Module.QuestionsView.extend({
        template: '#select-template',
        itemView: Module.SelectOptionItemView,
        emptyView: function() {
            return new App.EmptyView({
                message: 'Add options for the user to choose from.'
            });
        },
        
        events: {
            'input :text': 'onUpdateModel',
            'change :checkbox': 'onUpdateModel',
            
            'click .add': 'onAddItem',
            'click .close': 'onDeleteEntry'
        },
        
        ui: {
            prompt: ':text',
            isMulti: '.is-multi'
        },
        
        initialize: function() {
            this.collection = this.model.get('options');
            if (this.collection.length == 0) {
            	this.addItem(new App.Models.SelectOption());
            }
        },

        // Adds a new select entry to the field
        onAddItem: function() {
            this.addItem(new App.Models.SelectOption());
        },
        
        onUpdateModel: function() {
            this.model.set({
                prompt: this.ui.prompt.val(),
                isMulti: this.ui.isMulti.is(':checked')
            });
        }
    });
});