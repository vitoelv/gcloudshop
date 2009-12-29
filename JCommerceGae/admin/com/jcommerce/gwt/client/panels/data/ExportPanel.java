package com.jcommerce.gwt.client.panels.data;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;


public class ExportPanel extends BaseEntityEditPanel{
	
	public static interface Constants {
        String Export_MenuName();
        
    }
    /**
     * Initialize this example.
     */
    public static ExportPanel getInstance() {
    	if(instance==null) {
    		instance = new ExportPanel();
    	}
    	return instance;
    }
    private static ExportPanel instance; 
    private ExportPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return ExportPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Export_MenuName();
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
   		newState.setMessage("导出成功");

   		ExportPanel.State choice1 = new ExportPanel.State();
    	newState.addChoice(ExportPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute(); 
		
	}

	@Override
	protected void postSuperRefresh() {
	}

	@Override
	protected void setupPanelLayout() {
		
		AdapterField af = new AdapterField(new Label("Currently will export all data supported"));
		af.setFieldLabel("Note");
		formPanel.add(af, sfd());
		
		Button bu = new Button("Export");
		bu.setWidth(50);
		bu.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent sender) {
				   Window w = new Window();        
				   w.setHeading("Export");
				   w.setModal(false);
				   w.setSize(200, 100);
				   w.setMaximizable(false);
				   w.setToolTip("Exporting...");
				   w.setUrl("/admin/imexportService.do?action=export");
				   w.show();

                        
			}
		});

		af = new AdapterField(bu);
		af.setHideLabel(true);

		MultiField mf = new MultiField();
		mf.setFieldLabel("Export all data");
		mf.add(af);
		formPanel.add(mf, super.tfd());
		

		
	}

    @Override
    protected void submit() {
    	// do nothing
    }
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "Export";
	}

	
	
}
