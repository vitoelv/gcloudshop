package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.BrandWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class HomeAction extends BaseAction {
    private static Log log = LogFactory.getLog(HomeAction.class);

	public void debug(String s) {
		System.out.println(" in [HomeAction]: "+s );
	}
    

    @Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
    


    public void includeBrands(HttpServletRequest request) {
    	// brands.ftl
        List<Brand> brands = (List<Brand>)getDefaultManager().getList(ModelNames.BRAND, null);
        
        Criteria criteria = new Criteria();
        Condition cond = new Condition();
        cond.setField(IGoods.BRAND_ID);
        cond.setOperator(Condition.EQUALS);
        List<BrandWrapper> brandInfoList = new ArrayList<BrandWrapper>();
        for (Brand brand:brands) {
            cond.setValue(brand.getPkId());
            criteria.addCondition(cond);
            Integer goodsNum = getDefaultManager().getCount(ModelNames.GOODS, criteria);
            BrandWrapper bw = new BrandWrapper(brand);
            bw.put("goodsNum", goodsNum);
            bw.put("brandLogo", "dynaImageService.do?fileId="+brand.getLogoFileId());
            brandInfoList.add(bw);
            criteria.removeAllCondition();
        }        
        request.setAttribute("brandList", brandInfoList);
        
        
    }
    public void includeNewArticle(HttpServletRequest request) {
    	// new_articles.ftl
//      request.setAttribute("new_articles", articleManager.getArticleList());
        request.setAttribute("newArticles", new ArrayList());    	
    }
    public void includePromotionInfo(HttpServletRequest request) {
    	// promotion_info.ftl
        request.setAttribute("promotionInfo", new ArrayList());
    	
    }
    public void includeOrderQuery(HttpServletRequest request) {
        // order_query.ftl
        request.setAttribute("orderQuery", new HashMap<String, String>());    	
    }
    public void includeInvoiceQuery(HttpServletRequest request) {
        // invoice_query.ftl
    	// TODO invoice query
    	//      request.setAttribute("invoiceList", new ArrayList());
	
    }
    public void includeAuction(HttpServletRequest request) {
        // auction.ftl
    	//        	request.setAttribute("auctionList", new ArrayList());    	
    }
    public void includeGroupBuy(HttpServletRequest request) {
        // group_buy.ftl
//    	request.setAttribute("groupBuyGoods", new ArrayList());    	
    }
    public void includeTop10(HttpServletRequest request) {
    	// top10.ftl
    	
        //top 10
        //      List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();
        //      orderGoods = orderGoodsManager.getOrderGoodsList();
        //      String[] goodId = new String[10];
        //      int index = 0;
        //      for (Iterator it = orderGoods.iterator(); it.hasNext();) {
        //          OrderGoods orderGood = (OrderGoods)it.next();
        //          goodId[index] = orderGood.getGoods().getPkId()+"";
        //          index++;
        //      }
         

        List<OrderGoods> topList = new ArrayList<OrderGoods>();
//        topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
        request.setAttribute("topGoods", topList);    	
    }
    protected void initParameters(HttpServletRequest request) {
        request.setAttribute("flashTheme", "flash");
        // TODO shopNotice
        request.setAttribute("shopNotice", "Shop's latest Notice!!");
        
    }
    
    public static class TestData {
    	String name;
    	public void setName(String name) {
    		this.name = name;
    	}
    	public String get(String key) {
    		return name;
    	}
    	public String getMyName() {
    		return "xxx";
    	}
    	
    }

    @Override
    public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'home' method...");
        }
        debug("in execute");
        
        super.execute();
        
        HttpServletRequest request = getRequest();        
        
        // test only
        // map
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "xxx");
        String vkey="key1";
        request.setAttribute("vkey", vkey);
        request.setAttribute("mymap", map);

        TestData td = new TestData();
        td.setName("abc");
        request.setAttribute("testData", td);
        
        request.setAttribute("testUrl", "abc.action?aa=xx&bb=yy");
        
        Pager pager = new Pager();
        pager.setPageLast("abc1.action?aa=xx&bb=yy&amp;cc=zz");
        request.setAttribute("testpager", pager);
        
        initPager(request);
        initParameters(request);
        
        includeCategoryTree(request);
        includeRecommendBest(request);
        includeRecommendHot(request);
        includeRecommendNew(request);
        includeBrands(request);
        includeTop10(request);
        includeNewArticle(request);
        includeOrderQuery(request);
        includeInvoiceQuery(request);
        includeAuction(request);
        includeGroupBuy(request);
        
//        //Cart Info.....
//        cartInfoShow(request);
        
        return  Action.SUCCESS;
    }
    private Session getSession(HttpServletRequest request) {
//        String sid = request.getSession(true).getPkId();
//        Session sess = sessionManager.getSession(sid);
//        if (sess == null) {
//            sess = new Session();
//            sess.setIP(request.getRemoteAddr());
//            sess.setExpiry(new Timestamp(
//                            new Date().getTime() + 1000 * 60 * 60)); // expire after 1h
//            sess.setId(sid);
//            sessionManager.saveSession(sess);
//        } else {
//            // sess.setExpiry(new Timestamp(new Date().getTime() + 1000 * 60 *
//            // 60)); // expire after 1h
//        }
//        return sess;
    	return null;
    }
    
    private void cartInfoShow(HttpServletRequest request){
        //购物车提要信息
//         Session session = getSession(request);
//         List<Cart> cartList = new ArrayList<Cart>();
//         cartList = cartManager.getCartList(session);
         int number = 0;
         double price = 0;
//         for (Iterator it = cartList.iterator(); it.hasNext();) {
//                Cart cart = (Cart) it.next();
//                number+=cart.getGoodsNumber();
//                price+=cart.getGoodsPrice();
//            }
         request.setAttribute("number", number);
         request.setAttribute("price", price);
    }

    protected void getHistory() throws Exception {
//        List<Goods> goods = goodsManager.getList("from Goods t where t.onSale=true and t.aloneSale=true and t.delete=false");
    }
    
    public String goods() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'goods' method...");
        }
        
        ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
        
        initParameters(request);
        // Page info ............
        request.setAttribute("page_title", "ISHOP Goods");
        request.setAttribute("feed_url", "feed.action");

//        request.setAttribute("categories", categoryManager.getTopCategoryList());
        String id = request.getParameter("id");
        id.replace("'", "\"");
        id.replace("=", "");
        String email = request.getParameter("email");
        String commentRank = request.getParameter("comment_rank");
        String content = request.getParameter("content");
        
        // keep id is not null
        ArrayList errors = new ArrayList();
        if (id == null || id.trim().length() == 0) {
            errors.add("id.missing");
            setActionMessages(errors);
            return Action.ERROR;
        }
        // keep there have goods in that id
//        Goods goods = goodsManager.getGoods(id);
//        if (goods == null) {
//            errors.add("goods.missing");
//            setActionMessages(errors);
//            return Action.ERROR;
//        }
//        request.setAttribute("goods", goods);
        

//        Brand brand = goods.getBrand();
//        if (brand != null) {
//            brand = brandManager.initialize(brand);
//            goods.setBrand(brand);
//        }
        
//        if((email != null) && (commentRank != null) && (content != null)){
//            if((email.length()!=0)&&(commentRank.length()!=0)&&(content.length()!=0)){
//            Comment comment = new Comment();
//            Date currentTime = new Date();
//            Timestamp nowTime = new Timestamp(currentTime.getTime());
//            comment.setAddTime(nowTime);
//            comment.setCommentRank(Integer.parseInt(commentRank));
//            comment.setEmail(email);
//            comment.setContent(content);
//            comment.setCommentType(0);
//            comment.setIdValue(Integer.parseInt(id));
//            comment.setIpAddress(request.getRemoteAddr());
//            comment.setStatus(false);
//            comment.setUserName("匿名用户");
//            commentManager.saveComment(comment);
//            request.setAttribute("createComment", "success");
//            }
//            else{
//                request.setAttribute("createComment", "false");
//            }
//        }
//        List<Comment> commentList = new ArrayList<Comment>();
//        commentList = commentManager.getList("from Comment t where t.commentType = 0 and t.idValue = "+id+" and t.status = true");
//        if(commentList.isEmpty()){
//            request.setAttribute("commentList", null);
//        }
//        else{
//            request.setAttribute("commentList", commentList);
//        }
//        List<Comment> replyList = new ArrayList<Comment>();
//        replyList = commentManager.getList("from Comment t where t.parent is NOT NULL");
//        if(replyList.isEmpty()){            
//            request.setAttribute("replyList", null);
//        }
//        else{           
//            request.setAttribute("replyList", replyList);
//        }
        
        
//        Set<Gallery> galleries = goods.getGalleries();
//        Set<Gallery> _galleries = new HashSet<Gallery>(galleries);
//        for (Iterator it = _galleries.iterator(); it.hasNext();) {
//            Gallery img = (Gallery) it.next();
//            img = galleryManager.initialize(img);
//            galleries.remove(img);
//            galleries.add(img);
//        }
        
        
//        cartInfoShow(request);
        
        // Tag
//        request.setAttribute("tags", tagManager.getList("from Tag t where t.goods.id="+id));
        
        return Action.SUCCESS;
    }
    
    protected void assignPager() {
        ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        Pager pager = new Pager();
        String display = request.getParameter("display");
        if (display != null) pager.setDisplay(display);
        pager.setPage(getPage());
        request.setAttribute("pager", pager);
    }
    
    int page = 0;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    int pageSize = 0;
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    int brand;
    public void setBrand(int brand) {
        this.brand = brand;
    }
    public int getBrand() {
        return this.brand;
    }
    int priceMax;
    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }
    public int getPriceMax() {
        return this.priceMax;
    }
    int priceMin;
    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }
    public int getPriceMin() {
        return this.priceMin;
    }
    String filterAttr = "";
    public void setFilterAttr(String fa) {
        this.filterAttr = fa;
    }
    public String getFilterAttr() {
        return this.filterAttr;
    }
    
    public String category() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'goods' method...");
        }
        
//        ActionContext ctx = ActionContext.getContext();        
//        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
//        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
//        
//        initParameters(request);
//        // Page info ............
//        request.setAttribute("page_title", "ISHOP Category");
//        request.setAttribute("feed_url", "feed.action");
//        request
//                .setAttribute("categories", categoryManager
//                        .getTopCategoryList());
//        request.setAttribute("bestSold", goodsManager.getBestSoldGoodsList());
//        request.setAttribute("hotSold", goodsManager.getHotSoldGoodsList());
//        
//        String id = request.getParameter("id");   
//        
//        request.setAttribute("id", id);
//        List<Goods> goods = new ArrayList<Goods>();
//        Criteria criteria = new Criteria();
//        Condition cond = new Condition();
//        cond.setField("mainCategory");
//        cond.setOperator(Condition.EQUALS);
//        cond.setValue(id);
//        criteria.addCondition(cond);
//        goods = goodsManager.getGoodsList(criteria);
//        request.setAttribute("goods_list", goods);
//        List<Goods> bestProducts = new ArrayList<Goods>();
//        for (Iterator it = goods.iterator(); it.hasNext();) {
//            Goods good = (Goods) it.next();
//            if (good.isBestSold()) {
//                bestProducts.add(good);
//            }
//        }
//        
//        request.setAttribute("category", id);
//        request.setAttribute("imageName", "goods_list");
//        request.setAttribute("bestProducts", bestProducts);
//        
//        //销量前十
//        List<OrderGoods> topList = new ArrayList<OrderGoods>();
//        topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
//        request.setAttribute("topList", topList);
//        //购物车信息
//        cartInfoShow(request);
//        
//        assignPager();
        // Filter Attribute
        
        return Action.SUCCESS;
    }



}
