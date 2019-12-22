/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.interfaces;

import com.google.gson.JsonElement;

/**
 * Json persistable interface.
 */
public interface JsonPersistable {
	
	boolean loadFromJson(final JsonElement element);
	
	JsonElement saveToJson();

}
