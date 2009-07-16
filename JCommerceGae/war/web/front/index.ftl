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
<link rel="alternate" type="application/rss+xml" title="RSS|${page_title}" href="${feed_url}" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<#include "library/page_header.ftl" >
<div class="blank"></div>
<div class="block clearfix">
  <!--left start-->
  <div class="AreaL">
    <!--站内公告 start-->
    <div class="box">
     <div class="box_1">
      <h3><span><@s.text name="shop_notice"/></span></h3>
      <div class="boxCenterList RelaArticle">
        ${shop_notice}
      </div>
     </div>
    </div>
    <div class="blank5"></div>
    <!--站内公告 end-->
  <!-- TemplateBeginEditable name="左边区域" -->
<#include "library/cart.ftl">
<div class="blank5"></div>

<#include "library/category_tree.ftl">
<#include "library/top10.ftl">
<#include "library/promotion_info.ftl">
<#include "library/auction.ftl">
<#include "library/group_buy.ftl">
<#include "library/order_query.ftl">
<#include "library/invoice_query.ftl">
<#include "library/vote.ftl">
<#include "library/email_list.ftl">

  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
   <!--焦点图和站内快讯 START-->
    <div class="box clearfix">
     <div class="box_1 clearfix">
       <div class="f_l" id="focus">
        <script type="text/javascript">
        var swf_width=484;
        var swf_height=200;
        </script>
        <script type="text/javascript" src="data/flashdata/{$flash_theme}/cycle_image.js"></script>
       </div>
       <!--news-->
       <div id="mallNews" class="f_r">
        <div class="NewsTit"></div>
        <div class="NewsList tc">
        <#include "library/new_articles.ftl">
        </div>
       </div>
       <!--news end-->
     </div>
    </div>
    <div class="blank5"></div>
   <!--焦点图和站内快讯 END-->
   <!--今日特价，品牌 start-->
    <div class="clearfix">
      <!--特价-->
      <#include "library/recommend_promotion.ftl">
      <!--品牌-->
      <div class="box f_r brandsIe6">
       <div class="box_1 clearfix" id="brands">
        <#include "library/brands.ftl">
       </div>
      </div>
    </div>
    <div class="blank5"></div>
<#include "library/recommend_best.ftl">
<#include "library/recommend_new.ftl">
<#include "library/recommend_hot.ftl">
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
