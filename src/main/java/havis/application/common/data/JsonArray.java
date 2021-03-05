package havis.application.common.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class JsonArray<T extends JavaScriptObject> extends JsArray<T> {

	protected JsonArray() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends JavaScriptObject> JsonArray<T> createInstance() {
		return (JsonArray<T>) createArray();
	}

	public static <T extends JsArray<E>, E extends JavaScriptObject> T createInstance(
			E[] e) {
		@SuppressWarnings("unchecked")
		T instance = (T) createArray();
		for (int i = e.length - 1; i > -1; i--) {
			instance.unshift(e[i]);
		}
		return instance;
	}

	public final static native <T extends JavaScriptObject> JsonArray<T> parse(
			String string) /*-{
		return JSON.parse(string);
	}-*/;

	public final native String stringify()/*-{
		return JSON.stringify(this);
	}-*/;
}