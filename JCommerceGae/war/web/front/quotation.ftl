<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<meta name="Description" content="${description}" />
<#if  autoRedirect??  >
<meta http-equiv="refresh" content="3;URL=${message.href}" />
</#if>
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
  <div class="flowBox">
    <h6><span>${lang.printQuotation}</span></h6>
    <form action="quotation.action" method="post" name="searchForm" target="_blank" class="quotation">
      <!-- 分类 -->
      <select name="cat_id"><option value="0">${lang.allCategory}</option>${catList}</select>
      <!-- 品牌 -->
      <select name="brand_id"><option value="0">${lang.allBrand}</option>TODO: htmlOptions CLAUSE</select>
      <!-- 关键字 -->
      ${lang.keywords} <input type="text" name="keyword" class="inputBg"/>
      <!-- 搜索 -->
      <input name="act" type="hidden" value="print_quotation" />
      <input type="submit" name="print_quotation" id="print_quotation" value="${lang.printQuotation}" style="vertical-align:middle;" class="bnt_blue_1" />
    </form>
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
