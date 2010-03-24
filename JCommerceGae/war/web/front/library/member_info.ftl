<div id="append_parent"></div>
<#if ( userInfo??  ) >
<font style="position:relative; top:10px;">
${lang.hello}，<font class="f4_b">${userInfo.username}</font>, ${lang.welcomeReturn}！
<a href="user.action">${lang.userCenter}</a>|
 <a href="user.action?act=logout">${lang.userLogout}</a>
</font>
<#else>
 ${lang.welcome}&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="user.action"><img src="images/bnt_log.gif" /></a>
 <a href="user.action?act=register"><img src="images/bnt_reg.gif" /></a>
</#if>