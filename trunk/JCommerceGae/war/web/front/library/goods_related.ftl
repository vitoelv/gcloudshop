<#if ( relatedGoods??  ) >
<div class="box">
     <div class="box_1">
      <h3><span>${lang.releateGoods}</span></h3>
      <div class="boxCenterList clearfix">
      <#list relatedGoods as releatedGoodsData>
        <ul class="clearfix">
          <li class="goodsimg"><a href="${releatedGoodsData.url}"><img src="${releatedGoodsData.goodsThumb}" alt="${releatedGoodsData.goodsName}" class="B_blue" /></a></li>
          <li>
        <a href="${releatedGoodsData.url}" title="${releatedGoodsData.goodsName}">${releatedGoodsData.shortName}</a><br />
        <#if ( releatedGoodsData.promotePrice != 0  ) >
        ${lang.promotePrice}<font class="f1">${releatedGoodsData.formatedPromotePrice}</font>
        <#else>
        ${lang.shopPrice}<font class="f1">${releatedGoodsData.shopPrice}</font>
        </#if>
          </li>
        </ul>
        </#list>
      </div>
     </div>
    </div>
<div class="blank5"></div>
</#if>
