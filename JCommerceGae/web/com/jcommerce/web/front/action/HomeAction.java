package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.web.component.ComponentUrl;
import com.jcommerce.web.component.Navigator;
import com.jcommerce.web.front.action.helper.Pager;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
    private static Log log = LogFactory.getLog(HomeAction.class);

    private IDefaultManager defaultManager;
    
    

    //内部类 实现商品品牌的货物数量统计
    public class BrandInfo {
        private Brand brand = new Brand();
        private int number = 0;

        public BrandInfo(Brand brand, int goodsCount) {
            this.brand = brand;
            this.number = goodsCount;
        }

        public BrandInfo() {
        }

        public Brand getBrand() {
            return brand;
        }

        public int getNumber() {
            return number;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
    
    protected void initConfig(HttpServletRequest request) {
        request.setAttribute("show_marketprice", true);
    }
    
    protected void initPager(HttpServletRequest request) {
        Pager pager = new Pager();
        request.setAttribute("pager", pager);
    }

    @Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
    
    protected void initParameters(HttpServletRequest request) {
        initConfig(request);
        initPager(request);
        request.setAttribute("template_root", "front");
        
        request.setAttribute("now_time", new Date().toString());
        // Navigator ............
        
        Navigator nav = new Navigator();
        nav.addTop(new ComponentUrl("cart.action", getText("browse_cart"), true));
        nav.addTop(new ComponentUrl("user.action", getText("user_center"), true));
        nav.addTop(new ComponentUrl("pick_out.action", getText("pick_out_center"), true));
        nav.addTop(new ComponentUrl("group_by.action", getText("buy_by_group"), true));
        nav.addTop(new ComponentUrl("snatch.action", getText("snatch"), true));
        nav.addTop(new ComponentUrl("tag_cloud.action", getText("tag_cloud"), true));
        
        

//        nav.addMiddle(new ComponentUrl("home.action", getText("home_title"), true, true));
        nav.addMiddle(new ComponentUrl("home.action", "TODOtitle", true, true));
        List<Category> categoryList = defaultManager.getList(ModelNames.CATEGORY, null);
        Iterator<Category> it = categoryList.iterator();
        while (it.hasNext()) {
            Category cat = it.next();
            if (cat.isShowInNavigator())
                nav.addMiddle(new ComponentUrl("category.action?id="+cat.getId(), cat.getName(), true, false));
        }

        request.setAttribute("navigator_list", nav);

        // Search key words ..........
        ArrayList searchKeywords = new ArrayList();
        request.setAttribute("searchkeywords", searchKeywords);
        request.setAttribute("search_keywords", "");
//        
//        List categorys = this.categoryManager.getCategoryList();
        
        request.setAttribute("category_list", categoryList);
//        request.setAttribute("category_list", categorys);
//        
        request.setAttribute("copyright", "Copyright");
        request.setAttribute("shop_address", "Shop Address");
        request.setAttribute("shop_postcode", "Postcode:1000000");
        request.setAttribute("copryright", "Copyright");
    }

    private Session getSession(HttpServletRequest request) {
//        String sid = request.getSession(true).getId();
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

    @Override
    public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'home' method...");
        }
        
        
        ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
        
        initParameters(request);
        // Page info ............
        request.setAttribute("page_title", "ISHOP Home");
        request.setAttribute("feed_url", "feed.action");
        
        //Shop Notice...
        request.setAttribute("shop_notice", "Hot!! Hot!!");
        //Cart Info.....
        cartInfoShow(request);
        
        User user = new User();
        user.setName("Guest");
        
        request.getSession().setAttribute("user_info", user);
        
        List<Category> categories = (List<Category>)defaultManager.getList(ModelNames.CATEGORY, null);
        categories.size();
        request.setAttribute("categories", categories);
        
//        request.setAttribute("bestSold", goodsManager.getBestSoldGoodsList());
//        request.setAttribute("hotSold", goodsManager.getHotSoldGoodsList());
//        request.setAttribute("newGoods", goodsManager.getNewGoodsList());
        
        
        List<Brand> brands = (List<Brand>)defaultManager.getList(ModelNames.BRAND, null);
        
        Criteria criteria = new Criteria();
        Condition cond = new Condition();
        cond.setField(IGoods.BRANDID);
        cond.setOperator(Condition.EQUALS);
        List<BrandInfo> brandInfoList = new ArrayList<BrandInfo>();
        for (Brand brand:brands) {
            cond.setValue(brand.getId());
            criteria.addCondition(cond);
            brandInfoList.add(new BrandInfo(brand, defaultManager.getCount(ModelNames.GOODS, criteria)));
            criteria.removeAllCondition();
        }        
         request.setAttribute("brandinfoList", brandInfoList);
        
//        List<Brand> brandInfoList = (List<Brand>)defaultManager.getList(ModelNames.BRAND, null);
//        brandInfoList.size();
//        request.setAttribute("brandinfoList", brandInfoList);

        //top 10
        //      List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();
        //      orderGoods = orderGoodsManager.getOrderGoodsList();
        //      String[] goodId = new String[10];
        //      int index = 0;
        //      for (Iterator it = orderGoods.iterator(); it.hasNext();) {
        //          OrderGoods orderGood = (OrderGoods)it.next();
        //          goodId[index] = orderGood.getGoods().getId()+"";
        //          index++;
        //      }
         

        List<OrderGoods> topList = new ArrayList<OrderGoods>();
//        topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
        request.setAttribute("topList", topList);
        
        // new article
        
//        request.setAttribute("new_articles", articleManager.getArticleList());
        request.setAttribute("new_articles", new ArrayList());
        return  Action.SUCCESS;
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

	public IDefaultManager getDefaultManager() {
		return defaultManager;
	}

	public void setDefaultManager(IDefaultManager defaultManager) {
		this.defaultManager = defaultManager;
	}


}
