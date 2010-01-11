<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
</head>

<%
// This is necessary to be allowed as entrance page in dev mode
	String key = com.jcommerce.gwt.server.IAdminConstants.KEY_GWT_DEV_SERVER;
	String devServer = request.getParameter(key);

	
%>

<body>
<p><a href="/site/zh/index.html">Goto site homepage...</a></p>
<p><a href="/login.jsp?gwt.codesvr=<%=devServer%>&locale=zh">Goto administration login page (zh)...</a></p>
<p><a href="/login.jsp?gwt.codesvr=<%=devServer%>&locale=en">Goto administration login page (en)...</a></p>
<p><a href="/admin_zh.html?gwt.codesvr=<%=devServer%>">Goto administration (zh) homepage (skip login)...</a></p>
<p><a href="/admin.html?gwt.codesvr=<%=devServer%>">Goto administration (en) homepage (skip login)...</a></p>
<p><a href="/web/front/home.action">Goto public homepage...</a></p>
<p><a href="/_ah/admin">Goto DataViewer on Dev Server...</a></p>
</body>
</html>
