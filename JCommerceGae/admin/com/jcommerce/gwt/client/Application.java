package com.jcommerce.gwt.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;



/**
 * <p>
 * A generic application that includes a title bar, main menu, content area, and
 * some external links at the top.
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li>.Application { Applied to the entire Application }</li>
 * <li>.Application-top { The top portion of the Application }</li>
 * <li>.Application-title { The title widget }</li>
 * <li>.Application-links { The main external links }</li>
 * <li>.Application-options { The options widget }</li>
 * <li>.Application-menu { The main menu }</li>
 * <li>.Application-content-wrapper { The scrollable element around the content }</li>
 * </ul>
 */
public class Application extends Composite implements WindowResizeListener {
  /**
   * Images used in the {@link Application}.
   */
  public interface ApplicationImages extends TreeImages {
    /**
     * 
     */
    @Resource("com/jcommerce/gwt/client/resources/noimage.png")
    AbstractImagePrototype treeLeaf();
  }

  /**
   * A listener to handle events from the Application.
   */
  public interface ApplicationListener {
    /**
     * Fired when a menu item is selected.
     * 
     * @param item the item that was selected
     */
    void onMenuItemSelected(com.google.gwt.user.client.ui.TreeItem item);
  }

  /**
   * The base style name.
   */
  public static final String DEFAULT_STYLE_NAME = "Application";

  /**
   * The panel that contains the menu and content.
   */
  private HorizontalPanel bottomPanel;

  /**
   * The decorator around the content.
   */
//  private DecoratorPanel contentDecorator;

  /**
   * The main wrapper around the content and content title.
   */
  private SimplePanel contentLayout;
  
  /**
   * The main wrapper around the content and content title.
   */
//  private LayoutContainer contentTitleLayout;

  /**
   * The wrapper around the content.
   */
  private LayoutContainer contentWrapper;
    
  /**
   * The wrapper around the content title.
   */
  //private HTML contentTitleLeftWrapper;
  /**
   * The wrapper around the content title.
   */
 // private SimplePanel contentTitleRightWrapper;
  
  /**
   * The panel that holds the main links.
   */
  private VerticalPanel linksPanel;
  private HorizontalPanel links;
  
  /**
   * The panel that holds the GuidePanel.
   */
  private HorizontalPanel navigationPanel;

  /**
   * The {@link ApplicationListener}.
   */
  private ApplicationListener listener = null;

  /**
   * The main menu.
   */
  private Tree mainMenu;
  private LayoutContainer leftContainer = new LayoutContainer();

  /**
   * The last known width of the window.
   */
  private int windowWidth = -1;

  /**
   * The panel that contains the title widget and links.
   */
  private LayoutContainer topPanel;

  /**
   * Constructor.
   */
  public Application() {
    // Setup the main layout widget
	Viewport mainContainer = new Viewport();
	mainContainer.setHeight("100%");
    initWidget(mainContainer);
    BorderLayout bl = new BorderLayout();
    mainContainer.setLayout(bl);

    // Setup the top panel with the title and links
    createTopPanel();
    topPanel.setBorders(true);
    mainContainer.add(topPanel,new BorderLayoutData(LayoutRegion.NORTH,120));

    // Add the main menu
    leftContainer.setLayout(new FitLayout());
    leftContainer.setScrollMode(Scroll.AUTOY);
    leftContainer.setStyleAttribute("border", "4px solid #d0e4f6");
    createMainMenu();
    leftContainer.add(mainMenu);
    mainContainer.add(leftContainer,new BorderLayoutData(LayoutRegion.WEST));

    // Setup the content layout
    contentWrapper = new LayoutContainer(new FitLayout());
    contentWrapper.setScrollMode(Scroll.AUTOY);
    contentWrapper.setStyleName(DEFAULT_STYLE_NAME+"-content");
  
    contentLayout  = new SimplePanel();
    contentWrapper.add(contentLayout);

    mainContainer.add(contentWrapper,new BorderLayoutData(LayoutRegion.CENTER));

    setContent(null);
  }

  /**
   * Add a link to the top of the page.
   * 
   * @param link the widget to add to the mainLinks
   */
  public void addLink(Widget link) {
    if (links.getWidgetCount() > 0) {
    	HTML seperaor = new HTML("|");
      links.add(seperaor);
    }
    links.add(link);
  }

  /**
   * @return the {@link Widget} in the content area
   */
//  public Widget getContent() {
//    return contentWrapper.getWidget();
//  }

  /**
   * @return the content title widget
   */


  /**
   * @return the main menu.
   */
  public Tree getMainMenu() {
    return mainMenu;
  }

  
  public static class MyTabPanel extends TabPanel {
	  @Override
	  protected void onRender(Element target, int index) {
		  super.onRender(target, index);
		  getLayoutTarget().setStyleAttribute("Width", "100%");
		  
		  System.out.println("this: "+this.toString());
	  }
  }
  /**
   * @return the {@link Widget} used as the title
   */
  
  public void onWindowResized(int width, int height) {
    if (width == windowWidth) {
      return;
    }
    windowWidth = width;
    onWindowResizedImpl(width);
  }
//  TabPanel tabs = new MyTabPanel();
//  FormPanel formPanel = new FormPanel();
  /**
   * Set the {@link Widget} to display in the content area.
   * 
   * @param content the content widget
   */
  public void setContent(Widget content) {
    if (content == null) {
      contentLayout.setWidget(new HTML("&nbsp;"));
    } else {
      contentLayout.setWidget(content);
    }

	  
//	  ContentPanel contentPanel = new ContentPanel();
//	  contentPanel.setHeaderVisible(false);
//	  contentPanel.setLayout(new TableLayout(1));
//	  
//	  TabItem tab1 = new TabItem();
//	  tab1.setText("tab1");
//	  tab1.setLayout(new FormLayout());
//	  tabs.add(tab1);
////	  tabs.setWidth("auto");
////	  tabs.setTabMargin(800);
////	  tabs.setWidth(800);
////	  tabs.setAutoWidth(true);
////	  VerticalPanel tabs = new VerticalPanel();
//	  
//	  for(int i=0;i<20;i++) {
//		  TextField<String> tf = new TextField<String>();
//		  tf.setFieldLabel("field "+i);
//		  if(i==4) {
//			  tf.setWidth(400);
//			  FormData fd1 = new FormData();
//			  fd1.setWidth(400);
//			  tab1.add(tf, fd1);
//		  }
//		  else if(i==6) {
//			  tf.setWidth(600);
//			  FormData fd1 = new FormData();
//			  fd1.setWidth(600);
//			  tab1.add(tf, fd1);			  
//		  }
//		  else {
//			  tab1.add(tf);
//		  }
////		  formPanel.add(tf);
////		  tabs.add(tf);
//	  }
//
//	  tabs.setBodyBorder(false);
////	  formPanel.add(tabs, new FormData("100%"));
//	  formPanel.add(tabs);
//
//	  TableData td1 = new TableData();
//	  td1.setWidth("100%");
//	  contentPanel.add(formPanel, td1);
//	  Button btnNew = new Button();  
//	  btnNew.setText(Resources.constants.ok());        
//	  formPanel.addButton(btnNew);
//	  
//	  System.out.println("TabPanel: "+tabs.toString());
//	  
//	  contentWrapper.setWidget(contentPanel);
//	  
//	  System.out.println("formPanel: "+formPanel+"</formPanel>");
//	  System.out.println("bottomPanel: "+bottomPanel.toString()+"</bottomPanel>");
  }

  /**
   * Set the title of the content area.
   * 
   * @param title the content area title
   */
//  public void setContentTitleLeft(String title) {
//	  contentTitleLeftWrapper.setHTML(title);
//  }
  /**
   * Set the title of the content area.
   * 
   * @param title the content area title
   */
//  public void setContentTitleRight(Button button) {
//	  if(button==null) {
//		  contentTitleRightWrapper.setWidget(new HTML("TODO: override getShortCutButton"));
////		  contentTitleLayout.setWidget(0,1, new HTML("&nbsp;&nbsp;TODO: override getShortCutButton"));
//	  }else {
////		  contentTitleLayout.setWidget(0,1, button);
//		  contentTitleRightWrapper.setWidget(button);
//	  }
//  }
  
  /**
   * Set the {@link ApplicationListener}.
   * 
   * @param listener the listener
   */
  public void setListener(ApplicationListener listener) {
    this.listener = listener;
  }

  /**
   * Set the {@link Widget} to use as options, which appear to the right of the
   * title bar.
   * 
   * @param options the options widget
   */
  public void setOptionsWidget(Widget options) {
	  options.setStyleName(DEFAULT_STYLE_NAME +"-options");
//	  options.setLayoutData(new FitData());
	  topPanel.add(options, new BorderLayoutData(LayoutRegion.CENTER));
    //topPanel.setWidget(1, 1, options);
  }

  /**
   * Set the {@link Widget} to use as the title bar.
   * 
   * @param title the title widget
   */
  public void setTitleWidget(Widget title) {
	 title.setStyleName(DEFAULT_STYLE_NAME +"-title");
	 topPanel.add(title,new BorderLayoutData(LayoutRegion.WEST));
    //topPanel.setWidget(1, 0, title);
  }

  public void setNavigationWidget(Widget navigation){
	  navigation.setStyleName(DEFAULT_STYLE_NAME +"-navigation");
	  topPanel.add(navigation, new BorderLayoutData(LayoutRegion.SOUTH,26));
  }
  
  @Override
  protected void onLoad() {
    super.onLoad();
    Window.addWindowResizeListener(this);
    onWindowResized(Window.getClientWidth(), Window.getClientHeight());
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    Window.removeWindowResizeListener(this);
    windowWidth = -1;
  }

  protected void onWindowResizedImpl(int width) {
	  
    int menuWidth = mainMenu.getOffsetWidth();
    int contentWidth = width - menuWidth - 30;
    int contentWidthInner = contentWidth - 10;
//    bottomPanel.setCellWidth(mainMenu, menuWidth + "px");
//    bottomPanel.setCellWidth(contentDecorator, contentWidth + "px");
//    contentTitleLayout.setWidth(contentWidth + "px");
//    contentLayout.setWidth(contentWidth + "px");
    
    System.out.println("resizing to "+width+", contentWidth="+contentWidth);
//    contentLayout.getCellFormatter().setWidth(0, 0, contentWidthInner + "px");
//    contentLayout.getCellFormatter().setWidth(1, 0, contentWidthInner + "px");
  }

  /**
   * Create the main menu.
   */
  private void createMainMenu() {
    // Setup the main menu
    ApplicationImages treeImages = GWT.create(ApplicationImages.class);
    mainMenu = new Tree(treeImages);
    mainMenu.setAnimationEnabled(true);
    mainMenu.addStyleName(DEFAULT_STYLE_NAME + "-menu");
    mainMenu.addTreeListener(new TreeListener() {
      public void onTreeItemSelected(TreeItem item) {
        item.setSelected(false);
    	  if (listener != null) {
          listener.onMenuItemSelected(item);
        }
      }

      public void onTreeItemStateChanged(TreeItem item) {
    	  
      }
    });
  }

  /**
   * Create the panel at the top of the page that contains the title and links.
   */
  private void createTopPanel() {
    boolean isRTL = LocaleInfo.getCurrentLocale().isRTL();
    topPanel = new LayoutContainer(new BorderLayout());
    topPanel.setStyleName(DEFAULT_STYLE_NAME + "-top");
    topPanel.setStyleAttribute("padding", "0 0 0 0");
    topPanel.setStyleAttribute("margin","0 0 4 0");
    linksPanel = new VerticalPanel();
    linksPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    links = new HorizontalPanel();
    linksPanel.add(links);
    linksPanel.setStyleName(DEFAULT_STYLE_NAME + "-links");
    topPanel.add(linksPanel, new BorderLayoutData(LayoutRegion.NORTH,24));
  }
  
  public String toString() {
	  StringBuffer buf = new StringBuffer();
	  buf.append("bottomPanel: "+bottomPanel+"<end>");
	  return buf.toString();
  }
}
