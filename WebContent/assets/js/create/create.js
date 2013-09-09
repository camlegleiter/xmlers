TaskManager.module("Create", function(Module, App, Backbone, Marionette, $, _) {
    App.addRegions({
        types: '#types',
        mainForm: '#form'
    });
    
    App.addInitializer(function(options) {
        App.types.show(new Module.TypesView());
        App.mainForm.show(new Module.FormView());
    });
});