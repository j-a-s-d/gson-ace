/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.gson.Json;
import ace.gson.builders.JsonObjectBuilder;
import com.google.gson.*;

/**
 * Json model field class.
 */
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

	/**
	 * Converts the json model field to a json object including the name, the mandatory flag and the validator as a json object.
	 * 
	 * @return the resulting json object
	 */
	public final JsonObject toJsonObject() {
		return new JsonObjectBuilder()
			.add("name", _name)
			.add("mandatory", _mandatory)
			.add("validator", _validator.toJsonObject())
			.getAsJsonObject();
	}

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public final String getName() {
		return _name;
	}

	/**
	 * Determines if the field is mandatory.
	 * 
	 * @return <tt>true</tt> if the field is mandatory, <tt>false</tt> otherwise
	 */
	public final boolean isMandatory() {
		return _mandatory;
	}

	/**
	 * Validates the specified json element value with the internal validator.
	 * 
	 * @param value
	 * @return <tt>true</tt> if the specified value was validated, <tt>false</tt> otherwise
	 */
	public final boolean validateValue(final JsonElement value) {
		return _validator.validateField(value);
	}

}
