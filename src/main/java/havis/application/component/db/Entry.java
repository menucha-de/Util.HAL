package havis.application.component.db;

/**
 * Provides a database entry
 */
public class Entry {

	int index;
	String key, value;

	/**
	 * Creates a new instance
	 * 
	 * @param key
	 *            The entry key
	 * @param value
	 *            The entry value
	 */
	Entry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Creates a new instance
	 * 
	 * @param index
	 *            The entry index
	 * @param key
	 *            The entry key
	 * @param value
	 *            The entry value
	 */
	Entry(int index, String key, String value) {
		this.index = index;
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the entry index
	 * 
	 * @return The index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the entry key
	 * 
	 * @return The key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the entry value
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}
}