<div class="box">
 <div class="box_2">
  <div class="top10Tit"></div>
  <div class="top10List">
   <ul>
    <#list topGoods as goods>
    <li><img src="../images/top_${smarty.foreach.topGoods.iteration}.gif" /> <a href="${goods.url}" title="${goods.name?html}">${goods.shortName}</a></li>
    </#list>
   </ul>
  </div>
 </div>
</div>
<div class="blank5"></div>
