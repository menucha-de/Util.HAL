package havis.application.common.data;

public class Result extends Json {

	protected Result() {
	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final native String getData() /*-{
		return this.data;
	}-*/;

	public final native void setData(String data) /*-{
		this.data = data;
	}-*/;

	public final native String getState() /*-{
		return this.state;
	}-*/;

	public final native void setState(String state) /*-{
		this.state = state;
	}-*/;
}