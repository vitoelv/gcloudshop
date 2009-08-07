package com.jcommerce.gwt.client.panels.goods;

import java.util.List;

import com.extjs.gxt.ui.client.Style;
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
import com.jcommerce.gwt.client.model.IGallery;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;

public class GalleryPanel4 extends TabItem {
	int uploaderCount = 0;
	String formSufix = IGoods.GALLERIES; 
	LayoutContainer lc2 = new LayoutContainer();
	LayoutContainer lc1 = new LayoutContainer();
	GoodsPanel gp;
	
	public GalleryPanel4(GoodsPanel gp) {
		super();
		this.gp = gp;
				
		TableLayout layout = new TableLayout(1);
		layout.setCellSpacing(5);
		layout.setCellPadding(5);
		setLayout(layout);
		
		
		TableLayout tl = new TableLayout(3);
		tl.setBorder(1);
		lc1.setLayout(tl);
		lc1.setAutoHeight(true);

		TableData td = new TableData();
		td.setWidth("100%");
		add(lc1, td);
		add(lc2, td);
		
		lc2.setAutoHeight(true);
		RowLayout rl = new RowLayout();
		rl.setOrientation(Orientation.VERTICAL);
		lc2.setLayout(rl);

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
		idField.setName(buildElementName(GalleryForm.ID));
		idField.setValue("xx");
		TextField<String> descField = new TextField<String>();
		descField.setName(buildElementName(GalleryForm.DESCRIPTION));
		descField.setFieldLabel("descrition:");
		
		lc.add(link);
		lc.add(image);
		lc.add(idField);
		lc.add(descField);
		
		lc1.add(lc);
	}

	private void addUploader(boolean isFirst) {
		uploaderCount++;
		final LayoutContainer lcc = new LayoutContainer();
		TableRowLayout cl = new TableRowLayout();
		cl.setWidth("100%");
		lcc.setLayout(cl);
		
		Text lbl1 = new Text("Image Description :");
		final TextField<String> t1 = new TextField<String>();
		t1.setName(buildElementName(IGallery.DESCRIPTION));
//		t1.setFieldLabel("Name :");
		
		Text lbl2 = new Text("Image File :");
		final FileUploadField file = new FileUploadField();
		file.setName(buildElementName(IGallery.IMAGE));
		
		gp.addDynaField(t1);
		gp.addDynaField(file);
		
		Button b1 = null;
		if(isFirst) {
			b1 = new Button("[+]");
			b1.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					System.out.println("+ button clicked");
					// TODO remove the gallery 
					addUploader(false);
					
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
		td.setWidth("10%");
		lcc.add(lbl1, td);

		td = new TableData();
		td.setWidth("20%");
		lcc.add(t1, td);
		
		td = new TableData();
		td.setWidth("10%");
		lcc.add(lbl2, td);
		
		td = new TableData();
		td.setWidth("20%");
		lcc.add(file, td);
		
		lc2.add(lcc, new RowData(1, -1));
		
		this.layout();
		this.repaint();
	}
	
	public void refresh(String goodsId) {	
		System.out.println("GalleryPanel2:  [refresh] goodsId="+goodsId);
		lc1.removeAll();
		lc2.removeAll();
		uploaderCount = 0;
		
		addUploader(true);
		
		if(goodsId!=null) {
		new ListService().listBeans(ModelNames.GALLERY, IGallery.GOODS, goodsId, new ListService.Listener() {
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
		uploaderCount++;
		String imageFileId = gallery.getString(IGallery.IMAGEFILEID);
		String image = gallery.getString(IGallery.IMAGE);
		final String id = gallery.getString(IGallery.ID);
		String description = gallery.getString(IGallery.DESCRIPTION);
		
		System.out.println("imageFileId: "+imageFileId);
		System.out.println("image: "+image);
		System.out.println("id: "+id);
		System.out.println("description: "+description);
		
		final LayoutContainer lc = new LayoutContainer();
		TableLayout layout = new TableLayout(1);
		layout.setCellHorizontalAlign(Style.HorizontalAlignment.CENTER);
		layout.setCellVerticalAlign(Style.VerticalAlignment.MIDDLE);
		layout.setCellSpacing(2);
		layout.setCellPadding(2);
		
		lc.setLayout(layout);
		
		Image im = new Image();
		im.setUrl(GWT.getModuleBaseURL()+"dynaImageService.do?fileId="+imageFileId);

		
		final HiddenField<String> idField = new HiddenField<String>();
		idField.setName(buildElementName(GalleryForm.ID));
		idField.setValue(id);
		
		final HiddenField<String> imageFileIdField = new HiddenField<String>();
		imageFileIdField.setName(buildElementName(GalleryForm.IMAGEFILEID));
		imageFileIdField.setValue(imageFileId);
		
		final TextField<String> descField = new TextField<String>();
		descField.setName(buildElementName(GalleryForm.DESCRIPTION));
		descField.setFieldLabel("descrition:");
		descField.setMaxLength(20);
		descField.setValue(description);
		
		// do not show fileName
//		final HiddenField<String> imageField = new HiddenField<String>();
//		imageField.setName(buildElementName(GalleryForm.IMAGE));
//		imageField.setValue(image);
		Button link = new Button("[-]");
		link.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				System.out.println("- button clicked");
				// TODO remove the gallery 
				new DeleteService().deleteBean(ModelNames.GALLERY, id, new DeleteService.Listener() {
					@Override
					public void onSuccess(Boolean success) {
						lc1.remove(lc);
						gp.removeDynaField(idField);
						gp.removeDynaField(descField);
//						gp.removeDynaField(imageField);
						gp.removeDynaField(imageFileIdField);
						Info.display("Great", "Gallery item deleted");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Info.display("Ooops", "Error when deleting gallery item due to the #91 bug");
					}
					
				});

			}
		});
		
		lc.add(link);
		lc.add(im);
		lc.add(idField);
		lc.add(descField);
//		lc.add(imageField);
		lc.add(imageFileIdField);
		
		gp.addDynaField(idField);
		gp.addDynaField(descField);
//		gp.addDynaField(imageField);
		gp.addDynaField(imageFileIdField);
		
		lc1.add(lc);
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
	

	

}