package com.jcommerce.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.Application.ApplicationListener;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.panels.article.ArticleCatListPanel;
import com.jcommerce.gwt.client.panels.data.ExportPanel;
import com.jcommerce.gwt.client.panels.data.ImportPanel;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.panels.goods.AttributePanel;
import com.jcommerce.gwt.client.panels.goods.BrandListPanel;
import com.jcommerce.gwt.client.panels.goods.BrandPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryListPanel;
import com.jcommerce.gwt.client.panels.goods.CategoryPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsListPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsTypeListPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsTypePanel;
import com.jcommerce.gwt.client.panels.member.ShippingAddressPanel;
import com.jcommerce.gwt.client.panels.member.UserListPanel;
import com.jcommerce.gwt.client.panels.member.UserPanel;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;
import com.jcommerce.gwt.client.panels.order.OrderUserPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminListPanel;
import com.jcommerce.gwt.client.panels.privilege.AdminUserPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaListPanel;
import com.jcommerce.gwt.client.panels.system.PaymentMetaPanel;
import com.jcommerce.gwt.client.panels.system.RegionListPanel;
import com.jcommerce.gwt.client.panels.system.RegionPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingAreaPanel;
import com.jcommerce.gwt.client.panels.system.ShippingMetaListPanel;
import com.jcommerce.gwt.client.panels.system.ShippingTemplatePanel;
import com.jcommerce.gwt.client.panels.system.ShopConfigPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JCommerceGae implements EntryPoint, GWT.UncaughtExceptionHandler, ApplicationListener{
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
    
	public void onHistoryChanged(String fullHistoryToken) {
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
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String fullHistoryToken = event.getValue(); 
				onHistoryChanged(fullHistoryToken);
			}
		});

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
		RootPanel.getBodyElement().setAttribute("style", "Padding:0;margin:0;");
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
        setupMainMenuOption(catGoods, new GoodsListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new GoodsPanel.State(), Resources.images.catWidgets());   
        setupMainMenuOption(catGoods, new CategoryListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new BrandListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(catGoods, new GoodsTypeListPanel.State(), Resources.images.catWidgets());

		
		TreeItem catOrder = mainMenu.addItem(Resources.constants.categoryOrder());
		setupMainMenuOption(catOrder, new OrderListPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(catOrder, new OrderUserPanel.State(), Resources.images.catWidgets());
		
		TreeItem system = mainMenu.addItem(Resources.constants.categorySystem());
		setupMainMenuOption(system, new ShopConfigPanel.State(), Resources.images.catWidgets());
//		setupMainMenuOption(system, new RegionPanel.State(), Resources.images.catWidgets());
		setupMainMenuOption(system, new PaymentMetaListPanel.State(),Resources.images.catWidgets()); 
		setupMainMenuOption(system, new ShippingMetaListPanel.State(),Resources.images.catWidgets());   
        setupMainMenuOption(system, new RegionListPanel.State(), Resources.images.catWidgets());
        

        TreeItem user = mainMenu.addItem(Resources.constants.categoryUser());
        setupMainMenuOption(user, new UserListPanel.State(), Resources.images.catWidgets());
        setupMainMenuOption(user, new UserPanel.State(), Resources.images.catWidgets());
        

        TreeItem article = mainMenu.addItem(Resources.constants.categoryArticle());
        setupMainMenuOption(article, new ArticleCatListPanel.State(),Resources.images.catWidgets());
		
        TreeItem catData = mainMenu.addItem(Resources.constants.categoryData());
        setupMainMenuOption(catData, new ImportPanel.State(), Resources.images.catWidgets());        
        setupMainMenuOption(catData, new ExportPanel.State(), Resources.images.catWidgets());

        TreeItem privilege = mainMenu.addItem(Resources.constants.categoryPrivilege());      
        setupMainMenuOption(privilege, new AdminListPanel.State(), Resources.images.catWidgets());

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
    	com.google.gwt.user.client.ui.HorizontalPanel titlePanel = new com.google.gwt.user.client.ui.HorizontalPanel();
        titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        titlePanel.add(Resources.images.gwtLogo().createImage());          
        app.setTitleWidget(titlePanel);
    }

    
    /**
     * Create the navigation bar at the top of the application.
     * 
     */private void setupNavigationPanel(){
    	HorizontalPanel naviPanel = new HorizontalPanel();
    	//naviPanel.setHorizontalAlign(horizontalAlign)
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
    	userList.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
				PageState  state = UserListPanel.getInstance().getCurState();
				state.execute();
			}
    	});
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
            content.refresh();
//            System.out.println("app: "+app);
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
			} else if (pageClassName.equals(PaymentMetaListPanel.class.getName())) {
				page = PaymentMetaListPanel.getInstance();
			} else if (pageClassName.equals(PaymentMetaPanel.class.getName())) {
				page = PaymentMetaPanel.getInstance();

			}else if (pageClassName.equals(RegionListPanel.class.getName())) {
				page = RegionListPanel.getInstance(); 
					
			}else if (pageClassName.equals(BrandListPanel.class.getName())) {
				page = BrandListPanel.getInstance();
			} else if (pageClassName.equals(CategoryListPanel.class.getName())) {
				page = CategoryListPanel.getInstance();
			} else if (pageClassName.equals(CategoryPanel.class.getName())) {
					page = CategoryPanel.getInstance();
			} else if (pageClassName.equals(ImportPanel.class.getName())) {
				page = ImportPanel.getInstance();	
			} else if (pageClassName.equals(ExportPanel.class.getName())) {
				page = ExportPanel.getInstance();				

			} else if (pageClassName.equals(GoodsListPanel.class.getName())) {
				page = GoodsListPanel.getInstance();
			} else if (pageClassName.equals(BrandPanel.class.getName())) {
				page = BrandPanel.getInstance();				

			} else if (pageClassName.equals(Success.class.getName())) {
				page = Success.getInstance();


			} else if (pageClassName.equals(RegionPanel.class.getName())) {
				page = RegionPanel.getInstance();
			} else if (pageClassName.equals(OrderUserPanel.class.getName())) {
				page = OrderUserPanel.getInstance();
			} else if (pageClassName.equals(OrderListPanel.class.getName())) {
				page = OrderListPanel.getInstance();
			
			} else if (pageClassName.equals(ShippingMetaListPanel.class.getName())) {
				page = ShippingMetaListPanel.getInstance();
			} else if (pageClassName.equals(ShippingTemplatePanel.class.getName())) {
				page = ShippingTemplatePanel.getInstance();
			}
			else if(pageClassName.equals(ShippingAreaListPanel.class.getName())) {
				page = ShippingAreaListPanel.getInstance();
			}
			else if(pageClassName.equals(ShippingAreaPanel.class.getName())){
				page = ShippingAreaPanel.getInstance();
			}
			else if(pageClassName.equals(ShopConfigPanel.class.getName())){
				page = ShopConfigPanel.getInstance();
			}
			else if(pageClassName.equals(UserListPanel.class.getName())){
				page = UserListPanel.getInstance();
			}
			else if(pageClassName.equals(UserPanel.class.getName())) {
				page = UserPanel.getInstance();
			}
			else if(pageClassName.equals(ShippingAddressPanel.class.getName())) {
				page = ShippingAddressPanel.getInstance();
			}
			else if(pageClassName.equals(ArticleCatListPanel.class.getName())){
				page = ArticleCatListPanel.getInstance();
			}
			else if(pageClassName.equals(AdminListPanel.class.getName())){
				page = AdminListPanel.getInstance();
			}
			else if(pageClassName.equals(AdminUserPanel.class.getName())){
				page = AdminUserPanel.getInstance();
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
	
    //显示商品列表主页        
    public void displayGoodsList(){    	
    	GoodsListPanel goodsListPanel = (GoodsListPanel)getPage(GoodsListPanel.class.getName());
    	displayContentWidget(goodsListPanel);
    }

}
