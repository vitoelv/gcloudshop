package com.jcommerce.web.front.action.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Pager {
	
	
	private String url;
	private int start;

	
    private int page;
    private int size;
    private String sort;
    private String order;
    private int recordCount;
    private int pageCount;
    private String display;
    private int styleid;
    
    private Map<Integer, Integer> array = new TreeMap<Integer, Integer>();
    
    private Map<String, Object> search = new HashMap<String, Object>();
    
    private String pageFirst;
    private String pagePrev;
    private String pageNext;
    private String pageLast;
    public Pager() {
        display = "list";
        sort = "sort_goods_id";
        order = "order_DESC";
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public int getRecordCount() {
        return recordCount;
    }
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }

    public String getPageFirst() {
        return pageFirst;
    }
    public void setPageFirst(String pageFirst) {
        this.pageFirst = pageFirst;
    }
    public String getPagePrev() {
        return pagePrev;
    }
    public void setPagePrev(String pagePrev) {
        this.pagePrev = pagePrev;
    }
    public String getPageNext() {
        return pageNext;
    }
    public void setPageNext(String pageNext) {
        this.pageNext = pageNext;
    }
    public String getPageLast() {
        return pageLast;
    }
    public void setPageLast(String pageLast) {
        this.pageLast = pageLast;
    }
    public String[] getSorts() {
        return new String[] {"sort_goods_id", "sort_shop_price", "sort_last_update"};
    }
    public String[] getOrders() {
        return new String[] {"order_DESC", "order_ASC"};
    }
	public int getStyleid() {
		return styleid;
	}
	public void setStyleid(int styleid) {
		this.styleid = styleid;
	}
	public Map<String, Object> getSearch() {
		return search;
	}
	public void setSearch(Map<String, Object> search) {
		this.search = search;
	}
	public Map<Integer, Integer> getArray() {
		return array;
	}
	public void setArray(Map<Integer, Integer> array) {
		this.array = array;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}

}
