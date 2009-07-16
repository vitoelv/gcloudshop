<!-- 开始循环属性关联的商品-->
<#if attribute_linked??>
<#list attribute_linked as linked>
<#if linked.goods??>
<div class="box">
 <div class="box_1">
  <h3><span title="${linked.title}">${linked.title}<!-- 11 chars --></span></h3>
  <div class="boxCenterList RelaArticle">
  <#list linked.goods as linked_goods_data>
  <a href="goods.action?id=${linked_goods_data.id}" title="${linked_goods_data.name}">${linked_goods_data.shortName}</a><br />
  </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>
</#list>
</#if>
<!-- 结束属性关联的商品 -->