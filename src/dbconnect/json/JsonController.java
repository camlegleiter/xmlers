package dbconnect.json;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

import dbconnect.IDBController;
import dbconnect.json.converters.FormConverter;
import dbconnect.json.converters.UserConverter;
import form.Form;
import form.User;

public class JsonController implements IDBController {

	private MongoClient mongoClient;

	/**
	 * Creates a new JsonController, and initializes the MongoClient to use a
	 * database within localhost. This is the default way to use MongoDB,
	 * especially when testing and running both Tomcat and MongoDB on a local
	 * machine.
	 */
	public JsonController() {
		try {
			mongoClient = new MongoClient(IDBController.LOCALHOST);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new JsonController, and initializes the MongoClient to use a
	 * specific host and port number. Useful for external databases.
	 * 
	 * @param host
	 *            The URI to the host
	 * @param port
	 *            The port to access the host at
	 */
	public JsonController(String host, int port) {
		try {
			mongoClient = new MongoClient(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new JsonController, and initializes the MongoClient using the
	 * given MongoClient. Useful when needed to initialize the JsonController to
	 * connect to multiple databases for replication.
	 * 
	 * @param mongoClient
	 *            The MongoClient to use
	 */
	public JsonController(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	/**
	 * Gets the given collection from the Mongo database, and sets the
	 * collection to return documents with the given Class. The given class must
	 * implement DBObject in order to avoid any issues with casting within
	 * MongoDB.
	 * 
	 * @param collectionName
	 * @param c
	 * @return
	 */
	private DBCollection createCollection(String collectionName, Class<?> clazz) {
		DB db = mongoClient.getDB("taskmanager");
		DBCollection collection = db.getCollection(collectionName);
		collection.setObjectClass(clazz);
		return collection;
	}

	@Override
	public boolean formExists(int formId) {
		return fetchForm(formId) != null;
	}

	@Override
	public boolean userExists(int userId) {
		return fetchUser(userId) != null;
	}

	@Override
	public boolean upsertForm(Form newForm) {
		try {
			DBCollection collection = createCollection("forms",
					dbconnect.json.dao.Form.class);

			dbconnect.json.dao.Form originalForm = (dbconnect.json.dao.Form) collection
					.findOne(new BasicDBObject("formID", newForm.getFormId()));
			if (originalForm == null) {
				dbconnect.json.dao.Form f = FormConverter.getInstance()
						.unconvert(newForm);
				f.setFormId(getNewId());
				collection.insert(f);
			} else {
				ObjectId originalID = originalForm.getObjectId("_id");
				dbconnect.json.dao.Form updatedForm = FormConverter
						.getInstance().unconvert(newForm);
				updatedForm.put("_id", originalID);
				collection.save(updatedForm);
			}
			return true;
		} catch (MongoException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean upsertUser(User newUser) {
		try {
			DBCollection collection = createCollection("users",
					dbconnect.json.dao.User.class);

			dbconnect.json.dao.User originalUser = (dbconnect.json.dao.User) collection
					.findOne(new BasicDBObject("userID", newUser.getUserID()));
			if (originalUser == null) {
				dbconnect.json.dao.User u = UserConverter.getInstance()
						.unconvert(newUser);
				u.setUserId(getNewId());
				collection.insert(u);
			} else {
				ObjectId originalID = originalUser.getObjectId("_id");
				dbconnect.json.dao.User u = UserConverter.getInstance()
						.unconvert(newUser);
				u.put("_id", originalID);
				collection.save(u);
			}
			return true;
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Form fetchForm(int formId) {
		try {
			DBCollection collection = createCollection("forms",
					dbconnect.json.dao.Form.class);

			DBObject form = collection.findOne(new BasicDBObject("formID",
					formId));
			if (form == null) {
				return null;
			}
			return FormConverter.getInstance().convert(
					(dbconnect.json.dao.Form) form);
		} catch (MongoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User fetchUser(int userId) {
		try {
			DBCollection collection = createCollection("users",
					dbconnect.json.dao.User.class);

			DBObject user = collection.findOne(new BasicDBObject("userID",
					userId));
			if (user == null) {
				return null;
			}
			return UserConverter.getInstance().convert(
					(dbconnect.json.dao.User) user);
		} catch (MongoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User fetchUserByEmail(String email) {
		try {
			DBCollection collection = createCollection("users",
					dbconnect.json.dao.User.class);

			DBObject user = collection
					.findOne(new BasicDBObject("email", email));
			if (user == null) {
				return null;
			}
			return UserConverter.getInstance().convert(
					(dbconnect.json.dao.User) user);
		} catch (MongoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteForm(int formId) {
		try {
			DBCollection collection = createCollection("forms",
					dbconnect.json.dao.Form.class);

			WriteResult result = collection.remove(new BasicDBObject("formID",
					formId));
			return result.getN() == 1;
		} catch (MongoException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) {
		try {
			DBCollection collection = createCollection("forms",
					dbconnect.json.dao.User.class);

			WriteResult result = collection.remove(new BasicDBObject("userID",
					userId));
			return result.getN() == 1;
		} catch (MongoException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Form> getOwnerForms(int userId) {
		DBCollection collection = createCollection("forms",
				dbconnect.json.dao.Form.class);

		List<Form> forms = new ArrayList<Form>();
		try (DBCursor cursor = collection.find(new BasicDBObject("formOwner",
				userId))) {
			while (cursor.hasNext()) {
				Form form = FormConverter.getInstance().convert(
						(dbconnect.json.dao.Form) cursor.next());
				forms.add(form);
			}
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return forms;
	}

	@Override
	public List<Form> getParticipantForms(int userId) {
		DBCollection collection = createCollection("forms",
				dbconnect.json.dao.Form.class);

		List<Form> forms = new ArrayList<Form>();
		try (DBCursor cursor = collection.find()) {
			while (cursor.hasNext()) {
				Form form = FormConverter.getInstance().convert(
						(dbconnect.json.dao.Form) cursor.next());
				if (form.containsParticipant(userId))
					forms.add(form);
			}
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return forms;
	}

	/**
	 * A mutex used for synchronizing on. Used mainly with generating unique IDs
	 * and ensuring no duplicate access to the database.
	 */
	private final Object mutex = new Object();

	@Override
	public int getNewId() {
		synchronized (mutex) {
			DB db = mongoClient.getDB("taskmanager");
			DBCollection collection = db.getCollection("id_generator");

			try (DBCursor cursor = collection.find(new BasicDBObject(),
					new BasicDBObject("id", 1))) {
				if (cursor.hasNext()) {
					BasicDBObject o = (BasicDBObject) cursor.next();
					int i = o.getInt("id");

					BasicDBObject updatedId = new BasicDBObject("_id",
							o.getObjectId("_id"));
					updatedId.put("id", i + 1);
					collection.save(updatedId);
					return i;
				} else {
					collection.insert(new BasicDBObject("id", 1000));
				}
			} catch (MongoException e) {
				e.printStackTrace();
			}

			// Default: generate a positive ID using MongoDB's ObjectID class
			return Math.abs(ObjectId.get().hashCode());
		}
	}
}
