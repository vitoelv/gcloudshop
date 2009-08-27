package com.jcommerce.gwt.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.jcommerce.gwt.client.resources.Resources;



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
  private DecoratorPanel contentDecorator;

  /**
   * The main wrapper around the content and content title.
   */
  private VerticalPanel contentLayout;
  
  /**
   * The main wrapper around the content and content title.
   */
  private Grid contentTitleLayout;

  /**
   * The wrapper around the content.
   */
  private SimplePanel contentWrapper;
    
  /**
   * The wrapper around the content title.
   */
  private HTML contentTitleLeftWrapper;
  /**
   * The wrapper around the content title.
   */
  private SimplePanel contentTitleRightWrapper;
  
  /**
   * The panel that holds the main links.
   */
  private HorizontalPanel linksPanel;

  /**
   * The {@link ApplicationListener}.
   */
  private ApplicationListener listener = null;

  /**
   * The main menu.
   */
  private Tree mainMenu;

  /**
   * The last known width of the window.
   */
  private int windowWidth = -1;

  /**
   * The panel that contains the title widget and links.
   */
  private FlexTable topPanel;

  /**
   * Constructor.
   */
  public Application() {
    // Setup the main layout widget
//    FlowPanel layout = new FlowPanel();
	 
	VerticalPanel layout = new VerticalPanel();
    initWidget(layout);

    // Setup the top panel with the title and links
    createTopPanel();
    layout.add(topPanel);

    // Add the main menu
    bottomPanel = new HorizontalPanel();
    bottomPanel.setWidth("100%");

    bottomPanel.setSpacing(0);
    bottomPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
    layout.add(bottomPanel);
    createMainMenu();
    bottomPanel.add(mainMenu);

    // Setup the content layout
    contentLayout = new VerticalPanel();
//    contentLayout.setCellPadding(0);
//    contentLayout.setCellSpacing(0);
    contentTitleLayout = new Grid(1, 2);
    contentTitleLayout.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    contentTitleLayout.setCellPadding(0);
    contentTitleLayout.setCellSpacing(0);
    contentDecorator = new DecoratorPanel();
    contentDecorator.setWidget(contentLayout);
    contentDecorator.addStyleName(DEFAULT_STYLE_NAME + "-content-decorator");
    bottomPanel.add(contentDecorator);
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      bottomPanel.setCellHorizontalAlignment(contentDecorator,
          HasHorizontalAlignment.ALIGN_LEFT);
      contentDecorator.getElement().setAttribute("align", "LEFT");
    } else {
      bottomPanel.setCellHorizontalAlignment(contentDecorator,
          HasHorizontalAlignment.ALIGN_RIGHT);
      contentDecorator.getElement().setAttribute("align", "RIGHT");
    }
//    CellFormatter formatter = contentLayout.getCellFormatter();
//    formatter.setStyleName(0, 0, DEFAULT_STYLE_NAME + "-content-title");

//    setContentTitle();

    // Add the content wrapper
    contentWrapper = new SimplePanel();
    contentTitleLeftWrapper = new HTML("ISHOP管理中心");
    contentTitleRightWrapper = new SimplePanel();
    
//    contentTitleLayout.setWidth("100%");
    contentTitleLayout.setWidget(0,0, contentTitleLeftWrapper);
    contentTitleLayout.setWidget(0,1, contentTitleRightWrapper);
    
//    contentLayout.setWidth("100%");
//    contentLayout.setWidget(0, 0, contentTitleLayout);
//    contentLayout.setWidget(1, 0, contentWrapper);
    TableData fd = new TableData();
    fd.setWidth("800px");
    contentLayout.add(contentTitleLayout, fd);
    contentLayout.add(contentWrapper, fd);
    


    
    
//    contentLc = new VerticalPanel();
//    contentLayout.add(contentLc, fd);
    
//    formatter.setStyleName(1, 0, DEFAULT_STYLE_NAME + "-content-wrapper");
    setContent(null);
  }

  /**
   * Add a link to the top of the page.
   * 
   * @param link the widget to add to the mainLinks
   */
  public void addLink(Widget link) {
    if (linksPanel.getWidgetCount() > 0) {
      linksPanel.add(new HTML("&nbsp;|&nbsp;"));
    }
    linksPanel.add(link);
  }

  /**
   * @return the {@link Widget} in the content area
   */
  public Widget getContent() {
    return contentWrapper.getWidget();
  }

  /**
   * @return the content title widget
   */
  public Widget getContentTitle() {
    return contentTitleLayout;
  }

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
  public Widget getTitleWidget() {
    return topPanel.getWidget(0, 0);
  }

  public Widget getNavigationWidget(){
	  return topPanel.getWidget(2, 0);
  }
  
  public void onWindowResized(int width, int height) {
    if (width == windowWidth) {
      return;
    }
    windowWidth = width;
    onWindowResizedImpl(width);
  }
  TabPanel tabs = new MyTabPanel();
  FormPanel formPanel = new FormPanel();
  /**
   * Set the {@link Widget} to display in the content area.
   * 
   * @param content the content widget
   */
  public void setContent(Widget content) {
    if (content == null) {
      contentWrapper.setWidget(new HTML("&nbsp;"));
    } else {
      contentWrapper.setWidget(content);
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
  public void setContentTitleLeft(String title) {
	  contentTitleLeftWrapper.setHTML(title);
  }
  /**
   * Set the title of the content area.
   * 
   * @param title the content area title
   */
  public void setContentTitleRight(Button button) {
	  if(button==null) {
//		  contentTitleRightWrapper.setWidget(new HTML("TODO: override getShortCutButton"));
		  contentTitleLayout.setWidget(0,1, new HTML("&nbsp;&nbsp;TODO: override getShortCutButton"));
	  }else {
		  contentTitleLayout.setWidget(0,1, button);
//		  contentTitleRightWrapper.setWidget(button);
	  }
  }
  
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
    topPanel.setWidget(1, 1, options);
  }

  /**
   * Set the {@link Widget} to use as the title bar.
   * 
   * @param title the title widget
   */
  public void setTitleWidget(Widget title) {
    topPanel.setWidget(1, 0, title);
  }

  public void setNavigationWidget(Widget navigation){
	  topPanel.setWidget(2, 0, navigation);
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
    bottomPanel.setCellWidth(mainMenu, menuWidth + "px");
    bottomPanel.setCellWidth(contentDecorator, contentWidth + "px");
    contentTitleLayout.setWidth(contentWidth + "px");
    contentLayout.setWidth(contentWidth + "px");
    
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
    topPanel = new FlexTable();
    topPanel.setCellPadding(0);
    topPanel.setCellSpacing(0);
    topPanel.setStyleName(DEFAULT_STYLE_NAME + "-top");
    FlexCellFormatter formatter = topPanel.getFlexCellFormatter();

    // Setup the links cell
    linksPanel = new HorizontalPanel();
    topPanel.setWidget(0, 0, linksPanel);
    formatter.setStyleName(0, 0, DEFAULT_STYLE_NAME + "-links");
    if (isRTL) {
      formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
    } else {
      formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    }
    formatter.setColSpan(0, 0, 2);

    // Setup the title cell
    setTitleWidget(null);
    formatter.setStyleName(1, 0, DEFAULT_STYLE_NAME + "-title");

    // Setup the options cell
    setOptionsWidget(null);
    formatter.setStyleName(1, 1, DEFAULT_STYLE_NAME + "-options");
    // Setup the navigation cell
    setNavigationWidget(null);
    formatter.setStyleName(2,0,DEFAULT_STYLE_NAME +"-navigation");
    formatter.setStyleName(2,1,DEFAULT_STYLE_NAME +"-navigation");
    if (isRTL) {
      formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT);
    } else {
      formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    // Align the content to the top
    topPanel.getRowFormatter().setVerticalAlign(0,
        HasVerticalAlignment.ALIGN_TOP);
    topPanel.getRowFormatter().setVerticalAlign(1,
        HasVerticalAlignment.ALIGN_TOP);
  }
}
