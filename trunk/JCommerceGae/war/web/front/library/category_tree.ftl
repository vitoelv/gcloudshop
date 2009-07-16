<div class="box">
 <div class="box_1">
  <div id="category_tree">
    <#list categories as cat>
     <dl>
     <dt><a href="category.action?id=${cat.id}">${cat.name}</a></dt>
     <#list cat.children as child>
     <dd><a href="category.action$id={child.id}">${child.name}</a></dd>
     </#list>
     </dl>
    </#list> 
  </div>
 </div>
</div>
<div class="blank5"></div>
