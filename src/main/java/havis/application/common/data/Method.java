package havis.application.common.data;

public class Method extends Json {

	protected Method() {
	}

	public static Method createInstance(String name) {
		Method method = createInstance();
		method.setName(name);
		return method;
	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;
}