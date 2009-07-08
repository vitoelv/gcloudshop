package example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;

public class DynaImageServlet extends HttpServlet {
	
	WebApplicationContext ctx = null;
	
	@Override
	public void init() {
		ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}
	
	public IDefaultManager getDefaultManager() {
		return (IDefaultManager)ctx.getBean("DefaultManager");
	}
	
	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager)ctx.getBean("CustomizedManager");
	}
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("==========================DynaImageServlet doGet()==================================");
        try {
            IDefaultManager manager = getDefaultManager();
            
            List<Brand> res = new ArrayList<Brand>();
            manager.getList(res, Brand.class.getName(), null, -1, -1);
            String logoFileId = null;
            for(Brand brand:res) {
            	logoFileId = brand.getLogoFileId();
//            	String fileName = brand.getLogo();
//            	String name = brand.getName();
//            	System.out.println("name: "+name+", fileName: "+fileName+", fileId: "+logoFileId);
//            	if(!StringUtils.isEmpty(logoFileId) && "bbb".equals(name)) {
//            		break;
//            	}
            	
            }
            
            if(logoFileId!=null) {
            	DSFile dsFile = (DSFile)manager.get(DSFile.class.getName(), logoFileId);
            	byte[] content = dsFile.getContent().getBytes();
            	response.setHeader("Content-Type", "image/jpeg");
            	response.getOutputStream().write(content);
            	
            }
            else {
            	response.sendRedirect("/jcommercegae/gwt/standard/images/corner.png");
            }
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
    }
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("==========================DynaImageServlet doPost()==================================");
        try {
        IDefaultManager manager = getDefaultManager();
        
        List<Brand> res = new ArrayList<Brand>();
        manager.getList(res, Brand.class.getName(), null, -1, -1);
        String logoFileId = null;
        for(Brand brand:res) {
        	logoFileId = brand.getLogoFileId();
        	if(!StringUtils.isEmpty(logoFileId)) {
        		break;
        	}
        }
        
        if(logoFileId!=null) {
        	DSFile dsFile = (DSFile)manager.get(DSFile.class.getName(), logoFileId);
        	byte[] content = dsFile.getContent().getBytes();
        	response.setHeader("Content-Type", "image/jpeg");
        	response.getOutputStream().write(content);
        	
        }
        else {
        	response.sendRedirect("/jcommercegae/gwt/standard/images/corner.png");
        }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        
    }
}
