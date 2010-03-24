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
  <#list lang.jsLanguages?keys as key> <#assign item = lang.jsLanguages.get(key)>
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
    <h3><span>${lang.groupbuyGoodsInfo}</span></h3>
    <div class="boxCenterList">
      <ul class="group clearfix">
      <li style="margin-right:8px; text-align:center;">
      <a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName?html}" /></a>
      </li>
      <li style="width:555px; line-height:23px;">
       ${lang.gbGoodsName} <font class="f5">${goods.goodsName?html}</font><br>
      <#if ( cfg.showGoodssn?? && 0  ) >
      ${lang.goodsSn} ${goods.goodsSn}<br>
      </#if>
      <#if ( cfg.goods.brandName?? && showBrand?? && 0  ) >
      ${lang.goodsBrand} ${goods.brandName}<br>
      </#if>
      <#if ( cfg.showGoodsweight?? && 0  ) >
      ${lang.goodsWeight} ${goods.goodsWeight}<br>
      </#if>
      ${lang.actTime}：${groupBuy.formatedStartDate} -- ${groupBuy.formatedEndDate}<br>
      ${lang.gbPriceLadder}<br />
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
       <tr>
          <th width="29%" bgcolor="#FFFFFF">${lang.gbLadderAmount}</th>
         <th width="71%" bgcolor="#FFFFFF">${lang.gbLadderPrice}</th>
        </tr>
        <#list groupBuy.priceLadder as item>
        <tr>
          <td width="29%" bgcolor="#FFFFFF">${item.amount}</td>
         <td width="71%" bgcolor="#FFFFFF">${item.formatedPrice}</td>
        </tr>
        </#list>
      </table>
      <#if ( groupBuy.deposit > 0  ) >
      ${lang.gbDeposit} ${groupBuy.formatedDeposit}<br />
      </#if>

      <#if ( groupBuy.restrictAmount > 0  ) >
      ${lang.gbRestrictAmount} ${groupBuy.restrictAmount}<br />
      </#if>

      <#if ( groupBuy.giftIntegral > 0  ) >
      ${lang.gbGiftIntegral} ${groupBuy.giftIntegral}<br />
      </#if>

      <#if ( groupBuy.status == 0  ) >
      ${lang.gbsPreStart}
      <#elseif ( groupBuy.status == 1  ) >
      <font class="f4">${lang.gbsUnderWay}
      <span id="leftTime">${lang.pleaseWaiting}</span></font><br />
      ${lang.gbCurPrice} ${groupBuy.formatedCurPrice}<br />
      ${lang.gbValidGoods} ${groupBuy.validGoods}<br />
      <#elseif ( groupBuy.status == 2  ) >
      ${lang.gbsFinished} ${lang.gbCurPrice} ${groupBuy.formatedCurPrice} ${lang.gbValidGoods} ${groupBuy.validGoods}
      <#elseif ( groupBuy.status == 3  ) >
      ${lang.gbsSucceed}
      ${lang.gbFinalPrice} ${groupBuy.formatedTransPrice}<br />
      ${lang.gbFinalAmount} ${groupBuy.transAmount}
      <#elseif ( groupBuy.status == 4  ) >
      ${lang.gbsFail}
      </#if>
      </li>
      </ul>
    </div>
   </div>
  </div>
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.properties}</span></h3>
    <div class="boxCenterList">
    <form action="group_buy.action?act=buy" method="post" name="ECS_FORMBUY" id="ECS_FORMBUY">
           <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
              <#list specification?keys as specKey> <#assign spec = specification.get(specKey)>
              <tr>
                <td width="22%" bgcolor="#FFFFFF">${spec.name}</td>
                <td width="78%" bgcolor="#FFFFFF">
                    <#if ( cfg.goodsattrStyle == 1  ) >
                    <#list spec.values?keys as key> <#assign value = spec.values.get(key)>
                    <label for="spec_value_${value.id}">
                    <input type="radio" name="spec_${specKey}" value="${value.id}" id="spec_value_${value.id}" <#if ( key == 0  ) >checked</#if> />
                    ${value.label} </label>
                    </#list>
                  <#else>
                    <select name="spec_${specKey}" style="border:1px solid #ccc;">
                    <#list spec.values?keys as key> <#assign value = spec.values.get(key)>
                    <option label="${value.label}" value="${value.id}">${value.label} </option>
                    </#list>
                    </select>
                  </#if>
                </td>
              </tr>
              </#list>
              <#if ( userId??  ) >
              <tr>
                <td bgcolor="#FFFFFF"><strong>${lang.number}:</strong></td>
                <td bgcolor="#FFFFFF">
                <input name="number" type="text" class="inputBg" id="number" value="1" size="4" />
                <input type="hidden" name="group_buy_id" value="${groupBuy.groupBuyId}" />
                <input type="image" src="images/bnt_buy_1.gif" style="vertical-align:middle;" />
                </td>
              </tr>
              <#else>
              <tr>
                <td colspan="2" align="right" bgcolor="#FFFFFF"><br />
                  <font class="f_red">${lang.gbNoticeLogin}</font></td>
              </tr>
              </#if>
            </table>
          </form>
    </div>
   </div>
  </div>
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.groupbuyIntro}</span></h3>
    <div class="boxCenterList">
    ${groupBuy.groupBuyDesc}
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
var gmt_end_time = "${groupBuy.gmtEndDate}";
<#list lang.goodsJs?keys as key> <#assign item = lang.goodsJs.get(key)>
var ${key} = "${item}";
</#list>
var now_time = ${nowTime};


onload = function()
{
  try
  {
    onload_leftTime();
  }
  catch (e)
  {}
}

</script>
</html>
