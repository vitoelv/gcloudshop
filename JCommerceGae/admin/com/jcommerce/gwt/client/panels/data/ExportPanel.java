package com.jcommerce.gwt.client.panels.data;

import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;


public class ExportPanel extends BaseEntityEditPanel{
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
			return "Export";
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

    	ImportPanel.State choice1 = new ImportPanel.State();
    	newState.addChoice(ExportPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute(); 
		
	}

	@Override
	protected void postSuperRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setupPanelLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
