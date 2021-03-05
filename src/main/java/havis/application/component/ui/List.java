package havis.application.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class List extends Composite implements ClickHandler, KeyDownHandler {

	private static ListUiBinder uiBinder = GWT.create(ListUiBinder.class);

	interface ListUiBinder extends UiBinder<Widget, List> {
	}

	final static String STYLE_SELECTED = "havis-List-selected";
	final static int PAGE = 5;

	boolean enable = true;
	boolean styleExist = false;
	boolean autoselect;

	SingleSelectionModel<Integer> model;

	HandlerManager handlerManager;

	@UiField
	FlexTable table;

	public List() {
		initWidget(uiBinder.createAndBindUi(this));

		model = new SingleSelectionModel<Integer>();
		model.setSelected(-1, true);
	}

	public void setHeader(Widget[] widgets) {
		Element element = table.getElement();
		NodeList<Element> theadElements = element.getElementsByTagName("thead");
		if (theadElements != null) {
			for (int i = 0; i < theadElements.getLength();) {
				element.removeChild(theadElements.getItem(i));
				break;
			}
		}

		if (widgets != null) {

			NodeList<Element> tbodyElements = element
					.getElementsByTagName("tbody");
			if (tbodyElements != null) {
				Document doc = Document.get();

				Element trElement = doc.createTRElement();

				for (Widget widget : widgets) {
					Element thElement = doc.createTHElement();
					thElement.appendChild(widget.getElement());
					trElement.appendChild(thElement);
				}
				Element tHeadElement = doc.createTHeadElement();
				tHeadElement.appendChild(trElement);
				for (int i = 0; i < tbodyElements.getLength();) {
					element.insertBefore(tHeadElement, tbodyElements.getItem(i));
					break;
				}
			}
		}
	}

	public void addItem(Widget[] widgets) {
		int row = table.getRowCount();
		setItem(widgets, row);
	}

	public void setItem(Widget[] widgets, int index) {
		int column = 0;
		for (Widget widget : widgets) {
			table.setWidget(index, column, widget);
			column++;
		}
		if (autoselect && getSelectedIndex() == -1) {
			setSelected(index);
		}
	}

	public Widget[] getItem(int index) {
		if (index > -1 && index < table.getRowCount()) {
			Widget[] widgets = new Widget[table.getCellCount(index)];
			for (int i = 0; i < table.getCellCount(index); i++) {
				widgets[i] = table.getWidget(index, i);
			}
			return widgets;
		} else {
			return null;
		}
	}

	public void insertAt(int row, int column, Widget widget){
		table.setWidget(row, column, widget);
	}
	
	void setEnable(boolean enable) {
		this.enable = enable;
		if (enable) {
			if (styleExist) {
				removeStyleName("havis-List-disabled");
				styleExist = false;
			}
		} else {
			if (!styleExist) {
				addStyleName("havis-List-disabled");
				styleExist = true;
			}
		}
	}

	public void setAutoselect(boolean autoselect) {
		this.autoselect = autoselect;
	}

	public Widget[] getSelectedItem() {
		return getItem(model.getSelectedObject());
	}

	public void clear() {
		table.removeAllRows();
		model.setSelected(-1, true);
	}

	public int getItemCount() {
		return table.getRowCount();
	}

	public void removeRow(int row) {
		if (row > -1 && row < table.getRowCount()) {
			table.removeRow(row);
			if (row == getSelectedIndex()) {
				if (row == table.getRowCount()) {
					setSelected(row - 1);
				} else {
					setSelected(row);
				}
			}
		}
	}

	@UiHandler("panel")
	public void onClick(ClickEvent event) {
		if (enable) {
			Cell cell = table.getCellForEvent(event);
			if (cell != null) {
				setSelected(cell.getRowIndex());
			}
		}
	}

	@UiHandler("panel")
	public void onKeyDown(KeyDownEvent event) {
		if (enable) {
			int selected = getSelectedIndex();
			switch (event.getNativeKeyCode()) {
			case KeyCodes.KEY_DOWN:
				if (selected > -1) {
					int count = table.getRowCount();
					if (selected + 1 < count) {
						setSelected(selected + 1);
					}
				}
				event.stopPropagation();
				break;
			case KeyCodes.KEY_PAGEDOWN:
				if (selected > -1) {
					int count = table.getRowCount();
					if (selected + 1 < count) {
						if (selected + PAGE < count) {
							setSelected(selected + PAGE);
						} else {
							setSelected(count - 1);
						}
					}
				}
				event.stopPropagation();
				break;
			case KeyCodes.KEY_UP:
				if (selected > 0) {
					setSelected(selected - 1);
				}
				event.stopPropagation();
				break;
			case KeyCodes.KEY_PAGEUP:
				if (selected > 0) {
					if (selected - PAGE > -1) {
						setSelected(selected - PAGE);
					} else {
						setSelected(0);
					}
				}
				event.stopPropagation();
				break;
			case KeyCodes.KEY_DELETE:
				removeRow(selected);
				event.stopPropagation();
				break;
			case KeyCodes.KEY_HOME:
				if (selected > -1) {
					setSelected(0);
				}
				event.stopPropagation();
				break;
			case KeyCodes.KEY_END:
				if (selected > -1) {
					setSelected(table.getRowCount() - 1);
				}
				event.stopPropagation();
				break;
			}
		}
	}

	public HandlerRegistration addSelectionChangeHandler(Handler handler) {
		return model.addSelectionChangeHandler(handler);
	}

	public void setSelected(int index) {
		int selected = getSelectedIndex();
		RowFormatter formatter = table.getRowFormatter();
		if (selected > -1 && selected < table.getRowCount()) {
			formatter.removeStyleName(selected, STYLE_SELECTED);
		}
		if (index > -1) {
			formatter.addStyleName(index, STYLE_SELECTED);
		}
		model.setSelected(index, true);
	}

	public int getSelectedIndex() {
		return model.getSelectedObject();
	}
}