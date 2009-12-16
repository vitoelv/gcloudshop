package com.jcommerce.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.ConvertUtil;
import com.jcommerce.core.util.DataStoreUtils;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.util.SpringUtil;

public class ImportExportServlet extends HttpServlet{
	
	void debug(String s) {
		System.out.println("[ImportExportServlet]: "+s);
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		debug("==========================ImportExportServlet doPost()==================================");
		int contentLength = request.getContentLength();
		debug("contentLength: " + contentLength);
		if (contentLength < 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		try {

			String action = request.getParameter("action");
			debug("action=" + action);
			
			if("import".equals(action)) {
				handleImport(request, response);
				processSuccess(response);
			}
			else if("export".equals(action)) {
				handleExport(request, response);
				
				
			}
			else if("clear".equals(action)) {
				handleClear(request, response);
				processSuccess(response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			processError(response, e.getMessage());
		}
		
		
		System.out.println("==========================end of UploadFileServlet doPost()==================================");

	}
	
	protected void handleImport(HttpServletRequest request,
			HttpServletResponse response) throws FileUploadException, ServletException, IOException {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			throw new RuntimeException("import should be multi-part request");
		}
		

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();
		String data = null;
		
		// Parse the request
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream item = iter.next();
			String name = item.getFieldName();
			InputStream stream = item.openStream();
			
			Object value = null;
			if (item.isFormField()) {
				String val = Streams.asString(stream, "UTF-8");
				debug("Form field " + name
						+ " with value " + val + " detected.");
				// TODO escape of "," ??
				if(val.indexOf(",")>0) {
					String[] values = ConvertUtil.split(val, ",");
					value = Arrays.asList(values);
				} else {
					value = val;
				}
			} else {
				debug("File field " + name
						+ " with file name " + item.getName()
						+ " detected, contentType="
						+ item.getContentType());

				DataStoreUtils.importDSFromZip(stream, SpringUtil.getDefaultManager());
				
				
			}
		}

				
		
		
		
	}
	protected void handleClear(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		IDefaultManager manager = SpringUtil.getDefaultManager();
		String[] modelNames = new String[]{
				ModelNames.AREAREGION, ModelNames.ARTICLE_CAT, ModelNames.ATTRIBUTE
				, ModelNames.BRAND, ModelNames.CART, ModelNames.CATEGORY
				, ModelNames.COLLECTGOOD, ModelNames.COMMENT, ModelNames.GOODS
				, ModelNames.GOODSATTR, ModelNames.GOODSGALLERY, ModelNames.GOODSTYPE
				, ModelNames.ORDERGOODS, ModelNames.ORDERINFO, ModelNames.PAYMENT
				, ModelNames.REGION, ModelNames.SESSION, ModelNames.SHIPPINGAREA
				, ModelNames.SHOPCONFIG, ModelNames.USER, ModelNames.USER_RANK
				, ModelNames.USERADDRESS, DSFile.class.getName()
				};
		
		for(String modelName : modelNames) {
			try {
				List<ModelObject> models = manager.getList(modelName, null);
				manager.txdeleteall(models);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			debug("in [handleClear]: successfully deleted model: "+modelName);
			
		}
		
		
	}
	
	
	protected void handleExport(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
	}	
	
	public void processSuccess(HttpServletResponse response) {
		try {
			response.getWriter().print("0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void processError(HttpServletResponse response, String error) {
		try {
			response.getWriter().print(error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
