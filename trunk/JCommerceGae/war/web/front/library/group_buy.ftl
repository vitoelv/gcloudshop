<#if group_buy_goods??>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="group_buy_goods"/></span><a href="group_buy.action"><img src="${template_root}/images/more.gif"></a></h3>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
      <#list group_buy_goods as goods>
      <div class="goodsItem">
           <a href="goods.action?id=${goods.id}"><img src="${goods.thumb}" alt="${goods.name}" class="goodsimg" /></a><br />
					 <p><a href="goods.action?id=${goods.id}" title="${goods.name}">${goods.name}</a></p>
           <font class="shop_s">${goods.lastPrice}</font>
        </div>
      </#list>
    </div>
    </div>
 </div>
</div>
<div class="blank5"></div>
</#if>