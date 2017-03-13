/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.gson.Json;
import ace.gson.interfaces.NamedJsonArrayCallback;
import ace.interfaces.Treater;
import com.google.gson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonArraysDirectory extends JsonFilesDirectory {

	public JsonArraysDirectory(final String path) {
		super(path);
	}

	public JsonArraysDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, readingAdapter, writingAdapter);
	}

	@Override public JsonArray get(final String name) {
		final JsonElement e = super.get(name);
		return Json.isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	@Override public boolean put(final String name, final JsonElement item) {
		return Json.isAssignedJsonArray(item) && super.put(name, item);
	}

	public Map<String, JsonArray> getArraysAsMap() {
		return new HashMap<String, JsonArray>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	public void iterateArrays(final List<String> names, final NamedJsonArrayCallback callback) {
		for (final String n : names) {
			callback.callback(n, get(n));
		}
	}

	public void iterateArrays(final NamedJsonArrayCallback callback) {
		iterateArrays(listNames(), callback);
	}

}
