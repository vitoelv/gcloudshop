<#if ( auctionList??  ) >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.auctionGoods}</span><a href="auction.action"><img src="images/more.gif"></a></h3>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
      <#list auctionList as auction>
      <div class="goodsItem">
           <a href="${auction.url}"><img src="${auction.thumb}" alt="${auction.goodsName?html}" class="goodsimg" /></a><br />
           <p><a href="${auction.url}" title="${auction.goodsName?html}">${auction.shortStyleName?html}</a></p>
           <font class="shop_s">${auction.formatedStartPrice}</font>
        </div>
      </#list>
    </div>
    </div>
 </div>
</div>
<div class="blank5"></div>
</#if>