<#if  filterAttrList??  >
<div class="box">
 <div class="box_1">
  <h3><span>${filterAttrName}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list filterAttrList as attr>
    <#if  attr.selected??  >
    <a href="${attr.url}">${attr.attrValue}<#if  attr.goodsNum??  >(${attr.goodsNum})</#if></a><br />
    <#else>
    <a href="${attr.url}">${attr.attrValue}<#if  attr.goodsNum??  >(${attr.goodsNum})</#if></a><br />
    </#if>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>