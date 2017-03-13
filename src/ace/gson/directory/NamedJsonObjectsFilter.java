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

public class NamedJsonObjectsFilter extends Ace implements NamedJsonObjectCallback {

	private NamedJsonObjectCallback _innerCallback;
	private final HashMap<String, JsonObject> _items = Maps.make();
	private String _field;
	private String _valueFilter;

	public String getField() {
		return _field;
	}

	public NamedJsonObjectsFilter setField(final String filter) {
		_field = filter;
		return this;
	}

	public String getValueFilter() {
		return _valueFilter;
	}

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

	/*@Override*/ public void callback(final String n, final JsonObject o) {
		_innerCallback.callback(n, o);
	}

	public Map<String, JsonObject> get() {
		return _items;
	}

}
