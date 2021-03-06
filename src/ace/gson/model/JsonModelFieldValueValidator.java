/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.model;

import ace.Ace;
import ace.arrays.GenericArrays;
import ace.gson.Json;
import ace.gson.builders.JsonObjectBuilder;
import ace.text.Strings;
import com.google.gson.*;

/**
 * Abstract json model field value validator class.
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class JsonModelFieldValueValidator extends Ace {

	private static abstract class LocalJsonModelFieldValueValidator extends JsonModelFieldValueValidator {

		private final String _name;
		private final JsonArray _parameters;

		private LocalJsonModelFieldValueValidator(final String name, final JsonArray parameters) {
			_name = name;
			_parameters = parameters;
		}

		private LocalJsonModelFieldValueValidator(final String name) {
			this(name, new JsonArray());
		}

		@Override public JsonObject toJsonObject() {
			return new JsonObjectBuilder()
				.add("name", _name)
				.add("parameters", _parameters)
			.getAsJsonObject();
		}

	}

	/**
	 * Makes a lenient json element validator approving any assigned (not null) json element.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeLenientValidator() {
		return new LocalJsonModelFieldValueValidator("lenient") {
			@Override public boolean validateField(final JsonElement je) {
				return assigned(je);
			}
		};
	}

	/**
	 * Makes a json object validator approving any assigned (not null) json object.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeObjectValidator() {
		return new LocalJsonModelFieldValueValidator("object") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonObject(je);
			}
		};
	}

	/**
	 * Makes a json object validator approving any assigned (not null) and non empty json object.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeNonEmptyObjectValidator() {
		return new LocalJsonModelFieldValueValidator("nonEmptyObject") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isNotEmptyJsonObject(je);
			}
		};
	}

	/**
	 * Makes a json array validator approving any assigned (not null) json array.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeArrayValidator() {
		return new LocalJsonModelFieldValueValidator("array") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonArray(je);
			}
		};
	}

	/**
	 * Makes a json array validator approving any assigned (not null) and non empty json array.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeNonEmptyArrayValidator() {
		return new LocalJsonModelFieldValueValidator("nonEmptyArray") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isNotEmptyJsonArray(je);
			}
		};
	}

	/**
	 * Makes a json primitive validator approving any assigned (not null) json primitive.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makePrimitiveValidator() {
		return new LocalJsonModelFieldValueValidator("primitive") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonPrimitive(je);
			}
		};
	}

	/**
	 * Makes a boolean validator approving any assigned (not null) boolean.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeBooleanValidator() {
		return new LocalJsonModelFieldValueValidator("boolean") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonPrimitiveBoolean(je);
			}
		};
	}

	/**
	 * Makes a number validator approving any assigned (not null) number.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeNumberValidator() {
		return new LocalJsonModelFieldValueValidator("number") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonPrimitiveNumber(je);
			}
		};
	}

	/**
	 * Makes a string validator approving any assigned (not null) string.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeStringValidator() {
		return new LocalJsonModelFieldValueValidator("string") {
			@Override public boolean validateField(final JsonElement je) {
				return Json.isAssignedJsonPrimitiveString(je);
			}
		};
	}

	/**
	 * Makes an string validator approving any assigned (not null) and non empty string.
	 * 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeNonEmptyStringValidator() {
		return new LocalJsonModelFieldValueValidator("nonEmptyString") {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveString(je)) {
					return false;
				}
				final String value = je.getAsString();
				if (!assigned(value)) {
					return false;
				}
				return Strings.hasText(value);
			}
		};
	}

	/**
	 * Makes an string enumeration validator approving any assigned (not null) string that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeStringEnumValidator(final String... values) {
		return new LocalJsonModelFieldValueValidator("stringEnum", Json.convertStringArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveString(je)) {
					return false;
				}
				final String value = je.getAsString();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a byte enumeration validator approving any assigned (not null) byte that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeByteEnumValidator(final Byte... values) {
		return new LocalJsonModelFieldValueValidator("byteEnum", Json.convertByteArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Byte value = je.getAsByte();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a short enumeration validator approving any assigned (not null) short that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeShortEnumValidator(final Short... values) {
		return new LocalJsonModelFieldValueValidator("shortEnum", Json.convertShortArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Short value = je.getAsShort();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a integer enumeration validator approving any assigned (not null) integer that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeIntegerEnumValidator(final Integer... values) {
		return new LocalJsonModelFieldValueValidator("integerEnum", Json.convertIntegerArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Integer value = je.getAsInt();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a long enumeration validator approving any assigned (not null) long that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeLongEnumValidator(final Long... values) {
		return new LocalJsonModelFieldValueValidator("longEnum", Json.convertLongArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Long value = je.getAsLong();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a double enumeration validator approving any assigned (not null) double that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeDoubleEnumValidator(final Double... values) {
		return new LocalJsonModelFieldValueValidator("doubleEnum", Json.convertDoubleArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Double value = je.getAsDouble();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a float enumeration validator approving any assigned (not null) float that is present among the specified values.
	 * 
	 * @param values 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator makeFloatEnumValidator(final Float... values) {
		return new LocalJsonModelFieldValueValidator("floatEnum", Json.convertFloatArraytoJsonArray(values)) {
			@Override public boolean validateField(final JsonElement je) {
				if (!Json.isAssignedJsonPrimitiveNumber(je)) {
					return false;
				}
				final Float value = je.getAsFloat();
				if (!assigned(value)) {
					return false;
				}
				return GenericArrays.contains(values, value);
			}
		};
	}

	/**
	 * Makes a json model field value validator instance following the specification got from the specified json object.
	 * 
	 * @param object 
	 * @return the resulting validator
	 */
	public static JsonModelFieldValueValidator fromJsonObject(final JsonObject object) {
		final String name = Json.obtainString(object, "name");
		final JsonArray parameters = Json.obtainJsonArray(object, "parameters");
		if (name.equals("primitive")) {
			return makePrimitiveValidator();
		} else if (name.equals("boolean")) {
			return makeBooleanValidator();
		} else if (name.equals("number")) {
			return makeNumberValidator();
		} else if (name.equals("string")) {
			return makeStringValidator();
		} else if (name.equals("nonEmptyString")) {
			return makeNonEmptyStringValidator();
		} else if (name.equals("stringEnum")) {
			return makeStringEnumValidator(Json.convertJsonArraytoStringArray(parameters));
		} else if (name.equals("byteEnum")) {
			return makeByteEnumValidator(Json.convertJsonArraytoByteArray(parameters));
		} else if (name.equals("shortEnum")) {
			return makeShortEnumValidator(Json.convertJsonArraytoShortArray(parameters));
		} else if (name.equals("integerEnum")) {
			return makeIntegerEnumValidator(Json.convertJsonArraytoIntegerArray(parameters));
		} else if (name.equals("longEnum")) {
			return makeLongEnumValidator(Json.convertJsonArraytoLongArray(parameters));
		} else if (name.equals("doubleEnum")) {
			return makeDoubleEnumValidator(Json.convertJsonArraytoDoubleArray(parameters));
		} else if (name.equals("floatEnum")) {
			return makeFloatEnumValidator(Json.convertJsonArraytoFloatArray(parameters));
		} else if (name.equals("nonEmptyArray")) {
			return makeNonEmptyArrayValidator();
		} else if (name.equals("array")) {
			return makeArrayValidator();
		} else if (name.equals("nonEmptyObject")) {
			return makeNonEmptyObjectValidator();
		} else if (name.equals("object")) {
			return makeObjectValidator();
		} else {
			return makeLenientValidator();
		}
	}

	public abstract JsonObject toJsonObject();

	public abstract boolean validateField(final JsonElement value);

}
