package havis.application.base;

import havis.application.common.event.TaskListener;

import java.util.HashMap;

import com.google.gwt.dom.client.Document;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;

/**
 * Client-side synchronization component.<br/><br/><br/>
 * 
 * This component provides, combined with havis.application.client.Proxy, the ability to bypass
 * the <a href="https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy" target="extern">same-origin policy</a>.<br/><br/>
 * 
 * Usage Example:<br/><br/>
 * 
 * <pre>
 * <code>
 * RemoteService remoteService = new RemoteService();
 * 
 * //Set remote service host
 * String url = "http://kermit:kermit@localhost:8080/myapp";
 * remoteService.setUrl(url);
 * 
 * //load remote service component havis.application.client.Proxy in a iframe. 
 * //iframe will be loaded within <body>....</body> of html page.
 * //default connection timeout in this case will be set to 10s 
 * remoteService.open(new AsyncCallback<TaskListener>() {
 * 		public void onSuccess(TaskListener taskListener) {
 * 			 //Remote havis.application.client.Proxy component successfully loaded in a iframe.
 * 			 //Now you can execute "remote" calls, like in online applications are made, with TaskListener.
 *      }
 * 		public void onFailure(Throwable caught) { 
 * 			//Connection failed
 *      } 
 * });
 * </code>
 * </pre>
 * 
 * 
 * <h3>Client - Proxy communication</h3>
 * 
 * <img src="ClientProxyComm.png"/>
 * <br/><br/>
 * 
 * This communication with Hs-VIS Application Server is supplied as "blackbox" by {@link havis.application.base.RemoteService} class to the client.
 * 
 * 
 * <h3>IN HTTP Post Parameter:</h3>
 * 		<pre>
 *  	- type [completed|getContentCompleted|failure|error]:
 *  	<code>
 *  	+ completed: Service "complete" completed by GWT RPC Remote Service
 *  	+ getContentCompleted: Service "getContent" completed by GWT RPC Remote Service
 *  	+ failure: Exception occurred while calling Service "getContent"
 *  	+ error: Exception occurred while calling Service "complete"
 *      </code>
 *      - corellationId: Correlation ID, to assign the response of the client request to an appropriate client callback
 *      - text: In case of error, this argument is sent to the client and includes the error message
 *      - data: Havis Application Suite Server Request Results (process data)
 *      </pre>
 * 		 
 *  
 * <h3>OUT HTTP Post Parameter:</h3>
 * 		<pre>
 * 		- payload: Process parameters that are to be sent to the GWT RPC service. These are processed in the workflow. see {@link havis.application.base.ContentContainer}
 * 		- correlationId: Correlation ID, to assign the response of the client request to an appropriate client callback.
 * 		</pre>
 * 
 */
public class RemoteService {
	private int timeout = 10000;
	private String url;
	private Frame iframe;
	private Document contentWindow;
	private Timer timer;
	private AsyncCallback<TaskListener> onOpenedDelegate;
	private HashMap<String, CallbackContainer> asyncCallbacks;

	public RemoteService() {
		asyncCallbacks = new HashMap<String, CallbackContainer>();

		init();
	}

	/**
	 * Returns the URL
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL to send data from the client to the server.
	 * 
	 * @param url
	 *            to set
	 */
	public void setUrl(String url) {
		String proxyPath = "Proxy.html";
		this.url = url;

		if (!this.url.endsWith(proxyPath)) {
			String slash = "";

			if (!this.url.endsWith("/")) {
				slash = "/";
			}

			this.url = this.url + slash + proxyPath;
		}
	}

	/**
	 * Sets the URL to send data from the client to the server.
	 * 
	 * @param protocol
	 *            to set
	 * @param host
	 *            to set
	 * @param port
	 *            to set
	 * @param path
	 *            to set
	 * @param user
	 *            to set
	 * @param password
	 *            to set
	 */
	public void setUrl(String protocol, String host, int port, String path,
			String user, String password) {
		String proxyPath = "Proxy.html";

		// Validate URL implicit
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setProtocol(protocol);
		urlBuilder.setHost(host);
		urlBuilder.setPort(port);

		if ((path != null) && !path.endsWith("Proxy.html")) {
			String slash = "";

			if (!path.endsWith("/")) {
				slash = "/";
			}

			proxyPath = path + slash + proxyPath;
		}

		urlBuilder.setPath(proxyPath);

		// Initializes URL
		String tmpUrl = urlBuilder.buildString();
		String[] splitedUrl = tmpUrl.split(protocol + "://");

		if (splitedUrl.length == 2) {
			url = protocol + "://" + user + ":" + password + "@"
					+ splitedUrl[1];
		} else {
			throw new IllegalArgumentException("url");
		}
	}

	/**
	 * Loads the Havis Suite Server Synchronisazions component in an iframe. Is
	 * the host after a default hold time of 10s reached, the method throws
	 * {@link com.google.gwt.user.client.rpc.AsyncCallback#onFailure(Throwable)}, with the timeout exception.
	 * 
	 * @param delegate
	 *            {@link com.google.gwt.user.client.rpc.AsyncCallback}
	 */
	public void open(final AsyncCallback<TaskListener> delegate) {
		open(timeout, delegate);
	}

	/**
	 * Loads the Havis Suite Server Synchronisazionskomponente in an iframe. If
	 * the host after a specified timeout period can not be reached, the method
	 * throws {@link com.google.gwt.user.client.rpc.AsyncCallback#onFailure(Throwable)}, with the timeout
	 * exception.
	 * 
	 * @param delegate
	 *            Callback for the treatment of a response from the server
	 */
	public void open(final int timeout,
			final AsyncCallback<TaskListener> delegate) {

		this.timeout = timeout;
		this.onOpenedDelegate = delegate;

		if (getUrl() == null) {
			throw new IllegalArgumentException("url");
		}

		timer = new Timer() {
			@Override
			public void run() {
				if (delegate != null) {
					if (RemoteService.this.onOpenedDelegate != null) {
						RemoteService.this.onOpenedDelegate
								.onFailure(new Throwable("Timeout {" + timeout
										+ "ms}"));
					}
				}
			}
		};

		this.timeout = timeout;
		timer.schedule(timeout);

		iframe.setUrl(getUrl());
	}

	/**
	 * Makes a request to the server synchronization component. At the same time
	 * the passed callback is associated with a correlation ID. This is relevant
	 * for a reply from the server to just be able to assign the response of a
	 * request.
	 * 
	 * @param content
	 *            process data
	 * @param method
	 *            Which GWT RPC service {@link havis.application.base.Service} should be called?
	 *            Possible values: 'complete' or 'getContent'.
	 * @param callback
	 *            Callback for the handling of a response from the server
	 */
	private void request(Content content, String method,
			AsyncCallback<Content> callback) {

		if (callback == null) {
			throw new IllegalArgumentException("Callback is null");
		}

		if ((getUrl() == null) || (iframe.getUrl() == null)
				&& (iframe.getUrl().trim().length() == 0)) {
			callback.onFailure(new IllegalArgumentException("url"));
		} else {
			ContentContainer jsonTransferContainer = ContentContainer
					.createInstance();
			jsonTransferContainer.setAsContent(content);
			jsonTransferContainer.setMethodName(method);

			final String correlationId = post(jsonTransferContainer.stringify());

			Timer timer = new Timer() {
				@Override
				public void run() {
					execAssociatedRequestCallback(correlationId, "Timeout {"
							+ timeout + "}", true);
				}
			};

			CallbackContainer callbackContainer = new CallbackContainer(
					callback, timer);
			asyncCallbacks.put(correlationId, callbackContainer);

			timer.schedule(timeout);
		}
	}

	/**
	 * In a response from the server a corresponding callback is determined and
	 * executed. The corresponding callback in the request (see {@link havis.application.base.RemoteService}) will be associated with a correlation ID.
	 * 
	 * @param correlationId
	 *            Correlation ID to assign the response of the client request an
	 *            appropriate client callback again to
	 * @param data
	 *            Process / application data or error text
	 * @param isFailure
	 *            the value is set to true, data is treated as error text.
	 *            Otherwise, data is treated as {@link havis.application.base.ContentContainer}
	 */
	private void execAssociatedRequestCallback(String correlationId,
			String data, boolean isFailure) {
		if (asyncCallbacks.containsKey(correlationId)) {
			CallbackContainer callbackContainer = asyncCallbacks
					.get(correlationId);
			AsyncCallback<Content> callback = callbackContainer.getCallback();
			if (!isFailure) {
				if ((data != null)) {
					ContentContainer container = ContentContainer.parse(data);
					Content responseContent = container.getAsContent();
					callback.onSuccess(responseContent);
				} else {
					callback.onSuccess(new Content());
				}
			} else {
				callback.onFailure(new Throwable(data));
			}

			if (callbackContainer.getTimer() != null) {
				callbackContainer.getTimer().cancel();
			}

			asyncCallbacks.remove(correlationId);
		}
	}

	/**
	 * Method is called when the server synchronization component was
	 * successfully loaded. Then Executes
	 * {@link com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(Object)} to
	 * {@link havis.application.base.RemoteService#open(AsyncCallback)} passed callback from
	 * 
	 * @param contentWindow
	 *            JavaScript - event.source
	 */
	private void execCallbackOnUrlLoaded(Document contentWindow) {
		this.contentWindow = contentWindow;

		if (timer != null) {
			timer.cancel();
		}

		if (onOpenedDelegate != null) {
			onOpenedDelegate.onSuccess(new TaskListener() {

				@Override
				public void getContent(Content content,
						AsyncCallback<Content> callback) {
					RemoteService.this.request(content, "getContent", callback);
				}

				@Override
				public void complete(Content content,
						final ErrorCallback callback) {
					RemoteService.this.request(content, "complete",
							new AsyncCallback<Content>() {
								@Override
								public void onSuccess(Content result) {
									// Empty
								}

								@Override
								public void onFailure(Throwable caught) {
									if (callback != null) {
										callback.onError("" + caught);
									}
								}
							});
				}

				@Override
				public void complete(Content content) {
					complete(content, null);
				}
			});
		}
	}

	private void init() {
		iframe = new Frame();
		iframe.setVisible(false);

		Document.get().getBody().appendChild(iframe.getElement());

		setup();
	}

	/**
	 * Sends a request via postMessage incl. Process data the servers
	 * synchronization component.
	 * 
	 * @param transferContainer
	 *            {@link havis.application.base.ContentContainer} as JSON structure
	 * 
	 * @return Correlation ID
	 */
	private final native String post(String transferContainer) /*-{
		var me = this;
		var correlationId = @havis.application.common.data.UUID::createUUID()();
		var iframe = me.@havis.application.base.RemoteService::contentWindow;

		iframe.postMessage({
			'correlationId' : correlationId,
			'payload' : transferContainer
		}, "*");

		return correlationId;
	}-*/;

	/**
	 * Initializes JavaScript event listeners. The listener respond to Responses
	 * from the remote server Synchronizations.
	 */
	private final native void setup() /*-{
		var that = this;

		function onEvent(event) {

			switch (event.data.type) {
			case 'proxyIsAlive':
				that.@havis.application.base.RemoteService::execCallbackOnUrlLoaded(Lcom/google/gwt/dom/client/Document;)(event.source);
				break;

			case 'completed':
			case 'getContentCompleted':
				that.@havis.application.base.RemoteService::execAssociatedRequestCallback(Ljava/lang/String;Ljava/lang/String;Z)(event.data.correlationId, event.data.data, false);
				break;

			case 'failure':
			case 'error':
				that.@havis.application.base.RemoteService::execAssociatedRequestCallback(Ljava/lang/String;Ljava/lang/String;Z)(event.data.correlationId, event.data.text, true);
				break;
			}
		}

		$wnd.addEventListener('message', onEvent, false);
	}-*/;

	static class CallbackContainer {
		private AsyncCallback<Content> callback;
		private Timer timer;

		public CallbackContainer(AsyncCallback<Content> callback, Timer timer) {
			this.callback = callback;
			this.timer = timer;
		}

		public AsyncCallback<Content> getCallback() {
			return callback;
		}

		public Timer getTimer() {
			return timer;
		}
	}
}
