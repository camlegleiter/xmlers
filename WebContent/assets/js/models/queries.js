'use strict';

/**
 * Model for storing data related to querying the form with the given queries
 * string. The queries string contains zero or more XPath queries for use
 * against an XML database. The queries are not separated for simplicity, since
 * the user could format the queries in multiple ways, resulting in too many
 * edge cases to consider parsing into individual queries. The form data is not
 * required here, so the bare minimum needed is just the form ID.
 */
TaskManager.Models.Queries = Backbone.Model.extend({
	defaults: {
		// The corresponding form ID
		'formId': -1,
		
		// A string containing zero or more queries created by the user
		'queries': ''
	}
});