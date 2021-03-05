package havis.application.base;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {
	void start(AsyncCallback<String> callback);

	void getContent(AsyncCallback<Content> callback);

	void getContent(Content content, AsyncCallback<Content> callback);

	void complete(Content content, AsyncCallback<Void> callback);
}