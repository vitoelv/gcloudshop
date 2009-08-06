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
<script type="text/javascript" src="js/lefttime.js"></script>

<script type="text/javascript">
  <#list lang.jsLanguages as item>
    var ${key} = "${item}";
  </#list>
</script>
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
    <h3><span>${lang.auctionGoodsInfo}</span></h3>
    <div class="boxCenterList">
      <ul class="group clearfix">
      <li style="margin-right:8px; text-align:center;">
      <a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName?html}" /></a>
      </li>
      <li style="width:555px; line-height:23px;">
      <form name="theForm" action="auction.action" method="post">
      ${lang.goodsName}：<font class="f5">${auction.goodsName?html}</font><br>
      ${lang.auCurrentPrice}：${auction.formatedCurrentPrice}<br>
      起止时间：${auction.startTime} -- ${auction.endTime}<br>
      ${lang.auStartPrice}：${auction.formatedStartPrice}<br>
      ${lang.auAmplitude}：${auction.formatedAmplitude}<br>
      <#if  auction.endPrice  >  0  >
      ${lang.auEndPrice}：${auction.formatedEndPrice}<br>
      </#if>
      <#if  auction.deposit  >  0  >
      ${lang.auDeposit}：${auction.formatedDeposit}<br>
      </#if>
      <#if  auction.statusNo  ==  0  >
      ${lang.auPreStart}
      <#elseif  auction.statusNo  ==  1  >
      <font class="f4">${lang.auUnderWay}<span id="leftTime">${lang.pleaseWaiting}</span></font><br />
      ${lang.auIWantBid}：
      <input name="price" type="text" class="inputBg" id="price" size="8" />
      <input name="bid" type="submit" class="bnt_blue" id="bid" value="${lang.buttonBid}" style="vertical-align:middle;" />
      <input name="act" type="hidden" value="bid" />
      <input name="id" type="hidden" value="${auction.actId}" /><br />
      <#else>
      <#if  auction.isWinner??  >
      <span class="f_red">${lang.auIsWinner}</span><br />
      <input name="buy" type="submit" class="bnt_blue_1" value="${lang.buttonBuy}" />
      <input name="act" type="hidden" value="buy" />
      <input name="id" type="hidden" value="${auction.actId}" />
      <#else>
      ${lang.auFinished}
      </#if>
      </#if>
      </form>
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
    ${auction.actDesc?html|nl2br}
    </div>
   </div>
  </div>
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.bidRecord}</span></h3>
    <div class="boxCenterList">
    <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <tr>
        <th align="center" bgcolor="#ffffff">${lang.auBidUser}</th>
        <th align="center" bgcolor="#ffffff">${lang.auBidPrice}</th>
        <th align="center" bgcolor="#ffffff">${lang.auBidTime}</th>
        <th align="center" bgcolor="#ffffff">${lang.auBidStatus}</th>
      </tr>
<#list auctionLog as log>
      <tr>
        <td align="center" bgcolor="#ffffff">${log.userName}</td>
        <td align="center" bgcolor="#ffffff">${log.formatedBidPrice}</td>
        <td align="center" bgcolor="#ffffff">${log.bidTime}</td>
        <td align="center" bgcolor="#ffffff"><#if  smarty.foreach.feBidLog.first??  > ${lang.auBidOk}<#else>&nbsp;</#if></td>
      </tr>
    
    <tr>
      <td colspan="4" align="center" bgcolor="#ffffff">${lang.noBidLog}</td>
    </tr>
    </#list>
 </table>
    </div>
   </div>
  </div>
  </div>
  <!--right end-->
</div>
<div class="blank"></div>
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
<script type="text/javascript">
var gmt_end_time = "${auction.gmtEndTime|default:0}";
<#list lang.goodsJs as item>
var ${key} = "${item}";
var now_time = ${nowTime};
</#list>
{literal}

onload = function()
{
  try
  {
    onload_leftTime(now_time);
  }
  catch (e)
  {}
}

</script>
</html>
