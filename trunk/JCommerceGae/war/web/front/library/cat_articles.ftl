<div class="box">
 <div class="box_1">
  <h3>
  <span><a href="${articlesCat.url}">${articlesCat.name?html}</a></span>
  <a href="${articlesCat.url}"><img src="images/more.gif" alt="more" /></a>
  </h3>
  <div class="boxCenterList RelaArticle">
  <#list articles as article>
  <a href="${article.url}" title="${article.title?html}">${article.shortTitle}</a> ${article.addTime}<br />
  </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
