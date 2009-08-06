<#if  helps??  >
<#list helps as helpCat>
<dl>
  <dt>${helpCat.catName}</dt>
  <#list helpCat.article as item>
  <dd><a href="${item.url}" title="${item.title?html}">${item.shortTitle}</a></dd>
  </#list>
</dl>
</#list>
</#if>