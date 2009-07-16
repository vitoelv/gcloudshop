<#if fittings??>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="accessories_releate"/></span></h3>
  <div class="boxCenterList clearfix">
    <#list fittings as goods>
    <ul class="clearfix">
      <li class="goodsimg">
      <a href="goods.action?id={$goods.id}" target="_blank"><img src="${goods.thumb}" alt="${goods.name}" class="B_blue" /></a>
      </li>
      <li>
      <a href="goods.action?id=${goods.id}" target="_blank" title="${goods.name}">${goods.shortName}</a><br />
      <@s.text name="fittings_price"/><font class="f1">${goods.fittingsPrice}</font><br />
      </li>
    </ul>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>




