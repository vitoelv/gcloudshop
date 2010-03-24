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

<script type="text/javascript" src="js/utils.js"></script>
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
  <#if ( action == "form"  ) >
  <!--  搜索的表单 -->
  <div class="box">
   <div class="box_1">
    <h3><span>${lang.advancedSearch}</span></h3>
    <div class="boxCenterList">
      <form action="search.action" method="get" name="advancedSearchForm" id="advancedSearchForm">
    <table border="0" align="center" cellpadding="3">
      <tr>
        <td valign="top">${lang.keywords}：</td>
        <td>
          <input name="keywords" id="keywords" type="text" size="40" maxlength="120" class="inputBg" value="${advVal.keywords}" />
          <label for="sc_ds"><input type="checkbox" name="sc_ds" value="1" id="sc_ds" ${scck} />${lang.scDs}</label>
          <br />${lang.searchkeywordsNotice}
        </td>
      </tr>
      <tr>
        <td>${lang.category}：</td>
        <td><select name="category" id="select" style="border:1px solid #ccc;">
            <option value="0">${lang.allCategory}</option>${catList}</select>
        </td>
      </tr>
      <tr>
        <td>${lang.brand}：</td>
        <td><select name="brand" id="brand" style="border:1px solid #ccc;">
            <option value="0">${lang.allBrand}</option>
            <#list brandList?keys as key>
<#assign val = brandList.get(key)>
<option value="${key}" <#if advVal.brand == key>selected</#if> >${val}</option>
</#list>

          </select>
        </td>
      </tr>
      <tr>
        <td>${lang.price}：</td>
        <td><input name="min_price" type="text" id="min_price" class="inputBg" value="${advVal.minPrice}" size="10" maxlength="8" />
          -
          <input name="max_price" type="text" id="max_price" class="inputBg" value="${advVal.maxPrice}" size="10" maxlength="8" />
        </td>
      </tr>
      <#if ( goodsTypeList??  ) >
      <tr>
        <td>${lang.extension}：</td>
        <td><select name="goods_type" onchange="this.form.submit()" style="border:1px solid #ccc;">
            <option value="0">${lang.allOption}</option>
            <#list goodsTypeList?keys as key>
<#assign val = goodsTypeList.get(key)>
<option value="${key}" <#if goodsTypeSelected == key>selected</#if> >${val}</option>
</#list>

          </select>
        </td>
      </tr>
      </#if>
      <#if ( goodsTypeSelected > 0  ) >
      <#list goodsAttributes as item>
      <#if ( item.type == 1  ) >
      <tr>
        <td>${item.attr}：</td>
        <td colspan="3"><input name="attr[${item.id}]" value="${item.value}" class="inputBg" type="text" size="20" maxlength="120" /></td>
      </tr>
      </#if>
      <#if ( item.type == 2  ) >
      <tr>
        <td>${item.attr}：</td>
        <td colspan="3"><input name="attr[${item.id}][from]" class="inputBg" value="${item.value.from}" type="text" size="5" maxlength="5" />
          -
          <input name="attr[${item.id}][to]" value="${item.value.to}"  class="inputBg" type="text" maxlength="5" /></td>
      </tr>
      </#if>
      <#if ( item.type == 3  ) >
      <tr>
        <td>${item.attr}：</td>
        <td colspan="3"><select name="attr[${item.id}]" style="border:1px solid #ccc;">
            <option value="0">${lang.allOption}</option>
            <#list item.options?keys as key>
<#assign val = item.options.get(key)>
<option value="${key}" <#if item.value == key>selected</#if> >${val}</option>
</#list>

          </select></td>
      </tr>
      </#if>
      </#list>
      </#if>

      <#if ( useStorage == 1  ) >
      <tr>
        <td>&nbsp;</td>
        <td><label for="outstock"><input type="checkbox" name="outstock" value="1" id="outstock" /> ${lang.hiddenOutstock}</label>
        </td>
      </tr>
      </#if>

      <tr>
        <td colspan="4" align="center"><input type="hidden" name="action" value="form" />
          <input type="submit" name="Submit" class="bnt_blue_1" value="${lang.buttonSearch}" /></td>
      </tr>
    </table>
  </form>
    </div>
   </div>
  </div>
  <div class="blank5"></div>
  </#if>

   <#if ( goodsList??  ) >
     <div class="box">
     <div class="box_1">
      <h3>
    <!--标题及显示方式-->
        <#if ( intromode == 'best'  ) >
         <span>${lang.bestGoods}</span>
         <#elseif ( intromode == 'new'  ) >
         <span>${lang.newGoods}</span>
         <#elseif ( intromode == 'hot'  ) >
         <span>${lang.hotGoods}</span>
         <#elseif ( intromode == 'promotion'  ) >
         <span>${lang.promotionGoods}</span>
         <#else>
         <span>${lang.searchResult}</span>
         </#if>
          <#if ( goodsList??  ) >
          <form action="search.action" method="post" class="sort" name="listform" id="form">
          ${lang.btnDisplay}：
          <a href="javascript:;" onClick="javascript:display_mode('list')"><img src="images/display_mode_list<#if ( pager.display == 'list'  ) >_act</#if>.gif" alt="${lang.display.list}"></a>
          <a href="javascript:;" onClick="javascript:display_mode('grid')"><img src="images/display_mode_grid<#if ( pager.display == 'grid'  ) >_act</#if>.gif" alt="${lang.display.grid}"></a>
          <a href="javascript:;" onClick="javascript:display_mode('text')"><img src="images/display_mode_text<#if ( pager.display == 'text'  ) >_act</#if>.gif" alt="${lang.display.text}"></a>&nbsp;&nbsp;
              <select name="sort">
              <#list lang.sort?keys as key>
<#assign val = lang.sort.get(key)>
<option value="${key}" <#if pager.sort == key>selected</#if> >${val}</option>
</#list>

              </select>
              <select name="order">
              <#list lang.order?keys as key>
<#assign val = lang.order.get(key)>
<option value="${key}" <#if pager.order == key>selected</#if> >${val}</option>
</#list>

              </select>
              <input type="image" name="imageField" src="images/bnt_go.gif" alt="go"/>
              <input type="hidden" name="page" value="${pager.page}" />
              <input type="hidden" name="display" value="${pager.display}" id="display" />
              <#list pager.search?keys as key> <#assign item = pager.search.get(key)>
              <#if ( key != "sort" && key != "order"  ) >
                <#if ( key == 'keywords'  ) >
                  <input type="hidden" name="${key}" value="${item}" />
                <#else>
                  <input type="hidden" name="${key}" value="${item}" />
                </#if>
              </#if>
              </#list>
            </form>
          </#if>
           </h3>
        <#if ( goodsList??  ) >

          <form action="compare.action" method="post" name="compareForm" id="compareForm" onsubmit="return compareGoods(this);">
          <#if ( pager.display == 'list'  ) >
              <div class="goodsList">
                <#list goodsList as goods>
                <#if ( goods.goodsId??  ) >
                <ul class="clearfix bgcolor"<#if ( goods_index % 2 == 0  ) >id=""<#else>id="bgcolor"</#if>>
                <li>
                <br>
                <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.goodsId},'${goods.goodsName}:"quotes"','${goods.type}')" class="f6">${lang.compare}</a>
                </li>
                <li class="thumb"><a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName}" /></a></li>
                <li class="goodsName">
                <a href="${goods.url}" class="f6">
                    <#if ( goods.goodsStyleName??  ) >
                    ${goods.goodsStyleName}<br />
                    <#else>
                    ${goods.goodsName}<br />
                    </#if>
                  </a>
                 <#if ( goods.goodsBrief??  ) >
                ${lang.goodsBrief}${goods.goodsBrief}<br />
                </#if>
                </li>
                <li>
                <#if ( showMarketprice??  ) >
                ${lang.marketPrice}<font class="market">${goods.marketPrice}</font><br />
                </#if>
                <#if ( goods.promotePrice != ""  ) >
                ${lang.promotePrice}<font class="shop">${goods.promotePrice}</font><br />
                <#else>
                ${lang.shopPrice}<font class="shop">${goods.shopPrice}</font><br />
                </#if>
                </li>
                <li class="action">
                <a href="javascript:collect(${goods.goodsId});" class="abg f6">${lang.favourableGoods}</a>
                <a href="javascript:addToCart(${goods.goodsId})"><img src="images/bnt_buy_1.gif"></a>
                </li>
                </ul>
                </#if>
                </#list>
                </div>
             <#elseif ( pager.display == 'grid'  ) >
              <div class="centerPadd">
                <div class="clearfix goodsBox" style="border:none;">
                <#list goodsList as goods>
                <#if ( goods.goodsId??  ) >
                 <div class="goodsItem">
                       <a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName}" class="goodsimg" /></a><br />
                       <p><a href="${goods.url}" title="${goods.name?html}">${goods.goodsName}</a></p>
                       <#if ( showMarketprice??  ) >
                        ${lang.marketPrices}<font class="market_s">${goods.marketPrice}</font><br />
                        </#if>
                        <#if ( goods.promotePrice != ""  ) >
                        ${lang.promotePrice}<font class="shop_s">${goods.promotePrice}</font><br />
                        <#else>
                        ${lang.shopPrices}<font class="shop_s">${goods.shopPrice}</font><br />
                        </#if>
                       <a href="javascript:collect(${goods.goodsId});" class="f6">${lang.btnCollect}</a> |
                       <a href="javascript:addToCart(${goods.goodsId})" class="f6">${lang.btnBuy}</a> |
                       <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.goodsId},'${goods.goodsName}:"quotes"','${goods.type}')" class="f6">${lang.compare}</a>
                    </div>
                </#if>
                </#list>
                </div>
                </div>
             <#elseif ( pager.display == 'text'  ) >
              <div class="goodsList">
              <#list goodsList as goods>
               <ul class="clearfix bgcolor"<#if ( goods_index % 2 == 0  ) >id=""<#else>id="bgcolor"</#if>>
              <li style="margin-right:15px;">
              <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.goodsId},'${goods.goodsName}:"quotes"','${goods.type}')" class="f6">${lang.compare}</a>
              </li>
              <li class="goodsName">
              <a href="${goods.url}" class="f6 f5">
                  <#if ( goods.goodsStyleName??  ) >
                  ${goods.goodsStyleName}<br />
                  <#else>
                  ${goods.goodsName}<br />
                  </#if>
                </a>
               <#if ( goods.goodsBrief??  ) >
              ${lang.goodsBrief}${goods.goodsBrief}<br />
              </#if>
              </li>
              <li>
              <#if ( showMarketprice??  ) >
              ${lang.marketPrice}<font class="market">${goods.marketPrice}</font><br />
              </#if>
              <#if ( goods.promotePrice != ""  ) >
              ${lang.promotePrice}<font class="shop">${goods.promotePrice}</font><br />
              <#else>
              ${lang.shopPrice}<font class="shop">${goods.shopPrice}</font><br />
              </#if>
              </li>
              <li class="action">
              <a href="javascript:collect(${goods.goodsId});" class="abg f6">${lang.favourableGoods}</a>
              <a href="javascript:addToCart(${goods.goodsId})"><img src="images/bnt_buy_1.gif"></a>
              </li>
              </ul>
              </#list>
              </div>
             </#if>
          </form>
          <script type="text/javascript">
        <#list lang.compareJs?keys as key> <#assign item = lang.compareJs.get(key)>
        var ${key} = "${item}";
        </#list>

				<#list lang.compareJs?keys as key> <#assign item = lang.compareJs.get(key)>
        <#if ( key != 'buttonCompare'  ) >
        var ${key} = "${item}";
        <#else>
        var button_compare = '';
        </#if>
        </#list>


        var compare_no_goods = "${lang.compareNoGoods}";
        window.onload = function()
        {
          Compare.init();
          fixpng();
        }
        </script>
        <#else>
        <div style="padding:20px 0px; text-align:center" class="f5" >${lang.noSearchResult}</div>
        </#if>
        </div>
      </div>
      <div class="blank"></div>
      <#include "library/pages.ftl">
   </#if>

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
