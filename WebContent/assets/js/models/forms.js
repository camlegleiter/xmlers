'use strict';

/**
 * The Backbone model used in the front end for representing an XML form.
 */
TaskManager.Models.Form = Backbone.Model.extend({
	initialize: function() {

		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets formQuestions as a Backbone collection.
		 */
		this.set('formQuestions', new TaskManager.Collections.Questions(this
				.get('formQuestions')));
	},

	/*
	 * The basic model entries. See the form_metadata.json for more information
	 * on the values used. All values listed here are defaults in the case that
	 * the key hasn't been set in the form itself.
	 */
	defaults: function() {
		return {
			formID: -1,
			formName: '',
			formDescription: '',
			formOwner: -1,
			formParticipants: [],
			participantsCanSeeAll: false,
			participantsCanEditResponse: false,
			participantResponseIsRequired: true,
			queries: '',
			formQuestions: []
		};
	}
});

/**
 * Backbone collection for storing multiple Form models
 */
TaskManager.Collections.Forms = Backbone.Collection.extend({
	model: TaskManager.Models.Form,
	// Sort models by name when displaying them (mainly used on index page)
	comparator: 'formName'
});