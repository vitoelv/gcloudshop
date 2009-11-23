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
<div class="block clearfix">
  <!--left start-->
  <div class="AreaL">
<#include "library/cart.ftl">
<#include "library/category_tree.ftl">
 <#include "library/filter_attr.ftl">
 <#include "library/price_grade.ftl">
<!-- TemplateBeginEditable name="左边区域" -->
<#include "library/goods_related.ftl">
<!-- TemplateEndEditable -->
<!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
<!-- TemplateEndEditable -->
    <!--AD end-->
    <#include "library/history.ftl">
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
    <div class="box">
     <div class="box_1">
      <div style="border:4px solid #f1faff; background-color:#fff; padding:20px 15px;">
         <div class="tc" style="padding:8px;">
         <font class="f5 f6">${article.title?html}</font><br /><font class="f3">${article.author?html} / ${article.addTime}</font>
         </div>
         <#if  article.content??  >
          ${article.content}
         </#if>
         <#if  article.openType  ==  2  ||  article.openType  ==  1  ><br />
         <div><a href="${article.fileUrl}" target="_blank">${lang.relativeFile}</a></div>
          </#if>
         <div style="padding:8px; margin-top:15px; text-align:left; border-top:1px solid #ccc;">
         <!-- 上一篇文章 -->
          <#if  nextArticle??  >
            ${lang.nextArticle}:<a href="${nextArticle.url}" class="f6">${nextArticle.title}</a><br />
          </#if>
          <!-- 下一篇文章 -->
          <#if  prevArticle??  >
            ${lang.prevArticle}:<a href="${prevArticle.url}" class="f6">${prevArticle.title}</a>
          </#if>
         </div>
      </div>
    </div>
  </div>
  <div class="blank"></div>
  <#include "library/comments.ftl">

  </div>
  <!--right end-->
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
