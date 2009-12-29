<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GCShop Admin Login</title>
</head>
<style>
	#container {TEXT-ALIGN: center; vertical-align:middle;width:100%;height:100%;}
	#inner1 { MARGIN-RIGHT: auto; MARGIN-LEFT: auto; border:5px solid #CDDEEE; height:207px; width:394px; top:120px;position:relative;} 
	#inner2 { border:1px solid #348BDA;height:205px; width:392px;} 
	#title{height:38px;width:100%;}
	#form {height:207px;width:100%;background:url(resources/images/login_bg.gif) repeat-x 0 0;text-align:left;}
	#button {border:0px;width:50px;background:url(resources/images/loginBtn.png) no-repeat 0 0;width:50px;height:18px;padding-top:-1px;font-size:11px;font-family:Arial}
	#button:focus {border:0px;width:50px;background:url(resources/images/loginBtnFocus.png) no-repeat 0 0;}
	form {padding-top:40px;padding-left:98px}
	.label {font-size:11px; color:#FFFFFF; font-family:Arial;margin-bottom:4px;font-weight:bold}
	.input {margin:1px 0px 4px 0px;font-size:12px;nt-family:Arial;font-weight:bold}
	.inputbox { width:194px; border: 1px solid #9F9F9F; height:16px; margin-bottom:10px; font-size:11px; color:#000000; font-family:Arial; margin:1px;color:#7C7C7C}
	.inputbox:focus{border:2px solid #5B9BF2; margin:0px;color:black;}
	#errorPane {font-size:11px; color:red; font-family:Arial;margin-top:5px;width:100%;text-align:center;height:20px;}
	#bottomPane {height:20px;width:100%;text-align:right;};
  </style>
<body>
  <div id="container">
	<div id="inner1">
	<div id="inner2">
		<!-- div id="title">abc</div-->
		<div id="form">
			<form id = "login" method="post" autocomplete="off" accept-charset="UTF-8">
				<div class="label">User:</div>
				<div class="input"><input class="inputbox" type="text" id="userName" name="userName"/></div>
				<div class="label">Password:</div>
				<div class="input"><input class="inputbox" type="password" id="userPwd" name="userPwd"/></div>
			  <div><input id="button" type="submit" value="Login" title="Log into GCShop Admin"/>&nbsp;&nbsp;
			    <input id="button" type="reset" valign="Reset" title="Reset credentials"/> </div>
			</form>
			<div id="errorPane" class="label">
				<% String error = (String)request.getAttribute("error");
					if(error == null) error = "";%>
				<%=error%>
			</div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
  document.getElementById("userName").focus();
  var loginForm = document.forms[0];
  document.onkeydown = function(){
	if(event.keyCode == 13){
		loginForm.submit();
	}
  }
  loginAction = "/adminLogin.do";
  loginForm.action = loginAction;
</script>
</body>
</html>