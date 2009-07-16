<#if helps??>
<#list helps as help_cat>
<dl>
  <dt><a href='${help_cat.id}' title="${help_cat.name}">${help_cat.name}</a></dt>
  <#list help_cat.article as item>
  <dd><a href="${item.url}" title="${item.title}">${item.shortTitle}</a></dd>
  </#list>
</dl>
</#list>
</#if>