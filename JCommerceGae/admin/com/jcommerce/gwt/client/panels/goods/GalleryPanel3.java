package com.jcommerce.gwt.client.panels.goods;

import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GalleryForm;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsGallery;
import com.jcommerce.gwt.client.service.ListService;

public class GalleryPanel3 extends TabItem {
	int uploaderCount = 0;
	String formSufix = IGoods.GALLERIES; 
	LayoutContainer lc2 = new LayoutContainer();
	LayoutContainer lc1 = new LayoutContainer();
	GoodsPanel gp;
	
	public GalleryPanel3(GoodsPanel gp) {
		super();
		this.gp = gp;
		
		VBoxLayout layout = new VBoxLayout();  
		layout.setPadding(new Padding(5));  
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		setLayout(layout);
				
		lc1.setLayout(new TableLayout(3));
		lc1.setAutoHeight(true);
//		lc1.add(new Button("lc1"));
//		lc1.add(new Button("lc2"));
//		lc1.add(new Button("lc3"));
//		lc1.add(new Button("lc4"));
		
//		testAddImage();
//		testAddImage();
		
		
		VBoxLayoutData flex = new VBoxLayoutData(new Margins(0, 0, 5, 0));  
		flex.setFlex(1);
		add(lc1, flex);

		VBoxLayoutData flex2 = new VBoxLayoutData(new Margins(0));
		flex2.setFlex(1);
		add(lc2, flex2);
		
		lc2.setAutoHeight(true);
		test1();

	}


	private void testAddImage() {
		final LayoutContainer lc = new LayoutContainer();
		RowLayout lo = new RowLayout();
		lo.setOrientation(Orientation.VERTICAL);
		lc.setLayout(lo);
		
		Image image = new Image();
//		image.setUrl("http://www.google.cn/intl/zh-CN/images/logo_cn.gif");
		image.setUrl(GWT.getModuleBaseURL()+"dynaImageService.do?fileId=agpnY2xvdWRzaG9wciULEgZEU0ZpbGUiGV8yMjgxYTZiYjAxMjI4MWE2YmM0YTAwMDMM");
		
		Button link = new Button("[-]");
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(buildElementName(GalleryForm.PK_ID));
		idField.setValue("xx");
		TextField<String> descField = new TextField<String>();
		descField.setName(buildElementName(GalleryForm.IMG_DESC));
		descField.setFieldLabel("descrition:");
		
		lc.add(link);
		lc.add(image);
		lc.add(idField);
		lc.add(descField);
		
		lc1.add(lc);
	}
	
	
	public void test2() {
		// this does not work
		// seems bug of nested VBoxLayout 
		VBoxLayout layout = new VBoxLayout();  
		layout.setPadding(new Padding(5));  
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		lc2.setLayout(layout);
		lc2.add(new Text("xxxxx"), new VBoxLayoutData(new Margins(0, 0, 5, 0)));
		lc2.add(new Text("yy"), new VBoxLayoutData(new Margins(0)));		
	}
	public void test3() {
		// this does not work
		// seems bug of HBoxLayout nested in HBoxLayout
		RowLayout layout = new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		lc2.setLayout(layout);
		
		LayoutContainer lcc = new LayoutContainer();
		HBoxLayout hbl = new HBoxLayout();
		hbl.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
		lcc.setLayout(hbl);
		lcc.add(new Button("yyyyy"));
		lc2.add(lcc, new RowData(-1, 1));		
	}
	public void test4() {
		// this does not work
		// seems bug of nested RowLayout
		RowLayout layout = new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		lc2.setLayout(layout);
		
		LayoutContainer lcc = new LayoutContainer();
		layout = new RowLayout();
		layout.setOrientation(Orientation.HORIZONTAL);
		lcc.setLayout(layout);
		lcc.add(new Text("yyyyy"), new RowData(100, 1));
		lc2.add(lcc, new RowData(1, -1));		
	}
	public void test5() {
		// this works finally...
		RowLayout layout = new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		lc2.setLayout(layout);

		LayoutContainer lcc = new LayoutContainer();
		CenterLayout cl = new CenterLayout();
		lcc.setLayout(cl);
		lcc.add(new Button("yyyyy"));
		lc2.add(lcc, new RowData(1, -1));
	}
	public void test1() {
		System.out.println("test1");

		RowLayout layout = new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		lc2.setLayout(layout);
		

		
	}

	private void addNewRow(boolean isFirst) {
		uploaderCount++;
		final LayoutContainer lcc = new LayoutContainer();
		TableRowLayout cl = new TableRowLayout();
		cl.setWidth("100%");
		lcc.setLayout(cl);
		
		Text lbl1 = new Text("Name :");
		final TextField<String> t1 = new TextField<String>();
		t1.setName(buildElementName(IGoodsGallery.IMG_DESC));
//		t1.setFieldLabel("Name :");
		
		Text lbl2 = new Text("File :");
		final FileUploadField file = new FileUploadField();
		file.setName(buildElementName(IGoodsGallery.IMAGE));
		
		gp.addDynaField(t1);
		gp.addDynaField(file);
		
		Button b1 = null;
		if(isFirst) {
			b1 = new Button("[+]");
			b1.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					System.out.println("+ button clicked");
					// TODO remove the gallery 
					addNewRow(false);
					
				}
			});
		} else {
			b1 = new Button("[-]");
			b1.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					System.out.println("- button clicked");
					// TODO remove the gallery 
					lc2.remove(lcc);
					gp.removeDynaField(t1);
					gp.removeDynaField(file);
				}
			});
		}


		TableData td = new TableData();
		td.setWidth("5%");
		lcc.add(b1, td);
		
		td = new TableData();
		td.setWidth("5%");
		lcc.add(lbl1, td);

		td = new TableData();
		td.setWidth("20%");
		lcc.add(t1, td);
		
		td = new TableData();
		td.setWidth("5%");
		lcc.add(lbl2, td);
		
		td = new TableData();
		td.setWidth("30%");
		lcc.add(file, td);
		
		lc2.add(lcc, new RowData(1, -1));
		
		this.layout();
		this.repaint();
	}
	
	public void refresh(String goodsId) {	
		System.out.println("GalleryPanel2:  [refresh] goodsId="+goodsId);
//		testAddImage();

		
		initializeUploader();
		
//		testAddImage();
		
//		this.layout();
//		this.repaint();
		
		if(goodsId!=null) {
		new ListService().listBeans(ModelNames.GOODSGALLERY, IGoodsGallery.GOODS_ID, goodsId, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
				for(BeanObject gallery:beans) {
					addImage(gallery);
				}
				layout();
		        repaint();
				
			}
		});		
		}
	}
	
	private void addImage(BeanObject gallery) {
		System.out.println("addImage: ");
		String imageFileId = gallery.getString(IGoodsGallery.IMAGEFILEID);
		String image = gallery.getString(IGoodsGallery.IMAGE);
		String id = gallery.getString(IGoodsGallery.PK_ID);
		String description = gallery.getString(IGoodsGallery.IMG_DESC);
		
		System.out.println("imageFileId: "+imageFileId);
		System.out.println("image: "+image);
		System.out.println("id: "+id);
		System.out.println("description: "+description);
		
		final LayoutContainer lc = new LayoutContainer();
		RowLayout layout = new RowLayout();
		layout.setOrientation(Orientation.VERTICAL);
		lc.setLayout(layout);
		Button link = new Button("[-]");
		link.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				System.out.println("- button clicked");
				// TODO remove the gallery 
				lc1.remove(lc);
				
			}
		});
		
		
		Image im = new Image();
		im.setUrl(GWT.getModuleBaseURL()+"dynaImageService.do?fileId="+imageFileId);

		
		HiddenField<String> idField = new HiddenField<String>();
		idField.setName(buildElementName(GalleryForm.PK_ID));
		idField.setValue(id);
//		lc.add(idField);
		
		HiddenField<String> imageFileIdField = new HiddenField<String>();
		imageFileIdField.setName(buildElementName(GalleryForm.IMAGEFILEID));
		imageFileIdField.setValue(imageFileId);
//		lc.add(imageFileIdField);
		
		TextField<String> descField = new TextField<String>();
		descField.setName(buildElementName(GalleryForm.IMG_DESC));
		descField.setFieldLabel("descrition:");
		descField.setMaxLength(20);
		descField.setValue(description);
		
		HiddenField<String> imageField = new HiddenField<String>();
		imageField.setName(buildElementName(GalleryForm.IMAGE));
		imageField.setValue(image);
//		lc.add(imageField);


//		lc.add(new AdapterField(link));
//		lc.add(new AdapterField(im));

		lc.add(link);
		lc.add(im);

		lc.add(idField);
		lc.add(descField);
		lc.add(imageField);
		lc.add(imageFileIdField);
		
//		lc.add(link, fd);
//		lc.add(im, fd);
//		lc.add(idField, fd);
//		lc.add(descField, fd);
//		lc.add(imageField, fd);
//		lc.add(imageFileIdField, fd);
		
//		RowData rd = new RowData(1, -1);
//		lc1.add(lc, rd);
		lc1.add(lc);
	}
	
    private void addUploader(boolean isInitilizing) {
    	
    	uploaderCount++;
    	System.out.println("addUploader: ");
		final LayoutContainer lc = new LayoutContainer();
		lc.setLayout(new FormLayout());
		
		Button link = null;
		if(isInitilizing) {
			link = new Button("[+]");
			link.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					System.out.println("+ button clicked");
					addUploader(false);
			    	Info.display("ooops", "You were hit by a bug that the new uploader won't appear automatcially. Try click other tab and comeback to see the effect");
				}
			});		
		} else {
			link = new Button("[-]");
			link.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					System.out.println("- button clicked");
					lc2.remove(lc);
					lc2.layout();
				}
			});
		}

		HiddenField<String> id = new HiddenField<String>();
		id.setName(buildElementName(GalleryForm.PK_ID));
		id.setValue("");
		lc.add(id);
		
		TextField<String> desc = new TextField<String>();
		desc.setName(buildElementName(GalleryForm.IMG_DESC));
		desc.setFieldLabel("descrition:");
		desc.setMaxLength(20);
		
		FileUploadField file = new FileUploadField();
		file.setName(buildElementName(GalleryForm.IMAGE));
		file.setFieldLabel("file: ");
		
		HiddenField<String> imageFileIdField = new HiddenField<String>();
		imageFileIdField.setName(buildElementName(GalleryForm.IMAGEFILEID));
		imageFileIdField.setValue("");
		lc.add(imageFileIdField);
		
		lc.add(new AdapterField(link));
		lc.add(desc);
		lc.add(file);
		
//		RowData rd = new RowData(1, -1);
//		lc2.add(lc, rd);
//		lc2.add(new AdapterField(new Button("test")));
		lc2.add(lc, new VBoxLayoutData(new Margins(0)));
//        lc2.layout();
//        lc.layout();
//        this.layout();

    }

    private String buildElementName(String fieldName) {
    	return formSufix+"["+uploaderCount+"]."+fieldName;
    }
    
	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		System.out.println("GalleryPanel2:  [onRender]");
		super.onRender(parent, index);
		


		
	}
	
	private void initializeUploader() {
		lc2.removeAll();
		uploaderCount = 0;
		
		addNewRow(true);
	}
	

}
