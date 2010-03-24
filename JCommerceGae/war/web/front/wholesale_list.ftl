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
<link rel="alternate" type="application/rss+xml" title="RSS|${pageTitle}" href="${feedUrl}" />

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
<#if ( cartGoods??  ) >
<!-- 批发商品购物车 -->
<div class="block">
  <h5><span>${lang.wholesaleGoodsCart}</span></h5>
  <div class="blank"></div>
    <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <th bgcolor="#ffffff">${lang.goodsName}</th>
            <th bgcolor="#ffffff">${lang.goodsAttr}</th>
            <th bgcolor="#ffffff">${lang.number}</th>
            <th bgcolor="#ffffff">${lang.wsPrice}</th>
            <th bgcolor="#ffffff">${lang.wsSubtotal}</th>
            <th bgcolor="#ffffff">${lang.handle}</th>
          </tr>
          <#list cartGoods?keys as key> <#assign goods = cartGoods.get(key)>
          <tr>
            <td bgcolor="#ffffff" align="center"><a href="${goods.goodsUrl}" target="_blank" class="f6">${goods.goodsName}</a></td>
            <td bgcolor="#ffffff" align="center">${goods.goodsAttr}</td>
            <td bgcolor="#ffffff" align="center">${goods.goodsNumber}</td>
            <td bgcolor="#ffffff" align="center">${goods.formatedGoodsPrice}</td>
            <td bgcolor="#ffffff" align="center">${goods.formatedSubtotal}</td>
            <td bgcolor="#ffffff" align="center"><a href="wholesale.action?act=drop_goods&key=${key}" class="f6">${lang.drop}</a></td>
          </tr>
          </#list>
        </table>
   <form method="post" action="wholesale.action?act=submit_order">
          <table border="0" cellpadding="5" cellspacing="1" width="100%">
            <tr>
              <td class="f5" style="text-decoration:none;">${lang.wsRemark}</td>
            </tr>
            <tr>
              <td><textarea name="remark" rows="4" class="border" style="width:99%; border:1px solid #ccc;"></textarea>
              </td>
            </tr>
            <tr>
              <td align="center"><input type="submit" class="bnt_bonus"  value="${lang.submit}" /></td>
            </tr>
          </table>
        </form>
</div>
<div class="blank5"></div>
</#if>
<#if ( wholesaleList??  ) >
<div class="block">
  <h5><span>${lang.wholesaleGoodsList}</span><a href="wholesale.action?act=price_list" class="f6">${lang.wsPriceList}</a></h5>
  <div class="blank"></div>
  <form name="wholesale_goods" action="wholesale.action?act=add_to_cart" method="post">
          <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <th width="200" align="center" bgcolor="#ffffff">${lang.goodsName}</th>
              <th width="200" align="center" bgcolor="#ffffff">${lang.goodsAttr}</th>
              <th width="250" align="center" bgcolor="#ffffff">${lang.goodsPriceLadder}</th>
              <th width="80" align="center" bgcolor="#ffffff">${lang.number}</th>
              <th width="130" align="center" bgcolor="#ffffff">&nbsp;</th>
            </tr>
            <#list wholesaleList as wholesale>
            <tr>
              <td bgcolor="#ffffff"><a href="${wholesale.goodsUrl}" target="_blank" class="f6">${wholesale.goodsName}</a></td>
              <td bgcolor="#ffffff">

                <table width="100%" border="0" align="center">
                  <#list wholesale.goodsAttr?keys as key> <#assign propertyGroup = wholesale.goodsAttr.get(key)>
                  <#list propertyGroup as property>
                  <tr>
                    <td nowrap="true" style="border-bottom:2px solid #ccc;">${property.name?html}</td>
                    <td style="border-bottom:1px solid #ccc;">${property.value?html}</td>
                  </tr>
                  </#list>
                  </#list>
                </table>
              </td>

              <td bgcolor="#ffffff">
               <table width="100%" border="0" align="center" cellspacing="1" bgcolor="#547289">
                <#list wholesale.priceLadder?keys as key> <#assign attrPrice = wholesale.priceLadder.get(key)>
                  <tr>
                    <td align="left" nowrap="true" bgcolor="#ffffff" style="padding:5px;">${lang.number}:${key}</td>
                    <td bgcolor="#ffffff" style="padding:5px;">${lang.ladderPrice}:${attrPrice}</td>
                  </tr>
                  </#list>
</table>
              </td>
              <td align="center" bgcolor="#ffffff" style="padding:5px;">
              <input name="goods_number[${wholesale.actId}]" type="text" class="inputBg" value="" size="10" /></td>
              <td bgcolor="#ffffff" align="center">
              <input name="image" type="image" onClick="this.form.elements['act_id'].value = ${wholesale.actId}" src="images/bnt_buy_1.gif" style="margin:8px auto;" />
              </td>
            </tr>
            </#list>
          </table>
          <input type="hidden" name="act_id" value="" />
        </form>
 <#else>
  <div style="margin:2px 10px; font-size:14px; text-align:center; line-height:36px;">${lang.noWholesale}</div>
  </#if>
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
