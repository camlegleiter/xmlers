TaskManager = new Backbone.Marionette.Application();
TaskManager.Models = {};
TaskManager.Collections = {};

_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@(.+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};

/*
 * 
 */
TaskManager.EmptyView = Backbone.Marionette.ItemView.extend({
    template: '#empty-view-template',
    className: 'control-group',

    initialize: function(options) {
        this.message = options.message;
    },
    
    serializeData: function() {
        return {
            message: this.message
        };
    }
});