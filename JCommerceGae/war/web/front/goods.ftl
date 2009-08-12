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
<!-- TemplateEndEditable -->
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
     <#if  pictures??  >
     <a href="javascript:;" onclick="window.open('gallery.action?id=${goods.goodsId}'); return false;">
      <img src="${goods.goodsImg}" alt="${goods.goodsName?html}"/>
     </a>
		 <#else>
		 <img src="${goods.goodsImg}" alt="${goods.goodsName?html}"/>
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
     <form action="javascript:addToCart(${goods.goodsId})" method="post" name="ECS_FORMBUY" id="ECS_FORMBUY" >
      <p>${goods.goodsStyleName}</p>
      <ul>
       <#if  promotion??  >
      <li class="padd">
      <#list promotion as item>
      ${lang.activity}
      <#if  item.type  ==  "snatch"  >
      <a href="snatch.action" title="${lang.snatch}" style="font-weight:100; color:#006bcd; text-decoration:none;">[${lang.snatch}]</a>
      <#elseif  item.type  ==  "groupBuy"  >
      <a href="group_buy.action" title="${lang.groupBuy}" style="font-weight:100; color:#006bcd; text-decoration:none;">[${lang.groupBuy}]</a>
      <#elseif  item.type  ==  "auction"  >
      <a href="auction.action" title="${lang.auction}" style="font-weight:100; color:#006bcd; text-decoration:none;">[${lang.auction}]</a>
      <#elseif  item.type  ==  "favourable"  >
      <a href="activity.action" title="${lang.favourable}" style="font-weight:100; color:#006bcd; text-decoration:none;">[${lang.favourable}]</a>
      </#if>
      <a href="${item.url}" title="${lang.item.type} ${item.actName}${item.time}" style="font-weight:100; color:#006bcd;">${item.actName}</a><br />
      </#list>
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       <#if  cfg.showGoodssn??  >
       <strong>${lang.goodsSn}</strong>${goods.goodsSn}
       </#if>
       </dd>
       <dd class="ddR">
       <#if  cfg.showGoodsnumber??  >
        <#if  goods.goodsNumber  ==  0  >
          <strong>${lang.goodsNumber}</strong>
          <font color='red'>${lang.stockUp}</font>
        <#else>
          <strong>${lang.goodsNumber}</strong>
          ${goods.goodsNumber} ${goods.measureUnit}
        </#if>
      </#if>
       </dd>
      </li>
      <li class="clearfix">
       <dd>
       <#if  goods.goodsBrand  !=  ""  &&  cfg.showBrand??  >
       <strong>${lang.goodsBrand}</strong><a href="${goods.goodsBrandUrl}" >${goods.goodsBrand}</a>
       </#if>
       </dd>
       <dd class="ddR">
       <#if  cfg.showGoodsweight??  >
       <strong>${lang.goodsWeight}</strong>${goods.goodsWeight}
       </#if>
       </dd>
      </li>
      <li class="clearfix">
       <dd>
       <#if  cfg.showAddtime??  >
      <strong>${lang.addTime}</strong>${goods.addTime}
      </#if>
       </dd>
       <dd class="ddR">
       <!--点击数-->
       <strong>${lang.goodsClickCount}：</strong>${goods.clickCount}
       </dd>
      </li>
      <li class="clearfix">
       <dd class="ddL">
       <#if  cfg.showMarketprice??  >
       <strong>${lang.marketPrice}</strong><font class="market">${goods.marketPrice}</font><br />
       </#if>
       <!--本店售价-->
       <strong>${lang.shopPrice}</strong><font class="shop" id="ECS_SHOPPRICE">${goods.shopPriceFormated}</font><br />
       <#list rankPrices as rankPrice>
       <strong>${rankPrice.rankName}：</strong><font class="shop" id="ECS_RANKPRICE_${key}">${rankPrice.price}</font><br />
       </#list>
       </dd>
       <dd style="width:48%; padding-left:7px;">
       <strong>${lang.goodsRank}</strong>
      <img src="images/stars${goods.commentRank}.gif" alt="comment rank ${goods.commentRank}" />
       </dd>
      </li>
      <#if  goods.isPromote??  &&  goods.gmtEndTime??  >
      <script type="text/javascript" src="js/lefttime.js"></script>

      <li class="padd loop" style="margin-bottom:5px; border-bottom:1px dashed #ccc;">
      <strong>${lang.promotePrice}</strong><font class="shop">${goods.promotePrice}</font><br />
      <strong>${lang.residualTime}</strong>
      <font class="f4" id="leftTime">${lang.pleaseWaiting}</font><br />
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       <strong>${lang.amount}：</strong><font id="ECS_GOODS_AMOUNT" class="shop"></font>
       </dd>
       <dd class="ddR">
       <#if  (goods.giveIntegral  >  0)  >
        <strong>${lang.goodsGiveIntegral}</strong><font class="f4">${goods.giveIntegral} ${pointsName}</font>
        </#if>
       </dd>
      </li>
      <#if  goods.bonusMoney??  >
      <li class="padd loop" style="margin-bottom:5px; border-bottom:1px dashed #ccc;">
      <strong>${lang.goodsBonus}</strong><font class="shop">${goods.bonusMoney}</font><br />
      </li>
      </#if>
      <li class="clearfix">
       <dd>
       <strong>${lang.number}：</strong>
        <input name="number" type="text" id="number" value="1" size="4" onblur="changePrice()" style="border:1px solid #ccc; "/>
       </dd>
       <dd class="ddR">
       <#if  (goods.integral  >  0)  >
       <strong>${lang.goodsIntegral}</strong><font class="f4">${goods.integral} ${pointsName}</font>
       </#if>
       </dd>
      </li>
      
      <#list specification as spec>
      <li class="padd loop">
      <strong>${spec.name}:</strong><br />
        
                    <#if  spec.attrType  ==  1  >
                      <#if  cfg.goodsattrStyle  ==  1  >
                        <#list spec.values as value>
                        <label for="spec_value_${value.id}">
                        <input type="radio" name="spec_${specKey}" value="${value.id}" id="spec_value_${value.id}" <#if  key  ==  0  >checked</#if> onclick="changePrice()" />
                        ${value.label} [<#if  value.price  >  0  >${lang.plus}<#elseif  value.price  <  0  >${lang.minus}</#if> ${value.formatPrice|abs}] </label><br />
                        </#list>
                        <input type="hidden" name="spec_list" value="${key}" />
                        <#else>
                        <select name="spec_${specKey}" onchange="changePrice()">
                          <#list spec.values as value>
                          <option label="${value.label}" value="${value.id}">${value.label} <#if  value.price  >  0  >${lang.plus}<#elseif  value.price  <  0  >${lang.minus}</#if><#if  value.price  !=  0  >${value.formatPrice}</#if></option>
                          </#list>
                        </select>
                        <input type="hidden" name="spec_list" value="${key}" />
                      </#if>
                    <#else>
                      <#list spec.values as value>
                      <label for="spec_value_${value.id}">
                      <input type="checkbox" name="spec_${specKey}" value="${value.id}" id="spec_value_${value.id}" onclick="changePrice()" />
                      ${value.label} [<#if  value.price  >  0  >${lang.plus}<#elseif  value.price  <  0  >${lang.minus}</#if> ${value.formatPrice|abs}] </label><br />
                      </#list>
                      <input type="hidden" name="spec_list" value="${key}" />
                    </#if>
      </li>
      </#list>
      
      <li class="padd">
      <a href="javascript:addToCart(${goods.goodsId})"><img src="images/bnt_cat.gif" /></a>
      <a href="javascript:collect(${goods.goodsId})"><img src="images/bnt_colles.gif" /></a>
      <#if  affiliate.on??  >
      <a href="user.action?act=affiliate&goodsid=${goods.goodsId}" style="position:relative;left:10px; bottom:15px;">将此商品推荐给朋友</a>
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
        <h2>${lang.goodsBrief}</h2>
        <h2 class="h2bg">${lang.goodsAttr}</h2>
        </div>
      </h3>
      <div id="com_v" class="boxCenterList RelaArticle"></div>
      <div id="com_h">

       <blockquote>
        ${goods.goodsDesc}
       </blockquote>


     <blockquote>
      <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#dddddd">
        <#list properties as propertyGroup>
        <tr>
          <th colspan="2" bgcolor="#FFFFFF">${key}</th>
        </tr>
        <#list propertyGroup as property>
        <tr>
          <td bgcolor="#FFFFFF" align="left" width="30%" class="f1">[${property.name?html}]</td>
          <td bgcolor="#FFFFFF" align="left" width="70%">${property.value?html}</td>
        </tr>
        </#list>
        </#list>
      </table>
     </blockquote>

      </div>
     </div>
    </div>
    <script type="text/javascript">
    <!--
    reg("com");
    //-->
    </script>
  <div class="blank"></div>
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
var goods_id = ${goodsId};
var goodsattr_style = ${cfg.goodsattrStyle};
var gmt_end_time = ${promoteEndTime};
<#list lang.goodsJs as item>
var ${key} = "${item}";
</#list>
var goodsId = ${goodsId};
var now_time = ${nowTime};

{literal}
onload = function(){
  changePrice();
  fixpng();
  try {onloadLeftTime();}
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

</script>
</html>
