package bean;

import java.util.List;

public class CatalogueBean {
	private List<ItemBean> items;

	public CatalogueBean(List<ItemBean> items) {
		super();
		this.items = items;
	}

	public List<ItemBean> getItems() {
		return items;
	}

	public void setItems(List<ItemBean> items) {
		this.items = items;
	}
	
}
