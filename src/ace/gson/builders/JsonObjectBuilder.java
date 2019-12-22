/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.builders;

import ace.Ace;
import ace.gson.Json;
import com.google.gson.*;

/**
 * Useful json object builder class.
 */
public class JsonObjectBuilder extends Ace {

	private JsonObject _instance;

	/**
	 * Default constructor.
	 */
	public JsonObjectBuilder() {
		this(new JsonObject());
	}

	/**
	 * Constructor accepting other json object builder instance.
	 * 
	 * @param builder 
	 */
	public JsonObjectBuilder(final JsonObjectBuilder builder) {
		this(builder.getAsJsonObject());
	}

	/**
	 * Constructor accepting a json object instance.
	 * 
	 * @param instance 
	 */
	public JsonObjectBuilder(final JsonObject instance) {
		reset(instance);
	}

	/**
	 * Resets the content of the builder by dropping it.
	 * 
	 * @return itself
	 */
	public final JsonObjectBuilder reset() {
		return reset(new JsonObject());
	}

	/**
	 * Resets the content of the builder by assigning the values of the specified json object builder instance.
	 * 
	 * @param builder 
	 * @return itself
	 */
	public final JsonObjectBuilder reset(final JsonObjectBuilder builder) {
		return reset(builder.getAsJsonObject());
	}

	/**
	 * Resets the content of the builder by assigning the values of the specified json object instance.
	 * 
	 * @param instance 
	 * @return itself
	 */
	public final JsonObjectBuilder reset(final JsonObject instance) {
		_instance = Json.cloneJsonObject(instance);
		return this;
	}

	/**
	 * Adds a field with the specified name and with <tt>null</tt> value.
	 * 
	 * @param name 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name) {
		_instance.add(name, Json.NULL);
		return this;
	}

	/**
	 * Adds a field with the specified name and the specified element value.
	 * 
	 * @param name 
	 * @param element 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final JsonElement element) {
		_instance.add(name, element);
		return this;
	}

	/**
	 * Adds a field with the specified name and the specified json elements array value.
	 * 
	 * @param name 
	 * @param elements 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final JsonElement[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the specified boolean value.
	 * 
	 * @param name 
	 * @param element 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Boolean element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds a field with the specified name and the specified boolean array value.
	 * 
	 * @param name 
	 * @param elements 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Boolean[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the specified number value.
	 * 
	 * @param name 
	 * @param element 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Number element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds a field with the specified name and the specified number array value.
	 * 
	 * @param name 
	 * @param elements 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Number[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the specified character value.
	 * 
	 * @param name 
	 * @param element 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Character element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds a field with the specified name and the specified character array value.
	 * 
	 * @param name 
	 * @param elements 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final Character[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the specified string array value.
	 * 
	 * @param name 
	 * @param elements 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final String[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the specified string value.
	 * 
	 * @param name 
	 * @param element 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final String element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds a field with the specified name and the resulting array of the specified json array builder.
	 * 
	 * NOTE: the json object value from the builder is get immediately
	 * 
	 * @param name 
	 * @param builder 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final JsonArrayBuilder builder) {
		return add(name, builder.getAsJsonArray());
	}

	/**
	 * Adds a field with the specified name and the resulting object of the specified json object builder.
	 * 
	 * NOTE: the json array value from the builder is get immediately
	 * 
	 * @param name 
	 * @param builder 
	 * @return itself
	 */
	public final JsonObjectBuilder add(final String name, final JsonObjectBuilder builder) {
		return add(name, builder.getAsJsonObject());
	}

	/**
	 * Builds the resulting json object and returns it as its string representation.
	 * 
	 * @return the resulting json object as a string
	 */
	public final String getAsString() {
		return Json.JsonElementToString(_instance);
	}

	/**
	 * Builds the resulting json object and returns it as its prettily formatted string representation.
	 * 
	 * @return the resulting json object as a pretty formatted string
	 */
	public final String getAsPrettyString() {
		return Json.JsonElementToPrettyString(_instance);
	}

	/**
	 * Builds the resulting json object and returns it.
	 * 
	 * @return the resulting json object
	 */
	public final JsonObject getAsJsonObject() {
		return _instance;
	}

}
