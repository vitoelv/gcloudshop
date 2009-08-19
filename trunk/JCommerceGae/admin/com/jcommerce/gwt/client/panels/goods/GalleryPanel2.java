package com.jcommerce.gwt.client.panels.goods;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GalleryForm;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsGallery;

public class GalleryPanel2 extends TabItem {
	int uploaderCount = 0;
	String formSufix = IGoods.GALLERIES; 
	LayoutContainer lc2 = new LayoutContainer();
	LayoutContainer lc1 = new LayoutContainer();
	
	public GalleryPanel2() {
		super();
//		setLayout(new FillLayout(Style.Orientation.VERTICAL));
		VBoxLayout layout = new VBoxLayout();  
		layout.setPadding(new Padding(5));  
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		setLayout(layout);
		

//      RowData rd = new RowData(1, -1);
		lc1.setLayout(new TableLayout(3));
		lc1.setAutoHeight(true);
		lc1.add(new Button("lc1"));
		lc1.add(new Button("lc2"));
		lc1.add(new Button("lc3"));
//		lc1.add(new Button("lc4"));
//		this.add(lc1, rd);
		
		VBoxLayoutData flex = new VBoxLayoutData(new Margins(0, 0, 5, 0));  
		flex.setFlex(1);
		this.add(lc1, flex);


		

//		rd = new RowData(1, -1);
//		lc2.setLayout(new FillLayout(Style.Orientation.VERTICAL));
//		VBoxLayout vbl1 = new VBoxLayout();
//		vbl1.setPadding(new Padding(5));  
//		vbl1.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
//		lc2.setLayout(vbl1);

		
//		this.add(lc2, rd);
		VBoxLayoutData flex2 = new VBoxLayoutData(new Margins(0));
		flex2.setFlex(1);
		this.add(lc2, flex2);
		
		test1();
//		lc2.setAutoHeight(true);
//		lc2.add(new Button("lc2"), flex31);
//		lc2.add(new Button("lc21"), flex31);
//		lc2.add(new Button("lc22"), flex31);
//		LayoutContainer lcc = new LayoutContainer();
		
//		HBoxLayoutData hbld1 = new HBoxLayoutData(new Margins(0, 5, 0, 0));
//		HBoxLayout hbl = new HBoxLayout();  
//		hbl.setPadding(new Padding(5));  
//		hbl.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);  
//		hbl.setPack(BoxLayoutPack.START);
//		lcc.setLayout(hbl); 
//		lcc.setLayout(new TableLayout(3));
		
//		lcc.setLayout(new ColumnLayout());
		
		
//		lcc.setLayout(new ColumnLayout());
//		LayoutContainer left = new LayoutContainer();  
//		left.setStyleAttribute("paddingRight", "10px");  
//		FormLayout fl1 = new FormLayout();  
//		fl1.setLabelAlign(LabelAlign.LEFT);  
//		left.setLayout(fl1);  
//		
//		LayoutContainer right = new LayoutContainer();  
//		right.setStyleAttribute("paddingLeft", "10px");  
//		fl1 = new FormLayout();  
//		fl1.setLabelAlign(LabelAlign.LEFT);  
//		right.setLayout(fl1); 
//		
//		LayoutContainer leftside = new LayoutContainer();  
//		right.setStyleAttribute("paddingLeft", "10px");  
//		fl1 = new FormLayout();  
//		fl1.setLabelAlign(LabelAlign.LEFT);  
//		leftside.setLayout(fl1); 
//		
//		lcc.add(leftside, new ColumnData(.1));
//		lcc.add(left, new ColumnData(.45));  
//		lcc.add(right, new ColumnData(.45));  
//		
////		left.add(new Button("[+]"));
//		leftside.add(new AdapterField(new Button("[+]")), new FormData("20%"));
//		
//		
//		LayoutContainer lcc1 = new LayoutContainer();
//		lcc1.setLayout(new FormLayout());
//		TextField<String> tf = new TextField<String>();
//		tf.setFieldLabel("testField");
//		lcc1.add(tf);
//		
//		LayoutContainer lcc2 = new LayoutContainer();
//		lcc2.setLayout(new TableRowLayout());
//		lcc2.add(new Button("[+]"), new TableData("30px", "50px"));
//		lcc2.add(lcc1, new TableData("200px", "50px"));
//		left.add(lcc2);
////		lcc.add(new Text(), new ColumnData(50)); // space holder
//		
////		lcc1 = new LayoutContainer();
////		lcc1.setLayout(new FormLayout());
//		tf = new TextField<String>();
//		tf.setFieldLabel("t2");
////		lcc1.add(tf);
////		lcc.add(lcc1, new TableData("200px", "30px"));
//		right.add(tf);
////		lcc.add(new Text(), new ColumnData(50)); // space holder
//		lc2.add(lcc, flex31);
		
//		initializeUploader();
//		addUploader(true);
	}
	
	public void test1() {
		System.out.println("test1");
		VBoxLayout layout = new VBoxLayout();  
		layout.setPadding(new Padding(5));  
		layout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		lc2.setLayout(layout);
		LayoutContainer lcc = new LayoutContainer();
		lcc.setLayout(new FormLayout());
		Field bf = new AdapterField(new Button("[+]"));
		TextField<String> tf = new TextField<String>();
		tf.setFieldLabel("testField");
		TextField<String> tf2 = new TextField<String>();
		tf.setFieldLabel("tf2");
		TextField<String> tf3 = new TextField<String>();
		tf.setFieldLabel("tf3");
		
		MultiField mf = new MultiField();
		mf.setOrientation(Orientation.HORIZONTAL);
		mf.add(bf);
		mf.add(tf);
		mf.add(tf2);
		mf.add(tf3);
		
		lcc.add(mf);
		
		VBoxLayoutData flex31 = new VBoxLayoutData(new Margins(0, 0, 5, 0));
		lc2.add(lcc, flex31);
	}
	
	public void refresh(String goodsId) {	
		System.out.println("GalleryPanel2:  [refresh]");
//		initializeUploader();
//		
//		if(goodsId!=null) {
//		new ListService().listBeans(ModelNames.GALLERY, IGallery.GOODS, goodsId, new ListService.Listener() {
//			@Override
//			public void onSuccess(List<BeanObject> beans) {
//				for(BeanObject gallery:beans) {
//					addImage(gallery);
//				}
////		        repaint();
//				
//			}
//		});		
//		}
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
//		FormData fd = new FormData("100%");
		lc.setLayout(new FormLayout());
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


		lc.add(new AdapterField(link));
		lc.add(new AdapterField(im));


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
		
		addUploader(true);
	}
	

}
