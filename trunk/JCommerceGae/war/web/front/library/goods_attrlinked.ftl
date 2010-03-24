<#list attributeLinked as linked>
<#if ( linked.goods??  ) >
<div class="box">
 <div class="box_1">
  <h3><span title="${linked.title}">${linked.title}}</span></h3>
  <div class="boxCenterList RelaArticle">
  <#list linked.goods as linkedGoodsData>
  <a href="${linkedGoodsData.url}" title="${linkedGoodsData.goodsName?html}">${linkedGoodsData.shortName?html}</a><br />
  </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>
</#list>