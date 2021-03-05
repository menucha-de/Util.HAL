package havis.application.common.data;


public class DisplayResolution extends Json {
	protected DisplayResolution() {
	}

	public static DisplayResolution newInstance() {
		DisplayResolution display = createInstance();
		
		return display;
	}

	public final native int getHorizontalPixel() /*-{
		return this.horizontalPixel;
	}-*/;

	public final native void setHorizontalPixel(int horizontalPixel) /*-{
		this.horizontalPixel = horizontalPixel;
	}-*/;

	public final native int getVerticalPixel() /*-{
		return this.verticalPixel;
	}-*/;

	public final native void setVerticalPixel(int verticalPixel) /*-{
		this.verticalPixel = verticalPixel;
	}-*/;
}
