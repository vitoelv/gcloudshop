package com.jcommerce.gwt.client.panels.member;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CommentForm;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.model.IFeedback;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

public class ReplyMsgPanel extends ContentWidget {

	public static interface Constants {
		String ReplyMsg_title();
		String ReplyMsg_reply();
		String ReplyMsg_replyContent();
	}
	public interface Message extends Messages {
		String ReplyMsg_adminReply(String adminName,String time);
	}
	
	private LayoutContainer basePanel = new LayoutContainer();
	private FormPanel formPanel = new FormPanel();
	private ContentPanel feedbackPanel = new ContentPanel();
	private ContentPanel replyPanel = new ContentPanel();
	private BeanObject feedback = new BeanObject();
	private BeanObject reply = new BeanObject();
	Button btnNew = new Button();     
	TextField<String> userNameField ;
	TextField<String> emailField ;
	TextField<String> ipAddressField ;
	
	public static class State extends PageState {

		public static final String PK_ID = "pkId";
		
		public void setPkId(String gtid) {
			setValue(PK_ID, gtid);
		}
		public String getPkId() {
			return (String)getValue(PK_ID);
		}
		public String getPageClassName() {
			return ReplyMsgPanel.class.getName();
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}
	public void setCurState(State curState) {
		this.curState = curState;
	}

	private static ReplyMsgPanel instance;
    private ReplyMsgPanel() { 
    }
    public static ReplyMsgPanel getInstance() {
    	if(instance == null) {
    		instance = new ReplyMsgPanel();
    	}
    	return instance;
    }
    
    @Override
    protected void onRender(Element parent, int index) {

    	super.onRender(parent, index);
    	basePanel.setLayout(new BorderLayout());
    	basePanel.setStyleAttribute("padding", "10px");
    	super.add(basePanel);

    	FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
    	
    	formPanel.setLayout(fl);
        initCommentLayout();
		initReplyLayout();

		btnNew.setText(Resources.constants.ok());        
      
	    formPanel.setButtonAlign(HorizontalAlignment.CENTER);
	    formPanel.addButton(btnNew);
	      
	    btnNew.addSelectionListener(selectionListener);	
      
    }
    
    private void setupPanelLayout(LayoutRegion pos) {
    	if( formPanel.getFields().size() > 0 ){
    		formPanel.clear();
    	}
    	else {
	    	formPanel.setHeading(Resources.constants.ReplyMsg_reply());
	        	    	
	        emailField = new TextField<String>();
	        emailField.setName(IFeedback.USER_EMAIL);
	        emailField.setFieldLabel("email");
	        formPanel.add(emailField);
	        
	        TextArea contentField = new TextArea();
	        contentField.setName(IFeedback.MSG_CONTENT);
	        contentField.setAllowBlank(false);
	        contentField.setHeight("60px");
	        contentField.setWidth("180px"); 
	        contentField.setFieldLabel(Resources.constants.ReplyMsg_replyContent());
	        contentField.setValue("");
	        formPanel.add(contentField); 	
	        
    	}
    	
    	BorderLayoutData formData = new BorderLayoutData(pos, 250);  
    	formData.setMargins(new Margins(5, 0, 5, 0)); 
    	basePanel.add( formPanel ,formData);
	}
	private void setupReplyLayout() {
		replyPanel.removeAll();
		RemoteService.getSpecialService().getAdminUserInfo(new AsyncCallback<Map<String,String>>(){

			public void onFailure(Throwable caught) {
				replyPanel.setHeading(Resources.messages.ReplyMsg_adminReply("admin", new Timestamp((Long)reply.get(IFeedback.MSG_TIME)).toString()));
			}

			public void onSuccess(Map<String, String> result) {
				String adminName = result.get(IAdminUser.USER_NAME) == null ? "admin" : result.get(IAdminUser.USER_NAME);
				replyPanel.setHeading(Resources.messages.ReplyMsg_adminReply(adminName, new Timestamp((Long)reply.get(IFeedback.MSG_TIME)).toString()));
			}
        	
        });
		
    	TableLayout tl = new TableLayout();
		tl.setWidth("100%");
        tl.setCellSpacing(5);
        replyPanel.setLayout(tl);
        
        TableData td = new TableData();
        td.setWidth("100%");
        Label content = new Label();
        content.setText(reply.getString(IFeedback.MSG_CONTENT));
        replyPanel.add(content,td);
		
		BorderLayoutData replyData = new BorderLayoutData(LayoutRegion.CENTER ,150);
		replyData.setMargins(new Margins(5,0,5,0));
		basePanel.add( replyPanel , replyData );
	}
	private void setupCommentLayout() {
		feedbackPanel.removeAll();
		
		TableLayout tl = new TableLayout();
		tl.setWidth("100%");
        tl.setCellSpacing(5);
        feedbackPanel.setLayout(tl);
		feedbackPanel.setHeading((String) feedback.get(IFeedback.MSG_TITLE));
		
		TableData td = new TableData();
        td.setWidth("100%");
        Label content = new Label();
        content.setText(feedback.getString(IFeedback.MSG_CONTENT));
        feedbackPanel.add(content,td);

		td = new TableData();
        td.setWidth("100%");
        td.setHorizontalAlign(HorizontalAlignment.RIGHT);
        Label msgInfo = new Label();
        msgInfo.setText(feedback.getString(IFeedback.USER_NAME) + "@" + new Timestamp((Long)feedback.get(IFeedback.MSG_TIME)).toString());
        feedbackPanel.add(msgInfo , td);
        
		BorderLayoutData feedbackData = new BorderLayoutData(LayoutRegion.NORTH, 100);  
		feedbackData.setMargins(new Margins(5, 0, 5, 0)); 
		
		basePanel.add(feedbackPanel,feedbackData);
	}
	    
	protected SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
	    	try {
	    	if(!formPanel.isValid()) {
	    		Window.alert("Please check input before submit.");
	    		return;
	    	}

	    	submit();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	
	};
	

	private void submit() {
		Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
		BeanObject form = new BeanObject(ModelNames.FEEDBACK, props);
		form.set(IFeedback.MSG_TIME, System.currentTimeMillis());
		form.set(IFeedback.PARENT_ID, getCurState().getPkId());
		form.set(IFeedback.USER_EMAIL, emailField.getValue() == null ? "" : emailField.getValue());
		
		reply.set(IFeedback.MSG_CONTENT, form.get(IFeedback.MSG_CONTENT));
		reply.set(IFeedback.USER_EMAIL, form.get(IFeedback.USER_EMAIL));
		reply.set(IFeedback.MSG_TIME, System.currentTimeMillis());
		
		if(reply.get(IFeedback.PK_ID) != null ){
			new UpdateService().updateBean((String)reply.get(IFeedback.PK_ID), reply, new UpdateService.Listener(){

				@Override
				public void onSuccess(Boolean success) {
					basePanel.remove(replyPanel);
					basePanel.remove(formPanel);
					refresh();
					
				}
				
			});
		}
		else{
	        new CreateService().createBean(form, new CreateService.Listener() {
	          public synchronized void onSuccess(String id) {
	        	  log("new onSuccess( "+id); 
	        	  basePanel.remove(formPanel);
	              refresh();
	          }
	        });
		}
		
	}

    public Button getShortCutButton() {
      Button buttonAddClone = new Button(Resources.constants.UserMsgList_menuName());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    
    public void onButtonListClicked() {
		UserMsgListPanel.State newState = new UserMsgListPanel.State();
		newState.execute();
    }

	
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	
	public String getName() {
		return Resources.constants.ReplyMsg_title();
	}
	private void initCommentLayout(){
		new ReadService().getBean(ModelNames.FEEDBACK, getCurState().getPkId() , new ReadService.Listener(){
			public void onSuccess(BeanObject bean) {
				feedback = bean;
				setupCommentLayout();
				basePanel.layout();
			}
	        
	        public void onFailure(Throwable caught) {
	        	feedback = new BeanObject();
	        }
		});
	}
	private void initReplyLayout(){
		new ListService().listBeans(ModelNames.FEEDBACK, IFeedback.PARENT_ID, getCurState().getPkId(), new ListService.Listener(){
			@Override
	        public void onFailure(Throwable caught) {
	        	reply = new BeanObject();
	        }

			@Override
			public void onSuccess(List<BeanObject> beans) {
				if( beans.size() > 0 ){
					reply = beans.get(0);
					setupReplyLayout();
					setupPanelLayout(LayoutRegion.SOUTH);
					basePanel.setHeight(500);
					basePanel.layout();
				} else {
					setupPanelLayout(LayoutRegion.SOUTH);
					basePanel.setHeight(500);
					basePanel.layout();
				}
			}
		});
	}
	
	public void refresh() {
    	try {
    		basePanel.removeAll();
    		initCommentLayout();
    		initReplyLayout();
    		basePanel.repaint();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

    }

}