package havis.application.common.data;

public class Config extends Json {

	protected Config() {
	}

	public static Config createInstance(String urn, String username, String port) {
		Config config = createInstance();
		config.setUrn(urn);
		config.setUsername(username);
		config.setWebsocketPort(port);
		return config;
	}

	public final native String getUrn() /*-{
		return this.urn;
	}-*/;

	public final native void setUrn(String urn) /*-{
		this.urn = urn;
	}-*/;

	public final native String getUsername() /*-{
		return this.user;
	}-*/;

	public final native void setUsername(String user) /*-{
		this.user = user;
	}-*/;
	
	public final native void setPassword(String password) /*-{
		this.password = password;
	}-*/;
	
	public final native JsonArray<Property> getProperties() /*-{
		return this.properties;
	}-*/;
	
	public final native void setProperties(JsonArray<Property> properties) /*-{
		this.properties = properties;
	}-*/;

	public final native String getWebsocketPort() /*-{
		return this.websocketServerPort;
	}-*/;
	
	public final native void setWebsocketPort(String port) /*-{
		this.websocketServerPort = port;
	}-*/;

}