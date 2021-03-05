package havis.application.common.event;

import havis.application.base.Content;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskListener {

	public interface ErrorCallback {
		void onError(String error);
	}

	void complete(Content content);

	void complete(Content content, ErrorCallback callback);

	void getContent(Content content, AsyncCallback<Content> callback);
}