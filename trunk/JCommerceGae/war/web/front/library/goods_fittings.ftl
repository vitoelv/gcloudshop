<#if ( fittings??  ) >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.accessoriesReleate}</span></h3>
  <div class="boxCenterList clearfix">
    <#list fittings as goods>
    <ul class="clearfix">
      <li class="goodsimg">
      <a href="${goods.url}" target="_blank"><img src="${goods.goodsThumb}" alt="${goods.name?html}" class="B_blue" /></a>
      </li>
      <li>
      <a href="${goods.url}" target="_blank" title="${goods.goodsName?html}">${goods.shortName?html}</a><br />
      ${lang.fittingsPrice}<font class="f1">${goods.fittingsPrice}</font><br />
      </li>
    </ul>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>




