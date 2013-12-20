'use strict';

TaskManager.module('View', function(Module, App, Backbone, Marionette, $, _) {
    
    /*
     * Manually-defined region to remove outer tag from a view, but still bind
     * events correctly (although in this case they are not used).
     */
    Module.TableBodyRegion = Backbone.Marionette.Region.extend({
        open: function(view) {
            view.$el.children().clone(true).appendTo(this.$el);
        }
    });
    
    Module.TableLayout = Backbone.Marionette.Layout.extend({
        template: '#table-layout',
        
        regions: {
            head: 'thead',
            body: {
                selector: 'tbody',
                // The collection attached to the tbody tag will not need its
                // outer tag
                regionType: Module.TableBodyRegion
            }
        },
        
        ui: {
            table: 'table'
        },
        
        initialize: function() {
            this.tableHead = new Module.TableHead({
                collection: this.model.get('formQuestions')
            });
            this.tableBody = new Module.TableBody({
                collection: this.model.get('formResponses')
            });
        },
        
        onRender: function() {
            this.head.show(this.tableHead);
            this.body.show(this.tableBody);
            
            this.ui.table.tablesorter(); 
        }
    });
    
    
    /*
     * 
     */
    Module.TableHeadItem = Backbone.Marionette.ItemView.extend({
        template: '#table-header-template',
        tagName: 'th',
        className: 'header'
    });
    
    Module.TableHead = Backbone.Marionette.CollectionView.extend({
        tagName: 'tr',
        itemView: Module.TableHeadItem,
        
        initialize: function() {
            if (this.collection.length > 0) {
                this.$el.prepend('<th class="header">Participants</th>');
            }
        }
    });
    
    
    /*
     * 
     */
    Module.TableBodyRow = Backbone.Marionette.ItemView.extend({
        template: '#table-row-template',
        templateHelpers: {
            getResponseValue: function(response) {
                if (response.get('value'))
                    return response.get('value');
                else if (response.get('values'))
                	if (response.get('values').length > 0)
                    	return response.get('values').join(', ');
                	else
                		return 'No Options Selected';
                else
                    return 'No Response Collected';
            }
        },
        tagName: 'tr',
        
        initialize: function() {
            this.collection = this.model.get('responses');
        }
    });
    
    Module.TableBody = Backbone.Marionette.CollectionView.extend({
        itemView: Module.TableBodyRow
    });
});