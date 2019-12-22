/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.interfaces;

import com.google.gson.JsonElement;

/**
 * Named json element callback interface.
 */
public interface NamedJsonElementCallback {

	void callback(final String name, final JsonElement element);

}
