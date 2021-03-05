package havis.application.common.data;

public class TagOperation extends Json {

	protected TagOperation() {
	}

	public static TagOperation createInstance(String type, int bank) {
		TagOperation instance = createInstance();
		instance.setType(type);
		instance.setBank(bank);
		return instance;
	}

	public static TagOperation createInstance(String name, String type, int bank) {
		TagOperation instance = createInstance();
		instance.setName(name);
		instance.setType(type);
		instance.setBank(bank);
		return instance;
	}
	
	public static TagOperation createInstance(String type, String data) {
		TagOperation instance = createInstance();
		instance.setType(type);
		instance.setData(data);
		return instance;
	}

	public static TagOperation createInstance(String name, String type, String data) {
		TagOperation instance = createInstance();
		instance.setName(name);
		instance.setType(type);
		instance.setData(data);
		return instance;
	}
	
	public static TagOperation createInstance(String type, int bank,
			int offset, int length) {
		TagOperation instance = createInstance();
		instance.setType(type);
		instance.setBank(bank);
		instance.setOffset(offset);
		instance.setLength(length);
		return instance;
	}

	public static TagOperation createInstance(String name, String type, int bank,
			int offset, int length) {
	    	TagOperation instance = createInstance();
	    	instance.setName(name);
		instance.setType(type);
		instance.setBank(bank);
		instance.setOffset(offset);
		instance.setLength(length);
		return instance;
	}
	
	public static TagOperation createInstance(String type, int bank,
			int offset, int length, String data) {
		TagOperation instance = createInstance();
		instance.setType(type);
		instance.setBank(bank);
		instance.setOffset(offset);
		instance.setLength(length);
		instance.setData(data);
		return instance;
	}

	public static TagOperation createInstance(String name, String type, int bank,
			int offset, int length, String data) {
	    	TagOperation instance = createInstance();
		instance.setName(name);
		instance.setType(type);
		instance.setBank(bank);
		instance.setOffset(offset);
		instance.setLength(length);
		instance.setData(data);
		return instance;
	}
	
	public final native String getType() /*-{
		return this.type;
	}-*/;

	public final native void setType(String type) /*-{
		this.type = type;
	}-*/;

	public final native int getBank() /*-{
		return this.bank;
	}-*/;

	public final native void setBank(int bank) /*-{
		this.bank = bank;
	}-*/;

	public final native int getLength() /*-{
		return this.length;
	}-*/;

	public final native void setLength(int length) /*-{
		this.length = length;
	}-*/;

	public final native int getOffset() /*-{
		return this.offset;
	}-*/;

	public final native void setOffset(int offset) /*-{
		this.offset = offset;
	}-*/;

	public final native String getData() /*-{
		return this.data;
	}-*/;

	public final native void setData(String data) /*-{
		this.data = data;
	}-*/;
	
	public final native String getName() /*-{
	    	return this.name;
	}-*/;
	
	public final native void setName(String name) /*-{
	    	this.name = name;
	}-*/;
}