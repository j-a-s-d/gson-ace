/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.gson.Json;
import ace.gson.interfaces.NamedJsonObjectCallback;
import ace.interfaces.Treater;
import com.google.gson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonObjectsDirectory extends JsonFilesDirectory {

	public JsonObjectsDirectory(final String path) {
		super(path);
	}

	public JsonObjectsDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, readingAdapter, writingAdapter);
	}

	@Override public JsonObject get(final String name) {
		final JsonElement e = super.get(name);
		return Json.isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	@Override public boolean put(final String name, final JsonElement item) {
		return Json.isAssignedJsonObject(item) && super.put(name, item);
	}

	public Map<String, JsonObject> getObjectsAsMap() {
		return new HashMap<String, JsonObject>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	public void iterateObjects(final List<String> names, final NamedJsonObjectCallback callback) {
		for (final String n : names) {
			callback.callback(n, get(n));
		}
	}

	public void iterateObjects(final NamedJsonObjectCallback callback) {
		iterateObjects(listNames(), callback);
	}

	public Map<String, JsonObject> where(final List<String> names, final String field, final String valueFilter) {
		return new NamedJsonObjectsFilter() {{
			iterateObjects(names, setField(field).setValueFilter(valueFilter));
		}}.get();
	}

	public Map<String, JsonObject> where(final String field, final String valueFilter) {
		return where(listNames(), field, valueFilter);
	}

}
