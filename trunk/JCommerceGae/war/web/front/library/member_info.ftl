<div id="append_parent"></div>
<#if user_info??>
<font style="position:relative; top:10px;">
<@s.text name="hello"/>，<font class="f4_b"> ${user_info.name}</font>, <@s.text name="welcome_return"/>！
<a href="user.php"><@s.text name="user_center"/></a>|
 <a href="user.php?act=logout"><@s.text name="user_logout"/></a>
</font>
<#else>
 <@s.text name="welcome"/>&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="user.php"><img src="${template_root}/images/bnt_log.gif" /></a>
 <a href="user.php?act=register"><img src="${template_root}/images/bnt_reg.gif" /></a>
</#if>