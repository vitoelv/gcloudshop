/**
 * 
 */
package com.jcommerce.gwt.client.panels.privilege;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AdminUserForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

/**
 * @author Falcon
 *
 */
public class AdminUserPanel extends BaseEntityEditPanel {

	public static interface Constants {
        String AdminUser_userName();
        String AdminUser_email();
        String AdminUser_password();
        String AdminUser_confirmPassword();
        String AdminUser_oldPassword();
        String AdminUser_oldPasswordError();
        String AdminUser_editAdmin();
        String AdminUser_title();
        String AdminUser_addSuccessfully();
        String AdminUser_modifySuccessfully();
        String AdminUser_newPassword();
        String AdminUser_deleteSuccessfully();
        String AdminUser_deleteFailure();
    }
	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#getCurState()
	 */
	

	
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return AdminUserPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.AdminUser_title();
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}


	@Override
	protected String getEntityClassName() {
		return ModelNames.ADMINUSER;
	}
	
	public static AdminUserPanel getInstance(){
		if(instance==null) {
    		instance = new AdminUserPanel();
    	}
    	return instance;
	}

	private static AdminUserPanel instance;
    private AdminUserPanel() {
    }
    
    @Override
    public String getName() {
    	if(!getCurState().getIsEdit())
			return Resources.constants.AdminUser_title();
		else
			return Resources.constants.AdminUser_editAdmin();    	
    }
    
    public void onButtonListClicked() {
		AdminListPanel.State newState = new AdminListPanel.State();
		newState.execute();
    }
	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#gotoSuccessPanel()
	 */
	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.AdminUser_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.AdminUser_modifySuccessfully());
    	}
    	
    	AdminListPanel.State choice1 = new AdminListPanel.State();
    	newState.addChoice(AdminListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	newState.execute();
	}
	
	@Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.AdminList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }

	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#postSuperRefresh()
	 */
	@Override
	protected void postSuperRefresh() {
		new ListService().listBeans(ModelNames.ADMINUSER, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
//		    	adminList.removeAll();
//				adminList.add(beans);
			}
		});
	}
	// get called when refresh(), if isEdit
	@Override
    protected void retrieveEntity() {
    	new ReadService().getBean(getEntityClassName(), getCurState().getPkId(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			obj = bean;
        			// populate those statically rendered fields
        			populateFields();
        			// sub-class should populate those "dynamic" fields including combox/list, etc 
        			postSuperRefresh();
        		}
        	});
    }
	@Override
	public void refresh() {
    	try {
    		List<Field<?>> fields = formPanel.getFields();
    		for(Field<?> f:fields) {
    			formPanel.remove(f);
    		}
    		setupPanelLayout();
    		formPanel.layout();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        if(getCurState().getIsEdit()) {
        	retrieveEntity();
        } else {
        	obj = new BeanObject();
        	postSuperRefresh();
        }

    }

	/* (non-Javadoc)
	 * @see com.jcommerce.gwt.client.panels.BaseEntityEditPanel#setupPanelLayout()
	 */
	@Override
	protected void setupPanelLayout() {
		System.out.println("----------AdminPanel");
		TextField<String> fUserName = AdminUserForm.getUserNameField(Resources.constants.AdminUser_userName()+"：");
		fUserName.setFieldLabel(Resources.constants.AdminUser_userName());
        formPanel.add(fUserName, sfd());
        
        TextField<String> fEmail = AdminUserForm.getEmailField(Resources.constants.AdminUser_email()+"：");
        fEmail.setFieldLabel(Resources.constants.AdminUser_email());
        formPanel.add(fEmail, sfd());
        
        if(getCurState().getIsEdit()) {
        	TextField<String> fOldPassword = AdminUserForm.getOldPasswordField(Resources.constants.AdminUser_oldPassword()+"：");
        	fOldPassword.setFieldLabel(Resources.constants.AdminUser_oldPassword());
            formPanel.add(fOldPassword, sfd());
            
            TextField<String> fPassword = AdminUserForm.getPasswordField(Resources.constants.AdminUser_password()+"：");
	        fPassword.setFieldLabel(Resources.constants.AdminUser_password());
	        fPassword.setVisible(false);
	        formPanel.add(fPassword, sfd());
            
            TextField<String> fNewPassword = AdminUserForm.getNewPasswordField(Resources.constants.AdminUser_newPassword()+"：");
            fNewPassword.setFieldLabel(Resources.constants.AdminUser_newPassword());
            formPanel.add(fNewPassword, sfd());
        }
        else{
	        TextField<String> fPassword = AdminUserForm.getPasswordField(Resources.constants.AdminUser_password()+"：");
	        fPassword.setFieldLabel(Resources.constants.AdminUser_password());
	        formPanel.add(fPassword, sfd());
        }
        TextField<String> fAddTime = AdminUserForm.getAddTimeField("");
    	formPanel.add(fAddTime,sfd());
    	
    	TextField<String> fActionList = AdminUserForm.getActionListField("");
    	formPanel.add(fActionList,sfd());
    	
    	TextField<String> fLastIp = AdminUserForm.getLastIpField("");
    	formPanel.add(fLastIp , sfd());
    	
    	TextField<String> fLastLogin = AdminUserForm.getLastLoginField("");
    	formPanel.add(fLastLogin , sfd());
    	
    	TextField<String> fNavList = AdminUserForm.getNavListField("");
    	formPanel.add(fNavList , sfd());
    	
    	TextField<String> fTodoList = AdminUserForm.getTodoListField("");
    	formPanel.add(fTodoList , sfd());
    	
    	TextField<String> fLangType = AdminUserForm.getLangTypeField("");
    	formPanel.add(fLangType , sfd());   	
    	
    	
        TextField<String> fCfmPassword = AdminUserForm.getComfirmPasswordField(Resources.constants.AdminUser_confirmPassword()+"：");
        fCfmPassword.setFieldLabel(Resources.constants.AdminUser_confirmPassword());
        formPanel.add(fCfmPassword, sfd());
        		
	}
	@Override
	protected void submit() {
		// default implementation is thru GWT-RPC
		Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
    	BeanObject form = new BeanObject(getEntityClassName(), props);
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getPkId();
    		String oldPassword = (String)props.get("oldPassword");
    		if(oldPassword == null ){
    			updateAdminUser(props);
    		}
    		else{
	    		if(oldPassword.equals((String)props.get("password"))){
	    			props.put("password", props.get("newPassword"));
					updateAdminUser(props);
				}
				else{
					Info.display("Warning", Resources.constants.AdminUser_oldPasswordError());
				}
					
    		}
    	}else {
          new CreateService().createBean(form, new CreateService.Listener() {
          public synchronized void onSuccess(String id) {
        	  log("new onSuccess( "+id);                            
              getCurState().setPkId(id);
              gotoSuccessPanel();
          }
          });
    	}
	}

	@Override
	public String getDescription() {
		 return "cwBasicTextDescription";
	}
	
	private void updateAdminUser( Map<String, Object>props){
    	BeanObject form = new BeanObject(getEntityClassName(), props);
		String id = getCurState().getPkId();
		 new UpdateService().updateBean(id, form, new UpdateService.Listener() {
       	  public synchronized void onSuccess(Boolean success) {
       		  gotoSuccessPanel();
       	  }
       	  public void onFailure(Throwable caught) {
       		  // TODO a point to define common behavior
       	  }
         });
	}
	
	@Override
	protected String validateForm() {
    	Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
    	if(getCurState().getIsEdit() && props.get("oldPassword") == null ){
    		props.put("oldPassword", "");
    	}
		return AdminUserForm.validate(props);
	}
}
