/**
 * @structs.action path="/dispatch" scope="request"
 */

package com.jcommerce.web.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Gallery;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.User;
import com.jcommerce.core.payment.IPaymentMetaManager;
import com.jcommerce.core.service.ArticleManager;
import com.jcommerce.core.service.BrandManager;
import com.jcommerce.core.service.CartManager;
import com.jcommerce.core.service.CategoryManager;
import com.jcommerce.core.service.CommentManager;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.GalleryManager;
import com.jcommerce.core.service.GoodsManager;
import com.jcommerce.core.service.OrderGoodsManager;
import com.jcommerce.core.service.OrderManager;
import com.jcommerce.core.service.PaymentManager;
import com.jcommerce.core.service.RegionManager;
import com.jcommerce.core.service.SessionManager;
import com.jcommerce.core.service.ShippingManager;
import com.jcommerce.core.service.UserManager;
import com.jcommerce.core.util.SendMail;
import com.jcommerce.web.component.ComponentUrl;
import com.jcommerce.web.component.Navigator;

public class JspInitAction extends DispatchAction {
	private static Log log = LogFactory.getLog(JspInitAction.class);

	private CategoryManager categoryManager = null;
	private GalleryManager galleryManager = null;
	private GoodsManager goodsManager = null;
	private BrandManager brandManager = null;
	private CartManager cartManager = null;
	private SessionManager sessionManager = null;
	private RegionManager regionManager = null;
	private OrderManager orderManager = null;
	private OrderGoodsManager orderGoodsManager = null;
	private ShippingManager shippingManager = null;
	private PaymentManager paymentManager = null;
	private UserManager userManager = null;
	private ArticleManager articleManager = null;
	private CommentManager commentManager = null;
//    private ShippingManager shippingManager = null;
    private IPaymentMetaManager paymentMetaManager = null;
    
	public void setCommentManager(CommentManager commentManager) {
		this.commentManager = commentManager;
	}

	public void setArticleManager(ArticleManager articleManager) {
		this.articleManager = articleManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void setShippingManager(ShippingManager shippingManager) {
		this.shippingManager = shippingManager;
	}

	public void setPaymentManager(PaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}

	public void setOrderGoodsManager(OrderGoodsManager orderGoodsManager) {
		this.orderGoodsManager = orderGoodsManager;
	}

	public void setRegionManager(RegionManager regionManager) {
		this.regionManager = regionManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setGoodsManager(GoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setCartManager(CartManager cartManager) {
		this.cartManager = cartManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setGalleryManager(GalleryManager galleryManager) {
		this.galleryManager = galleryManager;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			return super.execute(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

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

	/**
	 * normal search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return search result
	 * @throws Exception
	 */
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'search' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String category = request.getParameter("category");
		String keyword = request.getParameter("keywords");
		Criteria criteria = new Criteria();
		if (!category.equals("0")) {
			Condition categoryCond = new Condition();
			categoryCond.setField("mainCategory");
			categoryCond.setOperator(Condition.EQUALS);
			categoryCond.setValue(category);
			criteria.addCondition(categoryCond);
		}
		Condition keywordCond = new Condition();
		keywordCond.setField("name");
		keywordCond.setOperator(Condition.LIKE);
		keywordCond.setValue(keyword);
		criteria.addCondition(keywordCond);
		List<Goods> searchGoods = new ArrayList<Goods>();
		searchGoods = goodsManager.getGoodsList(criteria);
		criteria.removeAllCondition();
		request.setAttribute("goodsInCondition", searchGoods);
		request.setAttribute("imageName", "search_result");
		//销量前十
		List<OrderGoods> topList = new ArrayList<OrderGoods>();
		topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
		request.setAttribute("topList", topList);
		//购物车信息
		cartInfoShow(request);
		return mapping.findForward("search");
	}

	/**
	 * advanced search 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return search result
	 * @throws Exception
	 */
	public ActionForward advanced_search(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'advanced_search' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		request.setAttribute("brands", brandManager.getBrandList());
		List<Goods> searchGoods = null;
		//销量前十
		List<OrderGoods> topList = new ArrayList<OrderGoods>();
		topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
		request.setAttribute("topList", topList);
		//购物车信息
		cartInfoShow(request);
		return mapping.findForward("advanced_search");
	}

	/**
	 * articles page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward article(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'article' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		request.setAttribute("brands", brandManager.getBrandList());
		String id = request.getParameter("id");
		Article article = articleManager.getArticle(id);
		request.setAttribute("article", article);
		return mapping.findForward("article");
	}
	
    protected void initParameters(HttpServletRequest request) {
        // Common messages......
        HashMap<String, String> lang = new HashMap<String, String>();
        lang.put("process_request", "Processing! Please wait...");
        lang.put("hello", "Hello");
        lang.put("welcome_return", "Welcome return");
        lang.put("user_logout", "Logout");
        lang.put("user_center", "User Center");
        lang.put("home", "Home");
        lang.put("hot_search", "Hot search");
        lang.put("all_category", "All categories");
        lang.put("advanced_search", "Advanced search");
        lang.put("shop_notice", "Shop notice:");
        lang.put("view_cart", "View cart");
        request.setAttribute("lang", lang);
        
        // Page info ............
        request.setAttribute("page_title", "ISHOP Home");
        request.setAttribute("feed_url", "feed.do");
        request.setAttribute("template_root", "front");
        
        // Navigator ............
        
        Navigator nav = new Navigator();
        nav.addTop(new ComponentUrl("cart.do", "Browse cart", true));
        nav.addTop(new ComponentUrl("user.do", "User center", true));
        nav.addTop(new ComponentUrl("pick_out.do", "Pick out center", true));
        nav.addTop(new ComponentUrl("group_by.do", "Buy by group", true));
        nav.addTop(new ComponentUrl("snatch.do", "Snatch", true));
        nav.addTop(new ComponentUrl("tag_cloud.do", "Tag cloud", true));

        nav.addMiddle(new ComponentUrl("home.do", "Home", true, true));
        nav.addMiddle(new ComponentUrl("Category1.do", "Category 1", true));
        nav.addMiddle(new ComponentUrl("Category1.do", "Category 2", true));
        nav.addMiddle(new ComponentUrl("Category1.do", "Category 3", true));
        nav.addMiddle(new ComponentUrl("Category1.do", "Category 4", true));
        request.setAttribute("navigator_list", nav);

        // Search key words ..........
        ArrayList searchKeywords = new ArrayList();
        request.setAttribute("searchkeywords", searchKeywords);
        request.setAttribute("search_keywords", "");
        
        List categorys = this.categoryManager.getCategoryList();
        request.setAttribute("category_list", categorys);
    }
    
	/**
	 * resolve action about index page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'home' method...");
		}
        
        initParameters(request);
        
        //Shop Notice...
        request.setAttribute("shop_notice", "Hot!! Hot!!");
        //Cart Info.....
        cartInfoShow(request);
        
        User user = new User();
        user.setName("Guest");
        
        request.getSession().setAttribute("user_info", user);
        
		request.setAttribute("categories", categoryManager.getTopCategoryList());
		request.setAttribute("bestSold", goodsManager.getBestSoldGoodsList());
		request.setAttribute("hotSold", goodsManager.getHotSoldGoodsList());
		request.setAttribute("newGoods", goodsManager.getNewGoodsList());
		List<Brand> brands = brandManager.getBrandList();
		
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField("brand");
		cond.setOperator(Condition.EQUALS);
		List<BrandInfo> brandInfoList = new ArrayList<BrandInfo>();
		for (Iterator it = brands.iterator(); it.hasNext();) {
			Brand brand = (Brand) it.next();
			cond.setValue(brand.getPkId() + "");
			criteria.addCondition(cond);
			brandInfoList.add(new BrandInfo(brandManager.getBrand(brand.getPkId()
					+ ""), goodsManager.getGoodsCount(criteria)));
			criteria.removeAllCondition();
		}		 
		 request.setAttribute("brandinfoList", brandInfoList);

		//top 10
		//		List<OrderGoods> orderGoods = new ArrayList<OrderGoods>();
		//		orderGoods = orderGoodsManager.getOrderGoodsList();
		//		String[] goodId = new String[10];
		//		int index = 0;
		//		for (Iterator it = orderGoods.iterator(); it.hasNext();) {
		//			OrderGoods orderGood = (OrderGoods)it.next();
		//			goodId[index] = orderGood.getGoods().getPkId()+"";
		//			index++;
		//		}
		//销量前十
		List<OrderGoods> topList = new ArrayList<OrderGoods>();
		topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
		request.setAttribute("topList", topList);

        return mapping.findForward("home");
	}

	/**
	 * resolve action about goods page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward goods(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'goods' method...");
		}
		request.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String id = request.getParameter("id");
		id.replace("'", "\"");
		id.replace("=", "");
		String email = request.getParameter("email");
		String commentRank = request.getParameter("comment_rank");
		String content = request.getParameter("content");
		
		// keep id is not null
		if (id == null || id.trim().length() == 0) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"id.missing"));
			saveErrors(request, errors);
			return new ActionForward("/dispatch.do?page=home");
		}
		// keep there have goods in that id
		Goods goods = goodsManager.getGoods(id);
		if (goods == null) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"goods.missing"));
			saveErrors(request, errors);
			return new ActionForward("/dispatch.do?page=home");
		}

		request.setAttribute("goods", goods);

		Brand brand = goods.getBrand();
		if (brand != null) {
			brand = brandManager.initialize(brand);
			goods.setBrand(brand);
		}
		
		if((email != null) && (commentRank != null) && (content != null)){
			if((email.length()!=0)&&(commentRank.length()!=0)&&(content.length()!=0)){
			Comment comment = new Comment();
			Date currentTime = new Date();
        	Timestamp nowTime = new Timestamp(currentTime.getTime());
			comment.setAddTime(nowTime);
			comment.setCommentRank(Integer.parseInt(commentRank));
			comment.setEmail(email);
			comment.setContent(content);
			comment.setCommentType(0);
			comment.setIdValue(Integer.parseInt(id));
			comment.setIpAddress(request.getRemoteAddr());
			comment.setStatus(false);
			comment.setUserName("匿名用户");
			commentManager.saveComment(comment);
			request.setAttribute("createComment", "success");
			}
			else{
				request.setAttribute("createComment", "false");
			}
		}
		List<Comment> commentList = new ArrayList<Comment>();
		commentList = commentManager.getList("from Comment t where t.commentType = 0 and t.idValue = "+id+" and t.status = true");
		if(commentList.isEmpty()){
			request.setAttribute("commentList", null);
		}
		else{
			request.setAttribute("commentList", commentList);
		}
		List<Comment> replyList = new ArrayList<Comment>();
		replyList = commentManager.getList("from Comment t where t.parent is NOT NULL");
		if(replyList.isEmpty()){			
			request.setAttribute("replyList", null);
		}
		else{			
			request.setAttribute("replyList", replyList);
		}
		Set<Gallery> galleries = goods.getGalleries();
		Set<Gallery> _galleries = new HashSet<Gallery>(galleries);
		for (Iterator it = _galleries.iterator(); it.hasNext();) {
			Gallery img = (Gallery) it.next();
			img = galleryManager.initialize(img);
			galleries.remove(img);
			galleries.add(img);
		}
		cartInfoShow(request);
		return mapping.findForward("goods");
	}

	/**
	 * resolve action about cart page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward cart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'cart' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String id = request.getParameter("id");
		Session session = getSession(request);
		String action = request.getParameter("action");
		if (id == null || id.trim().length() == 0) {
			if (action != null) {
				if (action.equals("clear")) {
					cartManager.removeCarts(session);
					return new ActionForward("/dispatch.do?page=home");
				} else {
					return new ActionForward("/dispatch.do?page=home");
				}
			}
			List<Cart> carts = cartManager.getCartList(session);
			request.setAttribute("carts", carts);
			return mapping.findForward("cart");
		}

		if (action != null) {
			if (action.equals("delete")) {
				cartManager.removeCart(id);
				List<Cart> carts = cartManager.getCartList(session);
				request.setAttribute("carts", carts);
				return mapping.findForward("cart");
			} else {
				System.out.println("Not sure action is: " + action);
				return new ActionForward("/dispatch.do?page=home");
			}
		}
		Goods goods = goodsManager.getGoods(id);
		if (goods == null) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"goods.missing"));
			saveErrors(request, errors);
			return new ActionForward("/dispatch.do?page=goods&id=" + id);
		}
		request.setAttribute("goods", goods);
		String snum = request.getParameter("number");
		int num = 1;
		if (snum != null) {
			try {
				num = Integer.parseInt(snum);
			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"invalid.number", snum));
				saveErrors(request, errors);
				return new ActionForward("/dispatch.do?page=goods&id=" + id);
			}
		}

		if (num <= 0) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"invalid.number", snum));
			saveErrors(request, errors);
			return new ActionForward("/dispatch.do?page=goods&id=" + id);
		}

		Cart cart = new Cart();
		cart.setGoods(goods);
		cart.setGift(goods.isAloneSale());
		cart.setRealGoods(goods.isRealGoods());
		cart.setMarketPrice(goods.getMarketPrice());
		cart.setGoodsNumber(num);
		cart.setGoodsName(goods.getName());
		cart.setGoodsSN(goods.getSN());
		cart.setGoodsPrice(goods.getShopPrice());
		cart.setSession(session);
		cartManager.saveCart(cart);
		List<Cart> carts = cartManager.getCartList(session);
		request.setAttribute("carts", carts);

		return mapping.findForward("cart");
	}

	/**
	 * resolve action about category part in every page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward category(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'category' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String id = request.getParameter("id");		
		request.setAttribute("id", id);
		List<Goods> goods = new ArrayList<Goods>();
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField("mainCategory");
		cond.setOperator(Condition.EQUALS);
		cond.setValue(id);
		criteria.addCondition(cond);
		goods = goodsManager.getGoodsList(criteria);
		request.setAttribute("goodsInCondition", goods);
		List<Goods> bestProducts = new ArrayList<Goods>();
		for (Iterator it = goods.iterator(); it.hasNext();) {
			Goods good = (Goods) it.next();
			if (good.isBestSold()) {
				bestProducts.add(good);
			}
		}
		request.setAttribute("imageName", "goods_list");
		request.setAttribute("bestProducts", bestProducts);
		//销量前十
		List<OrderGoods> topList = new ArrayList<OrderGoods>();
		topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
		request.setAttribute("topList", topList);
		//购物车信息
		cartInfoShow(request);
		return mapping.findForward("category");

	}

	/**
	 * check the users about login
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward cart_login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'cart_login' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());

		return mapping.findForward("cart_login");
	}

	/**
	 * Add the informations about consignee
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward cart_consignee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'cart_consignee' method...");
		}

		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String country = "1";// default country is China.
		request.setAttribute("provinces", regionManager.getChildList(country));
		if (province != null) {
			request.setAttribute("selectedProvince", province);
			request
					.setAttribute("cities", regionManager
							.getChildList(province));
		}
		if (city != null) {
			request.setAttribute("selectedCity", city);
			request.setAttribute("districts", regionManager.getChildList(city));
		}
		return mapping.findForward("cart_consignee");
	}

	/**
	 * check and set the informations about order
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward cart_checkout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'cart_checkout' method...");
		}
		request
				.setAttribute("categories", categoryManager.getTopCategoryList());

		String email = request.getParameter("email");
		request.getSession().setAttribute("email", email);
		request.setAttribute("email", email);

		String consignee = request.getParameter("consignee");
		request.getSession().setAttribute("consignee", consignee);
		request.setAttribute("consignee", consignee);

		String address = request.getParameter("address");
		request.getSession().setAttribute("address", address);
		request.setAttribute("address", address);

		String zipcode = request.getParameter("zipcode");
		request.getSession().setAttribute("zipcode", zipcode);
		request.setAttribute("zipcode", zipcode);

		String tel = request.getParameter("tel");
		request.getSession().setAttribute("tel", tel);
		request.setAttribute("tel", tel);

		String mobile = request.getParameter("mobile");
		request.getSession().setAttribute("mobile", mobile);
		request.setAttribute("mobile", mobile);

		String sign_building = request.getParameter("sign_building");
		request.getSession().setAttribute("sign_building", sign_building);
		request.setAttribute("sign_building", sign_building);

		String best_time = request.getParameter("best_time");
		request.getSession().setAttribute("best_time", best_time);
		request.setAttribute("best_time", best_time);

		String region = request.getParameter("city");
		request.getSession().setAttribute("region", region);

		Session session = getSession(request);
		List<Cart> carts = cartManager.getCartList(session);
		request.setAttribute("carts", carts);
		List<Shipping> shipping = shippingManager.getShippingList();
		request.setAttribute("shipping", shipping);
		List<Payment> payments = paymentManager.getPaymentList();
		request.setAttribute("payments", payments);
		return mapping.findForward("cart_checkout");
	}

	/**
	 * save the order informations into database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return address that the result will go
	 * @throws Exception
	 */
	public ActionForward cart_success(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'cart_success' method...");
		}
		Session session = getSession(request);
		if (session == null) {
			return new ActionForward("/dispatch.do?page=home");
		}
		Date currentTime = new Date();
		Timestamp nowTime = new Timestamp(currentTime.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());

		// collect the information
		String email = (String) request.getSession().getAttribute("email");
		String consignee = (String) request.getSession().getAttribute(
				"consignee");
		String address = (String) request.getSession().getAttribute("address");
		String zipcode = (String) request.getSession().getAttribute("zipcode");
		String tel = (String) request.getSession().getAttribute("tel");
		String mobile = (String) request.getSession().getAttribute("mobile");
		String sign_building = (String) request.getSession().getAttribute(
				"sign_building");
		String best_time = (String) request.getSession().getAttribute(
				"best_time");
		String region = (String) request.getSession().getAttribute("region");
		String shipping = request.getParameter("shipping");
		String payment = request.getParameter("payment");
		String postscript = request.getParameter("postscript");
		Order order = new Order();
		int rand = (int) (java.lang.Math.random() * 541);
		int rand2 = (int) (java.lang.Math.random() * 756);
		int saveID = (rand + rand2) * ((int) java.lang.Math.random() * 9 + 1);

		// insert into order form
		order.setSN(dateString + saveID);
		order.setAddress(address);
		order.setAddTime(nowTime);
		order.setBestTime(best_time);
		order.setConsignee(consignee);
		order.setEmail(email);
		order.setZip(zipcode);
		order.setPhone(tel);
		order.setCardFee(0);
		order.setPackFee(0);
		order.setShippingName(shippingManager.getShipping(shipping).getName());
		order.setPayName(paymentManager.getPayment(payment).getName());
		order.setMobile(mobile);
		order.setSignBuilding(sign_building);
		order.setBestTime(best_time);
		order.setReferer("本站添加");
		order.setRegion(regionManager.getRegion(region));
		order.setShipping(shippingManager.getShipping(shipping));
		order.setPayment(paymentManager.getPayment(payment));
		order.setPostScript(postscript);
		List<Cart> carts = cartManager.getCartList(session);
		double total = 0;// Initialization
		for (Iterator it = carts.iterator(); it.hasNext();) {
			Cart cart = (Cart) it.next();
			total += goodsManager.getGoods(cart.getGoods().getPkId() + "")
					.getShopPrice();// Statistics of the total amount
		}
		order.setGoodsAmount(total);

        // by liyong:   orderamount should be all of the money payable thru online payment. This field is passed to payment gateway such as alipay or chinabank
        // goodsamount + payfee + ? (more?)
        order.setOrderAmount(order.getGoodsAmount()+order.getPayFee());       

		orderManager.saveOrder(order);// save order
		Order ord = orderManager.getOrderBySN(dateString + saveID);
		OrderGoods orderGoods = new OrderGoods();			
		for (Iterator it = carts.iterator(); it.hasNext();) {
			Cart cart = (Cart) it.next();			
			orderGoods.setOrder(ord);
			//-----------------------------------------------------------
			orderGoods.setGoodsNumber(cart.getGoodsNumber());//need check
			//-----------------------------------------------------------
			orderGoods.setGoods(cart.getGoods());
			orderGoods.setGoodsName(cart.getGoodsName());
			orderGoods.setMarketPrice(cart.getMarketPrice());
			orderGoods.setGoodsPrice(cart.getGoodsPrice());
			orderGoods.setGoodsSN(cart.getGoodsSN());
			orderGoods.setGift(cart.isGift());
			orderGoods.setRealGoods(cart.isRealGoods());
			orderGoodsManager.saveOrderGoods(orderGoods);
		}
		// get some information to show
		//配送方式
		String shippingName = shippingManager.getShipping(shipping).getName();
		request.setAttribute("shippingName", shippingName);
		//付款方式
		String paymentName = paymentManager.getPayment(payment).getName();
		String paymentDescription = paymentManager.getPayment(payment).getDescription();		
		request.setAttribute("payment", payment);
		request.setAttribute("paymentName", paymentName);
		request.setAttribute("paymentDescription", paymentDescription);
		request.setAttribute("total", total);
		request.setAttribute("orderSN", dateString + saveID);
		cartManager.removeCarts(session);
        String paymentURL = paymentMetaManager.getCode(order.getPkId(), Integer.valueOf(payment));
        request.setAttribute("paymentURL", paymentURL);
		return mapping.findForward("cart_success");
	}

	/**
	 * register new users
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'register' method...");
		}
		request.setAttribute("categories", categoryManager.getTopCategoryList());

		return mapping.findForward("register");
	}

	/**
	 * save the new users' information
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward register_success(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'register_success' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		// get the users' information and save in database
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String MSN = request.getParameter("other[msn]");
		String QQ = request.getParameter("other[qq]");
		String office_phone = request.getParameter("other[office_phone]");
		String home_phone = request.getParameter("other[home_phone]");
		String mobile_phone = request.getParameter("other[mobile_phone]");
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setMSN(MSN);
		user.setQQ(QQ);
		user.setOfficePhone(office_phone);
		user.setHomePhone(home_phone);
		user.setMobilePhone(mobile_phone);
		userManager.saveUser(user);
		request.setAttribute("userName", name);

		return mapping.findForward("register_success");
	}

	/**
	 * User management center
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'user' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());

		return mapping.findForward("user");
	}

	public ActionForward get_password(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'get_password' method...");
		}
		request.setAttribute("categories", categoryManager.getTopCategoryList());		
		return mapping.findForward("get_password");
	}

	public ActionForward get_passwordPrompted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'get_passwordPrompted' method...");
		}
		request.setAttribute("categories", categoryManager.getTopCategoryList());
		String userName = request.getParameter("user_name");
		String email = request.getParameter("email");
		if(userName.length() != 0 && email.length() != 0){
			User user = userManager.getUserByName(userName);
			if(user != null && user.getEmail().equals(email)){
				Address addressTo[] = {new InternetAddress(email)};
				int pwdRandom1 = (int) (java.lang.Math.random() * 541);
				int pwdRandom2 = (int) (java.lang.Math.random() * 756);
				int pwdRandom = pwdRandom1 + pwdRandom2;
				if(new SendMail(addressTo,userName).sendChangePWMail(String.valueOf(pwdRandom))){
					user.setPassword(String.valueOf(pwdRandom));
					userManager.saveUser(user);
					request.setAttribute("getPWInfo", "密码重置信息已发送至您的邮箱请注意查收！");
				}
				else{
					request.setAttribute("getPWInfo", "发送邮件出错，请与管理员联系！");
				}
			}
			else{
				request.setAttribute("getPWInfo", "您的用户名或邮箱信息有误！");
			}
		}
		else{
			request.setAttribute("getPWInfo", "您的信息填写不完整！");
		}
		return mapping.findForward("get_passwordPrompted");
	}
	
	public ActionForward brand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'brand' method...");
		}
		request
				.setAttribute("categories", categoryManager
						.getTopCategoryList());
		String id = request.getParameter("id");
		Brand brand = brandManager.getBrand(id);
		String name = brand.getName();
		String url = brand.getSiteUrl();
		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.setAttribute("url", url);
		List<Goods> goods = new ArrayList<Goods>();
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField("brand");
		cond.setOperator(Condition.EQUALS);
		cond.setValue(id);
		criteria.addCondition(cond);
		goods = goodsManager.getGoodsList(criteria);
		request.setAttribute("goodsInCondition", goods);
		List<Goods> bestProducts =null;
		if(goods.iterator().hasNext()){
			bestProducts = new ArrayList<Goods>();
		for (Iterator it = goods.iterator(); it.hasNext();) {
			Goods good = (Goods) it.next();
			if (good.isBestSold()) {
				bestProducts.add(good);
			}
		}
		}
		request.setAttribute("imageName", "goods_list");
		request.setAttribute("bestProducts", bestProducts);
		//销量前十
		List<OrderGoods> topList = new ArrayList<OrderGoods>();
		topList = orderGoodsManager.getList("from OrderGoods t GROUP by t.goods ORDER by COUNT(t.goods) DESC limit 10");
		request.setAttribute("topList", topList);
		//购物车信息
		cartInfoShow(request);
		return mapping.findForward("brand");
	}

	/**
	 * get the session or new a session
	 * 
	 * @param request
	 * @return
	 */
	private Session getSession(HttpServletRequest request) {
		String sid = request.getSession(true).getPkId();
		Session sess = sessionManager.getSession(sid);
		if (sess == null) {
			sess = new Session();
			sess.setIP(request.getRemoteAddr());
			sess
					.setExpiry(new Timestamp(
							new Date().getTime() + 1000 * 60 * 60)); // expire after 1h
			sess.setId(sid);
			sessionManager.saveSession(sess);
		} else {
			// sess.setExpiry(new Timestamp(new Date().getTime() + 1000 * 60 *
			// 60)); // expire after 1h
		}
		return sess;
	}

	private void cartInfoShow(HttpServletRequest request){
		//购物车提要信息
		 Session session = getSession(request);
		 List<Cart> cartList = new ArrayList<Cart>();
		 cartList = cartManager.getCartList(session);
		 int number = 0;
		 double price = 0;
		 for (Iterator it = cartList.iterator(); it.hasNext();) {
			    Cart cart = (Cart) it.next();
				number+=cart.getGoodsNumber();
				price+=cart.getGoodsPrice();
			}
		 request.setAttribute("number", number);
		 request.setAttribute("price", price);
	}

    public IPaymentMetaManager getPaymentMetaManager() {
        return paymentMetaManager;
    }

    public void setPaymentMetaManager(IPaymentMetaManager paymentMetaManager) {
        this.paymentMetaManager = paymentMetaManager;
    }
}