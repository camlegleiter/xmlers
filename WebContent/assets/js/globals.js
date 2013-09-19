TaskManager = new Backbone.Marionette.Application();
TaskManager.Models = {};
TaskManager.Collections = {};

_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@(.+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};