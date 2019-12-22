/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.gson.Json;
import ace.gson.interfaces.NamedJsonArrayCallback;
import ace.interfaces.Treater;
import com.google.gson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Useful json array files directory class.
 */
public class JsonArraysDirectory extends JsonFilesDirectory {

	/**
	 * Constructor accepting a directory path.
	 * 
	 * @param path 
	 */
	public JsonArraysDirectory(final String path) {
		super(path);
	}

	/**
	 * Constructor accepting a directory path and adapters for reading and writing.
	 * 
	 * @param path 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public JsonArraysDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, readingAdapter, writingAdapter);
	}

	/**
	 * Gets the json array content of the file with the specified name.
	 * 
	 * @param name
	 * @return the json array content of the file with the specified name
	 */
	@Override public JsonArray get(final String name) {
		final JsonElement e = super.get(name);
		return Json.isAssignedJsonArray(e) ? e.getAsJsonArray() : null;
	}

	/**
	 * Sets the specified json array content of the file with the specified name.
	 * 
	 * @param name
	 * @param item
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	@Override public boolean put(final String name, final JsonElement item) {
		return Json.isAssignedJsonArray(item) && super.put(name, item);
	}

	/**
	 * Gets as a map the contents (file name with json array content for each file) of the json array directory.
	 * 
	 * @return the resulting map
	 */
	public Map<String, JsonArray> getArraysAsMap() {
		return new HashMap<String, JsonArray>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	/**
	 * Iterates the specified the list of file names of the json array directory loading its json content and calling the specified callback with them.
	 * 
	 * @param names
	 * @param callback 
	 */
	public void iterateArrays(final List<String> names, final NamedJsonArrayCallback callback) {
		for (final String n : names) {
			callback.callback(n, get(n));
		}
	}

	/**
	 * Iterates the all of file names of the json array directory loading its json content and calling the specified callback with them.
	 * 
	 * @param callback 
	 */
	public void iterateArrays(final NamedJsonArrayCallback callback) {
		iterateArrays(listNames(), callback);
	}

}
