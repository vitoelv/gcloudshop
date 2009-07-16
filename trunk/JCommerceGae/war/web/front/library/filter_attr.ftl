<#if filter_attr_list??>
<div class="box">
 <div class="box_1">
  <h3><span>${filter_attr_name}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list filter_attr_list as attr>
    <#if attr.selected>
    <a href="${attr.url}">${attr.value}<#if attr.goodsNum??>(${attr.goodsNum})</#if></a><br />
    <#else>
    <a href="${attr.url}">${attr.value}<#if attr.goodsNum??>(${attr.goodsNum})</#if></a><br />
    </#if>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>