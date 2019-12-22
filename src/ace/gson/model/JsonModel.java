/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.containers.Lists;
import ace.containers.Maps;
import ace.gson.Json;
import ace.gson.builders.JsonArrayBuilder;
import com.google.gson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json model class.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class JsonModel extends Ace {

	private final List<JsonModelField> _fields = Lists.make();
	private final HashMap<String, JsonElement> _values = Maps.make();
	private String _lastFieldRead;

	// MODEL FIELDS RELATED METHODS

	/**
	 * Gets the json model fields as a list.
	 * 
	 * @return the resulting list
	 */
	public List<JsonModelField> getModelFields() {
		return _fields;
	}

	/**
	 * Gets the json model field with the specified name.
	 * 
	 * @param name
	 * @return the resulting json model field if exists, <tt>null</tt> otherwise
	 */
	public JsonModelField getModelFieldByName(final String name) {
		for (final JsonModelField jmf : _fields) {
			if (jmf.getName().equals(name)) {
				return jmf;
			}
		}
		return null;
	}

	/**
	 * Registers a field with the specified name and the specified mandatory status.
	 * 
	 * @param name
	 * @param mandatory
	 * @return itself
	 */
	public JsonModel register(final String name, final boolean mandatory) {
		register(name, mandatory, JsonModelFieldValueValidator.makeLenientValidator());
		return this;
	}

	/**
	 * Registers a field with the specified name, the specified mandatory status and the specified validator.
	 * 
	 * @param name
	 * @param mandatory
	 * @param validator
	 * @return itself
	 */
	public JsonModel register(final String name, final boolean mandatory, final JsonModelFieldValueValidator validator) {
		_fields.add(new JsonModelField(name, mandatory, validator));
		return this;
	}

	/**
	 * Registers a mandatory field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatory(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeLenientValidator());
	}

	/**
	 * Registers a mandatory field with the specified name and the specified validator.
	 * 
	 * @param name
	 * @param validator
	 * @return itself
	 */
	public JsonModel registerMandatory(final String name, final JsonModelFieldValueValidator validator) {
		return register(name, true, validator);
	}

	/**
	 * Registers an optional field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptional(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeLenientValidator());
	}

	/**
	 * Registers an optional field with the specified name and the specified validator.
	 * 
	 * @param name
	 * @param validator
	 * @return itself
	 */
	public JsonModel registerOptional(final String name, final JsonModelFieldValueValidator validator) {
		return register(name, false, validator);
	}

	/**
	 * Registers a mandatory json object field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryObject(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeObjectValidator());
	}

	/**
	 * Registers an optional json object field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalObject(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeObjectValidator());
	}

	/**
	 * Registers a mandatory non empty json object field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryNonEmptyObject(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyObjectValidator());
	}

	/**
	 * Registers an optional non empty json object field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalNonEmptyObject(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyObjectValidator());
	}

	/**
	 * Registers a mandatory json array field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryArray(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeArrayValidator());
	}

	/**
	 * Registers an optional json array field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalArray(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeArrayValidator());
	}

	/**
	 * Registers a mandatory non empty json array field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryNonEmptyArray(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyArrayValidator());
	}

	/**
	 * Registers an optional non empty json array field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalNonEmptyArray(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyArrayValidator());
	}

	/**
	 * Registers a mandatory json primitive field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryPrimitive(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makePrimitiveValidator());
	}

	/**
	 * Registers an optional json primitive field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalPrimitive(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makePrimitiveValidator());
	}

	/**
	 * Registers a mandatory number field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryNumber(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNumberValidator());
	}

	/**
	 * Registers an optional number field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalNumber(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNumberValidator());
	}

	/**
	 * Registers a mandatory boolean field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryBoolean(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeBooleanValidator());
	}

	/**
	 * Registers an optional boolean field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalBoolean(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeBooleanValidator());
	}

	/**
	 * Registers a mandatory string field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryString(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeStringValidator());
	}

	/**
	 * Registers an optional string field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalString(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeStringValidator());
	}

	/**
	 * Registers a mandatory non empty string field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerMandatoryNonEmptyString(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyStringValidator());
	}

	/**
	 * Registers an optional non empty string field with the specified name.
	 * 
	 * @param name
	 * @return itself
	 */
	public JsonModel registerOptionalNonEmptyString(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyStringValidator());
	}

	/**
	 * Registers a mandatory enumerated string field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryStringEnum(final String name, final String[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeStringEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated string field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalStringEnum(final String name, final String[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeStringEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated byte field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryByteEnum(final String name, final Byte[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeByteEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated string field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalByteEnum(final String name, final Byte[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeByteEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated short field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryShortEnum(final String name, final Short[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeShortEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated short field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalShortEnum(final String name, final Short[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeShortEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated integer field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryIntegerEnum(final String name, final Integer[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeIntegerEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated integer field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalIntegerEnum(final String name, final Integer[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeIntegerEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated long field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryLongEnum(final String name, final Long[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeLongEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated long field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalLongEnum(final String name, final Long[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeLongEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated float field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryFloatEnum(final String name, final Float[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeFloatEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated float field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalFloatEnum(final String name, final Float[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeFloatEnumValidator(values));
	}

	/**
	 * Registers a mandatory enumerated double field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerMandatoryDoubleEnum(final String name, final Double[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeDoubleEnumValidator(values));
	}

	/**
	 * Registers an optional enumerated double field with the specified name and the specified possible values.
	 * 
	 * @param name
	 * @param values
	 * @return itself
	 */
	public JsonModel registerOptionalDoubleEnum(final String name, final Double[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeDoubleEnumValidator(values));
	}

	// FIELDS RELATED METHODS

	/**
	 * Convenience method that checks for the existence of the specified field by path in the specified json object.
	 * 
	 * NOTE: the specified field path must be specified in the form of xpath like 'obj1/obj2/field'
	 * 
	 * @param object
	 * @param name
	 * @return <tt>true</tt> if the field exists, <tt>false</tt> otherwise
	 */
	public static boolean hasField(final JsonObject object, final String name) {
		JsonObject item = object;
		boolean result = item.has(name);
		if (!result) {
			final String[] keys = name.split("/");
			for (int i = 0; i < keys.length - 1; i++) {
				if (item.has(keys[i])) {
					item = Json.obtainJsonObject(item, keys[i]);
					if (Json.isAssignedJsonElement(item)) {
						result = item.has(keys[i + 1]);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets the specified field by path in the specified json object as a json element.
	 * 
	 * NOTE: the specified field path must be specified in the form of xpath like 'obj1/obj2/field'
	 * 
	 * @param object
	 * @param key
	 * @return the resulting json element if exists, <tt>null</tt> otherwise
	 */
	public JsonElement getField(final JsonObject object, final String key) {
		JsonObject item = object;
		JsonElement result = null;
		if (item.has(key)) {
			result = item.get(key);
		} else {
			final String[] keys = key.split(Json.LEVEL_SEPARATOR);
			for (int i = 0; i < keys.length - 1; i++) {
				if (item.has(keys[i])) {
					item = Json.obtainJsonObject(item, keys[i]);
					if (Json.isAssignedJsonElement(item) && item.has(keys[i + 1])) {
						result = item.get(keys[i + 1]);
					}
				}
			}
		}
		return result;
	}

	// VALUES RELATED METHODS

	/**
	 * Gets the read values as a map with their name and their json element values.
	 * 
	 * @return the resulting map
	 */
	public Map<String, JsonElement> getValues() {
		return _values;
	}

	/**
	 * Sets the specified field with the specified value.
	 * 
	 * @param name
	 * @param value
	 * @return itself
	 */
	public JsonModel setValue(final String name, final JsonElement value) {
		if (_values.containsKey(name)) {
			_values.remove(name);
		}
		_values.put(name, value);
		return this;
	}

	/**
	 * Gets the specified field read value as a json element.
	 * 
	 * @param name
	 * @return the specified field read value as a json element if exists, <tt>null</tt> otherwise
	 */
	public JsonElement getValue(final String name) {
		return _values.get(name);
	}

	/**
	 * Determines if the specified field value was read.
	 * 
	 * @param name
	 * @return <tt>true</tt> if the specified field value was read, <tt>false</tt> otherwise
	 */
	public boolean hasValue(final String name) {
		return _values.containsKey(name);
	}

	/**
	 * Drops all the read values.
	 */
	public void clearValues() {
		_values.clear();
	}

	// VALIDATING AND READING RELATED METHODS

	/**
	 * Validates the specified json element value with the validator of the specified field.
	 * 
	 * @param name
	 * @param value
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean validateFieldValue(final String name, final JsonElement value) {
		final JsonModelField jmf = getModelFieldByName(name);
		return assigned(jmf) ? jmf.validateValue(value) : false;
	}

	/**
	 * Gets the last field read.
	 * 
	 * @return the last field read
	 */
	public String getLastFieldRead() {
		return _lastFieldRead;
	}

	/**
	 * Performs a value reading with the specified json model field from the specified json object.
	 * 
	 * @param object
	 * @param item
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean readFieldValue(final JsonObject object, final JsonModelField item) {
		_lastFieldRead = item.getName();
		if (hasField(object, item.getName())) {
			final JsonElement value = getField(object, item.getName());
			if (validateFieldValue(item.getName(), value)) {
				setValue(item.getName(), value);
				return true;
			}
			return false;
		} else {
			return !item.isMandatory();
		}
	}

	/**
	 * Performs the value reading from the specified json object.
	 * 
	 * @param object
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean readFieldValues(final JsonObject object) {
		clearValues();
		for (final JsonModelField item : _fields) {
			if (!readFieldValue(object, item)) {
				return false;
			}
		}
		return true;
	}

	// SERIALIZATION

	/**
	 * Loads the json model fields configuration from a json array.
	 * 
	 * @param array
	 * @return itself
	 */
	public JsonModel fromJsonArray(final JsonArray array) {
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonObject(e)) {
				_fields.add(new JsonModelField(e.getAsJsonObject()));
			}
		}
		return this;
	}

	/**
	 * Saves the json model fields configuration to a json array.
	 * 
	 * @return itself
	 */
	public JsonArray toJsonArray() {
		return new JsonArrayBuilder() {
			{
				for (final JsonModelField field : _fields) {
					add(field.toJsonObject());
				}
			}
		}.getAsJsonArray();
	}

}
