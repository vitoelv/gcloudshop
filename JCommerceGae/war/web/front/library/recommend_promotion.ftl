<#if ( promotionGoods??  ) >
<div id="sales" class="f_l clearfix">
      <h1><a href="search.action?intro=promotion"><img src="images/more.gif" /></a></h1>
       <div class="clearfix goodBox">
         <#list promotionGoods as goods>
         <#if ( goods_index <= 3  ) >
           <div class="goodList">
           <a href="${goods.url}"><img src="${goods.thumb}" border="0" alt="${goods.name?html}"/></a><br />
					 <p><a href="${goods.url}" title="${goods.name?html}">${goods.shortName?html}</a></p>
           ${lang.promotePrice}<font class="f1">${goods.promotePrice}</font>
           </div>
         </#if>
         </#list>
       </div>
      </div>
</#if>