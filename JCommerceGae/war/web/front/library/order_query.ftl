<#if !order_query??>
<script>var invalid_order_sn = "<@s.text name="invalid_order_sn"/>"</script>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="order_query"/></span></h3>
  <div class="boxCenterList">
    <form name="ecsOrderQuery">
    <input type="text" name="order_sn" class="inputBg" /><br />
    <div class="blank5"></div>
    <input type="button" value="<@s.text name="query_order"/>" class="bnt_blue_2" onclick="orderQuery()" />
    </form>
    <div id="ECS_ORDER_QUERY" style="margin-top:8px;">
      <#else>
      <#if order_query.userId??>
<b><@s.text name="order_number"/>：</b><a href="user.php?act=order_detail&order_id=${order_query.id}" class="f6">${order_query.sn}</a><br>
  <#else>
<b><@s.text name="order_number"/>：</b>${order_query.orderSn}<br>
  </#if>
<b><@s.text name="order_status"/>：</b><br><font class="f1">${order_query.status}</font><br>
  <#if order_query.invoiceNo??>
<b><@s.text name="consignment"/>：</b>{$order_query.invoice_no}<br>
  </#if>
  <#if order_query.shippingDate??> 
  <@s.text name="shipping_date"/> ${order_query.shippingDate}<br>
  </#if>
  </#if>
    </div>
  </div>
 </div>
</div>
<div class="blank5"></div>
