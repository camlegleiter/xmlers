'use strict';

/**
 * A Backbone model that uses a modified version of the Form model for storing a
 * user's responses. The defaults used represent the base items needed for storing
 * response information from a particular participant.
 */
TaskManager.Models.Response = Backbone.Model.extend({
	initialize: function() {

		/*
		 * Ensures that creating a new form or instantiating a form with
		 * existing data correctly sets responses as a Backbone collection.
		 */
		this.set('responses',
				new TaskManager.Collections.QuestionResponses(this
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
			responseOwner: -1,
			formParticipants: [],
			responses: []
		};
	}
});

/**
 * A Backbone model for storing multiple Response models
 */
TaskManager.Collections.Responses = Backbone.Collection.extend({
	model: TaskManager.Models.Response
});

/**
 * An abstract model that acts as a super class for the different response types
 */
TaskManager.Models.QuestionResponse = Backbone.Model.extend({
	defaults: {}
});

/**
 * A Backbone model for storing individual responses to each question
 */
TaskManager.Collections.QuestionResponses = Backbone.Collection.extend({
	model: TaskManager.Models.QuestionResponse
});

/**
 * A Backbone model for storing a participant's responses to a Checkbox field
 */
TaskManager.Models.CheckboxResponse = TaskManager.Models.QuestionResponse
		.extend({

			/*
			 * The basic model entries. See the response_metadata.json for more
			 * information on the values used. All values listed here are
			 * defaults in the case that the key hasn't been set in the form
			 * itself.
			 */
			defaults: function() {
				return {
					questionID: -1,
					type: 'Checkbox',
					values: new TaskManager.Collections.CheckboxResponses()
				};
			}
		});

/**
 * A Backbone collection of individual CheckboxValues
 */
TaskManager.Collections.CheckboxResponses = Backbone.Collection.extend({
	model: TaskManager.Models.CheckboxValue
});

/**
 * A Backbone model for storing one Checkbox input
 */
TaskManager.Models.CheckboxValue = Backbone.Model.extend({

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: {
		value: ''
	}
});

/**
 * A Backbone model for storing a participant's responses to a Radio field
 */
TaskManager.Models.RadioResponse = TaskManager.Models.QuestionResponse.extend({

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: function() {
		return {
			questionID: -1,
			type: 'Radio',
			values: new TaskManager.Collections.RadioResponses()
		};
	}
});

/**
 * A Backbone collection of individual RadioValues
 */
TaskManager.Collections.RadioResponses = Backbone.Collection.extend({
	model: TaskManager.Models.RadioValue
});

/**
 * A Backbone model for storing one Radio input
 */
TaskManager.Models.RadioValue = Backbone.Model.extend({

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: {
		value: ''
	}
});

/**
 * A Backbone model for storing a participant's responses to a Text field
 */
TaskManager.Models.Textbox = TaskManager.Models.QuestionResponse.extend({

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: function() {
		return {
			questionID: -1,
			type: 'Textbox',
			value: ''
		};
	}
});

/**
 * A Backbone model for storing a participant's responses to a Select field
 */
TaskManager.Models.SelectResponse = TaskManager.Models.QuestionResponse
		.extend({

			/*
			 * The basic model entries. See the response_metadata.json for more
			 * information on the values used. All values listed here are
			 * defaults in the case that the key hasn't been set in the form
			 * itself.
			 */
			defaults: function() {
				return {
					questionID: -1,
					type: 'Select',
					values: new TaskManager.Collections.SelectResponses()
				};
			}
		});

/**
 * A Backbone collection of individual SelectValues
 */
TaskManager.Collections.SelectResponses = Backbone.Collection.extend({
	model: TaskManager.Models.SelectValue
});

/**
 * A Backbone model for storing one option tag of a Select input
 */
TaskManager.Models.SelectValue = Backbone.Model.extend({

	/*
	 * The basic model entries. See the response_metadata.json for more
	 * information on the values used. All values listed here are defaults in
	 * the case that the key hasn't been set in the form itself.
	 */
	defaults: {
		value: ''
	}
});
