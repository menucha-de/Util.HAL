package havis.application.common.data;

import com.google.gwt.core.client.JavaScriptObject;

public class Json extends JavaScriptObject {

	protected Json() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T createInstance() {
		return (T) createObject();
	}

	public final static native <T> T parse(String string) /*-{
		return JSON.parse(string);
	}-*/;

	public final native String stringify()/*-{
		return JSON.stringify(this);
	}-*/;
}