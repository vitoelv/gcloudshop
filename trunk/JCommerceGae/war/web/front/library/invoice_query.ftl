<#if invoice_list??>
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="shipping_query"/></span></h3>
  <div class="boxCenterList">
    <#list invoice_list as invoice>
   <@s.text name="order_number"/> ${invoice.sn}<br />
   <@s.text name="consignment"/> ${invoice.no}
   <div class="blank"></div>
   </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>