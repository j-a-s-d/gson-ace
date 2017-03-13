/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.builders;

import ace.Ace;
import ace.gson.Json;
import com.google.gson.*;
import java.util.Collection;

public class JsonArrayBuilder extends Ace {

	private JsonArray _instance;

	public JsonArrayBuilder() {
		this(new JsonArray());
	}

	public JsonArrayBuilder(final JsonArrayBuilder builder) {
		this(builder.getAsJsonArray());
	}

	public JsonArrayBuilder(final JsonArray instance) {
		reset(instance);
	}

	public final JsonArrayBuilder reset() {
		return reset(new JsonArray());
	}

	public final JsonArrayBuilder reset(final JsonArrayBuilder builder) {
		return reset(builder.getAsJsonArray());
	}

	public final JsonArrayBuilder reset(final JsonArray instance) {
		_instance = instance;
		return this;
	}

	public final JsonArrayBuilder addElements(final JsonArray array) {
		for (final JsonElement e : array) {
			add(e);
		}
		return this;
	}

	public final JsonArrayBuilder add(final JsonElement element) {
		_instance.add(element);
		return this;
	}

	public final JsonArrayBuilder addElements(final JsonElement[] elements) {
		for (final JsonElement element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder addElements(final Collection<JsonElement> elements) {
		for (final JsonElement element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder addObjects(final Collection<JsonObject> objects) {
		for (final JsonObject object : objects) {
			add(object);
		}
		return this;
	}

	public final JsonArrayBuilder addArrays(final Collection<JsonArray> arrays) {
		for (final JsonArray array : arrays) {
			add(array);
		}
		return this;
	}

	public final JsonArrayBuilder add(final Boolean element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	public final JsonArrayBuilder addElements(final Boolean[] elements) {
		for (final Boolean element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder add(final Number element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	public final JsonArrayBuilder addElements(final Number[] elements) {
		for (final Number element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder add(final Character element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	public final JsonArrayBuilder addElements(final Character[] elements) {
		for (final Character element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder add(final String element) {
		return add(Json.convertValueToJsonPrimitive(element));
	}

	public final JsonArrayBuilder addElements(final String[] elements) {
		for (final String element : elements) {
			add(element);
		}
		return this;
	}

	public final JsonArrayBuilder add(final JsonArrayBuilder builder) {
		return add(builder.getAsJsonArray());
	}

	public final JsonArrayBuilder addElements(final JsonArrayBuilder builder) {
		return addElements(builder.getAsJsonArray());
	}

	public final JsonArrayBuilder add(final JsonObjectBuilder builder) {
		return add(builder.getAsJsonObject());
	}

	public final String getAsString() {
		return Json.JsonElementToString(_instance);
	}

	public final String getAsPrettyString() {
		return Json.JsonElementToPrettyString(_instance);
	}

	public final JsonArray getAsJsonArray() {
		return _instance;
	}

}
