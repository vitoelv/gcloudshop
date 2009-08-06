<#if  !orderQuery?has_content  >
<script>var invalid_order_sn = "${lang.invalidOrderSn}"</script>
<div class="box">
 <div class="box_1">
  <h3><span>${lang.orderQuery}</span></h3>
  <div class="boxCenterList">
    <form name="ecsOrderQuery">
    <input type="text" name="order_sn" class="inputBg" /><br />
    <div class="blank5"></div>
    <input type="button" value="${lang.queryOrder}" class="bnt_blue_2" onclick="orderQuery()" />
    </form>
    <div id="ECS_ORDER_QUERY" style="margin-top:8px;">
      <#else>
      <#if  orderQuery.userId??  >
<b>${lang.orderNumber}：</b><a href="user.action?act=order_detail&order_id=${orderQuery.orderId}" class="f6">${orderQuery.orderSn}</a><br>
  <#else>
<b>${lang.orderNumber}：</b>${orderQuery.orderSn}<br>
  </#if>
<b>${lang.orderStatus}：</b><br><font class="f1">${orderQuery.orderStatus}</font><br>
  <#if  orderQuery.invoiceNo??  >
<b>${lang.consignment}：</b>${orderQuery.invoiceNo}<br>
  </#if>
      <#if  orderQuery.shippingDate??  >：${lang.shippingDate} ${orderQuery.shippingDate}<br>
  </#if>
  </#if>
    </div>
  </div>
 </div>
</div>
<div class="blank5"></div>
