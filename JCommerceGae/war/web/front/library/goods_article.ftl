<#if  goodsArticleList??  >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.articleReleate}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list goodsArticleList as article>
    <a href="${article.url}" title="${article.title?html}">${article.shortTitle?html}</a><br />
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>