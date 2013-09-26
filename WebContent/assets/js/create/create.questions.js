TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    Module.QuestionOptionView = Backbone.Marionette.ItemView.extend({
        template: '#question-option-template',
        className: 'question-option',
        
        events: {
            'change input[type="radio"]': 'onRadioChange'
        },
        
        ui: {
            radio: 'input[type="radio"]'
        },
        
        initialize: function(options) {
            this.questionOptionLabel = options.model.get('questionOptionLabel');
        },
        
        onRadioChange: function() {
            if ($(this.ui.radio).is(':checked')) {
                this.model.trigger('change:radio', this.questionOptionLabel);
            }
        },
        
        serializeData: function() {
            return {
                id: this.model.cid,
                label: this.questionOptionLabel
            };
        }
    });
    
    Module.QuestionOptionsView = Backbone.Marionette.CompositeView.extend({
        template: '#question-options-template',
        className: 'question-options clearfix',
        itemView: Module.QuestionOptionView,
        
        events: {
            'click .add': 'onAddQuestion'
        },
        
        ui: {
            addButton: '.add'
        },
        
        initialize: function() {
            this.collection = new App.Collections.QuestionOptions();
            
            this.listenTo(this.collection, 'change:radio', this.onSetSelectedQuestion);
        },
        
        onRender: function() {
            this.collection.add([
                { questionOptionLabel: 'Textbox' },
                { questionOptionLabel: 'Radio' },
                { questionOptionLabel: 'Checkbox' },
                { questionOptionLabel: 'Select' }
            ]);
        },
        
        onAddQuestion: function() {
            if (this.selectedQuestion)
                this.trigger('add:question', this.selectedQuestion);
        },
        
        onSetSelectedQuestion: function(question) {
            this.selectedQuestion = question;
            $(this.ui.addButton).removeClass('disabled');
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('#questions-content').append(itemView.el);
        }
    });
    
    App.Collections.QuestionOptions = Backbone.Collection.extend();
});