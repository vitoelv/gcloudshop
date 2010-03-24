<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}</title>
<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/user.js"></script>
<script type="text/javascript" src="js/transport.js"></script>

</head>
<body>
<#include "library/page_header.ftl">
<!--当前位置 start-->
<div class="block box">
 <div id="ur_here">
  <#include "library/ur_here.ftl">
  </div>
</div>
<!--当前位置 end-->
<div class="blank"></div>
<!--#登录界面 start-->
<#if ( action == 'login'  ) >
<div class="usBox clearfix">
  <div class="usBox_1 f_l">
   <div class="logtitle"></div>
   <form name="formLogin" action="user.action" method="post" onSubmit="return userLogin()">
        <table width="100%" border="0" align="left" cellpadding="3" cellspacing="5">
          <tr>
            <td width="15%" align="right">${lang.labelUsername}</td>
            <td width="85%"><input name="username" type="text" size="25" class="inputBg" /></td>
          </tr>
          <tr>
            <td align="right">${lang.labelPassword}</td>
            <td>
            <input name="password" type="password" size="15"  class="inputBg"/>
            <a href="user.action?act=get_password" class="f3">${lang.getPassword}</a>
            </td>
          </tr>
          <#if ( enabledCaptcha??  ) >
          <tr>
            <td align="right">${lang.commentCaptcha}</td>
            <td><input type="text" size="8" name="captcha" class="inputBg" />
            <img src="captcha.action?is_login=1&${rand}" alt="captcha" style="vertical-align: middle;cursor: pointer;" onClick="this.src='captcha.action?is_login=1&'+Math.random()" /> </td>
          </tr>
          </#if>
          <tr>
            <td>&nbsp;</td>
            <td align="left">
            <input type="hidden" name="act" value="act_login" />
            <input type="hidden" name="back_act" value="${backAct}" />
            <input type="submit" name="submit" value="" class="us_Submit" />
            </td>
          </tr>
      </table>
    </form>
  </div>
  <div class="usTxt">
    <strong>${lang.userRegInfo[0]}</strong>  <br />
    <strong class="f4">${lang.userRegInfo[1]}：</strong><br />
    ${lang.userRegInfo[2]}<br />
    ${lang.userRegInfo[3]}：<br />
    1. ${lang.userRegInfo[4]}<br />
    2. ${lang.userRegInfo[5]}<br />
    3. ${lang.userRegInfo[6]}<br />
    4. ${lang.userRegInfo[7]}  <br />
    <a href="user.action?act=register"><img src="images/bnt_ur_reg.gif" /></a>
  </div>
</div>
</#if>
<!--#登录界面 end-->

<!--*会员注册界面 start-->
    <#if ( action == 'register'  ) >
    <#if ( shopRegClosed == 1  ) >
    <div class="usBox">
      <div class="usBox_2 clearfix">
        <div class="f1 f5" align="center">${lang.shopRegisterClosed}</div>
      </div>
    </div>
    <#else>
    <script type="text/javascript" src="js/utils.js"></script>

<div class="usBox">
  <div class="usBox_2 clearfix">
   <div class="regtitle"></div>
    <form action="user.action" method="post" name="formUser" onsubmit="return register();">
      <table width="100%"  border="0" align="left" cellpadding="5" cellspacing="3">
        <tr>
          <td width="11%" align="right">${lang.labelUsername}</td>
          <td width="89%">
          <input name="username" type="text" size="25" id="username" onblur="is_registered(this.value);" class="inputBg"/>
            <span id="username_notice" style="color:#FF0000"> *</span>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.labelEmail}</td>
          <td>
          <input name="email" type="text" size="25" id="email" onblur="checkEmail(this.value);"  class="inputBg"/>
            <span id="email_notice" style="color:#FF0000"> *</span>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.labelPassword}</td>
          <td>
          <input name="password" type="password" id="password" onblur="check_password(this.value);" onkeyup="checkIntensity(this.value)" class="inputBg" style="width:179px;" />
            <span style="color:#FF0000" id="password_notice"> *</span>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.labelPasswordIntensity}</td>
          <td>
            <table width="145" border="0" cellspacing="0" cellpadding="1">
              <tr align="center">
                <td width="33%" id="pwd_lower">${lang.pwdLower}</td>
                <td width="33%" id="pwd_middle">${lang.pwdMiddle}</td>
                <td width="33%" id="pwd_high">${lang.pwdHigh}</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.labelConfirmPassword}</td>
          <td>
          <input name="confirm_password" type="password" id="conform_password" onblur="check_conform_password(this.value);"  class="inputBg" style="width:179px;"/>
            <span style="color:#FF0000" id="conform_password_notice"> *</span>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.otherMsn}</td>
          <td>
          <input name="other[msn]" type="text" size="25" class="inputBg" />
           </td>
        </tr>
        <tr>
          <td align="right">${lang.otherQq}</td>
          <td>
          <input name="other[qq]" type="text" size="25" class="inputBg"/>
           </td>
        </tr>
        <tr>
          <td align="right">${lang.otherOfficePhone}</td>
          <td>
          <input name="other[office_phone]" size="25" type="text" class="inputBg"/>
           </td>
        </tr>
        <tr>
          <td align="right">${lang.otherHomePhone}</td>
          <td>
          <input name="other[home_phone]" type="text" size="25" class="inputBg" id="home_phone" onblur="check_home_phone(this.value);" />

          </td>
        </tr>
        <tr>
          <td align="right">${lang.otherMobilePhone}</td>
          <td>
          <input name="other[mobile_phone]" type="text" size="25" class="inputBg" />
           </td>
        </tr>
      <#if ( enabledCaptcha??  ) >
      <tr>
      <td align="right">${lang.commentCaptcha}</td>
      <td><input type="text" size="8" name="captcha" class="inputBg" />
      <img src="captcha.action?${rand}" alt="captcha" style="vertical-align: middle;cursor: pointer;" onClick="this.src='captcha.action?'+Math.random()" /> </td>
      </tr>
      </#if>
        <tr>
          <td>&nbsp;</td>
          <td><label>
            <input name="agreement" type="checkbox" value="1" checked="checked" />
            ${lang.agreement}</label></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td align="left">
          <input name="act" type="hidden" value="act_register" >
          <input name="Submit" type="submit" value="" class="us_Submit_reg">
          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td class="actionSub">
          <a href="user.action?act=login">${lang.wantLogin}</a><br />
          <a href="user.action?act=get_password">${lang.forgotPassword}</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>
</#if>
</#if>
<!--#会员注册界面 end-->

<!--*找回密码界面 -->
    <#if ( action == 'getPassword'  ) >
    <script type="text/javascript" src="js/utils.js"></script>

    <script type="text/javascript">
    <#list lang.passwordJs?keys as key> <#assign item = lang.passwordJs.get(key)>
      var ${key} = "${item}";
    </#list>
    </script>
<div class="usBox">
  <div class="usBox_2 clearfix">
    <form action="user.action" method="post" name="getPassword" onsubmit="return submitPwdInfo();">
        <br />
        <table width="70%" border="0" align="center">
          <tr>
            <td colspan="2" align="center"><strong>${lang.usernameAndEmail}</strong></td>
          </tr>
          <tr>
            <td width="29%" align="right">${lang.username}</td>
            <td width="61%"><input name="user_name" type="text" size="30" class="inputBg" /></td>
          </tr>
          <tr>
            <td align="right">${lang.email}</td>
            <td><input name="email" type="text" size="30" class="inputBg" /></td>
          </tr>
          <tr>
            <td></td>
            <td><input type="hidden" name="act" value="send_pwd_email" />
              <input type="submit" name="submit" value="${lang.submit}" class="bnt_blue" style="border:none;" />
              <input name="button" type="button" onclick="history.back()" value="${lang.backPageUp}" style="border:none;" class="bnt_blue_1" />
            </td>
          </tr>
        </table>
        <br />
      </form>
  </div>
</div>
</#if>

<#if ( action == 'resetPassword'  ) >
    <script type="text/javascript">
    <#list lang.passwordJs?keys as key> <#assign item = lang.passwordJs.get(key)>
      var ${key} = "${item}";
    </#list>
    </script>
<div class="usBox">
  <div class="usBox_2 clearfix">
    <form action="user.action" method="post" name="getPassword2" onSubmit="return submitPwd()">
      <br />
      <table width="80%" border="0" align="center">
        <tr>
          <td>${lang.newPassword}</td>
          <td><input name="new_password" type="password" size="25" class="inputBg" /></td>
        </tr>
        <tr>
          <td>${lang.confirmPassword}:</td>
          <td><input name="confirm_password" type="password" size="25"  class="inputBg"/></td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <input type="hidden" name="act" value="act_edit_password" />
            <input type="hidden" name="uid" value="${uid}" />
            <input type="hidden" name="code" value="${code}" />
            <input type="submit" name="submit" value="${lang.confirmSubmit}" />
          </td>
        </tr>
      </table>
      <br />
    </form>
  </div>
</div>
</#if>
<!--#找回密码界面 end-->
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
<script type="text/javascript">
var process_request = "${lang.processRequest}";
<#list lang.passportJs?keys as key> <#assign item = lang.passportJs.get(key)>
var ${key} = "${item}";
</#list>
var username_exist = "${lang.usernameExist}";
</script>
</html>
