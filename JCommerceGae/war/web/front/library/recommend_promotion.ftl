<#if promotion_goods??>
<div id="sales" class="f_l clearfix">
      <h1><a href="search.action?intro=promotion"><img src="${template_root}/images/more.gif" /></a></h1>
       <div class="clearfix goodBox">
         <#list promotion_goods as goods>
         <#if goods_index <= 3>
           <div class="goodList">
           <a href="goods.action?id=${goods.id}"><img src="${goods.thumb}" border="0" alt="${goods.name}"/></a><br />
		   <p><a href="goods.action?id=${goods.id}" title="${goods.name}">${goods.shortName}</a></p>
           <@s.text name="promote_price"/><font class="f1">${goods.promotePrice}</font>
           </div>
         </#if>
         </#list>
       </div>
      </div>
</#if>