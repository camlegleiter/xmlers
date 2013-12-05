TaskManager = new Backbone.Marionette.Application();
TaskManager.Models = {};
TaskManager.Collections = {};

/*
 * Since the HTML templates are within JSP files, the Underscore template
 * JavaScript tags need to be replaced with <@ @>
 */
_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@(.+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};

/*
 * The optional view for CollectionViews or CompositeViews when the view's
 * collection has no items. Initialize the view's message using:
 * 
 * new TaskManager.EmptyView({
 *   message: <your-message-here>
 * });
 * 
 * and use the 'message' variable name within the HTML template using the
 * Underscore <@= @> evaluate tags:
 * 
 * <script type="text/template">
 *   <@= message @>
 * </script
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