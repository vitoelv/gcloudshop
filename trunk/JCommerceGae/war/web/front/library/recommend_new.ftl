<#if  newGoods??  >
<#if  catRecSign  !=  1  >
<div class="box">
<div class="box_2 centerPadd">
  <div class="itemTit New" id="itemNew">
      <#if  catRec[2]??  >
      <h2><a href="javascript:void(0)" onclick="change_tab_style('itemNew', 'h2', this);get_cat_recommend(2, 0);">${lang.allGoods}</a></h2>
      <#list catRec[2] as recData>
      <h2 class="h2bg"><a href="javascript:void(0)" onclick="change_tab_style('itemNew', 'h2', this);get_cat_recommend(2, ${recData.catId})">${recData.catName}</a></h2>
      </#list>
      </#if>
  </div>
  <div id="show_new_area" class="clearfix goodsBox">
  </#if>
  <#list newGoods as goods>
  <div class="goodsItem">
         <span class="news"></span>
           <a href="${goods.url}"><img src="${goods.thumb}" alt="${goods.name?html}" class="goodsimg" /></a><br />
           <p><a href="${goods.url}" title="${goods.name?html}">${goods.shortStyleName}</a></p>
           <font class="f1">
           <#if  goods.promotePrice  !=  ""  >
          ${goods.promotePrice}
          <#else>
          ${goods.shopPrice}
          </#if>
           </font>
        </div>
  </#list>
  <div class="more"><a href="../search.action?intro=new"><img src="images/more.gif" /></a></div>
  <#if  catRecSign  !=  1  >
  </div>
</div>
</div>
<div class="blank5"></div>
  </#if>
</#if>
