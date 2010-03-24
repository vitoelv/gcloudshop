<#if ( catList??  ) >
<div class="box">
 <div class="box_1">
  <div id="category_tree">
    <#list catList as cat>
     <dl>
     <dt><a href="${cat.url}">${cat.catName?html} <#if ( cat.goodsNum??  ) >(${cat.goodsNum})</#if></a></dt>
     </dl>
    </#list> 
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>