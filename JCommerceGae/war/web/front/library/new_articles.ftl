<ul>
<#list new_articles as article>
  <li>
	[<a href="article.action?id=${article.id}">${article.articleCategory.name}</a>] <a href="article.action?id="${article.id}" title="${article.title}">${article.title}</a>
  </li>
</#list>
</ul>