/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.gson.Json;
import ace.text.Strings;
import com.google.gson.*;
import java.io.File;
import java.net.URL;

/**
 * Json model reader class.
 */
public class JsonModelReader extends Ace {

	private JsonModel _model;
	private String _errorField;

	/**
	 * Default constructor.
	 */
	public JsonModelReader() {
		this(null);
	}

	/**
	 * Constructor accepting a json model instance.
	 * 
	 * @param model 
	 */
	public JsonModelReader(final JsonModel model) {
		_model = model;
	}

	/**
	 * Gets the json model instance.
	 * 
	 * @return the json model instance
	 */
	public JsonModel getModel() {
		return _model;
	}

	/**
	 * Sets the json model instance.
	 * 
	 * @param model
	 * @return itself
	 */
	public JsonModelReader setModel(final JsonModel model) {
		_model = model;
		return this;
	}

	/**
	 * Gets the json field that generated the error.
	 * 
	 * @return the json field that generated the error
	 */
	public String getErrorField() {
		return _errorField;
	}

	/**
	 * Loads the specified json object with the json model.
	 * 
	 * @param options
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
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

	/**
	 * Determines if the specified field was read with the json model.
	 * 
	 * @param key
	 * @return <tt>true</tt> if the specified field was read with the json model, <tt>false</tt> otherwise
	 */
	public boolean has(final String key) {
		return _model.hasValue(key);
	}

	// GETTERS

	/**
	 * Gets the read value of the specified field as a json element.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public JsonElement getAsJsonElement(final String key) {
		return _model.getValue(key);
	}

	/**
	 * Gets the read value of the specified field as a json array.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public JsonArray getAsJsonArray(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	/**
	 * Gets the read value of the specified field as a json object.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public JsonObject getAsJsonObject(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	/**
	 * Gets the read value of the specified field as a json primitive.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public JsonPrimitive getAsJsonPrimitive(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitive(e) ? e.getAsJsonPrimitive() : null;
	}

	/**
	 * Gets the read value of the specified field as an string value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public String getAsString(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveString(e) ? e.getAsString() : null;
	}

	/**
	 * Gets the read value of the specified field as a boolean value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Boolean getAsBoolean(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveBoolean(e) ? e.getAsBoolean() : null;
	}

	/**
	 * Gets the read value of the specified field as a number value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Number getAsNumber(final String key) {
		final JsonElement e = getAsJsonElement(key);
		return Json.isAssignedJsonPrimitiveNumber(e) ? e.getAsNumber() : null;
	}

	/**
	 * Gets the read value of the specified field as a byte value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Byte getAsByte(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.byteValue() : null;
	}

	/**
	 * Gets the read value of the specified field as a short value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Short getAsShort(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.shortValue() : null;
	}

	/**
	 * Gets the read value of the specified field as an integer value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Integer getAsInteger(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.intValue() : null;
	}

	/**
	 * Gets the read value of the specified field as a long value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Long getAsLong(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.longValue() : null;
	}

	/**
	 * Gets the read value of the specified field as a float value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Float getAsFloat(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.floatValue() : null;
	}

	/**
	 * Gets the read value of the specified field as a double value.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public Double getAsDouble(final String key) {
		final Number n = getAsNumber(key);
		return assigned(n) ? n.doubleValue() : null;
	}

	/**
	 * Gets the read value of the specified field as a file instance.
	 * 
	 * @param key
	 * @return the resulting value
	 */
	public File getAsFile(final String key) {
		final String s = getAsString(key);
		return Strings.hasText(s) ? new File(s) : null;
	}

	/**
	 * Gets the read value of the specified field as an url instance.
	 * 
	 * @param key
	 * @return the resulting value
	 */
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
