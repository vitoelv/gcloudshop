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
    <!-- TemplateBeginEditable name="左边区域" -->
    <#include "library/cart.ftl">
    <#include "library/category_tree.ftl">
    <#include "library/goods_related.ftl">
    <#include "library/goods_fittings.ftl">
    <#include "library/goods_article.ftl">
    <#include "library/goods_attrlinked.ftl">
    <!-- TemplateEndEditable -->
    <!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
    <!-- TemplateEndEditable -->
    <!--AD end-->
    <#include "library/history.ftl">
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
	 <!-- TemplateBeginEditable name="右边通栏广告（宽750px）" -->
   <!-- TemplateEndEditable -->
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.auctionGoods}</span></h3>
    <div class="boxCenterList">
      <#if ( auctionList??  ) >
      <#list auctionList as auction>
      <ul class="group clearfix">
      <li style="margin-right:8px; text-align:center;">
      <a href="${auction.url}"><img src="${auction.goodsThumb}" border="0" alt="${auction.goodsName?html}" style="vertical-align: middle" /></a>
      </li>
      <li style="width:555px; line-height:23px;">
      ${lang.goodsName}：<a href="${auction.url}" class="f5">${auction.goodsName?html}</a><br />
      ${lang.actStatus}：
    <#if ( auction.statusNo == 0  ) >
            ${lang.auPreStart}<br>
            <#elseif ( auction.statusNo == 1  ) >
            ${lang.auUnderWay_1}<br>
            <#else>
            ${lang.auFinished}<br>
            </#if>
    ${lang.auStartPrice}：${auction.formatedStartPrice}<br>
    <#if ( auction.endPrice > 0  ) >
    ${lang.auEndPrice}：${auction.formatedEndPrice}
          </#if>
      </li>
      </ul>
      </#list>
      <#else>
       <span style="margin:2px 10px; font-size:14px; line-height:36px;">${lang.noAuction}</span>
      </#if>
    </div>
   </div>
  </div>
  <div class="blank5"></div>
  <#include "library/pages.ftl">
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
