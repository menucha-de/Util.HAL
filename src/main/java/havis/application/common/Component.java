package havis.application.common;

import havis.application.base.Content;
import havis.application.base.Service;
import havis.application.base.ServiceAsync;
import havis.application.common.event.KeyEventListener;
import havis.application.common.event.TaskListener;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Component extends Composite implements EntryPoint,
		KeyEventListener {

	protected final ServiceAsync service = GWT.create(Service.class);

	protected TaskListener handler;

	public Component() {
		ServiceDefTarget target = (ServiceDefTarget) service;
		if (target != null
				&& !target.getServiceEntryPoint().startsWith("file:///")) {
			target.setServiceEntryPoint("application/service");
			handler = new TaskListener() {
				@Override
                public void complete(Content content,
						final ErrorCallback callback) {
					service.complete(content, new AsyncCallback<Void>() {
						@Override
                        public void onSuccess(Void result) {
							// nothing to do
						}

						@Override
                        public void onFailure(Throwable caught) {
							if (callback != null)
								callback.onError(caught.getMessage());
						}
					});
				}

				@Override
                public void complete(Content content) {
					complete(content, null);
				}

				@Override
                public void getContent(Content content,
						AsyncCallback<Content> callback) {
					service.getContent(content, callback);
				}
			};
		}

		HAL.addSocketListener(new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				HAL.addServiceListener("keyboard", new AsyncCallback<havis.application.common.data.Service>() {
					@Override
					public void onSuccess(havis.application.common.data.Service service) {
						if (service.isActive()) {
							HAL.Service.Keyboard.setKeyEventListener(Component.this);					
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						// nothing to do
					}
				});
			}
			@Override
			public void onFailure(Throwable caught) {
				// nothing to do
			}			
		});
	}

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(this);

        // delay the getContent() call 10ms to avoid status code 0 issue
        new Timer() {
            @Override
            public void run() {
                service.getContent(new AsyncCallback<Content>() {
                    @Override
                    public void onSuccess(Content result) {
                        setContent(result);
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        Content content = new Content();
                        content.put("error", caught.getMessage());
                        setContent(content);
                    }
                });
            }
        }.schedule(10);
    }

	public abstract void setContent(Content content);
}