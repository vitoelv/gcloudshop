package com.jcommerce.web.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PaymentRespondAction extends DispatchAction {

    public PaymentRespondAction() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * check the users about login
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return address that the result will go
     * @throws Exception
     */
    public ActionForward chinabank(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'chinabank' method...");
        }
        Enumeration enu = request.getAttributeNames();
        while(enu.hasMoreElements()) {
            String key = (String)enu.nextElement();
            Object value = request.getAttribute(key);
            System.out.println("key: "+key+", value: "+value);
        }
        request.setAttribute("result", "success!!!!!");

        return mapping.findForward("success");
    }
}
