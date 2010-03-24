<div class="box">
 <div class="box_1">
  <h3>
  <span>${lang.goodsList}</span>
  <form method="GET" class="sort" name="listform">
  ${lang.btnDisplay}：
  <a href="javascript:;" onClick="javascript:display_mode('list')"><img src="images/display_mode_list<#if ( pager.display == 'list'  ) >_act</#if>.gif" alt="${lang.display.list}"></a>
  <a href="javascript:;" onClick="javascript:display_mode('grid')"><img src="images/display_mode_grid<#if ( pager.display == 'grid'  ) >_act</#if>.gif" alt="${lang.display.grid}"></a>
  <a href="javascript:;" onClick="javascript:display_mode('text')"><img src="images/display_mode_text<#if ( pager.display == 'text'  ) >_act</#if>.gif" alt="${lang.display.text}"></a>&nbsp;&nbsp;
      <select name="sort" style="border:1px solid #ccc;">
        <#list lang.sort?keys as key>
<#assign val = lang.sort.get(key)>
<option value="${key}" <#if pager.sort == key>selected</#if> >${val}</option>
</#list>

      </select>
      <select name="order" style="border:1px solid #ccc;">
        <#list lang.order?keys as key>
<#assign val = lang.order.get(key)>
<option value="${key}" <#if pager.order == key>selected</#if> >${val}</option>
</#list>

      </select>
      <input type="image" name="imageField" src="images/bnt_go.gif" alt="go"/>
      <input type="hidden" name="category" value="${category}" />
      <input type="hidden" name="display" value="${pager.display}" id="display" />
      <input type="hidden" name="brand" value="${brandId}" />
      <input type="hidden" name="price_min" value="${priceMin}" />
      <input type="hidden" name="price_max" value="${priceMax}" />
      <input type="hidden" name="filter_attr" value="${filterAttr}" />
      <input type="hidden" name="page" value="${pager.page}" />
    </form>
  </h3>

    <#if ( category > 0  ) >
  <form name="compareForm" action="compare.action" method="post" onSubmit="return compareGoods(this);">
    </#if>
    <#if ( pager.display == 'list'  ) >
    <div class="goodsList">
    <#list goodsList as goods>
    <ul class="clearfix bgcolor"<#if ( goods_index % 2 == 0  ) >id=""<#else>id="bgcolor"</#if>>
    <li>
    <br>
    <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.goodsId},'${goods.goodsName}:"quotes"','${goods.type}')" class="f6">比较</a>
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
  <#if ( category > 0  ) >
  </form>
  </#if>

 </div>
</div>
<div class="blank5"></div>
<script type="Text/Javascript" language="JavaScript">
<!--

function selectPage(sel)
{
  sel.form.submit();
}

//-->
</script>
<script type="text/javascript">
window.onload = function()
{
  Compare.init();
  fixpng();
}
<#list lang.compareJs?keys as key> <#assign item = lang.compareJs.get(key)>
<#if ( key != 'buttonCompare'  ) >
var ${key} = "${item}";
<#else>
var button_compare = '';
</#if>
</#list>
var compare_no_goods = "${lang.compareNoGoods}";
</script>