<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="{$keywords}" />
<meta name="Description" content="{$description}" />
<title>{$page_title}</title>
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="${template_root}/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
function $(element) {
  return document.getElementById(element);
}
//切屏--是按钮，_v是内容平台，_h是内容库
function reg(str){
  var bt=$(str+"_b").getElementsByTagName("h2");
  for(var i=0;i<bt.length;i++){
    bt[i].subj=str;
    bt[i].pai=i;
    bt[i].style.cursor="pointer";
    bt[i].onclick=function(){
      $(this.subj+"_v").innerHTML=$(this.subj+"_h").getElementsByTagName("blockquote")[this.pai].innerHTML;
      for(var j=0;j<$(this.subj+"_b").getElementsByTagName("h2").length;j++){
        var _bt=$(this.subj+"_b").getElementsByTagName("h2")[j];
        var ison=j==this.pai;
        _bt.className=(ison?"":"h2bg");
      }
    }
  }
  $(str+"_h").className="none";
  $(str+"_v").innerHTML=$(str+"_h").getElementsByTagName("blockquote")[0].innerHTML;
}

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

<!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
<!-- TemplateEndEditable -->
    <!--AD end-->
    
    <#include "library/history.ftl">
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
   <!--商品详情start-->
   <div id="goodsInfo" class="clearfix">
     <!--商品图片和相册 start-->
     <div class="imgInfo">
     <#if pictures??>
     <a href="javascript:;" onclick="window.open('gallery.action?id=${goods.id}'); return false;">
      <img src="${goods.image}" alt="${goods.name}"/>
     </a>
         <#else>
         <img src="${goods.image}" alt="${goods.name}"/>
         </#if>
     <div class="blank5"></div>
     <!--相册 START-->
     <#include "library/goods_gallery.ftl">
     <!--相册 END-->
         <div class="blank5"></div>
         <!-- TemplateBeginEditable name="商品相册下广告（宽230px）" -->
     <!-- TemplateEndEditable -->
     </div>
     <!--商品图片和相册 end-->
     <div class="textInfo">
     <form action="javascript:addToCart(${goods.id})" method="post" name="ECS_FORMBUY" id="ECS_FORMBUY" >
		 <div class="clearfix">
      <p class="f_l">${goods.nameStyle}</p>
      <p class="f_r">
      <#if prev_good??>
      <a href="goods.action?id=${prev_good.id}"><img alt="prev" src="${template_root}/images/up.gif" /></a>
      </#if>
      <#if next_good??>
      <a href="goods.action?id=${next_good.id}"><img alt="next" src="${template_root}/images/down.gif" /></a>
      </#if>
      </p>
			</div>
      <ul>
       <#if promotion??>
      <li class="padd">
      <#list promotion as item><!-- 优惠活动-->
      <@s.text name="activity"/>
      <#if item.type == "snatch">
      <a href="snatch.action" title="<@s.text name="snatch"/>" style="font-weight:100; color:#006bcd; text-decoration:none;">[<@s.text name="snatch"/>]</a>
      </#if>
      <#if item.type == "group_buy">
      <a href="group_buy.action" title="<@s.text name="group_buy"/>" style="font-weight:100; color:#006bcd; text-decoration:none;">[<@s.text name="group_buy"/>]</a>
      </#if>
      <#if item.type == "auction">
      <a href="auction.action" title="<@s.text name="auction"/>" style="font-weight:100; color:#006bcd; text-decoration:none;">[<@s.text name="auction"/>]</a>
      </#if>
      <#if item.type == "favourable">
      <a href="activity.action" title="<@s.text name="favourable"/>" style="font-weight:100; color:#006bcd; text-decoration:none;">[<@s.text name="favourable"/>]</a>
      </#if>
      <a href="${item.url}" title="<@s.text name="item.type"/> ${item.act_name}${item.time}" style="font-weight:100; color:#006bcd;"><@s.text name="act_name"/></a><br />
      </#list>
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       
       <#if cfg?? && cfg.show_goodssn??>
       <strong><@s.text name="goods_sn"/></strong>${goods.sn}
       </#if>
       </dd>
       <dd class="ddR">
       <#if goods.number??><!-- and $cfg.show_goodsnumber} 商品库存-->
        <#if goods.number == 0>
          <strong><@s.text name="goods_number"/></strong>
          <font color='red'><@s.text name="stock_up"/></font>
        <#else>
          <strong><@s.text name="goods_number"/></strong>
          ${goods.number} {goods.measureUnit}
        </#if>
      </#if>
       </dd>
      </li>
      <li class="clearfix">
       <dd>
       <#if goods.brand??><!-- and $cfg.show_brand} 显示商品品牌-->
       <strong><@s.text name="goods_brand"/></strong><a href="${goods.brand.siteUrl}" >${goods.brand.name}</a>
       </#if>
       </dd>
       <dd class="ddR">
       <!-- {if $cfg.show_goodsweight} 商品重量-->
       <strong><@s.text name="goods_weight"/></strong>${goods.weight}
       <!-- {/if} -->
       </dd>
      </li>
      <li class="clearfix">
       <dd>
       <!-- {if $cfg.show_addtime} 上架时间-->
      <strong><@s.text name="add_time"/></strong>${goods.addTime}
      <!-- {/if} -->
       </dd>
       <dd class="ddR">
       <!--点击数-->
       <strong><@s.text name="goods_click_count"/>：</strong>${goods.clickCount}
       </dd>
      </li>
      <li class="clearfix">
       <dd class="ddL">
       <!-- {if $cfg.show_marketprice} 市场价格-->
       <strong><@s.text name="market_price"/></strong><font class="market">${goods.marketPrice}</font><br />
       <!-- {/if} -->
       <!--本店售价-->
       <strong><@s.text name="shop_price"/></strong><font class="shop" id="ECS_SHOPPRICE">${goods.shopPrice}</font><br />
       <#if rand_prices??>
       <#list rank_prices as rank_price><!-- 会员等级对应的价格-->
       <strong>${rank_price.name}：</strong><font class="shop" id="ECS_RANKPRICE_${rank_price.id}">${rank_price.price}</font><br />
       </#list>
       </#if>
       </dd>
       <dd style="width:48%; padding-left:7px;">
       <strong><@s.text name="goods_rank"/></strong>
      <img src="${template_root}/images/stars{$goods.commentRank}.gif" alt="comment rank {goods.commentRank}" />
       </dd>
      </li>

      <#if volume_price_list??>
      <li class="padd">
       <font class="f1"><@s.text name="volume_price"/>：</font><br />
			 <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#aad6ff">
				<tr>
					<td align="center" bgcolor="#FFFFFF"><strong><@s.text name="number_to"/></strong></td>
					<td align="center" bgcolor="#FFFFFF"><strong>优惠价格</strong></td>
				</tr>
				<#list volume_price_list as price_list>
				<tr>
					<td align="center" bgcolor="#FFFFFF" class="shop">${price_list.number}</td>
					<td align="center" bgcolor="#FFFFFF" class="shop">${price_list.price}</td>
				</tr>
				</#list>
	     </table>
      </li>
      </#if>

      <#if goods.promoted && goods.gmtEndTime><!-- 促销-->
<script type="text/javascript" src="js/lefttime.js"></script>
      <li class="padd loop" style="margin-bottom:5px; border-bottom:1px dashed #ccc;">
      <strong><@s.text name="promote_price"/></strong><font class="shop">${goods.promotePrice}</font><br />
      <strong><@s.text name="residual_time"/></strong>
      <font class="f4" id="leftTime"><@s.text name="please_waiting"/></font><br />
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       <strong><@s.text name="amount"/>：</strong><font id="ECS_GOODS_AMOUNT" class="shop"></font>
       </dd>
       <dd class="ddR">
       <#if goods.giveIntegral != 0><!-- 购买此商品赠送积分-->
        <strong><@s.text name="goods_give_integral"/></strong><font class="f4">${goods.giveIntegral} ${pointsName}</font>
        </#if>
       </dd>
      </li>
      <#if goods.bonusType??><!-- 红包-->
      <li class="padd loop" style="margin-bottom:5px; border-bottom:1px dashed #ccc;">
      <strong><@s.text name="goods_bonus"/></strong><font class="shop">${goods.bonusType.money}</font><br />
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       <strong><@s.text name="number"/>：</strong>
        <input name="number" type="text" id="number" value="1" size="4" onblur="changePrice()" style="border:1px solid #ccc; "/>
       </dd>
       <dd class="ddR">
       <#if 0 < goods.integral > <!-- 购买此商品可使用积分-->
       <strong><@s.text name="goods_integral"/></strong><font class="f4">${goods.integral} ${pointsName}</font>
       </#if>
       </dd>
      </li>
      <!-- {* 开始循环所有可选属性 *} -->
      <#if specification??>
      <#list specification as spec>
      <li class="padd loop">
      <strong>${spec.name}:</strong><br />
        <!-- {* 判断属性是复选还是单选 *} -->
                    <#if spec.attrType == 1>
                      <#if cfg.goodsattrStyle == 1>
                        <#list spec.values as value>
                        <label for="spec_value_${value.id}">
                        <input type="radio" name="spec_${spec_key}" value="${value.id}" id="spec_value_${value.id}" <#if value_index == 0>checked</#if> onclick="changePrice()" />
                        ${value.label} [<#if 0 < value.price><@s.text name="plus"/><#else><#if value.price < 0><@s.text name="minus"/></#if></#if> ${value.formatPrice}] </label><br />
                        </#list>
                        <input type="hidden" name="spec_list" value="{$key}" />
                        <#else>
                        <select name="spec_${spec_key}" onchange="changePrice()">
                          <#list spec.values as value>
                          <option label="${value.label}" value="${value.id}">${value.label} <#if 0 < value.price><@s.text name="plus"/><#else><#if value.price < 0><@s.text name="minus"/></#if></#if><#if value.price != 0>${value.formatPrice}</#if></option>
                          </#list>
                        </select>
                        <input type="hidden" name="spec_list" value="{$key}" />
                      </#if>
                    <#else>
                      <#list spec.values as value>
                      <label for="spec_value_${value.id}">
                      <input type="checkbox" name="spec_${spec_key}" value="${value.id}" id="spec_value_${value.id}" onclick="changePrice()" />
                      ${value.label} [<#if 0 < value.price><@s.text name="plus"/><#else><#if value.price < 0><@s.text name="minus"/></#if></#if> ${value.formatPrice}] </label><br />
                      </#list>
                      <input type="hidden" name="spec_list" value="${value_index}" />
                    </#if>
      </li>
      </#list>
      </#if>
      <!-- {* 结束循环可选属性 *} -->
      <li class="padd">
      <a href="javascript:addToCart(${goods.id})"><img src="${template_root}/images/bnt_cat.gif" /></a>
      <a href="javascript:collect(${goods.id})"><img src="${template_root}/images/bnt_colles.gif" /></a>
      <#if affiliate?? && affiliate.on>
      <a href="user.action?act=affiliate&goodsid=${goods.id}" style="position:relative;left:10px; bottom:15px;">将此商品推荐给朋友</a>
      </#if>
      </li>
      </ul>
      </form>
     </div>
   </div>
   <div class="blank"></div>
   <!--商品详情end-->
   <!--商品描述，商品属性 START-->
     <div class="box">
     <div class="box_1">
      <h3 style="padding:0 5px;">
        <div id="com_b" class="history clearfix">
        <h2><@s.text name="goods_brief"/></h2>
        <h2 class="h2bg"><@s.text name="goods_attr"/></h2>
        <#if package_goods_list??>
        <h2 class="h2bg" style="color:red;"><@s.text name="remark_package"/></h2>
        </#if>
        </div>
      </h3>
      <div id="com_v" class="boxCenterList RelaArticle"></div>
      <div id="com_h">
       <blockquote>
        ${goods.description}
       </blockquote>

     <blockquote>
     <#if properties??>
      <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#dddddd">
        <#list properties as property_group>
        <tr>
          <th colspan="2" bgcolor="#FFFFFF">${property_group_index}</th>
        </tr>
        <#list property_group as property>
        <tr>
          <td bgcolor="#FFFFFF" align="left" width="30%" class="f1">[${property.name}]</td>
          <td bgcolor="#FFFFFF" align="left" width="70%">${property.value}</td>
        </tr>
        </#list>
        </#list>
      </table>
     </#if>
     </blockquote>

     <#if package_goods_list??>
     <blockquote>
       <#list package_goods_list as package_goods>
			  <strong>${package_goods.act_name}</strong><br />
        <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#dddddd">
				<tr>
					<td bgcolor="#FFFFFF">
					<#list package_goods.goods_list as goods_list>
					<a href="goods.action?id=${goods_list.id}" target="_blank"><font class="f1">${goods_list.name}</font></a> &nbsp;&nbsp;X ${goods_list.number}<br />
					</#list>
					</td>
					<td bgcolor="#FFFFFF">
					<strong><@s.text name="old_price"/></strong><font class="market">{$package_goods.subtotal}</font><br />
          <strong><@s.text name="package_price"/></strong><font class="shop">{$package_goods.package_price}</font><br />
          <strong><@s.text name="then_old_price"/></strong><font class="shop">{$package_goods.saving}</font><br />
					</td>
					<td bgcolor="#FFFFFF">
					<a href="javascript:addPackageToCart(${package_goods.act_id})" style="background:transparent"><img src="${template_root}/images/bnt_buy_1.gif" alt="<@s.text name="add_to_cart"/>" /></a>
					</td>
				</tr>
	    </table>
       </#list>
     </blockquote>
     </#if>

      </div>
     </div>
    </div>
    <script type="text/javascript">
    <!--
    reg("com");
    //-->
    </script>
  <div class="blank"></div>
  <!---------------------------------------------------------------->
  <!--商品描述，商品属性 END-->
  <!-- TemplateBeginEditable name="右边可编辑区域" -->
<#include "library/goods_tags.ftl">
<#include "library/bought_goods.ftl">
<#include "library/comments.ftl">
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
<script type="text/javascript">
var goods_id = ${goods.id};
var goodsattr_style = {$cfg.goodsattr_style|default:1};
var gmt_end_time = {$promote_end_time};
<#if goods_js??>
<#list goods_js as item>
var ${item_index} = "${item}";
</#list>
</#if>
var goodsId = ${goods.id};
var now_time = ${now_time};

<!-- {literal} -->
onload = function(){
  changePrice();
  fixpng();
  try { onload_leftTime(); }
  catch (e) {}
}

/**
 * 点选可选属性或改变数量时修改商品价格的函数
 */
function changePrice()
{
  var attr = getSelectedAttributes(document.forms['ECS_FORMBUY']);
  var qty = document.forms['ECS_FORMBUY'].elements['number'].value;

  Ajax.call('goods.action', 'act=price&id=' + goodsId + '&attr=' + attr + '&number=' + qty, changePriceResponse, 'GET', 'JSON');
}

/**
 * 接收返回的信息
 */
function changePriceResponse(res)
{
  if (res.err_msg.length > 0)
  {
    alert(res.err_msg);
  }
  else
  {
    document.forms['ECS_FORMBUY'].elements['number'].value = res.qty;

    if (document.getElementById('ECS_GOODS_AMOUNT'))
      document.getElementById('ECS_GOODS_AMOUNT').innerHTML = res.result;
  }
}
<!-- {/literal} -->
</script>
</html>
