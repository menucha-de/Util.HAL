package havis.application.base;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface Service extends RemoteService {
	String start();

	Content getContent();

	Content getContent(Content content);

	void complete(Content content);
}