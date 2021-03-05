package havis.application.base;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Content extends HashMap<String, String> {

	Map<String, String> header;

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}	
}