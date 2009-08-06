<ul>
<#list newArticles as article>
  <li>
	[<a href="${article.catUrl}">${article.catName}</a>] <a href="${article.url}" title="${article.title?html}">${article.shortTitle}</a>
	</li>
</#list>
</ul>