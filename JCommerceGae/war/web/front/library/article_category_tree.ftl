<div class="box">
 <div class="box_1">
  <h3><span>${lang.articleCat}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list articleCategories as cat>
    <a href="${cat.url}">${cat.name?html}</a><br />
      <#list cat.children as child>
      <a href="${child.url}" style="background-image:none;">${child.name?html}</a><br />
      </#list>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>