<#if related_goods??>
<div class="box">
     <div class="box_1">
      <h3><span><@s.text name="releate_goods"/></span></h3>
      <div class="boxCenterList clearfix">
      <#list related_goods as releated_goods_data>
        <ul class="clearfix">
          <li class="goodsimg"><a href="goods.action?id=${releated_goods_data.id}"><img src="${releated_goods_data.thumb}" alt="${releated_goods_data.name}" class="B_blue" /></a></li>
          <li>
        <a href="goods.action?id=${releated_goods_data.id}" title="${releated_goods_data.name}">${releated_goods_data.name}</a><br />
        <#if releated_goods_data.promotePrice != 0>
        <@s.text name="promote_price"/><font class="f1">${releated_goods_data.promotePrice}</font>
        <#else>
        <@s.text name="shop_price"/><font class="f1">${releated_goods_data.shopPrice}</font>
        </#if>
          </li>
        </ul>
        </#list>
      </div>
     </div>
    </div>
<div class="blank5"></div>
</#if>
