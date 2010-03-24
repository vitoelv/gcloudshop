<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}</title>
<!-- TemplateEndEditable -->
<!-- TemplateBeginEditable name="head" -->
<!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />
<#if ( topic.css != ''  ) >
<style type="text/css">
  ${topic.css}
</style>
</#if>

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
  <h5><span>${topic.title}</span></h5>
  <div class="blank"></div>
   ${topic.intro}<br /><br />
    <#list sortGoodsArr?keys as sortName> <#assign sort = sortGoodsArr.get(sortName)>
    <div class="box">
    <div class="box_1 clearfix">
    <h3><span>${sortName}</span></h3>
    <div class="centerPadd">
    <#list sort as goods>
    <div class="goodsItem">
       <a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.name?html}" class="goodsimg" /></a><br />
       <p><a href="${goods.url}" title="${goods.name?html}">${goods.shortStyleName}</a></p>
       <font class="f1">
       <#if ( goods.promotePrice != ""  ) >
      ${goods.promotePrice}
      <#else>
      ${goods.shopPrice}
      </#if>
       </font>
    </div>
    </#list>
    </div>
    </div>
    </div>
    </#list>
    
</div>
<div class="blank5"></div>

<!--友情链接 start-->
<#if ( imgLinks?? || txtLinks??  ) >
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="links clearfix">
    <#list imgLinks as link>
    <a href="${link.url}" target="_blank" title="${link.name}"><img src="${link.logo}" alt="${link.name}" border="0" /></a>
    </#list>
    <#if ( txtLinks??  ) >
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
