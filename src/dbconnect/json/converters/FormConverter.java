package dbconnect.json.converters;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import dbconnect.DBManager;
import dbconnect.IConverter;
import form.User;
import form.VisitMechanism;
import form.factory.DefaultFactory;
import form.visitors.JSONVisitor;
import form.visitors.JSONVisitorResponse;

public class FormConverter implements
		IConverter<form.Form, dbconnect.json.dao.Form> {

	private final static IConverter<form.Form, dbconnect.json.dao.Form> INSTANCE;
	static {
		INSTANCE = new FormConverter();
	}

	private FormConverter() {
	}

	public static IConverter<form.Form, dbconnect.json.dao.Form> getInstance() {
		return INSTANCE;
	}

	@Override
	public form.Form convert(dbconnect.json.dao.Form other) {
		JSONObject jsonObject = new JSONObject(JSON.serialize(other));
		return new DefaultFactory().buildForm(jsonObject,
				DBManager.getInstance());
	}

	@Override
	public dbconnect.json.dao.Form unconvert(form.Form other) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("formName", other.getTitle());
		jsonObject.put("formID", other.getFormId());
		jsonObject.put("formDescription", other.getDescription());
		jsonObject.put("formOwner", other.getOwnerId());
		jsonObject.put("participantsCanSeeAll", other.participantsCanSeeAll());
		jsonObject.put("participantsCanEditResponse",
				other.participantsCanEditResponse());
		jsonObject.put("participantResponseIsRequired",
				other.participantResponseIsRequired());

		JSONArray participants = new JSONArray();
		for (User u : other.getParticipants())
			participants.put(u.getUserID());
		jsonObject.put("formParticipants", participants);

		JSONArray questions = new JSONArray(
				"["
						+ VisitMechanism.visit(new JSONVisitor(),
								other.iterator(), ",") + "]");
		jsonObject.put("formQuestions", questions);

		JSONArray responseList = new JSONArray();
		for (User u : other.getParticipants()) {
			JSONObject reponse = new JSONObject();
			reponse.put("responseOwner", u.getUserID());
			reponse.put("responseOwnerName", u.getEmail());
			JSONArray array = new JSONArray("["
					+ VisitMechanism.visit(
							new JSONVisitorResponse(u.getUserID()),
							other.iterator(), ",") + "]");
			reponse.put("responses", array);
			responseList.put(reponse);
		}
		jsonObject.put("responses", responseList);
		jsonObject.put("queries", other.getQueries());

		BasicDBObject o = (BasicDBObject) JSON.parse(other.getJSONString());
//		return (dbconnect.json.dao.Form)
		return new dbconnect.json.dao.Form(o.toMap());
	}
}
