<div class="box">
 <div class="box_2">
  <div class="top10Tit"></div>
  <div class="top10List">
   <ul>
    <#list topList as goods>
    <li><img src="../images/top_{$smarty.foreach.top_goods.iteration}.gif" /> <a href="goods.action?id=${goods.id}" title="${goods.goodsName}">${goods.goodsName}</a></li>
    </#list>
   </ul>
  </div>
 </div>
</div>
<div class="blank5"></div>
