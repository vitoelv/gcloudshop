package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.core.model.UserRank;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.form.UserForm;
import com.jcommerce.gwt.client.model.IModelObject;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.util.GWTFormatUtils;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class UserPanel extends BaseEntityEditPanel {    
	
	public static interface Constants {
        String User_title();
        String User_edituser();
        String User_username();
        String User_usernameexist();
        String User_email();
        String User_emailexist();
        String User_password();
        String User_confirmpassword();
        String User_userrank();
        String User_sex();
        String User_secrecy();
        String User_male();
        String User_female();
        String User_creditline();
        String User_birthday();
        String User_MSN();
        String User_QQ();
        String User_officephone();
        String User_homephone();
        String User_mobilephome();
        String User_wrongpassword();
        String User_wrongqq();
        String User_wrongphone();
        String User_wrongemail();
        String User_addSuccessfully();
        String User_modifySuccessfully();
    }
	
	@Override
	public String getEntityClassName() {
		return ModelNames.USER; 
	}
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.UserList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		UserListPanel.State newState = new UserListPanel.State();
		newState.execute();
    }

	RadioGroup sex;
	DateField birthday;
	TextField<String> password;
	TextField<String> confirmPassword;
	TextField<String> userName;
	TextField<String> email;
	Criteria criteria = new Criteria();
	
	
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return UserPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.User_title();
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
    /**
     * Initialize this example.
     */
    public static UserPanel getInstance() {
    	if(instance==null) {
    		instance = new UserPanel();
    	}
    	return instance;
    }
    private static UserPanel instance;
    private UserPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())
			return Resources.constants.User_title();
		else
			return Resources.constants.User_edituser();    	
    }
    

    
    @Override
    public void setupPanelLayout() {
        System.out.println("----------UserRankPanel");

        userName = UserForm.getNameField(Resources.constants.User_username());
        userName.setFieldLabel(Resources.constants.User_username());
        formPanel.add(userName, sfd());
        
        email = UserForm.getEmailField(Resources.constants.User_email());
        email.setFieldLabel(Resources.constants.User_email());
        formPanel.add(email, sfd());
        
        password = UserForm.getPasswordField(Resources.constants.User_password());
        password.setFieldLabel(Resources.constants.User_password());
        formPanel.add(password, sfd());
        
        confirmPassword = UserForm.getPasswordField(Resources.constants.User_confirmpassword());
        confirmPassword.setFieldLabel(Resources.constants.User_confirmpassword());
        formPanel.add(confirmPassword, sfd());

        sex = new MyRadioGroup();
        sex.setFieldLabel(Resources.constants.User_sex());
        sex.setName(IUser.SEX);
        
        sex.setSelectionRequired(true);
        Radio secrecy = new Radio();
        secrecy.setName(IUser.SEX);
        secrecy.setValueAttribute("2");
        secrecy.setBoxLabel(Resources.constants.User_secrecy());
        sex.add(secrecy);
        
        Radio male = new Radio();
        male.setName(IUser.SEX);
        male.setValueAttribute("0");
        male.setBoxLabel(Resources.constants.User_male());
        sex.add(male);
        
        Radio female = new Radio();
        female.setName(IUser.SEX);
        female.setValueAttribute("1");
        female.setBoxLabel(Resources.constants.User_female());
        sex.add(female);        
		formPanel.add(sex, sfd());
		
		birthday = UserForm.getPromoteEndDateField();
		birthday.setPropertyEditor(new DateTimePropertyEditor(GWTFormatUtils.shortDateFormat()));
		birthday.setFieldLabel(Resources.constants.User_birthday());
		formPanel.add(birthday, sfd());
		
        NumberField fNum = UserForm.getCreditField();
        fNum.setFieldLabel(Resources.constants.User_creditline());
        formPanel.add(fNum, tfd());
        
        TextField<String> fText = UserForm.getMsnField();
        fText.setFieldLabel(Resources.constants.User_MSN());
        formPanel.add(fText, sfd());
        
        fText = UserForm.getQqField();
        fText.setFieldLabel(Resources.constants.User_QQ());
        formPanel.add(fText, sfd());
        
        fText = UserForm.getOfficePhoneField();
        fText.setFieldLabel(Resources.constants.User_officephone());
        formPanel.add(fText, sfd());
        
        fText = UserForm.getHomePhomeField();
        fText.setFieldLabel(Resources.constants.User_homephone());
        formPanel.add(fText, sfd());
        
        fText = UserForm.getMobilePhomeField();
        fText.setFieldLabel(Resources.constants.User_mobilephome());
        formPanel.add(fText, sfd());
        
        fText = UserForm.getQuestionField();
        formPanel.add(fText, sfd());
        fText = UserForm.getAnswerField();
        formPanel.add(fText, sfd());
        fText = UserForm.getUserMoneyField();
        formPanel.add(fText, sfd());
        fText = UserForm.getFrozenMoneyField();
        formPanel.add(fText, sfd());
        fText = UserForm.getAddressIdField();
        formPanel.add(fText, sfd());
        fText = UserForm.getRegTimeField();
        formPanel.add(fText, sfd());
        fText = UserForm.getLastLoginField();
        formPanel.add(fText, sfd());
        fText = UserForm.getLastIpField();
        formPanel.add(fText, sfd());
    }      
    
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
        			
        			//编辑会员信息时，不能修改会员名称,清空密码
        	        userName.setEnabled(false);
        	        password.setRawValue("");
        	        confirmPassword.setRawValue("");
        		}
        	});
	}
    
	@Override
    public void postSuperRefresh() {    	
		userName.setEnabled(true);		
    }
    
    @Override
    public void submit() {
    	//判断两次输入的密码是否一致
    	if(!password.getValue().equals(confirmPassword.getValue())) {
    		Window.alert(Resources.constants.User_wrongpassword());    		
    	}
    	//如果是判断用户名和邮箱是否已存在
    	else if(!getCurState().getIsEdit()) {
	    	criteria.removeAllConditions();
	    	criteria.addCondition(new Condition(IUser.USER_NAME, Condition.EQUALS, userName.getValue()));
	    	new ListService().listBeans(ModelNames.USER, criteria, new ListService.Listener() {
	    		public void onSuccess(List<BeanObject> beans) {
	    			if(beans.size() == 0) {
	    				criteria.removeAllConditions();
	    				criteria.addCondition(new Condition(IUser.EMAIL, Condition.EQUALS, email.getValue()));
	    				new ListService().listBeans(ModelNames.USER, criteria, new ListService.Listener() {
	    					public void onSuccess(List<BeanObject> beans) {
	    						if(beans.size() > 0) {
	    							tipExist("email");
	    						}
	    						else
	    							doSubmit();
	    					}
	    				});
	    			}
	    			else {
	    				tipExist("username");
	    			}
	    		}
	    	});
    	}
    	else {
    		//判断email是否修改，如果修改验证修改后的email是否已存在
    		if(email.isDirty()) {
	    		criteria.removeAllConditions();
		    	criteria.addCondition(new Condition(IUser.EMAIL, Condition.EQUALS, email.getValue()));
		    	new ListService().listBeans(ModelNames.USER, criteria, new ListService.Listener() {
		    		public void onSuccess(List<BeanObject> beans) {
		    			if(beans.size() == 0) {
		    				doSubmit();
		    			}
		    			else {
		    				tipExist("email");
		    			}
		    		}
		    	});
    		}
    		else
    			doSubmit();
    	}
    }
    
    //保存会员信息
    public void doSubmit() {
    	
    	//获得上次登录时间
		Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
		Map<String, Object> mapAttribute = obj.getProperties();
		Date lastTime = (Date) mapAttribute.get("lastTime");
		props.put("lastTime", lastTime);
		
    	BeanObject form = new BeanObject(getEntityClassName(), props);
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getPkId();
          new UpdateService().updateBean(id, form, new UpdateService.Listener() {
        	  public synchronized void onSuccess(Boolean success) {
        		  gotoSuccessPanel();
        	  }
        	  public void onFailure(Throwable caught) {
        		  // TODO a point to define common behavior
        	  }
          });
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
    
    //如果会员名或者email已存在，进入相应提示页面
    public void tipExist(String repeat) {
    	Success.State newState = new Success.State();
    	
    	if(repeat.equals("username")) { 
    		newState.setMessage(Resources.constants.User_usernameexist());
    	}
    	else if(repeat.equals("email")) {
    		newState.setMessage(Resources.constants.User_emailexist());
    	}
    	
    	UserPanel.State choice1 = new UserPanel.State();
    	newState.addChoice(UserPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
    }
	
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();

    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.User_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.User_modifySuccessfully());
    	}
    	
    	UserListPanel.State choice1 = new UserListPanel.State();
    	newState.addChoice(UserListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}
}
