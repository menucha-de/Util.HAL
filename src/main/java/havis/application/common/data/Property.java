package havis.application.common.data;

public class Property extends Json {

	protected Property() {
	}

	public static Property createInstance(String name, String value) {
		Property property = createInstance();
		property.setName(name);
		property.setValue(value);
		return property;
	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final native String getValue() /*-{
		return this.value;
	}-*/;

	public final native void setValue(String value) /*-{
		this.value = value;
	}-*/;

}
