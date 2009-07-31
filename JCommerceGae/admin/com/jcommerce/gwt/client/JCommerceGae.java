package com.jcommerce.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.Application.ApplicationListener;
import com.jcommerce.gwt.client.panels.AttributeListPanel;
import com.jcommerce.gwt.client.panels.AttributePanel;
import com.jcommerce.gwt.client.panels.BrandListPanel;
import com.jcommerce.gwt.client.panels.BrandPanel;
import com.jcommerce.gwt.client.panels.CategoryListPanel;
import com.jcommerce.gwt.client.panels.CategoryPanel;
import com.jcommerce.gwt.client.panels.GoodsListPanel;
import com.jcommerce.gwt.client.panels.GoodsTypeListPanel;
import com.jcommerce.gwt.client.panels.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.RegionPanel;
import com.jcommerce.gwt.client.panels.ShopConfigPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JCommerceGae implements EntryPoint, ValueChangeHandler<String>, GWT.UncaughtExceptionHandler, ApplicationListener{
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
//	private final GreetingServiceAsync greetingService = GWT
//			.create(GreetingService.class);
    private Application app = new Application();
    private static JCommerceGae me;
    /**
     * A mapping of history tokens to their associated menu items.
     */
    private Map<String, TreeItem> pageClassItems = new HashMap<String, TreeItem>();
    /**
     * A mapping of menu items to the widget display when the item is selected.
     */
   
    private Map<TreeItem, PageState> itemStates = new HashMap<TreeItem, PageState>();
  
    public Map<String, ContentWidget> pageRegistry = new HashMap<String, ContentWidget>();
    
    public static JCommerceGae getInstance() {
    	return me;
    }
    
	public void onValueChange(ValueChangeEvent<String> event) {
		String fullHistoryToken = event.getValue(); 
		// TODO Auto-generated method stub
		try {
			System.out.println("--- hyperLinkHandler");
			String[] parsed = PageState
					.parseFullHistoryToken(fullHistoryToken);
			String pageClassName = parsed[0];
			String stateStr = parsed[1];
			System.out.println("pageClassStr: " + pageClassName
					+ ", stateStr: " + stateStr);

			TreeItem item = pageClassItems.get(pageClassName);
			System.out.println("item: " + item);
			if (item != null) {
				app.getMainMenu().setSelectedItem(item, false);
				app.getMainMenu().ensureSelectedItemVisible();
			}

			ContentWidget page = getPage(pageClassName);

			if (page != null) {
				PageState state = page.getCurState();
				if (state != null) {
					state.fromHistoryToken(stateStr);
				}
				displayContentWidget(page);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void onUncaughtException(Throwable e) {
		Window.alert("error: " + e.getMessage());
	}
	public void onMenuItemSelected(TreeItem item) {
		PageState state = itemStates.get(item);
		// && !content.equals(app.getContent())
		if (state != null) {
			state.execute();
		}
	}
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		me = this;
		GWT.setUncaughtExceptionHandler(this);
		History.addValueChangeHandler(this);

		RemoteService.init();

		// Generate the source code and css for the examples
		// GWT.create(GeneratorInfo.class);

		// Swap out the style sheets for the RTL versions if needed.
		// updateStyleSheets();

		// Create the application
		setupTitlePanel();
		setupMainLinks();
		setupOptionsPanel();
		setupMainMenu();
		setupNavigationPanel();
		app.setListener(this);
		
		// need add app first, to ensure the other widgets to be rendered when
		// added to app
		RootPanel.get().add(app);

		String initToken = History.getToken();
		if (initToken.length() > 0) {
			// historyListener.onHistoryChanged(initToken);
			History.newItem(initToken);
		} else {
			// Use the first token available
			TreeItem firstItem = app.getMainMenu().getItem(0).getChild(0);
			PageState state = itemStates.get(firstItem);
			if (state != null)
				state.execute();
		}
		
	}
	
    /**
     * Create the main links at the top of the application.
     * 
     * @param constants
     *            the constants with text
     */
    private void setupMainLinks() {
        // Link to GWT Homepage
   	
    	app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkGuide() + "</a>"));		
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkNotepad() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkRefresh() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkPersonalSetting() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkComment() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkView() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkCalc() + "</a>"));
		app.addLink(new HTML("<a href=\"\" style=\"color: #ffffff;TEXT-DECORATION:none;\">"+ Resources.constants.mainLinkAbout() + "</a>"));

    }

    /**
     * Setup all of the options in the main menu.
     * 
     * @param constants
     *            the constant values to use
     */
    private void setupMainMenu() {
        Tree mainMenu = app.getMainMenu();      
        
        TreeItem catGoods = mainMenu.addItem(Resources.constants.categoryGoods());
		setupMainMenuOption(catGoods, new GoodsPanel.State(), Resources.images.catWidgets());      
		setupMainMenuOption(catGoods, new GoodsListPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catGoods, new GoodsTypeListPanel.State(), Resources.images.catWidgets());
      	setupMainMenuOption(catGoods, new BrandListPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catGoods, new CategoryListPanel.State(), Resources.images.catWidgets());
		
		TreeItem system = mainMenu.addItem(Resources.constants.categorySystem());
		setupMainMenuOption(system, new ShopConfigPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(system, new RegionPanel.State(), Resources.images.catWidgets());
		
//		setupMainMenuOption(catGoods, new UserComments.State(), Resources.images.catWidgets());		
//
//		setupMainMenuOption(catGoods,new GoodsTypeInfo.State(),Resources.images.catWidgets());
//        
//		TreeItem orderForm = mainMenu.addItem(Resources.constants.categoryOrder());		
//		setupMainMenuOption(orderForm, new OrderList.State(), Resources.images.catWidgets());
//		setupMainMenuOption(orderForm, new NewOrders.State(), Resources.images.catWidgets());
//        
//		TreeItem statisticsForm = mainMenu.addItem(Resources.constants.categoryStatistics());
//		setupMainMenuOption(statisticsForm,new OrderStatistics.State(),Resources.images.catWidgets());
//        
//		TreeItem user = mainMenu.addItem(Resources.constants.categoryMember());
//		setupMainMenuOption(user, new NewUsers.State(), Resources.images.catWidgets());
//        
//		TreeItem system = mainMenu.addItem(Resources.constants.categorySystem());
//		setupMainMenuOption(system,new ShopSetup.State(),Resources.images.catWidgets());
//		setupMainMenuOption(system, new PaymentMetaList.State(),Resources.images.catWidgets());        
        
        
        
//        TreeItem catGoods = mainMenu.addItem(Resources.constants.categoryGoods());
//        setupMainMenuOption(catGoods, new GoodsList(), Resources.images.catWidgets());
//        setupMainMenuOption(catGoods, new BrandInfo(), Resources.images.catWidgets());
//		setupMainMenuOption(catGoods, goodsPanel = new NewGoods(), Resources.images.catWidgets());
//		setupMainMenuOption(catGoods, new CategoryInfo(), Resources.images.catWidgets());
//		setupMainMenuOption(catGoods, new UserComments(), Resources.images.catWidgets());		
//		setupMainMenuOption(catGoods, GoodsTypeList.getInstance(), Resources.images.catWidgets());
//		setupMainMenuOption(catGoods,new GoodsTypeInfo(),Resources.images.catWidgets());
		

        
//		TreeItem orderForm = mainMenu.addItem(Resources.constants.categoryOrder());		
//		setupMainMenuOption(orderForm, new OrderList(), Resources.images.catWidgets());
//		setupMainMenuOption(orderForm, new NewOrders(), Resources.images.catWidgets());
//		TreeItem statisticsForm = mainMenu.addItem(Resources.constants.categoryStatistics());
//		setupMainMenuOption(statisticsForm,new OrderStatistics(),Resources.images.catWidgets());
//		TreeItem user = mainMenu.addItem(Resources.constants.categoryMember());
//		setupMainMenuOption(user, new NewUsers(), Resources.images.catWidgets());
//		TreeItem system = mainMenu.addItem(Resources.constants.categorySystem());
//		setupMainMenuOption(system,new ShopSetup(),Resources.images.catWidgets());
//      setupMainMenuOption(system,PaymentMetaList.getInstance(),Resources.images.catWidgets());
    }

    /**
     * Add an option to the main menu.
     * 
     * @param parent
     *            the {@link TreeItem} that is the option
     * @param content
     *            the {@link ContentWidget} to display when selected
     * @param image
     *            the icon to display next to the {@link TreeItem}
     */
//    private void setupMainMenuOption(TreeItem parent, ContentWidget content,
//            AbstractImagePrototype image) {
//        // Create the TreeItem
//        TreeItem option = parent.addItem(image.getHTML() + " "
//                + content.getName());
//
//        // Map the item to its history token and content widget
//        itemWidgets.put(option, content);
//        itemTokens.put(getContentWidgetToken(content), option);
//    }

    private void setupMainMenuOption(TreeItem parent, PageState state,
            AbstractImagePrototype image) {
        // Create the TreeItem
        TreeItem option = parent.addItem(image.getHTML() + " "
                + state.getMenuDisplayName());

        // Map the item to its history token and content widget
        itemStates.put(option, state);
        pageClassItems.put(state.getPageClassName(), option);
    }
    
    /**
     * Create the options that appear next to the title.
     */
    private void setupOptionsPanel() {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        if (LocaleInfo.getCurrentLocale().isRTL()) {
          vPanel.getElement().setAttribute("align", "left");
        } else {
          vPanel.getElement().setAttribute("align", "right");
        }
        app.setOptionsWidget(vPanel);

        // Add the option to change the locale
        final Button exitButton = new Button();       
        final Button clearButton = new Button();
        HorizontalPanel localeWrapper = new HorizontalPanel();  
        clearButton.setText(Resources.constants.mainCommandClearCache());
        clearButton.addStyleName("cl_button");
        exitButton.setText(Resources.constants.mainCommandExit());
        exitButton.addStyleName("ex_button");        
        localeWrapper.add(clearButton);
        localeWrapper.add(exitButton);
        vPanel.add(localeWrapper);
       
      }

    /**
     * Create the title bar at the top of the application.
     * 
     */
    private void setupTitlePanel() {         

        // Add the title and some images to the title bar
        HorizontalPanel titlePanel = new HorizontalPanel();
        titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        titlePanel.add(Resources.images.gwtLogo().createImage());          
        app.setTitleWidget(titlePanel);
    }

    
    /**
     * Create the navigation bar at the top of the application.
     * 
     */private void setupNavigationPanel(){
    	HorizontalPanel naviPanel = new HorizontalPanel();
    	naviPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT); 
    	app.setNavigationWidget(naviPanel);
    	final Button home = new Button();    	
    	home.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				
			}
    		
    	});
    	final Button setnavi = new Button();
    	final Button goodslist = new Button();
    	final Button orderlist = new Button();
    	final Button Comments = new Button();
    	final Button userList = new Button();
    	home.setText(Resources.constants.navHome());
    	setnavi.setText(Resources.constants.navSetting());
    	goodslist.setText(Resources.constants.navGoodsList());
    	orderlist.setText(Resources.constants.navOrderList());
    	Comments.setText(Resources.constants.navComment());
    	userList.setText(Resources.constants.navMemberList());
    	home.addStyleName("Nav_button");
    	setnavi.addStyleName("Nav_button");
    	goodslist.addStyleName("Nav_button");
    	orderlist.addStyleName("Nav_button");
    	Comments.addStyleName("Nav_button");
    	userList.addStyleName("Nav_button");
    	naviPanel.add(home);
    	naviPanel.add(setnavi);
    	naviPanel.add(goodslist);
    	naviPanel.add(orderlist);
    	naviPanel.add(Comments);
    	naviPanel.add(userList);    	
    }
    /**
     * Set the content to the {@link ContentWidget}.
     * 
     * @param content
     *            the {@link ContentWidget} to display
     */
    private void displayContentWidget(ContentWidget content) {
        if (content != null) {
        	app.setContent(content);
            app.setContentTitleLeft(Resources.constants.mainTitle()+"-"+content.getName());
            app.setContentTitleRight(content.getShortCutButton());
            content.refresh();
        }        
    }
	// ensure all instances be created and put in pageRegistry when first-time
	// accessed
	// and all following access will retrieve the cached instance in
	// pageRegistry
	public ContentWidget getPage(String pageClassName) {
		ContentWidget page = null;
		page = pageRegistry.get(pageClassName);
		if (page == null) {
			
			if(false) {
			// TODO use GWT.create to allow plugin of different implementation?
			// what's the condition?
			// TODO have a generator to generate these codes based on all page
			// classes we have in project
			} else if (pageClassName.equals(GoodsPanel.class.getName())) {
				// page = (ContentWidget)GWT.create(GoodsTypeList.class);
				page = GoodsPanel.getInstance();
			} else if (pageClassName.equals(AttributePanel.class.getName())) {
				page = AttributePanel.getInstance();
			} else if (pageClassName.equals(AttributeListPanel.class.getName())) {
				page = AttributeListPanel.getInstance();
			} else if (pageClassName.equals(GoodsTypePanel.class.getName())) {
				page = new GoodsTypePanel();				
			} else if (pageClassName.equals(GoodsTypeListPanel.class.getName())) {
				page = GoodsTypeListPanel.getInstance();
//			} else if (pageClassName.equals(PaymentMetaList.class.getName())) {
//				page = PaymentMetaList.getInstance();
//			} else if (pageClassName.equals(PaymentMetaPanel.class.getName())) {
//				page = PaymentMetaPanel.getInstance();
//			} else if (pageClassName.equals(AttributeInfo.class.getName())) {
//				page = new AttributeInfo();
//			}
//
			}else if (pageClassName.equals(BrandListPanel.class.getName())) {
				page = BrandListPanel.getInstance();
			} else if (pageClassName.equals(CategoryListPanel.class.getName())) {
				page = CategoryListPanel.getInstance();
			} else if (pageClassName.equals(CategoryPanel.class.getName())) {
					page = CategoryPanel.getInstance();
				
//			} else if (pageClassName.equals(CommentInfo.class.getName())) {
//				page = new CommentInfo();
			} else if (pageClassName.equals(GoodsListPanel.class.getName())) {
				page = GoodsListPanel.getInstance();
			} else if (pageClassName.equals(BrandPanel.class.getName())) {
				page = BrandPanel.getInstance();				
//			} else if (pageClassName.equals(GoodsTypeInfo.class.getName())) {
//				page = new GoodsTypeInfo();
//			} else if (pageClassName.equals(NewAttribute.class.getName())) {
//				page = new NewAttribute();

//
//			} else if (pageClassName.equals(NewGoodsType.class.getName())) {
//				page = new NewGoodsType();
//			} else if (pageClassName.equals(NewOrders.class.getName())) {
//				page = new NewOrders();
//			} else if (pageClassName.equals(NewUsers.class.getName())) {
//				page = new NewUsers();
//			} else if (pageClassName.equals(OrderInfo.class.getName())) {
//				page = new OrderInfo();
//			} else if (pageClassName.equals(OrderList.class.getName())) {
//				page = new OrderList();
//			} else if (pageClassName.equals(OrderStatistics.class.getName())) {
//				page = new OrderStatistics();
//			} else if (pageClassName.equals(ShopSetup.class.getName())) {
//				page = new ShopSetup();
			} else if (pageClassName.equals(Success.class.getName())) {
				page = Success.getInstance();
//			} else if (pageClassName.equals(UserComments.class.getName())) {
//				page = new UserComments();
			} else if (pageClassName.equals(ShopConfigPanel.class.getName())) {
				page = ShopConfigPanel.getInstance();
			} else if (pageClassName.equals(RegionPanel.class.getName())) {
				page = RegionPanel.getInstance();
			}

			if (page != null) {
				pageRegistry.put(pageClassName, page);
			} else {
				throw new RuntimeException(
						"cannot find page: "
								+ pageClassName
								+ ", a page has to be first registered in pageRegistry before being used ");
			}
		}

		return page;
	}
	
    //显示新建品牌页
//    public void displayNewBrand(){ 
//    	BrandPanel brandPanel = (BrandPanel)getPage(BrandPanel.class.getName());
//    	brandPanel.setBrand(null);
//    	displayContentWidget(brandPanel);
//    }
    
    //显示修改品牌页
//    public void displayModifyBrand(BeanObject brand){    	
//    	BrandPanel brandPanel = (BrandPanel)getPage(BrandPanel.class.getName());
//    	brandPanel.setBrand(brand);
//    	displayContentWidget(brandPanel);    	
//    }
    //显示品牌列表主页
//    public void displayBrandInfo(){    	
//    	BrandListPanel brandListPanel = (BrandListPanel)getPage(BrandListPanel.class.getName());
//    	displayContentWidget(brandListPanel);
//    	brandListPanel.refresh();
//    }
    
    //显示商品列表主页        
    public void displayGoodsList(){    	
    	GoodsListPanel goodsListPanel = (GoodsListPanel)getPage(GoodsListPanel.class.getName());
    	displayContentWidget(goodsListPanel);
    }
    // 显示新建货物或修改货物页
//    public void displayGoodsPanel(BeanObject goods) {
//    	NewGoods newGoods = (NewGoods)getPage(NewGoods.class.getName());
//    	newGoods.setGoods(goods);
//        displayContentWidget(newGoods);
//    }
    
    //显示修改商品分类页
//    public void displayModifyCategory(BeanObject category){    	
//    	NewCategory newCategory = (NewCategory)getPage(NewCategory.class.getName());
//    	newCategory.setCategory(category);
//    	displayContentWidget(newCategory);    	
//    }
    //显示商品分类列表主页
//    public void displayCategoryInfo(){    	
//    	CategoryListPanel categoryListPanel = (CategoryListPanel)getPage(CategoryListPanel.class.getName());
//    	displayContentWidget(categoryListPanel);
//    	categoryListPanel.refresh();
//    }
    //显示新建商品分类页
//    public void displayNewCategory(){
//    	NewCategory newCategory = (NewCategory)getPage(NewCategory.class.getName());
//    	newCategory.setCategory(null);
//    	displayContentWidget(newCategory);
//    }
}
