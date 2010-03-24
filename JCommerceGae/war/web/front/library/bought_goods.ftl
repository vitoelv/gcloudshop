<#if ( boughtGoods??  ) >
     <div class="box">
     <div class="box_1">
      <h3><span class="text">${lang.shoppingAndOther}</span></h3>
      <div class="boxCenterList clearfix ie6">
       <#list boughtGoods as boughtGoodsData>
        <div class="goodsItem">
         <a href="${boughtGoodsData.url}"><img src="${boughtGoodsData.goodsThumb}" alt="${boughtGoodsData.goodsName}"  class="goodsimg" /></a><br />
         <p><a href="${boughtGoodsData.url}" title="${boughtGoodsData.goodsName}">${boughtGoodsData.shortName}</a></p> 
         <#if ( boughtGoodsData.promotePrice != 0  ) >
        <font class="shop_s">${boughtGoodsData.formatedPromotePrice}</font>
        <#else>
        <font class="shop_s">${boughtGoodsData.shopPrice}</font>
        </#if>
        </div>
        </#list>
      </div>
     </div>
    </div>
    <div class="blank5"></div>
    </#if>