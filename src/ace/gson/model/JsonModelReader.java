/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.gson.Json;
import ace.text.Strings;
import com.google.gson.*;
import java.io.File;
import java.net.URL;

public class JsonModelReader extends Ace {

	private JsonModel _model;
	private String _errorField;

	public JsonModelReader() {
		this(null);
	}

	public JsonModelReader(final JsonModel model) {
		_model = model;
	}

	public JsonModel getModel() {
		return _model;
	}

	public JsonModelReader setModel(final JsonModel model) {
		_model = model;
		return this;
	}

	public String getErrorField() {
		return _errorField;
	}

	public boolean load(final JsonObject options) {
		_errorField = null;
		if (assigned(_model) && Json.isAssignedJsonObject(options)) {
			if (_model.readFieldValues(options)) {
				return true;
			} else {
				_errorField = _model.getLastFieldRead();
			}
		}
		return false;
	}

	public boolean has(final String key) {
		return _model.hasValue(key);
	}

	public JsonElement getAsJsonElement(final String key) {
		return _model.getValue(key);
	}

	public JsonArray getAsJsonArray(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	public JsonObject getAsJsonObject(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	public JsonPrimitive getAsJsonPrimitive(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitive(e) ? e.getAsJsonPrimitive() : null;
	}

	public String getAsString(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveString(e) ? e.getAsString() : null;
	}

	public Boolean getAsBoolean(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveBoolean(e) ? e.getAsBoolean() : null;
	}

	public Number getAsNumber(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveNumber(e) ? e.getAsNumber() : null;
	}

	public Byte getAsByte(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.byteValue() : null;
	}

	public Short getAsShort(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.shortValue() : null;
	}

	public Integer getAsInteger(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.intValue() : null;
	}

	public Long getAsLong(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.longValue() : null;
	}

	public Float getAsFloat(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.floatValue() : null;
	}

	public Double getAsDouble(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.doubleValue() : null;
	}

	public File getAsFile(final String key) {
		final String s = getAsString(key);
		return Strings.hasText(s) ? new File(s) : null;
	}

	public URL getAsUrl(final String key) {
		final String x = getAsString(key);
		if (assigned(x)) {
			try {
				return new URL(x);
			} catch (final Exception e) {
				GEH.setLastException(e);
			}
		}
		return null;
	}

}
