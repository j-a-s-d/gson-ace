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

@SuppressWarnings("PMD.TooManyMethods")
public class JsonModel extends Ace {

	private final List<JsonModelField> _fields = Lists.make();
	private final HashMap<String, JsonElement> _values = Maps.make();
	private String _lastFieldRead;

	// MODEL FIELDS RELATED METHODS
	public List<JsonModelField> getModelFields() {
		return _fields;
	}

	public JsonModelField getModelFieldByName(final String name) {
		for (final JsonModelField jmf : _fields) {
			if (jmf.getName().equals(name)) {
				return jmf;
			}
		}
		return null;
	}

	public JsonModel register(final String name, final boolean mandatory) {
		register(name, mandatory, JsonModelFieldValueValidator.makeLenientValidator());
		return this;
	}

	public JsonModel register(final String name, final boolean mandatory, final JsonModelFieldValueValidator validator) {
		_fields.add(new JsonModelField(name, mandatory, validator));
		return this;
	}

	public JsonModel registerMandatory(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeLenientValidator());
	}

	public JsonModel registerMandatory(final String name, final JsonModelFieldValueValidator validator) {
		return register(name, true, validator);
	}

	public JsonModel registerOptional(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeLenientValidator());
	}

	public JsonModel registerOptional(final String name, final JsonModelFieldValueValidator validator) {
		return register(name, false, validator);
	}

	public JsonModel registerMandatoryObject(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeObjectValidator());
	}

	public JsonModel registerOptionalObject(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeObjectValidator());
	}

	public JsonModel registerMandatoryNonEmptyObject(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyObjectValidator());
	}

	public JsonModel registerOptionalNonEmptyObject(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyObjectValidator());
	}

	public JsonModel registerMandatoryArray(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeArrayValidator());
	}

	public JsonModel registerOptionalArray(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeArrayValidator());
	}

	public JsonModel registerMandatoryNonEmptyArray(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyArrayValidator());
	}

	public JsonModel registerOptionalNonEmptyArray(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyArrayValidator());
	}

	public JsonModel registerMandatoryPrimitive(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makePrimitiveValidator());
	}

	public JsonModel registerOptionalPrimitive(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makePrimitiveValidator());
	}

	public JsonModel registerMandatoryNumber(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNumberValidator());
	}

	public JsonModel registerOptionalNumber(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNumberValidator());
	}

	public JsonModel registerMandatoryBoolean(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeBooleanValidator());
	}

	public JsonModel registerOptionalBoolean(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeBooleanValidator());
	}

	public JsonModel registerMandatoryString(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeStringValidator());
	}

	public JsonModel registerOptionalString(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeStringValidator());
	}

	public JsonModel registerMandatoryNonEmptyString(final String name) {
		return register(name, true, JsonModelFieldValueValidator.makeNonEmptyStringValidator());
	}

	public JsonModel registerOptionalNonEmptyString(final String name) {
		return register(name, false, JsonModelFieldValueValidator.makeNonEmptyStringValidator());
	}

	public JsonModel registerMandatoryStringEnum(final String name, final String[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeStringEnumValidator(values));
	}

	public JsonModel registerOptionalStringEnum(final String name, final String[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeStringEnumValidator(values));
	}

	public JsonModel registerMandatoryByteEnum(final String name, final Byte[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeByteEnumValidator(values));
	}

	public JsonModel registerOptionalByteEnum(final String name, final Byte[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeByteEnumValidator(values));
	}

	public JsonModel registerMandatoryShortEnum(final String name, final Short[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeShortEnumValidator(values));
	}

	public JsonModel registerOptionalShortEnum(final String name, final Short[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeShortEnumValidator(values));
	}

	public JsonModel registerMandatoryIntegerEnum(final String name, final Integer[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeIntegerEnumValidator(values));
	}

	public JsonModel registerOptionalIntegerEnum(final String name, final Integer[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeIntegerEnumValidator(values));
	}

	public JsonModel registerMandatoryLongEnum(final String name, final Long[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeLongEnumValidator(values));
	}

	public JsonModel registerOptionalLongEnum(final String name, final Long[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeLongEnumValidator(values));
	}

	public JsonModel registerMandatoryFloatEnum(final String name, final Float[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeFloatEnumValidator(values));
	}

	public JsonModel registerOptionalFloatEnum(final String name, final Float[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeFloatEnumValidator(values));
	}

	public JsonModel registerMandatoryDoubleEnum(final String name, final Double[] values) {
		return register(name, true, JsonModelFieldValueValidator.makeDoubleEnumValidator(values));
	}

	public JsonModel registerOptionalDoubleEnum(final String name, final Double[] values) {
		return register(name, false, JsonModelFieldValueValidator.makeDoubleEnumValidator(values));
	}

	// FIELDS RELATED METHODS
	public static boolean hasField(final JsonObject o, final String name) {
		JsonObject item = o;
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

	public JsonElement getField(final JsonObject o, final String key) {
		JsonObject item = o;
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
	public Map<String, JsonElement> getValues() {
		return _values;
	}

	public JsonModel setValue(final String name, final JsonElement value) {
		if (_values.containsKey(name)) {
			_values.remove(name);
		}
		_values.put(name, value);
		return this;
	}

	public JsonElement getValue(final String name) {
		return _values.get(name);
	}

	public boolean hasValue(final String name) {
		return _values.containsKey(name);
	}

	public void clearValues() {
		_values.clear();
	}

	// VALIDATING AND READING RELATED METHODS
	public boolean validateFieldValue(final String name, final JsonElement value) {
		final JsonModelField jmf = getModelFieldByName(name);
		return assigned(jmf) ? jmf.validateValue(value) : false;
	}

	public String getLastFieldRead() {
		return _lastFieldRead;
	}

	public boolean readFieldValue(final JsonObject o, final JsonModelField item) {
		_lastFieldRead = item.getName();
		if (hasField(o, item.getName())) {
			final JsonElement value = getField(o, item.getName());
			if (validateFieldValue(item.getName(), value)) {
				setValue(item.getName(), value);
				return true;
			}
			return false;
		} else {
			return !item.isMandatory();
		}
	}

	public boolean readFieldValues(final JsonObject o) {
		clearValues();
		for (final JsonModelField item : _fields) {
			if (!readFieldValue(o, item)) {
				return false;
			}
		}
		return true;
	}

	// SERIALIZATION
	public JsonModel fromJsonArray(final JsonArray array) {
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonObject(e)) {
				_fields.add(new JsonModelField(e.getAsJsonObject()));
			}
		}
		return this;
	}

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
