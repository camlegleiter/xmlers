'use strict';

TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    
    /*
     * 
     */
    Module.FormView = Backbone.Marionette.CompositeView.extend({
        template: '#form-template',
        getItemView: function(item) {
            return Module[item.get('type') + 'View'];
        },
        emptyView: function() {
            return new App.EmptyView({
                message: 'Select an entry type to get started!'
            });
        },
        
        events: {
            'input input[type="text"]': 'onUpdateModel',
            'input textarea': 'onUpdateModel',
            'change input[type="checkbox"]': 'onUpdateModel',
            
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
            
            loading: '.loading'
        },
        
        initialize: function() {
            this.collection = this.model.get('formQuestions');
        },
        
        onRender: function() {
            this.ui.formParticipants.select2({
                width: '300px',
                placeholder: 'Type an email to add a participant',
                
                tags: [],
                tokenSeparators: [',', ' '],                
            });
            
            // Need to set the current participants after initializing select
            this.ui.formParticipants.select2('val', this.model.get('formParticipants'));
            
            var self = this;
            this.ui.formParticipants.on('change', function(e) {
                self.model.set('formParticipants', e.val);
            });
        },
        
        onUpdateModel: function() {
            this.model.set({
                formName: this.ui.formName.val(),
                formDescription: this.ui.formDesc.val(),
                participantsCanSeeAll: this.ui.participantsCanSeeAll.is(':checked'),
                participantsCanEditResponse: this.ui.participantsCanEditResponse.is(':checked'),
                participantResponseIsRequired: this.ui.participantResponseIsRequired.is(':checked')
            });
        },
        
        onSubmit: function() {
            if (this.collection.length === 0) {
                alert('A form must have at least one question for users before submitting.');
                return;
            }
            
            if (this.inputsAreValid()) {
                if (this.model.get('formParticipants').length === 0) {
                    if (!confirm('No participants have been added. Are you sure you wish to continue?')) {
                        return;
                    }
                }
                
                this.toggleButtonsDisabled(true);
                this.ui.loading.show();
                
                var self = this;
                $.post('/xmlers/app/upsertForm', { model: JSON.stringify(this.model) })
                .done(function(data, textStatus, jqXHR) {
                    console.log(textStatus);
                })
                .error(function(jqXHR, textStatus, errorThrown) {
                    console.log(textStatus);
                    console.log(errorThrown);
                })
                .always(function() {
                    self.toggleButtonsDisabled(false);
                    self.ui.loading.hide();
                });
            }
        },
        
        onCancel: function() {
            return confirm("The current form will not be saved. Are you sure you wish to cancel?");
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('#form-content').append(itemView.el);
        },
        
        addQuestion: function(question) {
            if (App.Models[question]) {
                this.collection.add(new App.Models[question]());
            }
        },
        
        inputsAreValid: function() {
            var $inputs = this.$('.required-input');
            for (var i = 0; i < $inputs.length; ++i) {
                if ($($inputs[i]).is(':invalid')) {
                    $($inputs[i]).focus();
                    return false;
                }
            }
            
            return true;
        },
        
        toggleButtonsDisabled: function(isDisabled) {
            this.ui.submit.toggleClass('disabled', isDisabled);
            this.ui.cancel.toggleClass('disabled', isDisabled);
        }
    });
    
    
    
    
    
    /*
     * 
     */
    Module.QuestionView = Backbone.Marionette.ItemView.extend({
        className: 'control-group',
        
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
            'click .delete': 'onDeleteField'
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
            'click .delete': 'onDeleteEntry'
        },
        
        ui: {
            prompt: 'input[id^="prompt"]'
        },
        
        initialize: function() {
            this.collection = this.model.get('checkboxes');
            this.addItem(new App.Models.CheckboxItem());
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
            
            'click .delete': 'onDeleteField'
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
            'click .delete': 'onDeleteEntry'
        },
        
        ui: {
            prompt: 'input[id^="prompt"]'
        },
        
        initialize: function() {
            this.collection = this.model.get('radios');
            this.addItem(new App.Models.RadioItem());
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
            
            'click .delete': 'onDeleteEntry'
        },
        
        ui: {
            prompt: 'input[id^="prompt"]',
            maxLength: 'input[id^="max-length"]'
        },
        
        onUpdateModel: function() {
            this.model.set('prompt', this.ui.prompt.val());
            
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
            
            'click .delete': 'onDeleteField'
        },
        
        ui: {
            label: 'input[type="text"]'
        },
        
        onUpdateModel: function() {
            this.model.set({
                label: this.ui.label.val()
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
            'input input[type="text"]': 'onUpdateModel',
            'change input[type="checkbox"]': 'onUpdateModel',
            
            'click .add': 'onAddItem',
            'click .delete': 'onDeleteEntry'
        },
        
        ui: {
            prompt: 'input[id^="prompt"]',
            isMulti: 'input[type="checkbox"]'
        },
        
        initialize: function() {
            this.collection = this.model.get('options');
            this.addItem(new App.Models.SelectOption());
        },

        // Adds a new checkbox entry to the field
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