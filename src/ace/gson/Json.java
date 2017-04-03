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
import java.util.ArrayList;
import java.util.HashMap;
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
	public static final SemanticVersion GSON_ACE_VERSION = SemanticVersion.fromString("1.0.2");

	public static final String LEVEL_SEPARATOR = "/";

	public static final String FILE_EXTENSION = ".json";

	public static final JsonNull NULL = JsonNull.INSTANCE;
	public static final JsonPrimitive TRUE = bool(true);
	public static final JsonPrimitive FALSE = bool(false);

	public static String DEFAULT_CHARSET = "utf-8";

	// recursive
	public static boolean hasJsonObjectField(final JsonObject o, final String key) {
		return assigned(getJsonObjectField(o, key));
	}

	public static JsonElement getJsonObjectField(final JsonObject o, final String key) {
		JsonElement result = null;
		if (assigned(o)) {
			if (o.has(key)) {
				result = o.get(key);
			} else {
				JsonObject item = o;
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

	public static boolean setJsonObjectFieldValue(final JsonObject o, final String key, final JsonElement value) {
		if (assigned(o)) {
			if (o.has(key)) {
				o.add(key, value);
				return true;
			} else {
				JsonObject lastItem = null;
				String lastKey = null;
				JsonObject item = o;
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

	// primitive
	public static JsonPrimitive bool(final boolean value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive num(final byte value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive num(final int value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive num(final long value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive num(final double value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive chr(final char value) {
		return new JsonPrimitive(value);
	}

	public static JsonPrimitive str(final String value) {
		return new JsonPrimitive(value);
	}

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

	public static boolean isAssignedJsonPrimitive(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonPrimitive();
	}

	public static boolean isAssignedJsonPrimitiveBoolean(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isBoolean();
	}

	public static boolean isAssignedJsonPrimitiveNumber(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isNumber();
	}

	public static boolean isAssignedJsonPrimitiveString(final JsonElement e) {
		return isAssignedJsonPrimitive(e) && e.getAsJsonPrimitive().isString();
	}

	public static Boolean ensureBoolean(final JsonElement e) {
		return ensureBoolean(e, true);
	}

	public static Boolean ensureBoolean(final JsonElement e, final Boolean defaultValue) {
		return isAssignedJsonPrimitiveBoolean(e) ? e.getAsBoolean() : defaultValue;
	}

	public static Number ensureNumber(final JsonElement e) {
		return ensureNumber(e, 0);
	}

	public static Number ensureNumber(final JsonElement e, final Number defaultValue) {
		return isAssignedJsonPrimitiveNumber(e) ? e.getAsNumber() : defaultValue;
	}

	public static Number obtainNumber(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0);
	}

	public static Number obtainNumber(final JsonObject source, final String name, final Number defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue);
	}

	public static Byte obtainByte(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).byteValue();
	}

	public static Byte obtainByte(final JsonObject source, final String name, final Byte defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).byteValue();
	}

	public static Integer obtainInteger(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).intValue();
	}

	public static Integer obtainInteger(final JsonObject source, final String name, final Integer defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).intValue();
	}

	public static Short obtainShort(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).shortValue();
	}

	public static Short obtainShort(final JsonObject source, final String name, final Short defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).shortValue();
	}

	public static Long obtainLong(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).longValue();
	}

	public static Long obtainLong(final JsonObject source, final String name, final Long defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).longValue();
	}

	public static Float obtainFloat(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).floatValue();
	}

	public static Float obtainFloat(final JsonObject source, final String name, final Float defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).floatValue();
	}

	public static Double obtainDouble(final JsonObject source, final String name) {
		return obtainNumber(source, name, 0).doubleValue();
	}

	public static Double obtainDouble(final JsonObject source, final String name, final Double defaultValue) {
		return ensureNumber(obtainJsonElement(source, name), defaultValue).doubleValue();
	}

	public static Boolean obtainBoolean(final JsonObject source, final String name) {
		return obtainBoolean(source, name, true);
	}

	public static Boolean obtainBoolean(final JsonObject source, final String name, final Boolean defaultValue) {
		return ensureBoolean(obtainJsonElement(source, name), defaultValue);
	}

	// array
	public static boolean containsElements(final JsonArray a) {
		return a.size() > 0;
	}

	public static boolean containsElements(final JsonArray a, final JsonArray elements) {
		boolean result = isAssignedJsonArray(elements) && elements.size() > 0;
		for (final JsonElement e : elements) {
			result &= a.contains(e);
		}
		return result;
	}

	public static boolean containsElements(final JsonArray a, final JsonElement[] elements) {
		boolean result = elements != null && elements.length > 0;
		for (final JsonElement e : elements) {
			result &= a.contains(e);
		}
		return result;
	}

	public static boolean containsElement(final JsonArray a, final JsonElement e) {
		return containsElements(a, new JsonElement[] { e });
	}

	public static boolean isAssignedJsonArray(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonArray();
	}

	public static boolean isNotEmptyJsonArray(final JsonElement e) {
		return isAssignedJsonArray(e) && containsElements(e.getAsJsonArray());
	}

	public static boolean hasJsonArray(final JsonObject source, final String name) {
		return isAssignedJsonArray(obtainJsonElement(source, name));
	}

	public static boolean hasNotEmptyJsonArray(final JsonObject source, final String name) {
		return isNotEmptyJsonArray(obtainJsonElement(source, name));
	}

	public static JsonArray ensureJsonArray(final JsonElement e) {
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : new JsonArray();
	}

	public static JsonArray ensureJsonArray(final JsonElement e, final JsonArray defaultValue) {
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : ensureJsonArray(defaultValue);
	}

	public static JsonArray obtainJsonArray(final JsonObject source, final String name) {
		return ensureJsonArray(obtainJsonElement(source, name));
	}

	public static JsonArray obtainJsonArray(final JsonObject source, final String name, final JsonArray defaultValue) {
		return ensureJsonArray(obtainJsonElement(source, name), defaultValue);
	}

	public static JsonArray cloneJsonArray(final JsonArray a) {
		return readStringAsJsonArray(JsonElementToString(a));
	}

	public static JsonArray combineJsonArrays(final JsonArray... arrays) {
		return new JsonArrayBuilder() {
			{
				for (final JsonArray a : arrays) {
					addElements(a);
				}
			}
		}.getAsJsonArray();
	}

	public static JsonArray makeJsonArray() {
		return new JsonArray();
	}

	public static JsonArray wrapInJsonArray(final JsonElement... elements) {
		final JsonArray result = new JsonArray();
		for (final JsonElement e : elements) {
			result.add(e);
		}
		return result;
	}

	public static JsonArray removeNullElements(final JsonArray array) {
		final JsonArray result = new JsonArray();
		for (final JsonElement e : array) {
			if (!e.isJsonNull()) {
				result.add(e);
			}
		}
		return result;
	}

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

	public static JsonArray convertBooleanArraytoJsonArray(final Boolean... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertNumberArraytoJsonArray(final Number... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertByteArraytoJsonArray(final Byte... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertShortArraytoJsonArray(final Short... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertIntegerArraytoJsonArray(final Integer... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertLongArraytoJsonArray(final Long... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertDoubleArraytoJsonArray(final Double... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertFloatArraytoJsonArray(final Float... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

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

	public static JsonArray convertStringArraytoJsonArray(final String... values) {
		return new JsonArrayBuilder().addElements(values).getAsJsonArray();
	}

	public static List<JsonObject> convertJsonArraytoJsonObjectList(final JsonArray array) {
		return new ArrayList<JsonObject>() {
			{
				for (final JsonElement item : array) {
					add(ensureJsonObject(item));
				}
			}
		};
	}

	public static JsonArray convertListOfValuesToJsonArray(final List<?> list) {
		final JsonArray result = new JsonArray();
		for (final Object i : list) {
			result.add(convertValueToJsonPrimitive(i));
		}
		return result;
	}

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

	public static JsonArray readByteArrayAsJsonArray(final byte[] buffer) {
		return readByteArrayAsJsonArray(buffer, DEFAULT_CHARSET);
	}

	public static JsonArray readByteArrayAsJsonArray(final byte[] buffer, final String charset) {
		try {
			return readStringAsJsonArray(new String(buffer, charset));
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	public static JsonArray readStringAsJsonArray(final String text) {
		final JsonElement e = readStringAsJsonElement(text);
		return isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	public static JsonArray readFileAsJsonArray(final File file) {
		return readStringAsJsonArray(TextFiles.read(file));
	}

	public static JsonArray readFileAsJsonArray(final String filename) {
		return readStringAsJsonArray(TextFiles.read(filename));
	}

	// object
	public static boolean hasFields(final JsonObject o) {
		return !o.entrySet().isEmpty();
	}

	public static boolean hasFields(final JsonObject o, final String... fields) {
		boolean result = fields != null && fields.length > 0;
		for (final String s : fields) {
			result &= o.has(s);
		}
		return result;
	}

	public static boolean isAssignedJsonObject(final JsonElement e) {
		return isAssignedJsonElement(e) && e.isJsonObject();
	}

	public static boolean isNotEmptyJsonObject(final JsonElement e) {
		return isAssignedJsonObject(e) && hasFields(e.getAsJsonObject());
	}

	public static boolean hasJsonObject(final JsonObject source, final String name) {
		return isAssignedJsonObject(obtainJsonElement(source, name));
	}

	public static boolean hasNotEmptyJsonObject(final JsonObject source, final String name) {
		return isAssignedJsonObject(obtainJsonElement(source, name));
	}

	public static JsonObject ensureJsonObject(final JsonElement e) {
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : new JsonObject();
	}

	public static JsonObject ensureJsonObject(final JsonElement e, final JsonObject defaultValue) {
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : ensureJsonObject(defaultValue);
	}

	public static JsonObject obtainJsonObject(final JsonObject source, final String name) {
		return ensureJsonObject(obtainJsonElement(source, name));
	}

	public static JsonObject obtainJsonObject(final JsonObject source, final String name, final JsonObject defaultValue) {
		return ensureJsonObject(obtainJsonElement(source, name), defaultValue);
	}

	public static JsonObject cloneJsonObject(final JsonObject o) {
		return readStringAsJsonObject(JsonElementToString(o));
	}

	public static List<String> getKeysFromJsonObject(final JsonObject o) {
		return new ArrayList() {
			{
				for (final Map.Entry<String, JsonElement> e : o.entrySet()) {
					add(e.getKey());
				}
			}
		};
	}

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

	public static JsonObject makeJsonObject() {
		return new JsonObject();
	}

	public static JsonObject wrapInJsonObject(final String name, final Boolean b) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, b);
		return result;
	}

	public static JsonObject wrapInJsonObject(final String name, final Number n) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, n);
		return result;
	}

	public static JsonObject wrapInJsonObject(final String name, final Character c) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, c);
		return result;
	}

	public static JsonObject wrapInJsonObject(final String name, final String s) {
		final JsonObject result = new JsonObject();
		result.addProperty(name, s);
		return result;
	}

	public static JsonObject wrapInJsonObject(final String name, final JsonElement e) {
		final JsonObject result = new JsonObject();
		result.add(name, e);
		return result;
	}

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

	public static JsonObject readByteArrayAsJsonObject(final byte[] buffer) {
		return readByteArrayAsJsonObject(buffer, DEFAULT_CHARSET);
	}

	public static JsonObject readByteArrayAsJsonObject(final byte[] buffer, final String charset) {
		try {
			return readStringAsJsonObject(new String(buffer, charset));
		} catch (final Exception e) {
			GEH.setLastException(e);
			return null;
		}
	}

	public static JsonObject readStringAsJsonObject(final String text) {
		final JsonElement e = readStringAsJsonElement(text);
		return isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	public static JsonObject readFileAsJsonObject(final File file) {
		return readStringAsJsonObject(TextFiles.read(file));
	}

	public static JsonObject readFileAsJsonObject(final String filename) {
		return readStringAsJsonObject(TextFiles.read(filename));
	}

	// element
	public static boolean isAssignedJsonElement(final JsonElement element) {
		return element != null && !JsonNull.INSTANCE.equals(element);
	}

	public static boolean hasAssignedJsonElement(final JsonObject source, final String name) {
		return isAssignedJsonElement(obtainJsonElement(source, name));
	}

	public static JsonElement ensureJsonElement(final JsonElement e) {
		return isAssignedJsonElement(e) ? e : Json.NULL;
	}

	public static JsonElement ensureJsonElement(final JsonElement e, final JsonElement defaultValue) {
		return isAssignedJsonElement(e) ? e : defaultValue;
	}

	public static JsonElement obtainJsonElement(final JsonObject source, final String name) {
		if (source != null && source.has(name)) {
			return source.get(name);
		} else {
			return JsonNull.INSTANCE;
		}
	}

	public static JsonElement cloneJsonElement(final JsonElement o) {
		return readStringAsJsonElement(JsonElementToString(o));
	}

	public static JsonElement readByteArrayAsJsonElement(final byte[] buffer) {
		return readByteArrayAsJsonElement(buffer, DEFAULT_CHARSET);
	}

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

	public static JsonElement readFileAsJsonElement(final File file) {
		return readStringAsJsonElement(TextFiles.read(file));
	}

	public static JsonElement readFileAsJsonElement(final String filename) {
		return readStringAsJsonElement(TextFiles.read(filename));
	}

	public static boolean writeJsonElementToFile(final File file, final JsonElement element) {
		return TextFiles.write(file, JsonElementToPrettyString(element));
	}

	public static boolean writeJsonElementToFile(final String filename, final JsonElement element) {
		return TextFiles.write(filename, JsonElementToPrettyString(element));
	}

	// string
	public static boolean hasString(final JsonObject source, final String name) {
		return isAssignedJsonPrimitiveString(obtainJsonElement(source, name));
	}

	public static boolean hasNotEmptyString(final JsonObject source, final String name) {
		return Strings.hasText(obtainString(source, name));
	}

	public static String ensureString(final JsonElement e) {
		return isAssignedJsonPrimitiveString(e) ? e.getAsString() : STRINGS.EMPTY;
	}

	public static String ensureString(final JsonElement e, final String defaultValue) {
		return isAssignedJsonPrimitiveString(e) ? e.getAsString() : defaultValue;
	}

	public static String obtainString(final JsonObject source, final String name) {
		return obtainString(source, name, null);
	}

	public static String obtainString(final JsonObject source, final String name, final String defaultValue) {
		return ensureString(obtainJsonElement(source, name), defaultValue);
	}

	public static String JsonElementToString(final JsonElement e) {
		return new Gson().toJson(e);
	}

	public static String JsonElementToPrettyString(final JsonElement e) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(e);
	}

	// debug
	public static JsonElement debug(final JsonElement e) {
		debug(Json.JsonElementToString(e));
		return e;
	}

}
