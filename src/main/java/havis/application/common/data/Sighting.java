package havis.application.common.data;

public class Sighting extends Json {

	protected Sighting() {
	}

	public static Sighting createInstance(String host, int antenna, int strength) {
		Sighting sighting = createInstance();
		sighting.setHost(host);
		sighting.setAntenna(antenna);
		sighting.setStrength(strength);
		return sighting;
	}

	public final native String getHost() /*-{
		return this.host;
	}-*/;

	public final native void setHost(String host) /*-{
		this.host = host;
	}-*/;

	public final native int getAntenna() /*-{
		return this.antenna;
	}-*/;

	public final native void setAntenna(int antenna) /*-{
		this.antenna = antenna;
	}-*/;

	public final native int getStrength() /*-{
		return this.strength;
	}-*/;

	public final native void setStrength(int strength) /*-{
		this.strength = strength;
	}-*/;
}
