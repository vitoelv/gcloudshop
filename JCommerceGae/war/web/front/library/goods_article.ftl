<#if $goods_article_list??>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="article_releate"/></span></h3>
  <div class="boxCenterList RelaArticle">
    <#list goods_article_list as article>
    <a href="article.action?id={$article.id}" title="${article.title}">${article.shortTitle}</a><br />
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>