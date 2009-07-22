<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="{$keywords}" />
<meta name="Description" content="{$description}" />
<title>${page_title}</title>
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="${template_root}/style.css" rel="stylesheet" type="text/css" />
<#if cat_style??>
<link href="${cat_style}" rel="stylesheet" type="text/css" />
</#if>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/compare.js"></script>
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
    <!-- TemplateBeginEditable name="左边区域" -->
<#include "library/cart.ftl">
<#include "library/category_tree.ftl">
<#include "library/filter_attr.ftl">
<#include "library/price_grade.ftl">
<#include "library/history.ftl">
<!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
<!-- TemplateEndEditable -->
    <!--AD end-->
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
   <!-- TemplateBeginEditable name="右边区域" -->
<#include "library/recommend_best.ftl">
<#include "library/goods_list.ftl">
<#include "library/pages.ftl">
<!-- TemplateEndEditable -->


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
<#if img_links?? || txt_links??>
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="links clearfix">
    <#list img_links as link>
    <a href="${link.url}" target="_blank" title="${link.name}"><img src="${link.logo}" alt="${link.name}" border="0" /></a>
    </#list>
    <#if txt_links??>
    <#list txt_links as link>
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