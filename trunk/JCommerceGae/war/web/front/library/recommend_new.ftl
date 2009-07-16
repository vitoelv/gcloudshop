<#if newGoods??>
<#if cat_rec_sign??>
<!-- {if $cat_rec_sign neq 1} -->
<div class="box">
<div class="box_2 centerPadd">
  <div class="itemTit New" id="itemNew">
      <#if cat_rec[2]??>
      <h2><a href="javascript:void(0)" onclick="change_tab_style('itemNew', 'h2', this);get_cat_recommend(2, 0);">{$lang.all_goods}</a></h2>
      <#list cat_rec[2] as rec_data>
      <h2 class="h2bg"><a href="javascript:void(0)" onclick="change_tab_style('itemNew', 'h2', this);get_cat_recommend(2, {$rec_data.cat_id})">{$rec_data.cat_name}</a></h2>
      </#list>
      </#if>
  </div>
  <div id="show_new_area" class="clearfix goodsBox">
  </#if>
  <#list newGoods as goods>
  <div class="goodsItem">
         <span class="news"></span>
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
  <div class="more"><a href="search.action?intro=new"><img src="images/more.gif" /></a></div>
  <#if cat_rec_sign??>
  <!-- {if $cat_rec_sign neq 1} -->
  </div>
</div>
</div>
<div class="blank5"></div>
  </#if>
</#if>
