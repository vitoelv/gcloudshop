<#if bought_goods??>
     <div class="box">
     <div class="box_1">
      <h3><span class="text"><@s.text name="shopping_and_other"/></span></h3>
      <div class="boxCenterList clearfix ie6">
       <#list bought_goods as bought_goods_data>
        <div class="goodsItem">
         <a href="${bought_goods_data.url}"><img src="${bought_goods_data.thumb}" alt="${bought_goods_data.name}"  class="goodsimg" /></a><br />
         <p><a href="${bought_goods_data.url}" title="${bought_goods_data.name}">${bought_goods_data.shortName}</a></p> 
         <#if bought_goods_data.promotePrice != 0>
        <font class="shop_s">${bought_goods_data.formatedPromotePrice}</font>
        <#else>
        <font class="shop_s">${bought_goods_data.shopPrice}</font>
        </#if>
        </div>
        </#list>
      </div>
     </div>
    </div>
    <div class="blank5"></div>
</#if>