package havis.application.base;

import havis.application.common.data.Json;
import havis.application.common.data.Property;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.JsArray;

/**
 * This class Acts as transfer container for {@link Content}
 * 
 */
public class ContentContainer extends Json {
	protected ContentContainer() {

	}

	/**
	 * Converts a JsArray of {@link Property} to {@link Content}
	 * @return the content
	 */
	public final Content getAsContent() {
		Content content = new Content();

		JsArray<Property> properties = getProperties();

		if (properties != null) {
			for (int i = 0; i < properties.length(); i++) {
				Property property = properties.get(i);
				content.put(property.getName(), property.getValue());
			}
		}

		return content;
	}

	/**
	 * {@link Content} will be converted to a JsArray of {@link Property}
	 * 
	 * @param content the content to use
	 */
	public final void setAsContent(Content content) {
		JsArray<Property> properties = JsArray.createArray().cast();

		if (content != null) {
			Set<Entry<String, String>> entrySet = content.entrySet();
			Iterator<Entry<String, String>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				properties.push(Property.createInstance(entry.getKey(), entry.getValue()));
			}
		}

		setProperties(properties);
	}

	/**
	 * @return list of properties {@link Property}
	 */
	public final native JsArray<Property> getProperties() /*-{
		return this.properties;
	}-*/;

	/**
	 * @param properties
	 *            to set
	 */
	public final native void setProperties(JsArray<Property> properties) /*-{
		this.properties = properties;
	}-*/;

	/**
	 * @return method name
	 */
	public final native String getMethodName() /*-{
		return this.methodName;
	}-*/;

	/**
	 * @param methodName
	 *            to set
	 */
	public final native void setMethodName(String methodName) /*-{
		this.methodName = methodName;
	}-*/;
}
