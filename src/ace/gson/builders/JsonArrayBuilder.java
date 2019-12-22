/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.builders;

import ace.Ace;
import ace.gson.Json;
import com.google.gson.*;
import java.util.Collection;

/**
 * Useful json array builder class.
 */
public class JsonArrayBuilder extends Ace {

	private JsonArray _instance;

	/**
	 * Default constructor.
	 */
	public JsonArrayBuilder() {
		this(new JsonArray());
	}

	/**
	 * Constructor accepting other json array builder instance.
	 * 
	 * @param builder 
	 */
	public JsonArrayBuilder(final JsonArrayBuilder builder) {
		this(builder.getAsJsonArray());
	}

	/**
	 * Constructor accepting a json array instance.
	 * 
	 * @param instance 
	 */
	public JsonArrayBuilder(final JsonArray instance) {
		reset(instance);
	}

	/**
	 * Resets the content of the builder by dropping it.
	 * 
	 * @return itself
	 */
	public final JsonArrayBuilder reset() {
		return reset(new JsonArray());
	}

	/**
	 * Resets the content of the builder by assigning the values of the specified json array builder instance.
	 * 
	 * @param builder 
	 * @return itself
	 */
	public final JsonArrayBuilder reset(final JsonArrayBuilder builder) {
		return reset(builder.getAsJsonArray());
	}

	/**
	 * Resets the content of the builder by assigning the values of the specified json array instance.
	 * 
	 * @param instance 
	 * @return itself
	 */
	public final JsonArrayBuilder reset(final JsonArray instance) {
		_instance = instance;
		return this;
	}

	/**
	 * Adds all the elements of the specified array.
	 * 
	 * @param array 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final JsonArray array) {
		for (final JsonElement e : array) {
			add(e);
		}
		return this;
	}

	/**
	 * Adds the specified json element.
	 * 
	 * @param element 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final JsonElement element) {
		_instance.add(element);
		return this;
	}

	/**
	 * Adds all the json elements from the specified json elements array.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final JsonElement[] elements) {
		for (final JsonElement element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds all the json elements from the specified json elements collection.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final Collection<JsonElement> elements) {
		for (final JsonElement element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds all the json objects from the specified json objects array.
	 * 
	 * @param objects 
	 * @return itself
	 */
	public final JsonArrayBuilder addObjects(final Collection<JsonObject> objects) {
		for (final JsonObject object : objects) {
			add(object);
		}
		return this;
	}

	/**
	 * Adds all the json arrays from the specified json arrays array.
	 * 
	 * @param arrays 
	 * @return itself
	 */
	public final JsonArrayBuilder addArrays(final Collection<JsonArray> arrays) {
		for (final JsonArray array : arrays) {
			add(array);
		}
		return this;
	}

	/**
	 * Adds the specified boolean value.
	 * 
	 * @param element 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final Boolean element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds all the elements of the specified boolean array.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final Boolean[] elements) {
		for (final Boolean element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds the specified number value.
	 * 
	 * @param element 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final Number element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds all the elements of the specified number array.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final Number[] elements) {
		for (final Number element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds the specified character value.
	 * 
	 * @param element 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final Character element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds all the elements of the specified character array.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final Character[] elements) {
		for (final Character element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds the specified string value.
	 * 
	 * @param element 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final String element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	/**
	 * Adds all the elements of the specified string array.
	 * 
	 * @param elements 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final String[] elements) {
		for (final String element : elements) {
			add(element);
		}
		return this;
	}

	/**
	 * Adds the resulting json array of the specified json array builder value.
	 * 
	 * NOTE: the json array value from the builder is get immediately
	 * 
	 * @param builder 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final JsonArrayBuilder builder) {
		return add(builder.getAsJsonArray());
	}

	/**
	 * Adds the json elements of the resulting json array of the specified json array builder value.
	 * 
	 * NOTE: the json array value from the builder is get immediately
	 * 
	 * @param builder 
	 * @return itself
	 */
	public final JsonArrayBuilder addElements(final JsonArrayBuilder builder) {
		return addElements(builder.getAsJsonArray());
	}

	/**
	 * Adds the resulting json object of the specified json object builder value.
	 * 
	 * NOTE: the json object value from the builder is get immediately
	 * 
	 * @param builder 
	 * @return itself
	 */
	public final JsonArrayBuilder add(final JsonObjectBuilder builder) {
		return add(builder.getAsJsonObject());
	}

	/**
	 * Builds the resulting json array and returns it as its string representation.
	 * 
	 * @return the resulting json array as a string
	 */
	public final String getAsString() {
		return Json.JsonElementToString(_instance);
	}

	/**
	 * Builds the resulting json array and returns it as its prettily formatted string representation.
	 * 
	 * @return the resulting json array as a pretty formatted string
	 */
	public final String getAsPrettyString() {
		return Json.JsonElementToPrettyString(_instance);
	}

	/**
	 * Builds the resulting json array and returns it.
	 * 
	 * @return the resulting json array
	 */
	public final JsonArray getAsJsonArray() {
		return _instance;
	}

}
