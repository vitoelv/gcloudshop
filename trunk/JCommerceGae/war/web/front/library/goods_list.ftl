<div class="box">
 <div class="box_1">
  <h3>
  <span><@s.text name="goods_list"/></span>
  <form method="GET" class="sort" name="listform">
  <@s.text name="btn_display"/>：
  <a href="javascript:;" onClick="javascript:display_mode('list')"><img src="${template_root}/images/display_mode_list<#if pager.display == 'list'>_act</#if>.gif"></a>
  <a href="javascript:;" onClick="javascript:display_mode('grid')"><img src="${template_root}/images/display_mode_grid<#if pager.display == 'grid'>_act</#if>.gif"></a>
  <a href="javascript:;" onClick="javascript:display_mode('text')"><img src="${template_root}/images/display_mode_text<#if pager.display == 'text'>_act</#if>.gif"></a>&nbsp;&nbsp;
      <select name="sort" style="border:1px solid #ccc;">
        <#list pager.sorts as sort>
        <option value="<@s.text name="${sort}"/>" <#if pager.sort == sort> selected="selected"</#if>><@s.text name="${sort}"/></option>
        </#list>
      </select>
      <select name="order" style="border:1px solid #ccc;">
        <#list pager.orders as order>
        <option value="<@s.text name="${order}"/>" <#if pager.order == order> selected="selected"</#if>><@s.text name="${order}"/></option>
        </#list>
      </select>
      <input type="image" name="imageField" src="${template_root}/images/bnt_go.gif" alt="go"/>
      <input type="hidden" name="category" value="${category}" />
      <input type="hidden" name="display" value="${pager.display}" id="display" />
      <input type="hidden" name="brand" value="${brand}" />
      <input type="hidden" name="price_min" value="${priceMin}" />
      <input type="hidden" name="price_max" value="${priceMax}" />
      <input type="hidden" name="filter_attr" value="${filterAttr}" />
      <input type="hidden" name="page" value="${pager.page}" />
    </form>
  </h3>

    <#if category??>
  <form name="compareForm" action="compare.action" method="post" onSubmit="return compareGoods(this);">
    </#if>
    <#if pager.display == 'list'>
    <div class="goodsList">
    <#list goods_list as goods>
    <ul class="clearfix bgcolor"<#if goods_index % 2 == 0>id=""<#else>id="bgcolor"</#if>
    <li>
    <br>
    <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.id},'${goods.name}','${goods.type}')" class="f6">比较</a>
    </li>
    <li class="thumb"><a href="goods.action?id=${goods.id}"><img src="${goods.thumb}" alt="${goods.name}" /></a></li>
    <li class="goodsName">
    <a href="goods.action?id=${goods.id}" class="f6">
        <#if goods.nameStyle??>
        ${goods.nameStyle}<br />
        <#else>
        ${goods.name}<br />
        </#if>
    </a>
    <#if goods.brief??>
    <@s.text name="goods_brief"/>${goods.brief}<br />
    </#if>
    </li>
    <li>
    <#if show_marketprice>
    <@s.text name="market_price"/><font class="market">${goods.marketPrice}</font><br />
    </#if>
    <#if 0 < goods.promotePrice>
    <@s.text name="promote_price"/><font class="shop">${goods.promotePrice}</font><br />
    <#else>
    <@s.text name="shop_price"/><font class="shop">${goods.shopPrice}</font><br />
    </#if>
    </li>
    <li class="action">
    <a href="javascript:collect(${goods.id});" class="abg f6"><@s.text name="favourable_goods"/></a>
    <a href="javascript:addToCart(${goods.id})"><img src="${template_root}/images/bnt_buy_1.gif"></a>
    </li>
    </ul>
    </#list>
    </div>
    <#else><#if pager.display == 'grid'>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
    <#list goods_list as goods>
    <#if goods.id??>
     <div class="goodsItem">
           <a href="${goods.url}"><img src="${goods.thumb}" alt="${goods.name}" class="goodsimg" /></a><br />
           <p><a href="${goods.url}" title="${goods.name}">${goods.name}</a></p>
           <#if show_marketprice>
            <@s.text name="market_prices"/><font class="market_s">${goods.marketPrice}</font><br />
            </#if>
            <#if 0 < goods.promotePrice>
            <@s.text name="promote_price"/><font class="shop_s">${goods.promotePrice}</font><br />
            <#else>
            <@s.text name="shop_prices"/><font class="shop_s">${goods.shopPrice}</font><br />
            </#if>
           <a href="javascript:collect(${goods.id});" class="f6"><@s.text name="btn_collect"/></a> |
           <a href="javascript:addToCart(${goods.id})" class="f6"><@s.text name="btn_buy"/></a> |
           <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.id},'${goods.name}','${goods.type}')" class="f6"><@s.text name="compare"/></a>
        </div>
    </#if>
    </#list>
    </div>
    </div>
    </#if>
    <#if pager.display == 'text'>
    <div class="goodsList">
    <#list goods_list as goods>
     <ul class="clearfix bgcolor" <#if smarty.foreach.goods_list.index % 2 == 0>id=""<#else>id="bgcolor"</#if>>
    <li style="margin-right:15px;">
    <a href="javascript:;" id="compareLink" onClick="Compare.add(${goods.id},'${goods.name}','${goods.type}')" class="f6"><@s.text name="compare"/></a>
    </li>
    <li class="goodsName">
    <a href="${goods.url}" class="f6 f5">
        <#if goods.nameStyle??>
        ${goods.nameStyle}<br />
        <#else>
        ${goods.name}<br />
        </#if>
      </a>
     <#if goods.brief??>
    <@s.text name="goods_brief"/>${goods.brief}<br />
    </#if>
    </li>
    <li>
    <#if show_marketprice>
    <@s.text name="market_price"/><font class="market">${goods.marketPrice}</font><br />
    </#if>
    <#if 0 < goods.promotePrice>
    <@s.text name="promote_price"/><font class="shop">${goods.promotePrice}</font><br />
    <#else>
    <@s.text name="shop_price"/><font class="shop">${goods.shopPrice}</font><br />
    </#if>
    </li>
    <li class="action">
    <a href="javascript:collect(${goods.id});" class="abg f6"><@s.text name="favourable_goods"/></a>
    <a href="javascript:addToCart(${goods.id})"><img src="${template_root}/images/bnt_buy_1.gif"></a>
    </li>
    </ul>
    </#list>
    </div>
    </#if>
    </#if>
  <#if category??>
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
{foreach from=$lang.compare_js item=item key=key}
{if $key neq 'button_compare'}
var {$key} = "{$item}";
{else}
var button_compare = '';
{/if}
{/foreach}
var compare_no_goods = "<@s.text name="compare_no_goods"/>";
</script>