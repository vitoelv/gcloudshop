<#if ( invoiceList??  ) >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.shippingQuery}</span></h3>
  <div class="boxCenterList">
    <#list invoiceList as invoice>
   ${lang.orderNumber} ${invoice.orderSn}<br />
   ${lang.consignment} ${invoice.invoiceNo}
   <div class="blank"></div>
   </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>