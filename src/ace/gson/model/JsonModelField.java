/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.gson.Json;
import ace.gson.builders.JsonObjectBuilder;
import com.google.gson.*;

public class JsonModelField extends Ace {

	private final String _name;
	private final boolean _mandatory;
	private final JsonModelFieldValueValidator _validator;

	JsonModelField(final String name, final boolean mandatory, final JsonModelFieldValueValidator validator) {
		_name = name;
		_mandatory = mandatory;
		_validator = validator;
	}

	JsonModelField(final JsonObject o) {
		_name = Json.obtainString(o, "name");
		final JsonElement m = Json.obtainJsonElement(o, "mandatory");
		_mandatory = Json.isAssignedJsonPrimitiveBoolean(m) ? m.getAsBoolean() : false;
		_validator = JsonModelFieldValueValidator.fromJsonObject(Json.obtainJsonObject(o, "validator"));
	}

	public final JsonObject toJsonObject() {
		return new JsonObjectBuilder()
			.add("name", _name)
			.add("mandatory", _mandatory)
			.add("validator", _validator.toJsonObject())
			.getAsJsonObject();
	}

	public final String getName() {
		return _name;
	}

	public final boolean isMandatory() {
		return _mandatory;
	}

	public final boolean validateValue(final JsonElement value) {
		return _validator.validateField(value);
	}

}
