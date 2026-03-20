package robatortas.code.files.core.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


// Using Gson (not goon) to parse !
// {} = Object
// "key" = key
// Values can be strings, ints, floats, even arrays.
// : = separates from the key
// , = Separates pairs (a key and its value)
// I could use the normal library, but I like translating the libraries I use to something I can understand : )
// Cuz I hate docs.
public class ParsingUtils {

	public static Gson gson = new Gson();
	
	// Gets the file to read in basically.
	// The JsonReader class is heavily used to findNext and just querying tokens and objects in general.
	public JsonReader getJsonReader(InputStream in) {
		JsonReader result = null;
		try {
			result = new JsonReader(new InputStreamReader(in, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JsonObject createObject(String path) {
		JsonObject object = new JsonObject();
		try (FileReader reader = new FileReader(path)) {
		    object = JsonParser.parseReader(reader).getAsJsonObject();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return object;
	}
	
	// Generic that returns the correct data type according to the 'type' parameter
	public <T> T getValue(String key, Class<T> type, JsonObject object) {
		JsonElement element = object.get(key);
		if (element == null || element.isJsonNull()) throw new IllegalArgumentException("Key '" + key + "' not found!");
		if 		(type == String.class)	return type.cast(element.getAsString());
	    else if (type == Integer.class) return type.cast(element.getAsInt());
	    else if (type == Boolean.class) return type.cast(element.getAsBoolean());
	    else if (type == Double.class)	return type.cast(element.getAsDouble());
		throw new IllegalArgumentException("Unsoported json type !" + type);
	}
	
	// TODO: FINISH THIS SHIT !
	public <T> void setValue(String key, Class<T> value) {
		if 		(value == String.class)		;
		else if (value == Integer.class)	; 
	    else if (value == Boolean.class)	;
	    else if (value == Double.class)		;
	}
	
}