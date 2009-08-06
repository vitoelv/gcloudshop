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
  <div class="box">
   <div class="box_1">
    <h3><span>${lang.allTags}</span></h3>
    <div class="boxCenterList RelaArticle">
      <p class="f_red" style="text-decoration:none;">&nbsp;&nbsp; ${lang.tagCloudDesc} &nbsp;&nbsp;</p>
    <#if  tags??  >
          <#list tags as tag>
          <span style="font-size:${tag.size}; line-height:36px;"> <a href="${tag.url}" style="color:${tag.color}">
          <#if  tag.bold??  >
          <b>${tag.tagWords?html}</b>
          <#else>
          ${tag.tagWords?html}
          </#if>
          </a>
          <#if  tagsFrom  ==  'user'  >
          <a href="user.action?act=act_del_tag&amp;tag_words=${tag.tagWords}&amp;uid=${tag.userId}" title="${lang.drop}"> <img src="images/drop.gif" alt="${lang.drop}" /> </a>&nbsp;&nbsp;
          </#if>
          </span>
          </#list>
          <#else>
          <span style="margin:2px 10px; font-size:14px; line-height:36px;">${lang.noTag}</span>
          </#if>
    </div>
   </div>
  </div>
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
