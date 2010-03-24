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
    <!-- TemplateBeginEditable name="左边区域" -->
    <#include "library/cart.ftl">
    <#include "library/category_tree.ftl">
    <#include "library/goods_related.ftl">
    <#include "library/goods_fittings.ftl">
    <#include "library/goods_article.ftl">
    <#include "library/goods_attrlinked.ftl">
    <!-- TemplateEndEditable -->
    <div id="ECS_PRICE_LIST">
    <#include "Library/snatch_price.ftl">
    </div>
    <div class="box">
     <div class="box_1">
      <h3><span>${lang.activityList}</span></h3>
      <div class="boxCenterList RelaArticle">
        <ul class="clearfix">
        <#list snatchList as item>
        <li><a href="${item.url}">${item.snatchName}</a>&nbsp;&nbsp;
          <#if ( item.overtime??  ) >
          (${lang.end})
          </#if>
        </li>
        </#list>
        </ul>
      </div>
     </div>
    </div>
    <div class="blank5"></div>
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
    <h3><span>${lang.treasureInfo}</span></h3>
    <div class="boxCenterList">
      <ul class="group clearfix">
      <li style="margin-right:8px; text-align:center;">
      <a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName?html}" /></a>
      </li>
      <li style="width:555px; line-height:23px;">
       <script type="text/javascript" src="js/lefttime.js"></script>

     <a href="${goods.url}"><strong>${goods.goodsName?html}</strong></a><br />
     ${lang.shopPrice} <font class="shop">${goods.formatedShopPrice}</font><br />
     ${lang.marketPrice} <font class="market">${goods.formatedMarketPrice}</font> <br />
     ${lang.residualTime} <font color="red"><span id="leftTime">${lang.pleaseWaiting}</span></font><br />
     ${lang.activityDesc}：<br />${goods.desc?html}
      </li>
      </ul>
    </div>
   </div>
  </div>
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.activityIntro}</span></h3>
    <div class="boxCenterList">
    ${goods.snatchTime}<br />
    ${lang.priceExtent}${goods.formatedStartPrice} - ${goods.formatedEndPrice} <br />
    ${lang.userToUseUp}${goods.costPoints} ${pointsName}<br />
    ${lang.snatchVictoryDesc}<br />
    <#if ( goods.maxPrice != 0  ) >    ${lang.priceLessVictory}${goods.formatedMaxPrice}，${lang.priceThanVictory}${goods.formatedMaxPrice}，${lang.orCan}${goods.formatedMaxPrice}${lang.shoppingProduct}。
    <#else>
    ${lang.victoryPriceProduct}
    </#if>
    </div>
   </div>
  </div>
  <div class="blank5"></div>
  <div id="ECS_SNATCH">
    <#include "Library/snatch.ftl">
    </div>
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
<script type="text/javascript">
var gmt_end_time = ${goods.gmtEndTime};
var id = ${id};
<#list lang.snatchJs?keys as key> <#assign item = lang.snatchJs.get(key)>
var ${key} = "${item}";
</#list>
<#list lang.goodsJs?keys as key> <#assign item = lang.goodsJs.get(key)>
var ${key} = "${item}";
</#list>


onload = function()
{
  try
  {
    window.setInterval("newPrice(" + id + ")", 8000);
    onload_leftTime();
  }
  catch (e)
  {}
}

</script>
</html>
