package form;

import com.saxonica.schema.sdoc.XSDSchema;

public final class SchemaFactory {

	public Form deconstructSchema(String data) {
		// TODO
		return null;
	}

	public String constructSchema(Form data) {
		XSDSchema schema;
		
		schema = new XSDSchema();
		
		return schema.getStringValue();
	}
}
