package com.jcommerce.gwt.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.ConvertUtil;
import com.jcommerce.gwt.client.form.FileForm;

public class GWTHttpServlet extends HttpServlet {

	WebApplicationContext ctx = null;

	@Override
	public void init() {
		ctx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	public IDefaultManager getDefaultManager() {
		return (IDefaultManager) ctx.getBean("DefaultManager");
	}

	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager) ctx.getBean("CustomizedManager");
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
		debug("==========================UploadFileServlet doPost()==================================");

		int contentLength = request.getContentLength();
		debug("contentLength: " + contentLength);
		if (contentLength < 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		try {

			String action = request.getParameter("XXAction");
			String method = request.getParameter("XXMethod");
			System.out.println("XXAction=" + action);
			System.out.println("XXMethod=" + method);

			// TODO match it to impl via spring
			// ctx.getBean(action);
			Method m = null;
			BaseGWTHttpAction gwtAction = null;
			try {
				Class[] paras = new Class[] { Map.class };
				gwtAction = (BaseGWTHttpAction) Class.forName(action)
						.newInstance();
				gwtAction.setCtx(ctx);
				m = gwtAction.getClass().getMethod(method, paras);

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

			Map<String, Object> form = new HashMap<String, Object>();

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				for(Enumeration en = request.getParameterNames();en.hasMoreElements(); ) {
					String key = (String)en.nextElement();
					if(key.equals("XXAction") || key.endsWith("XXMethod")) {
						continue;
					}
					String[] values = request.getParameterValues(key);
					if(values !=null) {
						if(values.length==1) {
							form.put(key, values[0].trim());
						}
						else {
							form.put(key, Arrays.asList(values));
						}
					}
				}
				 
			} else {

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
						String val = Streams.asString(stream);
						debug("Form field " + name
								+ " with value " + val + " detected.");

						// TODO escape of "," ??
						if(val.indexOf(",")>0) {
							String[] values = ConvertUtil.split(val, ",");
//							form.put(name, Arrays.asList(values));
							value = Arrays.asList(values);
						} else {
//							form.put(name, val);
							value = val;
						}
						

						
					} else {
						debug("File field " + name
								+ " with file name " + item.getName()
								+ " detected, contentType="
								+ item.getContentType());

						// Process the input stream

						FileForm file = null;

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						// only write files out that are less than 1MB
						if (contentLength < (4 * 1024000)) {

							byte[] buffer = new byte[8192];
							int bytesRead = 0;
							while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
								baos.write(buffer, 0, bytesRead);
							}
							byte[] bytes = baos.toByteArray();
							if (contentLength < 1024) {
								data = new String(bytes);
							}

							file = new FileForm(name);
							file.setContent(bytes);
							file.setFileName(item.getName());
							file.setMimeType(item.getContentType());

						} else {
							data = new String(
									"The file is greater than 4MB, "
											+ " and has not been written to stream."
											+ " contentLength: "
											+ contentLength
											+ " bytes. This is a"
											+ " limitation of this particular web application, hard-coded"
											+ " in org.apache.struts.webapp.upload.UploadAction");
						}

						System.out.println("data: " + data);
						value = file;
//						form.put(name, file);
					} // end of else
					
					
					
					int index = name.indexOf(".");
					if(index>=0) {
						// nested form 
						String nestFormName = name.substring(0,index);
						String fn = name.substring(index+1);
						
						int i2 = nestFormName.indexOf("[");
						if(i2>=0) {
							// nested multiple form
							String k = nestFormName;
							nestFormName = nestFormName.substring(0, i2);
							Map<String, Map> nestedForms = (Map<String, Map>)form.get(nestFormName);
							if(nestedForms==null) {
								nestedForms = new HashMap<String, Map>();
								form.put(nestFormName, nestedForms);
							}
							Map<String, Object> nestedForm = nestedForms.get(k);
							if(nestedForm==null) {
								nestedForm = new HashMap<String, Object>();
								nestedForms.put(k, nestedForm);
							}
							nestedForm.put(fn, value);
							
						}else {
							Map<String, Object> nestedForm = (Map<String, Object>)form.get(nestFormName);
							if(nestedForm==null) {
								nestedForm = new HashMap<String, Object>();
								form.put(nestFormName, nestedForm);
							}
							nestedForm.put(fn, value);
						}

					} else {
						form.put(name, value);
					}
					
				}

			}

			for(String key:form.keySet()) {
				debug("key="+key+", value="+form.get(key)+", valueclass="+form.get(key).getClass().getName());
			}
			
			// special handling for GXT-ComboBox
			for(String name:form.keySet()) {
				if(name.endsWith("-hidden")) {
					String orginalName = name.substring(0, name.indexOf("-hidden"));
					debug("orginalName: "+orginalName);
					// override the old value
					form.put(orginalName, form.get(name));
				}
			}
			

			
			Object[] ref = new Object[] { form };
			m.invoke(gwtAction, ref);
			// process(form);

		} catch (Exception e) {
			e.printStackTrace();
			processError(response, e.getMessage());
		}
		
		processSuccess(response);
		System.out.println("==========================end of UploadFileServlet doPost()==================================");

	}

	public void processError(HttpServletResponse response, String error) {
		try {
			response.getWriter().print(error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processSuccess(HttpServletResponse response) {
		try {
			response.getWriter().print("0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void debug(String s) {
		System.out.println("[GWTHttpServlet]: "+s);
	}
}
