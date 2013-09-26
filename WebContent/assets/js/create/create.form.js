TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    
    Module.FormView = Backbone.Marionette.CompositeView.extend({
        template: '#form-template',
        getItemView: function(item) {
            return Module[item.get('type') + 'View'];
        },
        
        initialize: function() {
            this.collection = this.model.get('formQuestions');
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('#form-content').append(itemView.el);
        },
        
        addQuestion: function(question) {
            if (App.Models[question]) {
                this.collection.add(new App.Models[question]());
            }
        }
    });
    
    
    
    Module.QuestionsView = Backbone.Marionette.CompositeView.extend({
        className: 'question',
        deleteField: function() {
            this.model.destroy();
        },
        
        events: {
            'click .add': 'addItem',
            'click .delete': 'deleteField'
        },
        
        ui: {
            deleteBtn: '.delete'
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.content').append(itemView.el);
        }
    });
    
    
    /*
     * 
     */
    Module.CheckboxItemView = Backbone.Marionette.ItemView.extend({
        template: '#checkbox-item-template',
        tagName: 'li',
        
        events: {
            'click .delete': 'deleteEntry'
        },
        
        ui: {
            deleteBtn: '.delete'
        },
        
        deleteEntry: function() {
            this.model.destroy();
        }
    });
    
    Module.CheckboxView = Module.QuestionsView.extend({
        template: '#checkbox-template',
        itemView: Module.CheckboxItemView,
        
        initialize: function() {
            this.collection = this.model.get('checkboxes');
        },
        
        // Adds a new checkbox entry to the field
        addItem: function() {
            this.collection.add(new App.Models.CheckboxItem());
        }
    });
    
    
    /*
     * 
     */
    Module.RadioItemView = Backbone.Marionette.ItemView.extend({
        template: '#radio-item-template',
        tagName: 'li',
        
        events: {
            'click .delete': 'deleteEntry'
        },
        
        ui: {
            deleteBtn: '.delete'
        },
        
        deleteEntry: function() {
            this.model.destroy();
        }
    });
    
    Module.RadioView = Module.QuestionsView.extend({
        template: '#radio-template',
        itemView: Module.RadioItemView,
        
        initialize: function() {
            this.collection = this.model.get('radios');
        },

        // Adds a new checkbox entry to the field
        addItem: function() {
            this.collection.add(new App.Models.RadioItem());
        }
    });
    
    
    /*
     * 
     */
    Module.TextboxView = Module.QuestionsView.extend({
        template: '#textbox-template'
    });
    
    /*
     * 
     */
    Module.SelectOptionItemView = Backbone.Marionette.ItemView.extend({
        template: '#select-option-item-template',
        tagName: 'li',
        
        events: {
            'click .delete': 'deleteEntry'
        },
        
        ui: {
            deleteBtn: '.delete'
        },
        
        deleteEntry: function() {
            this.model.destroy();
        }
    });
    
    Module.SelectView = Module.QuestionsView.extend({
        template: '#select-template',
        itemView: Module.SelectOptionItemView,
        
        initialize: function() {
            this.collection = this.model.get('options');
        },

        // Adds a new checkbox entry to the field
        addItem: function() {
            this.collection.add(new App.Models.SelectOption());
        }
    });
    

});