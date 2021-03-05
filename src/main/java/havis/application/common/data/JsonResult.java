package havis.application.common.data;

import com.google.gwt.core.client.JavaScriptObject;

public class JsonResult<T> extends Json {

    protected JsonResult() {
    }

    public static <T extends JavaScriptObject> JsonResult<T> createInstance(
	    String status, T result) {
	JsonResult<T> instance = createInstance();
	instance.setStatus(status);
	instance.setResult(result);
	return instance;
    }

    public final native String getStatus() /*-{
		return this.status;
    }-*/;

    public final native void setStatus(String status) /*-{
		this.status = status;
    }-*/;

    public final native T getResult() /*-{
		return this.result;
    }-*/;

    public final native void setResult(T result) /*-{
		this.result = result;
    }-*/;
    
    public final native String getText() /*-{
		return this.result;
    }-*/;
}