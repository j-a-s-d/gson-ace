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

public class JsonFilesDirectory extends FilesDirectory {

	private final Treater<byte[]> _readingAdapter;
	private final Treater<byte[]> _writingAdapter;

	public JsonFilesDirectory(final String path) {
		this(path, null, null);
	}

	public JsonFilesDirectory(final String path, final Treater<byte[]> readingAdapter, final Treater<byte[]> writingAdapter) {
		super(path, Json.FILE_EXTENSION);
		_readingAdapter = readingAdapter;
		_writingAdapter = writingAdapter;
	}

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

	public boolean put(final String name, final JsonElement item) {
		String data = Json.JsonElementToPrettyString(item);
		if (assigned(_writingAdapter)) {
			data = new String(_writingAdapter.treat(data.getBytes()));
		}
		return TextFiles.write(composeFile(name), data);
	}

	public Map<String, JsonElement> getElementsAsMap() {
		return new HashMap<String, JsonElement>() {{
			for (final String n : listNames()) {
				put(n, get(n));
			}
		}};
	}

	public void iterateElements(final NamedJsonElementCallback callback) {
		for (final String n : listNames()) {
			callback.callback(n, get(n));
		}
	}

}
