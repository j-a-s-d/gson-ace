/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson;

import ace.Ace;
import ace.SemanticVersion;
import ace.constants.STRINGS;
import ace.files.TextFiles;
import ace.gson.builders.JsonArrayBuilder;
import ace.text.Strings;
import com.google.gson.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Gson Ace class.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Json extends Ace {

	/**
	 * Gson Ace version.
	 */
	public static final SemanticVersion GSON_ACE_VERSION = SemanticVersion.fromString("1.3.0");

	/**
	 * The level separator used for the xpath like paths.
	 */
	public static final String LEVEL_SEPARATOR = "/";

	/**
	 * The default json file name extension: '.json'.
	 */
	public static final String FILE_EXTENSION = ".json";

	/**
	 * An empty json object instance.
	 */
	public static final JsonObject EMPTY_JSON_OBJECT = new JsonObject();

	/**
	 * An empty json array instance.
	 */
	public static final JsonArray EMPTY_JSON_ARRAY = new JsonArray();

	/**
	 * The json null instance.
	 */
	public static final JsonNull NULL = JsonNull.INSTANCE;

	/**
	 * A json primitive boolean instance with the true value.
	 */
	public static final JsonPrimitive TRUE = bool(true);

	/**
	 * A json primitive boolean instance with the false value.
	 */
	public static final JsonPrimitive FALSE = bool(false);

	/**
	 * The default character set used to read and write.
	 */
	public static String DEFAULT_CHARSET = "utf-8";

	// RECURSIVE

	/**
	 * Determines if the specified key exists in the specified json object instance.
	 * 
	 * @param object
	 * @param key
	 * @return <tt>true</tt> if the specified key exists in the specified json object instance, <tt>false</tt> otherwise
	 */
	public static boolean hasJsonObjectField(final JsonObject object, final String key) {
		return assigned(getJsonObjectField(object, key));
	}

	/**
	 * Gets the value of the specified key in the specified object.
	 * 
	 * NOTE: the key supports an xpath like format: 'objectA/objectB/key'
	 * 
	 * @param object
	 * @param key
	 * @return the value of the specified key in the specified object if it exists, <tt>null</tt> otherwise
	 */
	public static JsonElement getJsonObjectField(final JsonObject object, final String key) {
		JsonElement result = null;
		if (assigned(object)) {
			if (object.has(key)) {
				result = object.get(key);
			} else {
				JsonObject item = object;
				final String[] keys = key.split(LEVEL_SEPARATOR);
				for (int i = 0; i < keys.length - 1; i++) {
					if (item.has(keys[i])) {
						item = Json.obtainJsonObject(item, keys[i]);
						if (Json.isAssignedJsonElement(item) && item.has(keys[i + 1])) {
							result = item.get(keys[i + 1]);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Sets the specified value to the specified key in the specified object.
	 * 
	 * NOTE: the key supports an xpath like format: 'objectA/objectB/key'
	 * 
	 * NOTE 2: the field in the key may not exist (it will be added if required) but the parent objects in the key must exist
	 * 
	 * @param object
	 * @param key
	 * @param value
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public static boolean setJsonObjectFieldValue(final JsonObject object, final String key, final JsonElement value) {
		if (assigned(object)) {
			if (object.has(key)) {
				object.add(key, value);
				return true;
			} else {
				JsonObject lastItem = null;
				String lastKey = null;
				JsonObject item = object;
				final String[] keys = key.split(LEVEL_SEPARATOR);
				for (int i = 0; i < keys.length - 1; i++) {
					if (item.has(keys[i])) {
						item = Json.obtainJsonObject(item, keys[i]);
						if (Json.isAssignedJsonElement(item) && item.has(keys[i + 1])) {
							lastKey = keys[i + 1];
							lastItem = item;
							if (keys.length - 1 == i + 1) {
								break;
							}
						}
					}
				}
				if (assigned(lastItem)) {
					lastItem.add(lastKey, value);
					return true;
				}
			}
		}
		return false;
	}

	// PRIMITIVE

	/**
	 * Converts the specified boolean value to a valid json primitive boolean value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive bool(final boolean value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified byte value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive num(final byte value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified integer value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive num(final int value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified long value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive num(final long value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified double value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive num(final double value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified character value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive chr(final char value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified string value to a valid json primitive number value.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive str(final String value) {
		return new JsonPrimitive(value);
	}

	/**
	 * Converts the specified value to a valid json primitive value.
	 * 
	 * NOTE: if the specified value is an instance of Boolean, Number or Character,
	 * it will be converted to their json primitive equivalences;
	 * if it is an instance of other object class, it will be converted via the toString() method
	 * and returned as a json primitive string;
	 * if it is null, it will return null.
	 * 
	 * @param value
	 * @return the resulting json primitive instance
	 */
	public static JsonPrimitive convertValueToJsonPrimitive(final Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Boolean) {
			return new JsonPrimitive((Boolean) value);
		} else if (value instanceof Number) {
			return new JsonPrimitive((Number) value);
		} else if (value instanceof Character) {
			return new JsonPrimitive((Character) value);
		} else {
			return new JsonPrimitive(value.toString());
		}
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json primitive instance.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified element is an assigned (not null) json primitive instance, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonPrimitive(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonPrimitive();
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json primitive boolean instance.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified element is an assigned (not null) json primitive boolean instance, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonPrimitiveBoolean(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isBoolean();
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json primitive number instance.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified element is an assigned (not null) json primitive number instance, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonPrimitiveNumber(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isNumber();
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json primitive string instance.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified element is an assigned (not null) json primitive string instance, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonPrimitiveString(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isString();
	}

	/**
	 * Ensures to get a boolean value from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive boolean instance,
	 * it will default to a <tt>true</tt> value, otherwise it will return the value of the specified json primitive boolean instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting boolean value
	 */
	public static Boolean ensureBoolean(final JsonElement e) {
		return ensureBoolean(e, true);
	}

	/**
	 * Ensures to get a boolean value from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive boolean instance,
	 * it will default to specified default value, otherwise it will return the value of the specified json primitive boolean instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting boolean value
	 */
	public static Boolean ensureBoolean(final JsonElement e, final Boolean defaultValue) {
		return isAssignedJsonPrimitiveBoolean(e) ? e.getAsBoolean() : defaultValue;
	}

	/**
	 * Ensures to get a number value from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive number instance,
	 * it will default to a <tt>0</tt> value, otherwise it will return the value of the specified json primitive number instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting number value
	 */
	public static Number ensureNumber(final JsonElement e) {
		return ensureNumber(e, 0);
	}

	/**
	 * Ensures to get a number value from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive number instance,
	 * it will default to specified default value, otherwise it will return the value of the specified json primitive number instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting number value
	 */
	public static Number ensureNumber(final JsonElement e, final Number defaultValue) {
		return isAssignedJsonPrimitiveNumber(e) ? e.getAsNumber() : defaultValue;
	}

	/**
	 * Obtains the number value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting number value if exist, <tt>0</tt> otherwise
	 */
	public static Number obtainNumber(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0);
	}

	/**
	 * Obtains the number value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting number value if exist, the default value otherwise
	 */
	public static Number obtainNumber(final JsonObject source, final String name, final Number defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue);
	}

	/**
	 * Obtains the byte value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting byte value if exist, <tt>0</tt> otherwise
	 */
	public static Byte obtainByte(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).byteValue();
	}

	/**
	 * Obtains the byte value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting byte value if exist, the default value otherwise
	 */
	public static Byte obtainByte(final JsonObject source, final String name, final Byte defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).byteValue();
	}

	/**
	 * Obtains the integer value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting integer value if exist, <tt>0</tt> otherwise
	 */
	public static Integer obtainInteger(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).intValue();
	}

	/**
	 * Obtains the integer value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting integer value if exist, the default value otherwise
	 */
	public static Integer obtainInteger(final JsonObject source, final String name, final Integer defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).intValue();
	}

	/**
	 * Obtains the short value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting short value if exist, <tt>0</tt> otherwise
	 */
	public static Short obtainShort(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).shortValue();
	}

	/**
	 * Obtains the short value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting short value if exist, the default value otherwise
	 */
	public static Short obtainShort(final JsonObject source, final String name, final Short defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).shortValue();
	}

	/**
	 * Obtains the long value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting long value if exist, <tt>0</tt> otherwise
	 */
	public static Long obtainLong(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).longValue();
	}

	/**
	 * Obtains the long value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting long value if exist, the default value otherwise
	 */
	public static Long obtainLong(final JsonObject source, final String name, final Long defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).longValue();
	}

	/**
	 * Obtains the float value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting float value if exist, <tt>0</tt> otherwise
	 */
	public static Float obtainFloat(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).floatValue();
	}

	/**
	 * Obtains the float value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting float value if exist, the default value otherwise
	 */
	public static Float obtainFloat(final JsonObject source, final String name, final Float defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).floatValue();
	}

	/**
	 * Obtains the double value from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting double value if exist, <tt>0</tt> otherwise
	 */
	public static Double obtainDouble(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).doubleValue();
	}

	/**
	 * Obtains the double value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting double value if exist, the default value otherwise
	 */
	public static Double obtainDouble(final JsonObject source, final String name, final Double defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).doubleValue();
	}

	/**
	 * Obtains the boolean value from the specified field of the specified json object instance.
	 * 
	 * NOTE: the default value is <tt>true</tt>.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting boolean value if exist, <tt>true</tt> otherwise
	 */
	public static Boolean obtainBoolean(final JsonObject source, final String name) {
		return obtainBoolean(source, name, true);
	}

	/**
	 * Obtains the boolean value from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting boolean value if exist, the default value otherwise
	 */
	public static Boolean obtainBoolean(final JsonObject source, final String name, final Boolean defaultValue) {
		return ensureBoolean(obtainJsonElement(source, name), defaultValue);
	}

	// ARRAY

	/**
	 * Determines if the specified json array is not empty.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param a
	 * @return <tt>true</tt> if the specifed json array is not empty, <tt>false</tt> otherwise
	 */
	public static boolean containsElements(final JsonArray a) {
		return a.size() > 0;
	}

	/**
	 * Determines if the first specified json array contains the elements of the second specified json array.
	 * 
	 * NOTE: this method requires the first specified json array is assigned (not null)
	 * 
	 * @param a
	 * @param elements
	 * @return <tt>true</tt> if the first specifed json array contains the specified json elements, <tt>false</tt> otherwise
	 */
	public static boolean containsElements(final JsonArray a, final JsonArray elements) {
		boolean result = isAssignedJsonArray(elements) && elements.size() > 0;
		for (final JsonElement e : elements) {
			result &= a.contains(e);
		}
		return result;
	}

	/**
	 * Determines if the specified json array contains the elements of the specified json elements array.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param a
	 * @param elements
	 * @return <tt>true</tt> if the specifed json array contains the specified json elements, <tt>false</tt> otherwise
	 */
	public static boolean containsElements(final JsonArray a, final JsonElement[] elements) {
		boolean result = elements != null && elements.length > 0;
		for (final JsonElement e : elements) {
			result &= a.contains(e);
		}
		return result;
	}

	/**
	 * Determines if the specified json array contains the specified json element.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param a
	 * @param e
	 * @return <tt>true</tt> if the specifed json array contains the specified json element, <tt>false</tt> otherwise
	 */
	public static boolean containsElement(final JsonArray a, final JsonElement e) {
		return containsElements(a, new JsonElement[] { e });
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json array.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified json element is an assigned (not null) json array, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonArray(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonArray();
	}

	/**
	 * Determines if the specified json element is an assigned (not null) and non empty json array.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified json element is an assigned (not null) and non empty json array, <tt>false</tt> otherwise
	 */
	public static boolean isNotEmptyJsonArray(final JsonElement e) {
		return isAssignedJsonArray(e) && containsElements(e.getAsJsonArray());
	}

	/**
	 * Determines if the specified json object cointains a json array in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object cointains a json array in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasJsonArray(final JsonObject source, final String name) {
		return isAssignedJsonArray(obtainJsonElement(source, name));
	}

	/**
	 * Determines if the specified json object cointains a non empty json array in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object cointains a non empty json array in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasNotEmptyJsonArray(final JsonObject source, final String name) {
		return isNotEmptyJsonArray(obtainJsonElement(source, name));
	}

	/**
	 * Ensures to get a json array instance from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json array instance,
	 * it will default to a new json array instance, otherwise it will return the value of the specified json array instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting json array instance
	 */
	public static JsonArray ensureJsonArray(final JsonElement e) {
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : new JsonArray();
	}

	/**
	 * Ensures to get a json array instance from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json array instance,
	 * it will default to the specified default value, otherwise it will return the value of the specified json array instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting json array instance
	 */
	public static JsonArray ensureJsonArray(final JsonElement e, final JsonArray defaultValue) {
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : ensureJsonArray(defaultValue);
	}

	/**
	 * Obtains the json array from the specified field of the specified json object.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting json array if it exists, a new json array otherwise
	 */
	public static JsonArray obtainJsonArray(final JsonObject source, final String name) {
		return ensureJsonArray(obtainJsonElement(source, name));
	}

	/**
	 * Obtains the json array from the specified field of the specified json object using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting json array if it exists, the specified default value otherwise
	 */
	public static JsonArray obtainJsonArray(final JsonObject source, final String name, final JsonArray defaultValue) {
		return ensureJsonArray(obtainJsonElement(source, name), defaultValue);
	}

	/**
	 * Clones the specified json array instance.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param a
	 * @return the cloned json array instance
	 */
	public static JsonArray cloneJsonArray(final JsonArray a) {
		return readStringAsJsonArray(JsonElementToString(a));
	}

	/**
	 * Combines the specified json array instances into a new json array instance containing all their elements.
	 * 
	 * NOTE: this method requires the specified json arrays are assigned (not null)
	 * 
	 * @param arrays
	 * @return the resulting json array instance
	 */
	public static JsonArray combineJsonArrays(final JsonArray... arrays) {
		return new JsonArrayBuilder() {
			{
				for (final JsonArray a : arrays) {
					addElements(a);
				}
			}
		}.getAsJsonArray();
	}

	/**
	 * Makes a new json array instance.
	 * 
	 * @return the resulting json array instance
	 */
	public static JsonArray makeJsonArray() {
		return new JsonArray();
	}

	/**
	 * Wraps the specified json elements into a new json array instance.
	 * 
	 * @param elements
	 * @return the resulting json array instance
	 */
	public static JsonArray wrapInJsonArray(final JsonElement... elements) {
		final JsonArray result = new JsonArray();
		for (final JsonElement e : elements) {
			result.add(e);
		}
		return result;
	}

	/**
	 * Constructs a new json array instance adding to it only the assigned (not null) elements of the speicifed json array instance.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting json array
	 */
	public static JsonArray removeNullElements(final JsonArray array) {
		final JsonArray result = new JsonArray();
		for (final JsonElement e : array) {
			if (!e.isJsonNull()) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Converts the boolean values of the specified json array instance to an array of boolean values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting boolean vlues array
	 */
	public static Boolean[] convertJsonArraytoBooleanArray(final JsonArray array) {
		final Boolean[] result = new Boolean[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveBoolean(e)) {
				result[i++] = e.getAsBoolean();
			}
		}
		return result;
	}

	/**
	 * Converts the specified boolean values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertBooleanArraytoJsonArray(final Boolean... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the number values of the specified json array instance to an array of number values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting number vlues array
	 */
	public static Number[] convertJsonArraytoNumberArray(final JsonArray array) {
		final Number[] result = new Number[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsNumber();
			}
		}
		return result;
	}

	/**
	 * Converts the specified number values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertNumberArraytoJsonArray(final Number... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the byte values of the specified json array instance to an array of byte values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting byte vlues array
	 */
	public static Byte[] convertJsonArraytoByteArray(final JsonArray array) {
		final Byte[] result = new Byte[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsByte();
			}
		}
		return result;
	}

	/**
	 * Converts the specified byte values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertByteArraytoJsonArray(final Byte... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the short values of the specified json array instance to an array of short values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting short vlues array
	 */
	public static Short[] convertJsonArraytoShortArray(final JsonArray array) {
		final Short[] result = new Short[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsShort();
			}
		}
		return result;
	}

	/**
	 * Converts the specified short values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertShortArraytoJsonArray(final Short... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the integer values of the specified json array instance to an array of integer values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting integer vlues array
	 */
	public static Integer[] convertJsonArraytoIntegerArray(final JsonArray array) {
		final Integer[] result = new Integer[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsInt();
			}
		}
		return result;
	}

	/**
	 * Converts the specified integer values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertIntegerArraytoJsonArray(final Integer... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the long values of the specified json array instance to an array of long values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting long vlues array
	 */
	public static Long[] convertJsonArraytoLongArray(final JsonArray array) {
		final Long[] result = new Long[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsLong();
			}
		}
		return result;
	}

	/**
	 * Converts the specified long values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertLongArraytoJsonArray(final Long... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the double values of the specified json array instance to an array of double values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting double vlues array
	 */
	public static Double[] convertJsonArraytoDoubleArray(final JsonArray array) {
		final Double[] result = new Double[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsDouble();
			}
		}
		return result;
	}

	/**
	 * Converts the specified double values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertDoubleArraytoJsonArray(final Double... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the float values of the specified json array instance to an array of float values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting float vlues array
	 */
	public static Float[] convertJsonArraytoFloatArray(final JsonArray array) {
		final Float[] result = new Float[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveNumber(e)) {
				result[i++] = e.getAsFloat();
			}
		}
		return result;
	}

	/**
	 * Converts the specified float values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertFloatArraytoJsonArray(final Float... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts the string values of the specified json array instance to an array of string values.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * @param array
	 * @return the resulting string vlues array
	 */
	public static String[] convertJsonArraytoStringArray(final JsonArray array) {
		final String[] result = new String[array.size()];
		int i = 0;
		for (final JsonElement e : array) {
			if (Json.isAssignedJsonPrimitiveString(e)) {
				result[i++] = e.getAsString();
			}
		}
		return result;
	}

	/**
	 * Converts the specified string values into a json array instance with that values.
	 * 
	 * @param values
	 * @return the resulting json array
	 */
	public static JsonArray convertStringArraytoJsonArray(final String... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	/**
	 * Converts specified json array instance to list of json object instances.
	 * 
	 * NOTE: this method requires the specified json array is assigned (not null)
	 * 
	 * NOTE 2: any json array element that is not an assigned (not null) json object
	 * will be replaced with an empty json object instance in the resulting list.
	 * 
	 * @param array
	 * @return the resulting list of json objects
	 */
	public static List<JsonObject> convertJsonArraytoJsonObjectList(final JsonArray array) {
		return new LinkedList<JsonObject>() {
			{
				for (final JsonElement item : array) {
					add(ensureJsonObject(item));
				}
			}
		};
	}

	/**
	 * Converts the specified list of values to a json array instance of json primitive values.
	 * 
	 * @param list
	 * @return the resulting json array instance
	 */
	public static JsonArray convertListOfValuesToJsonArray(final List<?> list) {
		final JsonArray result = new JsonArray();
		for (final Object i : list) {
			result.add(convertValueToJsonPrimitive(i));
		}
		return result;
	}

	/**
	 * Converts the specified hash map of values to a json array instance.
	 * 
	 * NOTE: this method requires the specified hash map is assigned (not null)
	 * 
	 * @param map
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray convertHashMapToJsonArray(final HashMap<?, ?> map) {
		try {
			final JsonArray result = new JsonArray();
			for (final Map.Entry<?, ?> e : map.entrySet()) {
				final Object key = e.getKey();
				final Object value = e.getValue();
				if (value instanceof List) {
					result.add(convertListOfValuesToJsonArray((List<?>) value));
				} else {
					final JsonObject o = new JsonObject();
					o.addProperty(key.toString(), value.toString());
					result.add(o);
				}
			}
			return result;
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Reads the specified byte array as a json array.
	 * 
	 * @param buffer
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray readByteArrayAsJsonArray(final byte[] buffer) {
		return readByteArrayAsJsonArray(buffer, DEFAULT_CHARSET);
	}

	/**
	 * Reads the specified byte array as a json array in the specified character set.
	 * 
	 * @param buffer
	 * @param charset
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray readByteArrayAsJsonArray(final byte[] buffer, final String charset) {
		try {
			return readStringAsJsonArray(new String(buffer, charset));
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Reads the specified string as a json array.
	 * 
	 * @param text
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray readStringAsJsonArray(final String text) {
		final JsonElement e = readStringAsJsonElement(text);
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	/**
	 * Reads the specified file as a json array.
	 * 
	 * @param file
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray readFileAsJsonArray(final File file) {
		return readStringAsJsonArray(TextFiles.read(file));
	}

	/**
	 * Reads the file with the specified file name as a json array.
	 * 
	 * @param filename
	 * @return the resulting json array instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonArray readFileAsJsonArray(final String filename) {
		return readStringAsJsonArray(TextFiles.read(filename));
	}

	// OBJECT

	/**
	 * Determines if the specified json object instance is not empty.
	 * 
	 * @param object
	 * @return <tt>true</tt> if the specified json object instance is not empty, <tt>false</tt> otherwise
	 */
	public static boolean hasFields(final JsonObject object) {
		return object != null && !object.entrySet().isEmpty();
	}

	/**
	 * Determines if the specified fields are present in the json object instance.
	 * 
	 * @param object
	 * @param fields
	 * @return <tt>true</tt> if the specified fields are present in the json object instance, <tt>false</tt> otherwise
	 */
	public static boolean hasFields(final JsonObject object, final String... fields) {
		boolean result = fields != null && fields.length > 0;
		for (final String s : fields) {
			result &= object.has(s);
		}
		return result;
	}

	/**
	 * Determines if the specified json element is an assigned (not null) json object.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified json element is an assigned (not null) json object, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonObject(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonObject();
	}

	/**
	 * Determines if the specified json element is an assigned (not null) and non empty json object.
	 * 
	 * @param e
	 * @return <tt>true</tt> if the specified json element is an assigned (not null) and non empty json object, <tt>false</tt> otherwise
	 */
	public static boolean isNotEmptyJsonObject(final JsonElement e) {
		return isAssignedJsonObject(e) && hasFields(e.getAsJsonObject());
	}

	/**
	 * Determines if the specified json object instance cointains a json object instance in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object instance cointains a json object instance in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasJsonObject(final JsonObject source, final String name) {
		return isAssignedJsonObject(obtainJsonElement(source, name));
	}

	/**
	 * Determines if the specified json object instance cointains a non empty json object instance in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object instance cointains a non empty json object instance in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasNotEmptyJsonObject(final JsonObject source, final String name) {
		return isAssignedJsonObject(obtainJsonElement(source, name));
	}

	/**
	 * Ensures to get a json object instance from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json object instance,
	 * it will default to a new json object instance, otherwise it will return the value of the specified json object instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting json object instance
	 */
	public static JsonObject ensureJsonObject(final JsonElement e) {
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : new JsonObject();
	}

	/**
	 * Ensures to get a json object instance from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json object instance,
	 * it will default to the specified default value, otherwise it will return the value of the specified json object instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting json object instance
	 */
	public static JsonObject ensureJsonObject(final JsonElement e, final JsonObject defaultValue) {
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : ensureJsonObject(defaultValue);
	}

	/**
	 * Obtains the json object from the specified field of the specified json object.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting json object if it exists, a new json object otherwise
	 */
	public static JsonObject obtainJsonObject(final JsonObject source, final String name) {
		return ensureJsonObject(obtainJsonElement(source, name));
	}

	/**
	 * Obtains the json object from the specified field of the specified json object using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting json object if it exists, the specified default value otherwise
	 */
	public static JsonObject obtainJsonObject(final JsonObject source, final String name, final JsonObject defaultValue) {
		return ensureJsonObject(obtainJsonElement(source, name), defaultValue);
	}

	/**
	 * Clones the specified json object instance.
	 * 
	 * NOTE: this method requires the specified json object is assigned (not null)
	 * 
	 * @param o
	 * @return the cloned json object instance
	 */
	public static JsonObject cloneJsonObject(final JsonObject o) {
		return readStringAsJsonObject(JsonElementToString(o));
	}

	/**
	 * Converts the specified json object instance to a hash map instance of string keys and values.
	 * 
	 * @param o
	 * @return the resulting hash map
	 */
	public static Map<String, String> convertJsonObjectToStringHashMap(final JsonObject o) {
		final Map<String, String> map = new LinkedHashMap();
		if (assigned(o)) {
			for (final String key : Json.getKeysFromJsonObject(o)) {
				map.put(key, o.get(key).getAsString());
			}
		}
		return map;
	}

	/**
	 * Gets the values of the fields of the specified json object instance as a list of strings.
	 * 
	 * @param o
	 * @return the values of the fields of the specified json object instance as a list of strings
	 */
	public static List<String> getValuesFromJsonObject(final JsonObject o) {
		return new LinkedList() {
			{
				if (assigned(o)) {
					for (final Map.Entry<String, JsonElement> e : o.entrySet()) {
						add(e.getValue());
					}
				}
			}
		};
	}

	/**
	 * Gets the keys of the fields of the specified json object instance as a list of strings.
	 * 
	 * @param o
	 * @return the keys of the fields of the specified json object instance as a list of strings
	 */
	public static List<String> getKeysFromJsonObject(final JsonObject o) {
		return new LinkedList() {
			{
				if (assigned(o)) {
					for (final Map.Entry<String, JsonElement> e : o.entrySet()) {
						add(e.getKey());
					}
				}
			}
		};
	}

	/**
	 * Combines the specified json object instances into a new json object instance containing all their content merged.
	 * 
	 * NOTE: this method requires the specified json objects are assigned (not null)
	 * 
	 * @param objects
	 * @return the resulting json array instance
	 */
	public static JsonObject combineJsonObjects(final JsonObject... objects) {
		if (objects == null || objects.length == 0) {
			return null;
		}
		final JsonObject result = new JsonObject();
		for (final JsonObject o : objects) {
			for (final Map.Entry<String, JsonElement> e : o.entrySet()) {
				result.add(e.getKey(), e.getValue());
			}
		}
		return result;
	}

	/**
	 * Makes a new json object instance.
	 * 
	 * @return the resulting json object instance
	 */
	public static JsonObject makeJsonObject() {
		return new JsonObject();
	}

	/**
	 * Wraps the specified boolean value into a new json object instance with the specified field name.
	 * 
	 * @param name
	 * @param b
	 * @return the resulting json object instance
	 */
	public static JsonObject wrapInJsonObject(final String name, final Boolean b) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, b);
		return result;
	}

	/**
	 * Wraps the specified number value into a new json object instance with the specified field name.
	 * 
	 * @param name
	 * @param n
	 * @return the resulting json object instance
	 */
	public static JsonObject wrapInJsonObject(final String name, final Number n) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, n);
		return result;
	}

	/**
	 * Wraps the specified character value into a new json object instance with the specified field name.
	 * 
	 * @param name
	 * @param c
	 * @return the resulting json object instance
	 */
	public static JsonObject wrapInJsonObject(final String name, final Character c) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, c);
		return result;
	}

	/**
	 * Wraps the specified string instance into a new json object instance with the specified field name.
	 * 
	 * @param name
	 * @param s
	 * @return the resulting json object instance
	 */
	public static JsonObject wrapInJsonObject(final String name, final String s) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, s);
		return result;
	}

	/**
	 * Wraps the specified json element instance into a new json object instance with the specified field name.
	 * 
	 * @param name
	 * @param e
	 * @return the resulting json object instance
	 */
	public static JsonObject wrapInJsonObject(final String name, final JsonElement e) {
		final JsonObject result = new JsonObject();
		result.add(name, e);
		return result;
	}

	/**
	 * Converts the specified hash map instance of string keys and values to a new json object instance.
	 * 
	 * @param map
	 * @return the resulting json object instance
	 */
	public static JsonObject convertStringsMapToJsonObject(final Map<String, String> map) {
		final JsonObject result = new JsonObject();
		for (final Map.Entry<String, String> e : map.entrySet()) {
			result.addProperty(e.getKey(), e.getValue());
		}
		return result;
	}

	/**
	 * Converts the specified hash map instance to a new json object instance.
	 * 
	 * @param map
	 * @return the resulting json object instance if the operation is successful, <tt>null</tt> otherwise
	 */
	public static JsonObject convertHashMapToJsonObject(final HashMap<?, ?> map) {
		try {
			final JsonObject result = new JsonObject();
			for (final Map.Entry<?, ?> e : map.entrySet()) {
				final Object key = e.getKey();
				final Object value = e.getValue();
				if (value instanceof List) {
					result.add(key.toString(), convertListOfValuesToJsonArray((List<?>) value));
				} else {
					result.addProperty(key.toString(), value.toString());
				}
			}
			return result;
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Reads the specified byte array as a json object.
	 * 
	 * @param buffer
	 * @return the resulting json object instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonObject readByteArrayAsJsonObject(final byte[] buffer) {
		return readByteArrayAsJsonObject(buffer, DEFAULT_CHARSET);
	}

	/**
	 * Reads the specified byte array as a json object in the specified character set.
	 * 
	 * @param buffer
	 * @param charset
	 * @return the resulting json object instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonObject readByteArrayAsJsonObject(final byte[] buffer, final String charset) {
		try {
			return readStringAsJsonObject(new String(buffer, charset));
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	/**
	 * Reads the specified string as a json object.
	 * 
	 * @param text
	 * @return the resulting json object instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonObject readStringAsJsonObject(final String text) {
		final JsonElement e = readStringAsJsonElement(text);
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	/**
	 * Reads the specified file as a json object.
	 * 
	 * @param file
	 * @return the resulting json object instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonObject readFileAsJsonObject(final File file) {
		return readStringAsJsonObject(TextFiles.read(file));
	}

	/**
	 * Reads the file with the specified file name as a json object.
	 * 
	 * @param filename
	 * @return the resulting json object instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonObject readFileAsJsonObject(final String filename) {
		return readStringAsJsonObject(TextFiles.read(filename));
	}

	// ELEMENT

	/**
	 * Determines if the specified json element is an assigned (not null) json element.
	 * 
	 * @param element
	 * @return <tt>true</tt> if the specified json element is an assigned (not null) json element, <tt>false</tt> otherwise
	 */
	public static boolean isAssignedJsonElement(final JsonElement element) {
		return element != null && !JsonNull.INSTANCE.equals(element);
	}

	/**
	 * Determines if the specified json object instances contains an assigned (non null) json element instance in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object instances contains an assigned (non null) json element instance in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasAssignedJsonElement(final JsonObject source, final String name) {
		return isAssignedJsonElement(obtainJsonElement(source, name));
	}

	/**
	 * Ensures to get a json element instance from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json element instance,
	 * it will default to a json null value, otherwise it will return the specified json element instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting json element instance
	 */
	public static JsonElement ensureJsonElement(final JsonElement e) {
		return isAssignedJsonElement(e) ? e : Json.NULL;
	}

	/**
	 * Ensures to get a json element instance from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json element instance,
	 * it will default to the specified default value, otherwise it will return the specified json element instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting json element instance
	 */
	public static JsonElement ensureJsonElement(final JsonElement e, final JsonElement defaultValue) {
		return isAssignedJsonElement(e) ? e : defaultValue;
	}

	/**
	 * Obtains the json element instance from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting json element instance if exist, json null otherwise
	 */
	public static JsonElement obtainJsonElement(final JsonObject source, final String name) {
		if (source != null && source.has(name)) {
			return source.get(name);
		} else {
			return JsonNull.INSTANCE;
		}
	}

	/**
	 * Clones the specified json element instance.
	 * 
	 * NOTE: this method requires the specified json element is assigned (not null)
	 * 
	 * @param element
	 * @return the cloned json element instance
	 */
	public static JsonElement cloneJsonElement(final JsonElement element) {
		return readStringAsJsonElement(JsonElementToString(element));
	}

	/**
	 * Reads the specified byte array as a json element.
	 * 
	 * @param buffer
	 * @return the resulting json element instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonElement readByteArrayAsJsonElement(final byte[] buffer) {
		return readByteArrayAsJsonElement(buffer, DEFAULT_CHARSET);
	}

	/**
	 * Reads the specified byte array as a json element in the specified character set.
	 * 
	 * @param buffer
	 * @param charset
	 * @return the resulting json element instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonElement readByteArrayAsJsonElement(final byte[] buffer, final String charset) {
		if (buffer != null) {
			try {
				return readStringAsJsonElement(new String(buffer, charset));
			} catch (final Exception e) {
				GEH.setLastException(e);
			}
		}
		return null;
	}

	/**
	 * Reads the specified string as a json element.
	 * 
	 * @param text
	 * @return the resulting json element instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonElement readStringAsJsonElement(final String text) {
		if (assigned(text)) {
			try {
				return new JsonParser().parse(text);
			} catch (final Exception e) {
				GEH.setLastException(e);
			}
		}
		return null;
	}

	/**
	 * Reads the specified file as a json element.
	 * 
	 * @param file
	 * @return the resulting json element instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonElement readFileAsJsonElement(final File file) {
		return readStringAsJsonElement(TextFiles.read(file));
	}

	/**
	 * Reads the file with the specified file name as a json element.
	 * 
	 * @param filename
	 * @return the resulting json element instance if the operation was successful, <tt>null</tt> otherwise
	 */
	public static JsonElement readFileAsJsonElement(final String filename) {
		return readStringAsJsonElement(TextFiles.read(filename));
	}

	/**
	 * Writes the specified json element instance to the specified file.
	 * 
	 * NOTE: the resulting json content will be prettily formatted for convenience
	 * 
	 * @param file
	 * @param element
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public static boolean writeJsonElementToFile(final File file, final JsonElement element) {
		return TextFiles.write(file, JsonElementToPrettyString(element));
	}

	/**
	 * Writes the specified json element instance to the file with the specified filename.
	 * 
	 * NOTE: the resulting json content will be prettily formatted for convenience
	 * 
	 * @param filename
	 * @param element
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public static boolean writeJsonElementToFile(final String filename, final JsonElement element) {
		return TextFiles.write(filename, JsonElementToPrettyString(element));
	}

	// STRING

	/**
	 * Determines if the specified json object cointains an string in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object cointains an string in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasString(final JsonObject source, final String name) {
		return isAssignedJsonPrimitiveString(obtainJsonElement(source, name));
	}

	/**
	 * Determines if the specified json object cointains a non empty string in the specified field.
	 * 
	 * @param source
	 * @param name
	 * @return <tt>true</tt> if the specified json object cointains a non empty string in the specified field, <tt>false</tt> otherwise
	 */
	public static boolean hasNotEmptyString(final JsonObject source, final String name) {
		return Strings.hasText(obtainString(source, name));
	}

	/**
	 * Ensures to get an string instance from the specified json element.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive string instance,
	 * it will default to an empty string instance, otherwise it will return the string value of the specified json element instance.
	 * If you need a different default value, use the corresponding overload of this method.
	 * 
	 * @param e
	 * @return the resulting string instance
	 */
	public static String ensureString(final JsonElement e) {
		return isAssignedJsonPrimitiveString(e) ? e.getAsString() : STRINGS.EMPTY;
	}

	/**
	 * Ensures to get an string instance from the specified json element using the specified default value.
	 * 
	 * NOTE: if the specified json element is not an assigned (not null) json primitive string instance,
	 * it will default to the specified default value, otherwise it will return the string value of the specified json element instance.
	 * 
	 * @param e
	 * @param defaultValue
	 * @return the resulting string instance
	 */
	public static String ensureString(final JsonElement e, final String defaultValue) {
		return isAssignedJsonPrimitiveString(e) ? e.getAsString() : defaultValue;
	}

	/**
	 * Obtains the string instance from the specified field of the specified json object instance.
	 * 
	 * @param source
	 * @param name
	 * @return the resulting string instance if exist, <tt>0</tt> otherwise
	 */
	public static String obtainString(final JsonObject source, final String name) {
		return obtainString(source, name, null);
	}

	/**
	 * Obtains the string instance from the specified field of the specified json object instance using the specified default value.
	 * 
	 * @param source
	 * @param name
	 * @param defaultValue
	 * @return the resulting string instance if exist, the default value otherwise
	 */
	public static String obtainString(final JsonObject source, final String name, final String defaultValue) {
		return ensureString(obtainJsonElement(source, name), defaultValue);
	}

	/**
	 * Gets the string representation of the specified json element.
	 * 
	 * @param e
	 * @return the string representation of the specified json element
	 */
	public static String JsonElementToString(final JsonElement e) {
		return new Gson().toJson(e);
	}

	/**
	 * Gets the prettily formatted string representation of the specified json element.
	 * 
	 * @param e
	 * @return the string representation of the specified json element
	 */
	public static String JsonElementToPrettyString(final JsonElement e) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(e);
	}

	// DEBUG

	/**
	 * Convenience method that prints, only in debug mode, the specified json element converted to string to the system console.
	 * 
	 * @param e
	 * @return the specified json element
	 */
	public static JsonElement debug(final JsonElement e) {
		debug(Json.JsonElementToString(e));
		return e;
	}

}
