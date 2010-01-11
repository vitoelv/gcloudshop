package example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import com.jcommerce.web.front.action.BaseAction;

public class DynaImageServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(BaseAction.class.getName());
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
    	log.info("==========================DynaImageServlet doGet()==================================");
        try {
            IDefaultManager manager = getDefaultManager();
            
            String fileId = request.getParameter("fileId");
            log.info("fileId: "+fileId);
//            if(StringUtils.isNotEmpty(fileId) && !fileId.equals("null") ) {
            	writeImage(response, manager, fileId);
            	return;
//            }
            
            
            
            // test purpose only
//            List<Brand> res = new ArrayList<Brand>();
//            manager.getList(res, Brand.class.getName(), null, -1, -1);
//            String logoFileId = null;
//            for(Brand brand:res) {
//            	logoFileId = brand.getLogoFileId();
////            	String fileName = brand.getLogo();
////            	String name = brand.getName();
////            	System.out.println("name: "+name+", fileName: "+fileName+", fileId: "+logoFileId);
////            	if(!StringUtils.isEmpty(logoFileId) && "bbb".equals(name)) {
////            		break;
////            	}
//            	
//            }
//            
//            if(logoFileId!=null) {
//            	writeImage(response, manager, logoFileId);
//            	
//            }
//            else {
//            	response.sendRedirect("/jcommercegae/gwt/standard/images/corner.png");
//            }
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
    }

	private void writeImage(HttpServletResponse response,
			IDefaultManager manager, String logoFileId) throws IOException {
		DSFile dsFile = (DSFile)manager.get(DSFile.class.getName(), logoFileId);
		log.info("dsFile: "+dsFile);
		response.setHeader("Content-Type", "image/gif");
		if( dsFile == null ){
			 InputStream is = this.getClass().getResourceAsStream("noPicture.gif");  
			 int leng = is.available();  
			 BufferedInputStream buff = new BufferedInputStream(is);  
			 byte[] mapObj = new byte[leng];  
			 buff.read(mapObj, 0, leng);  
			 response.getOutputStream().write(mapObj);
		}
		else {
			byte[] content = dsFile.getContent().getBytes();
			response.getOutputStream().write(content);
		}		
	}
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("==========================DynaImageServlet doPost()==================================");
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
        	writeImage(response, manager, logoFileId);
        	
        }
        else {
        	response.sendRedirect("/jcommercegae/gwt/standard/images/corner.png");
        }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        
    }
}
