package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.resources.Resources;

public class GoodsTypePanel extends BaseEntityEditPanel {
	public static interface Constants {
		String GoodsTypeEdit_title();
	}
	
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return GoodsTypePanel.class.getName();
		}
	}
	private State curState = new State();
	
	@Override
	public State getCurState() {
		return curState;
	}
	public void setCurState(State curState) {
		this.curState = curState;
	}
	@Override
	public void setupPanelLayout() {
        
        TextField<String> nameField = GoodsTypeForm.getNameField(Resources.constants.GoodsTypeList_ColumnName()+"：");
        nameField.setFieldLabel(Resources.constants.GoodsTypeList_ColumnName());
        formPanel.add(nameField);
        
        TextArea agField = GoodsTypeForm.getAttributeGroupField(Resources.constants.GoodsTypeList_ColumnGroup()+"：");
        agField.setHeight("180px");
        agField.setWidth("100px");        
        agField.setFieldLabel(Resources.constants.GoodsTypeList_ColumnGroup());
        formPanel.add(agField);
        
        // TODO make it english : ? 
        formPanel.setLabelSeparator("：");
        formPanel.setLabelWidth(100);
    }
	
	@Override
	public String getEntityClassName() {
		return ModelNames.GOODSTYPE; 
	}
	
	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage("添加商品类型成功");
    	} else {
    		newState.setMessage("修改商品类型成功");
    	}
      	
      	GoodsTypeListPanel.State choice1 = new GoodsTypeListPanel.State();
      	newState.addChoice(GoodsTypeListPanel.getInstance().getName(), choice1.getFullHistoryToken());
      	
      	newState.execute();
	}
	
    
	@Override
	public String getDescription() {
        return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
        return Resources.constants.GoodsTypeEdit_title();
	}

	public void postSuperRefresh() {
		System.out.println("postSuperRefresh do nothing");
	}


	
	

}
