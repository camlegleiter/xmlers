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
	/* 
	 * The basic model entries. All values listed here are defaults in the case
	 * that the key hasn't been set in the form itself.
	 */
	defaults: {
		'formId': -1,
		'queries': ''
	}
});