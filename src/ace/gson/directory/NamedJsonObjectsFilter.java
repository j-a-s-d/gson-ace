/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.Ace;
import ace.containers.Maps;
import ace.gson.Json;
import ace.gson.interfaces.NamedJsonObjectCallback;
import ace.text.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Useful named json objects filter class.
 */
public class NamedJsonObjectsFilter extends Ace implements NamedJsonObjectCallback {

	private NamedJsonObjectCallback _innerCallback;
	private final HashMap<String, JsonObject> _items = Maps.make();
	private String _field;
	private String _valueFilter;

	/**
	 * Gets the filter of the objects to be used in the filtering.
	 * 
	 * @return the field of the objects to be used in the filtering
	 */
	public String getField() {
		return _field;
	}

	/**
	 * Sets the filter of the objects to be used in the filtering.
	 * 
	 * @param filter
	 * @return itself
	 */
	public NamedJsonObjectsFilter setField(final String filter) {
		_field = filter;
		return this;
	}

	/**
	 * Gets the filter value.
	 * 
	 * @return the filter value
	 */
	public String getValueFilter() {
		return _valueFilter;
	}

	/**
	 * Sets the filter value.
	 * 
	 * @param filter
	 * @return itself
	 */
	public NamedJsonObjectsFilter setValueFilter(final String filter) {
		_valueFilter = filter;
		if (Strings.hasText(filter)) {
			final boolean unknownEnd = "*".equals(String.valueOf(filter.charAt(filter.length() - 1)));
			final boolean unknownStart = "*".equals(String.valueOf(filter.charAt(0)));
			if (unknownStart && unknownEnd) {
				final String knownPart = Strings.dropBoth(filter, 1);
				_innerCallback = new NamedJsonObjectCallback() {
					/*@Override*/ public void callback(final String name, final JsonObject object) {
						final JsonElement e = Json.getJsonObjectField(object, _field);
						if (Json.isAssignedJsonPrimitiveString(e) && e.getAsString().contains(knownPart)) {
							_items.put(name, object);
						}
					}
				};
			} else if (unknownStart) {
				final String knownPart = Strings.dropLeft(filter, 1);
				_innerCallback = new NamedJsonObjectCallback() {
					/*@Override*/ public void callback(final String name, final JsonObject object) {
						final JsonElement e = Json.getJsonObjectField(object, _field);
						if (Json.isAssignedJsonPrimitiveString(e) && e.getAsString().endsWith(knownPart)) {
							_items.put(name, object);
						}
					}
				};
			} else if (unknownEnd) {
				final String knownPart = Strings.dropRight(filter, 1);
				_innerCallback = new NamedJsonObjectCallback() {
					/*@Override*/ public void callback(final String name, final JsonObject object) {
						final JsonElement e = Json.getJsonObjectField(object, _field);
						if (Json.isAssignedJsonPrimitiveString(e) && e.getAsString().startsWith(knownPart)) {
							_items.put(name, object);
						}
					}
				};
			} else {
				final String knownPart = filter;
				_innerCallback = new NamedJsonObjectCallback() {
					/*@Override*/ public void callback(final String name, final JsonObject object) {
						final JsonElement e = Json.getJsonObjectField(object, _field);
						if (Json.isAssignedJsonPrimitiveString(e) && e.getAsString().equals(knownPart)) {
							_items.put(name, object);
						}
					}
				};
			}
		}
		return this;
	}

	/**
	 * The callback method that will be called and in which will be applied the configured filter.
	 * 
	 * @param name 
	 * @param object 
	 */
	/*@Override*/ public void callback(final String name, final JsonObject object) {
		_innerCallback.callback(name, object);
	}

	/**
	 * Gets the filtered results map.
	 * 
	 * @return the filtered results map
	 */
	public Map<String, JsonObject> get() {
		return _items;
	}

}
