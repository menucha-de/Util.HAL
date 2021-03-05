package havis.application.common;

import havis.application.common.data.DisplayResolution;
import havis.application.common.data.JsonArray;
import havis.application.common.data.Result;
import havis.application.common.data.Tag;
import havis.application.common.data.TagOperation;
import havis.application.common.event.KeyEventListener;
import havis.application.common.event.SocketErrorListener;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HAL {

	private final static native JavaScriptObject getContext() /*-{
		if ($wnd.parent) {
			return $wnd.parent; // fallback
		}
		return $wnd;
	}-*/;

	private final static native void checkStatus(String status) /*-{
		return (!status || 0 === status.length);
	}-*/;

	private final static native void throwException(String message, AsyncCallback<Object> callback) /*-{
		// see: http://stackoverflow.com/questions/23858601/how-to-track-jsni-errors-in-gwt
		var obj = new Object(message);
		var ex = @com.google.gwt.core.client.JavaScriptException::new(Ljava/lang/Object;)(obj);
		callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
	}-*/;

	private final static native void handleResponse(Object response, AsyncCallback<Object> callback) /*-{
    	if (@havis.application.common.HAL::checkStatus(Ljava/lang/String;)(response['status'])) {
    		callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(response['result']);
    	} else {
    		@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    													 (response['result'], callback);
    	}
	}-*/;

	public static class Service {

		public static class Display {

			public final static native boolean isSupported() /*-{
				var context = @havis.application.common.HAL::getContext()();
				return context && context.Service && context.Service.Display;
			}-*/;

			public final static native void getDisplayResolution(AsyncCallback<DisplayResolution> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
				if (context && context.Service && context.Service.Display) {
					context.Service.Display.getDisplayResolution(function(response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
					});
				} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																 ('Error in JSNI getDisplayResolution()', callback);
				}
			}-*/;

		}

		public static class Barcode {

			public final static native boolean isSupported() /*-{
				var context = @havis.application.common.HAL::getContext()();
				return context && context.Service && context.Service.Barcode;
			}-*/;

			public final static native void barcodeScan(AsyncCallback<String> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
				if (context && context.Service && context.Service.Barcode) {
					context.Service.Barcode.barcodeScan(function(response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
					});
				} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																 ('Error in JSNI barcodeScan()', callback);
				}
			}-*/;

		}

		public static class C1G2 {

			public final static native boolean isSupported() /*-{
				var context = @havis.application.common.HAL::getContext()();
				return context && context.Service && context.Service.C1G2;
			}-*/;

			public final static native void tagInventory(AsyncCallback<JsonArray<Tag>> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
        		if (context && context.Service && context.Service.C1G2) {
        			context.Service.C1G2.tagInventory(function(response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
       				});
        		} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI tagInventory()', callback);
        		}
			}-*/;

			public final static native void executeTagOperation(JsonArray<TagOperation> operations, String id, AsyncCallback<JsonArray<Result>> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
    			if (context && context.Service && context.Service.C1G2) {
    				context.Service.C1G2.executeTagOperation(operations, id, function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI executeTagOperation()', callback);
    			}
			}-*/;

		}
		
		public static class Keyboard {
			
			public final static native boolean isSupported() /*-{
				var context = @havis.application.common.HAL::getContext()();
				return context && context.Service && context.Service.Keyboard;
			}-*/;

			public final static native void setKeyEventListener(KeyEventListener listener) /*-{
    			var context = @havis.application.common.HAL::getContext()();
    			if (context && context.Service && context.Service.Keyboard) {
    				context.Service.Keyboard.getKeyEvents(function(response) {
    					if (@havis.application.common.HAL::checkStatus(Ljava/lang/String;)(response['status'])) {
    		            	var keyList = response['result'];
    						for (key in keyList) {
    							context.Service.Keyboard.subscribeKeyEvent(keyList[key]);
    						}
    
    						context.Service.Keyboard.notifyKeyEvent.connect(function(keyEvent) {
    							listener.@havis.application.common.event.KeyEventListener::onKeyEvent(Ljava/lang/String;)(keyEvent);
    						});
    	            	}
    	         	});
    	        }
    		}-*/;

    		public final static native void getKeyEvents(AsyncCallback<JsArrayString> callback) /*-{
    			var context = @havis.application.common.HAL::getContext()();
    	        if (context && context.Service && context.Service.Keyboard) {
    				context.Service.Keyboard.getKeyEvents(function(response) {
    					@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    																 (response, callback);
    				});
    			} else {
    				@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    	        												 ('Error in JSNI getKeyEvents()', callback);
    			}
    		}-*/;

		}
		
		public static class Capabilities {
			public final static native void getCapabilities(AsyncCallback<JsArrayString> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
    			if (context && context.Service && context.Service.Capabilities) {
    				context.Service.Capabilities.getCapabilities(function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI getCapabilities()', callback);
    			}
			}-*/;
			
			public final static native void getCapabilityValue(String capability, AsyncCallback<Object> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
    			if (context && context.Service && context.Service.Capabilities) {
    				context.Service.Capabilities.getCapability(capability, function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
																	 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI getCapabilityValue()', callback);
    			}
			}-*/;
		}
	}

	public static class System {

		public static class Config {

			public final static native void getConfig(AsyncCallback<havis.application.common.data.Config> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
				if (context && context.System && context.System.Config) {
     				context.System.Config.getConfig(function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    																 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI getConfig()', callback);
    			}
    		}-*/;

			public final static native void setConfig(havis.application.common.data.Config config, AsyncCallback<Void> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
				if (context && context.System && context.System.Config) {
     				context.System.Config.setConfig(config, function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    																 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI setConfig()', callback);
    			}
    		}-*/;

			public final static native void getSupportedProperties(String path, AsyncCallback<JsArrayString> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
    		 	if (context && context.System && context.System.Config) {
     				context.System.Config.getSupportedProperties(path, function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    																 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI getSupportedProperties()', callback);
    			}
    		}-*/;

			public final static native void getSupportedValues(String property, AsyncCallback<String> callback) /*-{
				var context = @havis.application.common.HAL::getContext()();
    		 	if (context && context.System && context.System.Config) {
     				context.System.Config.getSupportedValues(property, function (response) {
						@havis.application.common.HAL::handleResponse(Ljava/lang/Object;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    																 (response, callback);
    				});
    			} else {
					@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        														 ('Error in JSNI getSupportedValues()', callback);
    			}
			}-*/;

		}
	}

	public final static native void addServiceListener(String serviceName, final AsyncCallback<havis.application.common.data.Service> callback) /*-{
		var context = @havis.application.common.HAL::getContext()();
    	if (context && context.hal) {
        	context.hal.addServiceListener(serviceName, function (key, active) {
        		var service = @havis.application.common.data.Service::createInstance(Ljava/lang/String;Z)(key, active);
        		callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(service);
        	});
    	} else {
    		@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
    													 ('Error in JSNI addServiceListener()', callback);
    	}
	}-*/;

	public final static native void addSocketListener(final AsyncCallback<Void> callback) /*-{
		var context = @havis.application.common.HAL::getContext()();
    	if (context && context.hal) {
        	context.hal.addSocketListener(function () {
        		callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(null);
        	});
        } else {
        	@havis.application.common.HAL::throwException(Ljava/lang/String;Lcom/google/gwt/user/client/rpc/AsyncCallback;)
        												 ('Error in JSNI addSocketListener()', callback);
        }
	}-*/;

	public final static native void addSocketErrorListener(final SocketErrorListener callback) /*-{
		var context = @havis.application.common.HAL::getContext()();
		if (context && context.hal) {
			context.hal.addSocketErrorListener(function(origin) {
				return callback.@havis.application.common.event.SocketErrorListener::onError(Ljava/lang/String;)(origin);
			});
		}
	}-*/;

	public final static native void createSocket(String url) /*-{
		var context = @havis.application.common.HAL::getContext()();
		if (context && context.Socket) {
			context.Socket.create(url);
		}
	}-*/;

	public final static native JavaScriptObject createWebSocket(String url) /*-{
		var context = @havis.application.common.HAL::getContext()();
		if (context && context.hal) {
			return context.hal.createWebSocket(url);
		} else {
			return null;
		}
	}-*/;
}