/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.directory;

import ace.files.FilesDirectory;
import ace.files.TextFiles;
import ace.gson.Json;
import ace.gson.interfaces.NamedJsonElementCallback;
import ace.interfaces.Treater;
import com.google.gson.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Useful json element files directory class.
 */
public class JsonFilesDirectory extends FilesDirectory {

	private final Treater<byte[]> _readingAdapter;
	private final Treater<byte[]> _writingAdapter;

	/**
	 * Constructor accepting a directory path.
	 * 
	 * @param path 
	 */
	public JsonFilesDirectory(final String path) {
		this(path, null, null);
	}

	/**
	 * Constructor accepting a directory path and adapters for reading and writing.
	 * 
	 * @param path 
	 * @param readingAdapter 
	 * @param writingAdapter 
	 */
	public JsonFilesDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, Json.FILE_EXTENSION);
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

	/**
	 * Gets the json element content of the file with the specified name.
	 * 
	 * @param name
	 * @return the json element content of the file with the specified name
	 */
	public JsonElement get(final String name) {
		final File w = composeFile(name);
		if (w.exists()) {
			String data = TextFiles.read(w);
			if (assigned(_readingAdapter)) {
				data = new String(_readingAdapter.treat(data.getBytes()));
			}
			return Json.readStringAsJsonElement(data);
		} else {
			return Json.NULL;
		}
	}

	/**
	 * Sets the specified json element content of the file with the specified name.
	 * 
	 * @param name
	 * @param item
	 * @return <tt>true</tt> if the operation was successful, <tt>false</tt> otherwise
	 */
	public boolean put(final String name, final JsonElement item) {
		String data = Json.JsonElementToPrettyString(item);
		if (assigned(_writingAdapter)) {
			data = new String(_writingAdapter.treat(data.getBytes()));
		}
		return TextFiles.write(composeFile(name), data);
	}

	/**
	 * Gets as a map the contents (file name with json element content for each file) of the json element directory.
	 * 
	 * @return the resulting map
	 */
	public Map<String, JsonElement> getElementsAsMap() {
		return new HashMap<String, JsonElement>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	/**
	 * Iterates the all of file names of the json element directory loading its json content and calling the specified callback with them.
	 * 
	 * @param callback 
	 */
	public void iterateElements(final NamedJsonElementCallback callback) {
		for (final String n : listNames()) {
			callback.callback(n, get(n));
		}
	}

}
