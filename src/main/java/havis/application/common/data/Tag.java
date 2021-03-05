package havis.application.common.data;

import java.util.Map;

public class Tag extends Json {

	protected Tag() {
	}

	public static Tag createInstance(String id) {
		Tag tag = createInstance();
		tag.setId(id);
		return tag;
	}

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final native String getEpc() /*-{
		return this.epc;
	}-*/;

	public final native void setEpc(String epc) /*-{
		this.epc = epc;
	}-*/;

	public final native String getPc() /*-{
		return this.pc;
	}-*/;

	public final native void setPc(String pc) /*-{
		this.pc = pc;
	}-*/;

	public final native Sighting getSighting() /*-{
		return this.sighting;
	}-*/;

	public final native void setSighting(Sighting sighting) /*-{
		this.sighting = sighting;
	}-*/;

	public final native Map<String, Sighting> getSightings() /*-{
		return this.sightings;
	}-*/;

	public final native JsonArray<Result> getResults() /*-{
		return this.results;
	}-*/;

	public final native void setResults(JsonArray<Result> results) /*-{
		this.results = results;
	}-*/;
}