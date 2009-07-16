<#if auction_list??>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="auction_goods"/></span><a href="auction.action"><img src="${template_root}/images/more.gif"></a></h3>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
      <#list auction_list as auction>
      <div class="goodsItem">
           <a href="auction.action?id=${auction.id}"><img src="${auction.thumb}" alt="${auction.goodsName}" class="goodsimg" /></a><br />
           <p><a href="auction.action?id=${auction.id}" title="${auction.goodsName}">${auction.shortStyleName}</a></p>
           <font class="shop_s">${auction.formatedStartPrice}</font>
        </div>
      </#list>
    </div>
    </div>
 </div>
</div>
<div class="blank5"></div>
</#if>