package com.jcommerce.gwt.client.panels.data;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.BaseFileUploadFormPanel;
import com.jcommerce.gwt.client.panels.Success;

public class ImportPanel extends BaseFileUploadFormPanel {
    /**
     * Initialize this example.
     */
    public static ImportPanel getInstance() {
    	if(instance==null) {
    		instance = new ImportPanel();
    	}
    	return instance;
    }
    private static ImportPanel instance; 
    private ImportPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return ImportPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return "Import";
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
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
   		newState.setMessage("导入成功");
    	
    	ImportPanel.State choice1 = new ImportPanel.State();
    	newState.addChoice(ImportPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute(); 

	}
	
	FileUploadField fufField;
    
	
	public void refresh() {
		fufField.reset();
		fufField.setValue("");
		fufField.setEnabled(true);
		fufField.setReadOnly(false);
	}
	

	protected void postSuperRefresh() {
	}
	

	@Override
	protected void setupPanelLayout() {
		Button bu = new Button("Clear");
		bu.setWidth(50);
		bu.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				final Dialog dialog = new Dialog();
				dialog.setButtons(Dialog.OKCANCEL);
				dialog.setHeading("Warning!");
				dialog.addText("Do you wish to clear all data?");
				dialog.setBodyStyle("fontWeight:bold;padding:13px;");
				dialog.setSize(300, 100);
				dialog.setHideOnButtonClick(true);
				dialog.addWindowListener(new WindowListener() {
					@Override
					public void windowHide(WindowEvent we) {
						Button button = we.getButtonClicked();
						Button b2 = (Button)dialog.getButtonBar().getItemByItemId(Dialog.OK);
						if(button == b2) {
							GWTHttpDynaForm form = new GWTHttpDynaForm();
							form.setUrl("/admin/imexportService.do?action=clear");
							form.SetListener(new GWTHttpDynaForm.Listener() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("error: " + caught.getMessage());
								}
								
								@Override
								public void onSuccess(String response) {
									Info.display("successful", "all data is cleared");
								}
							});
							form.submit();
						}
					}
				});
				dialog.show();
			}
		});

		AdapterField af = new AdapterField(bu);
		af.setHideLabel(true);

		MultiField mf = new MultiField();
		mf.setFieldLabel("Clear all data");
		mf.setToolTip("Please be careful, all data would be clear upon clicking this button");
		mf.add(af);
		
		formPanel.add(mf, super.tfd());
		
		
		fufField = new FileUploadField();
		fufField.setFieldLabel("Select File");
		fufField.setName("file");
		fufField.setAutoValidate(true);
		formPanel.add(fufField);
        formPanel.setAction("/admin/imexportService.do?action=import");

	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "Import";
	}

}
