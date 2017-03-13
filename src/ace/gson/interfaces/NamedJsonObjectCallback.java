/* Gson Ace by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com) */

package ace.gson.interfaces;

import com.google.gson.JsonObject;

public interface NamedJsonObjectCallback {

	void callback(final String name, final JsonObject element);

}
