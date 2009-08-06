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
<div class="block">
  <h5><span>${lang.activityList}</span></h5>
  <div class="blank"></div>
   <#list list as val>
  <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
    <tr>
      <th bgcolor="#ffffff">${lang.labelActName}</th>
      <td colspan="3" bgcolor="#ffffff">${val.actName}</td>
    </tr>
    <tr>
      <th bgcolor="#ffffff">${lang.labelStartTime}</th>
      <td width="200" bgcolor="#ffffff">${val.startTime}</td>
      <th bgcolor="#ffffff">${lang.labelMaxAmount}</th>
      <td bgcolor="#ffffff">
        <#if  val.maxAmount  >  0  >
        ${val.maxAmount}
        <#else>
        ${lang.nolimit}
        </#if>
      </td>
    </tr>
    <tr>
      <th bgcolor="#ffffff">${lang.labelEndTime}</th>
      <td bgcolor="#ffffff">${val.endTime}</td>
      <th bgcolor="#ffffff">${lang.labelMinAmount}</th>
      <td width="200" bgcolor="#ffffff">${val.minAmount}</td>
    </tr>
    <tr>
      <th bgcolor="#ffffff">${lang.labelActRange}</th>
      <td bgcolor="#ffffff">
        ${val.actRange}
        <#if  val.actRange  !=  lang.farAll??  >
        :<br />
        <#list val.actRangeExt as ext>
        <a href="${val.program}${ext.id}" taget="_blank" class="f6"><span class="f_user_info"><u>${ext.name}</u></span></a>
        </#list>
        </#if>
      </td>
      <th bgcolor="#ffffff">${lang.labelUserRank}</th>
      <td bgcolor="#ffffff">
        <#list val.userRank as user>
        ${user}
        </#list>
      </td>
    </tr>
    <tr>
      <th bgcolor="#ffffff">${lang.labelActType}</th>
      <td colspan="3" bgcolor="#ffffff">
        ${val.actType}<#if  val.actType  !=  lang.fatGoods??  >${val.actTypeExt}</#if>
      </td>
    </tr>
    <#if  val.gift??  >
    <tr>
      <td colspan="4" bgcolor="#ffffff">
      <#list val.gift as goods>
      <table border="0" style="float:left;">
        <tr>
          <td align="center"><a href="goods.action?id=${goods.id}"><img src="${goods.thumb}" alt="${goods.name}" /></a></td>
        </tr>
        <tr>
          <td align="center"><a href="goods.action?id=${goods.id}" class="f6">${goods.name}</a></td>
        </tr>
        <tr>
          <td align="center">
            <#if  goods.price  >  0  >
            ${goods.price}${lang.unitYuan}
            <#else>
            ${lang.forFree}
            </#if>
          </td>
        </tr>
      </table>
      </#list>
      </td>
    </tr>
    </#if>
  </table>
  <div class="blank5"></div>
  </#list>
</div>
<div class="blank5"></div>
<!--帮助-->
<div class="block">
  <div class="box">
   <div class="helpTitBg clearfix">
    <#include "library/help.ftl">
   </div>
  </div>
</div>
<div class="blank"></div>
<!--帮助-->
<!--友情链接 start-->
<#if  imgLinks??  ||  txtLinks??  >
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="links clearfix">
    <#list imgLinks as link>
    <a href="${link.url}" target="_blank" title="${link.name}"><img src="${link.logo}" alt="${link.name}" border="0" /></a>
    </#list>
    <#if  txtLinks??  >
    <#list txtLinks as link>
    [<a href="${link.url}" target="_blank" title="${link.name}">${link.name}</a>]
    </#list>
    </#if>
  </div>
 </div>
</div>
</#if>
<!--友情链接 end-->
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
</html>
