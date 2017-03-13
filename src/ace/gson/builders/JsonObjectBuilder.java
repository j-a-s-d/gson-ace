/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.builders;

import ace.Ace;
import ace.gson.Json;
import com.google.gson.*;

public class JsonObjectBuilder extends Ace {

	private JsonObject _instance;

	public JsonObjectBuilder() {
		this(new JsonObject());
	}

	public JsonObjectBuilder(final JsonObjectBuilder builder) {
		this(builder.getAsJsonObject());
	}

	public JsonObjectBuilder(final JsonObject instance) {
		reset(instance);
	}

	public final JsonObjectBuilder reset() {
		return reset(new JsonObject());
	}

	public final JsonObjectBuilder reset(final JsonObjectBuilder builder) {
		return reset(builder.getAsJsonObject());
	}

	public final JsonObjectBuilder reset(final JsonObject instance) {
		_instance = Json.cloneJsonObject(instance);
		return this;
	}

	public final JsonObjectBuilder add(final String name) {
		_instance.add(name, Json.NULL);
		return this;
	}

	public final JsonObjectBuilder add(final String name, final JsonElement element) {
		_instance.add(name, element);
		return this;
	}

	public final JsonObjectBuilder add(final String name, final JsonElement[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final Boolean element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	public final JsonObjectBuilder add(final String name, final Boolean[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final Number element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	public final JsonObjectBuilder add(final String name, final Number[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final Character element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	public final JsonObjectBuilder add(final String name, final Character[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final String[] elements) {
		return add(name, new JsonArrayBuilder().addElements(elements).getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final String element) {
		return add(name, Json.convertValueToJsonPrimitive(element));
	}

	public final JsonObjectBuilder add(final String name, final JsonArrayBuilder builder) {
		return add(name, builder.getAsJsonArray());
	}

	public final JsonObjectBuilder add(final String name, final JsonObjectBuilder builder) {
		return add(name, builder.getAsJsonObject());
	}

	public final String getAsString() {
		return Json.JsonElementToString(_instance);
	}

	public final String getAsPrettyString() {
		return Json.JsonElementToPrettyString(_instance);
	}

	public final JsonObject getAsJsonObject() {
		return _instance;
	}

}
