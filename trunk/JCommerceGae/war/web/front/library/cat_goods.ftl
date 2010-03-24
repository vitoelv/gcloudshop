<div class="box">
 <div class="box_1">
  <h3><span><a href="${goodsCat.url}" class="f6">${goodsCat.name?html}</a></span></h3>
    <div class="centerPadd">
    <div class="clearfix goodsBox" style="border:none;">
      <#list catGoods as goods>
      <div class="goodsItem">
           <a href="${goods.url}"><img src="${goods.thumb}" alt="${goods.name?html}" class="goodsimg" /></a><br />
           <p><a href="${goods.url}" title="${goods.name?html}">${goods.shortName?html}</a></p>
           <#if ( goods.promotePrice != ""  ) >
          <font class="shop_s">${goods.promotePrice}</font>
          <#else>
          <font class="shop_s">${goods.shopPrice}</font>
          </#if>
        </div>
      </#list>
    </div>
    </div>
 </div>
</div>
<div class="blank5"></div>
