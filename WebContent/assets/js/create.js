TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    App.addRegions({
        mainForm: '#form'
    });
    
    App.addInitializer(function(options) {
        App.mainForm.show(new Module.FormView());
    });
    
    

    /*
     * 
     */
    App.Models.Checkbox = new Backbone.Model.extend({
        defaults: function() {
            return {
                description: '',
                checkboxes: new App.Collections.Checkboxes()
            };
        }
    });
    
    App.Collections.Checkboxes = new Backbone.Collection.extend({
        model: App.Models.CheckboxItem
    });
    
    App.Models.CheckboxItem = new Backbone.Model.extend({
        defaults: {
            entryText: ''
        }
    });
    
    
    

    
    Module.TypeView = Backbone.Marionette.CompositeView.extend({
        deleteField: function() {
            this.model.destroy();
        }
    });
    
    Module.CheckboxView = Module.TypeView.extend({
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
    
    Module.FormView = Backbone.Marionette.CompositeView.extend({
        
    });
});