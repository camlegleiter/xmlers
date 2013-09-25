TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    
    
    
    
    
    Module.QuestionsView = Backbone.Marionette.CompositeView.extend({
        deleteField: function() {
            this.model.destroy();
        }
    });
    
    Module.CheckboxView = Module.QuestionsView.extend({
        template: '#checkbox-template',
        itemView: Module.CheckboxItemView,
        
        events: {
            'click .add-checkbox': 'addCheckboxItem',
            'click .delete': 'deleteField'
        },
        ui: {
            deleteBtn: '.delete'
        },
        
        appendHtml: function(collectionView, itemView) {
            collectionView.$('.checkbox-content').append(itemView.el);
        },
        
        // Adds a new checkbox entry to the field
        addCheckboxItem: function() {
            this.collection.add(new Module.CheckboxItemView());
        }
    });
    
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
});