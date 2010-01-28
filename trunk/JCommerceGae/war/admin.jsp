<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
  <%
	//NO, we do not allow directly access without going thru login.jsp
	// TODO: add filter to check user token in session, apply to all .do and all .jsp URL  
  
  	String locale = (String)request.getSession().getAttribute(com.jcommerce.gwt.server.IAdminConstants.KEY_LOCALE);
  	if(!("en".equals(locale) || "zh".equals(locale))){
  		// TODO redirect to a page allowing selection of language. 
	  	throw new RuntimeException("Go thru login.jsp first, please");
  	} 
  	
  	// this is necessary to keep the devserver info to logout correctly
	String key = com.jcommerce.gwt.server.IAdminConstants.KEY_GWT_DEV_SERVER;
	String devServer = request.getParameter(key);
	if(devServer==null || devServer.length()==0) {
		//devServer = (String)request.getSession().getAttribute(key);
	} else {
		// comes from dev_home.jsp (dev mode)
		request.getSession().setAttribute(key, devServer);
	}
	%>
	
	<meta name='gwt:property' content='locale=<%=locale%>'">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <!--                                                               -->
    <!-- Consider inlining CSS to reduce the number of requested files -->
    <!--                                                               -->
    <!--link rel="stylesheet" type="text/css" href="jcommercegae/css/ext-all.css" /-->
		<link rel="stylesheet" type="text/css" href="resources/css/gxt-all.css" />
		
    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>Web Application Starter Project</title>
    
    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                -->
    <!--                                           -->
    <script type="text/javascript" language="javascript" src="admin/admin.nocache.js"></script>
    
  </head>

  <!--                                           -->
  <!-- The body can have arbitrary html, or      -->
  <!-- you can leave the body empty if you want  -->
  <!-- to create a completely dynamic UI.        -->
  <!--                                           -->
  <body>

    <!-- OPTIONAL: include this if you want history support -->
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>

  </body>
</html>
