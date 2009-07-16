<#if bestSold??>
<#if cat_rec_sign??><!-- != 1-->
<div class="box">
<div class="box_2 centerPadd">
  <div class="itemTit" id="itemBest">
      <#if cat_rec[1]??>
      <h2><a href="javascript:void(0)" onclick="change_tab_style('itemBest', 'h2', this);get_cat_recommend(1, 0);"><@s.text name="all_goods"/></a></h2>
      <#list cat_rec[1] as rec_data>
      <h2 class="h2bg"><a href="javascript:void(0)" onclick="change_tab_style('itemBest', 'h2', this);get_cat_recommend(1, ${rec_data.id})">${rec_data.name}</a></h2>
      </#list>
      </#if>
  </div>
  <div id="show_best_area" class="clearfix goodsBox">
  </#if>
  <#list bestSold as goods>
  <div class="goodsItem">
         <span class="best"></span>
           <a href="goods.action?id=${goods.id}"><img src="${goods.thumb}" alt="${goods.name}" class="goodsimg" /></a><br />
           <p><a href="goods.action?id=${goods.id}" title="${goods.name}">${goods.nameStyle}</a></p>
           <font class="f1">
           <#if 0 < goods.promotePrice>
          ${goods.promotePrice}
          <#else>
          ${goods.shopPrice}
          </#if>
           </font>
        </div>
  </#list>
  <div class="more"><a href="search.action?intro=best"><img src="${template_root}/images/more.gif" /></a></div>
  <#if cat_rec_sign??><!-- != 1-->
  </div>
</div>
</div>
<div class="blank5"></div>
  </#if>
</#if>
