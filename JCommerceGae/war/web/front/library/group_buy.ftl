<#if  groupBuyGoods??  >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.groupBuyGoods}</span><a href="group_buy.action"><img src="../images/more.gif"></a></h3>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
      <#list groupBuyGoods as goods>
      <div class="goodsItem">
           <a href="${goods.url}"><img src="${goods.thumb}" alt="${goods.goodsName?html}" class="goodsimg" /></a><br />
					 <p><a href="${goods.url}" title="${goods.goodsName?html}">${goods.shortStyleName?html}</a></p>
           <font class="shop_s">${goods.lastPrice}</font>
        </div>
      </#list>
    </div>
    </div>
 </div>
</div>
<div class="blank5"></div>
</#if>