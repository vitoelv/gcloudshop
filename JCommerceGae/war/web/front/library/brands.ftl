<#if brandinfoList??>
  <#list brandinfoList as brand>
    <#if brand_index <= 11>
      <#if brand.brand_logo??>
        <a href="brand.action?${brand.brand.id}"><img src="data/brandlogo/${brand.brand.logo}" alt="${brand.brand.name} (${brand.num})" /></a>
      <#else>
      <div style="clear:both;">
        <a href="brand.action?id=${brand.brand.id}">${brand.brand.name} <#if brand.num??> (${brand.num})</#if></a>
      </div>
      </#if>
    </#if>
  </#list>
<div class="brandsMore"><a href="brand.action"><img src="${template_root}/images/moreBrands.gif" /></a></div>
</#if>