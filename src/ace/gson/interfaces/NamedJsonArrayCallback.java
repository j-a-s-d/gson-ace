/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.interfaces;

import com.google.gson.JsonArray;

/**
 * Named json array callback interface.
 */
public interface NamedJsonArrayCallback {

	void callback(final String name, final JsonArray element);

}
