<div class="box">
 <div class="box_1">
  <div id="category_tree">
    <#list categories as cat>
     <dl>
     <dt><a href="${cat.url}">${cat.catName?html}</a></dt>
     <#list cat.children as child>
     <dd><a href="${child.url}">${child.catName?html}</a></dd>
     </#list>
     </dl>
    </#list> 
  </div>
 </div>
</div>
<div class="blank5"></div>
