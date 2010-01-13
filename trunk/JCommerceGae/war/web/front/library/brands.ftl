<#if  brandList??  >
  <#list brandList as brand>
      <#if  brand.brandLogo??  >
        <a href="${brand.url}"><img src="data/brandlogo/${brand.brandLogo}" alt="${brand.brandName?html} (${brand.goodsNum})" /></a>
      <#else>
      <div style="clear:both;">
        <a href="${brand.url}">${brand.brandName?html} <#if  brand.goodsNum??  >(${brand.goodsNum})</#if></a>
      </div>
      </#if>

  </#list>
<div class="brandsMore"><a href="brand.action"><img src="images/moreBrands.gif" /></a></div>
</#if>