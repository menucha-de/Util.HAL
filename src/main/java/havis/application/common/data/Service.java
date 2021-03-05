package havis.application.common.data;

public class Service extends Json {

	protected Service() {
	}

	public static Service createInstance(String name, boolean active) {
		Service config = createInstance();
		config.setName(name);
		config.setActive(active);
		return config;
	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final native boolean isActive() /*-{
		return this.active;
	}-*/;

	public final native void setActive(boolean active) /*-{
		this.active = active;
	}-*/;
}