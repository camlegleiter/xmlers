'use strict';

TaskManager.Models.View = Backbone.Model.extend({
	initialize: function() {

		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets both formQuestions and responses as
		 * Backbone collections.
		 */
		this.set('formQuestions', new TaskManager.Collections.Questions(this
				.get('formQuestions')));
		this.set('responses', new TaskManager.Collections.Responses(this
				.get('responses')));
	},

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: function() {
		return {
			formID: -1,
			formName: '',
			formDescription: '',
			formOwner: -1,
			formQuestions: [],
			responses: []
		};
	}
});
