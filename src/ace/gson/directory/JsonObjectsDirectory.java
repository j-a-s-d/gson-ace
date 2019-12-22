/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.gson.Json;
import ace.gson.interfaces.NamedJsonObjectCallback;
import ace.interfaces.Treater;
import com.google.gson.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Useful json object files directory class.
 */
public class JsonObjectsDirectory extends JsonFilesDirectory {

	/**
	 * Constructor accepting a directory path.
	 * 
	 * @param path 
	 */
	public JsonObjectsDirectory(final String path) {
		super(path);
	}

	/**
	 * Constructor accepting a directory path and adapters for reading and writing.
	 * 
	 * @param path 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public JsonObjectsDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, readingAdapter, writingAdapter);
	}

	/**
	 * Gets the json object content of the file with the specified name.
	 * 
	 * @param name
	 * @return the json object content of the file with the specified name
	 */
	@Override public JsonObject get(final String name) {
		final JsonElement e = super.get(name);
		return Json.isAssignedJsonObject(e) ? e.getAsJsonObject() : null;
	}

	/**
	 * Sets the specified json object content of the file with the specified name.
	 * 
	 * @param name
	 * @param item
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	@Override public boolean put(final String name, final JsonElement item) {
		return Json.isAssignedJsonObject(item) && super.put(name, item);
	}

	/**
	 * Gets as a map the contents (file name with json object content for each file) of the json object directory.
	 * 
	 * @return the resulting map
	 */
	public Map<String, JsonObject> getObjectsAsMap() {
		return new HashMap<String, JsonObject>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	/**
	 * Iterates the specified the list of file names of the json object directory loading its json content and calling the specified callback with them.
	 * 
	 * @param names
	 * @param callback 
	 */
	public void iterateObjects(final List<String> names, final NamedJsonObjectCallback callback) {
		for (final String n : names) {
			callback.callback(n, get(n));
		}
	}

	/**
	 * Iterates the all of file names of the json object directory loading its json content and calling the specified callback with them.
	 * 
	 * @param callback 
	 */
	public void iterateObjects(final NamedJsonObjectCallback callback) {
		iterateObjects(listNames(), callback);
	}

	/**
	 * Select the files in the list of file names of the json object directory that pass the filtering of the specified object field with the specified filtering value.
	 * 
	 * @param names
	 * @param field
	 * @param valueFilter
	 * @return the filtered files map
	 */
	public Map<String, JsonObject> where(final List<String> names, final String field, final String valueFilter) {
		return new NamedJsonObjectsFilter() {{
			iterateObjects(names, setField(field).setValueFilter(valueFilter));
		}}.get();
	}

	/**
	 * Select the files in json object directory that pass the filtering of the specified object field with the specified filtering value.
	 * 
	 * @param field
	 * @param valueFilter
	 * @return the filtered files map
	 */
	public Map<String, JsonObject> where(final String field, final String valueFilter) {
		return where(listNames(), field, valueFilter);
	}

}
