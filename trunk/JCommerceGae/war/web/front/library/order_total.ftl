<script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/utils.js"></script>

<div id="ECS_ORDERTOTAL">
<table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
  <#if ( userId?? && ( config.useIntegral?? || config.useBonus?? )  ) >
  <tr>
    <td align="right" bgcolor="#ffffff">
      ${lang.completeAcquisition} <font class="f4_b">${total.willGetIntegral}</font> ${pointsName}
      <#if ( total.willGetBonus??  ) >
      ，${lang.withPrice} <font class="f4_b">${total.willGetBonus}</font>${lang.de}${lang.bonus}。
      </#if>    </td>
  </tr>
  </#if>
  <tr>
    <td align="right" bgcolor="#ffffff">
      ${lang.goodsAllPrice}: <font class="f4_b">${total.goodsPriceFormated}</font>
      <#if ( total.discount > 0  ) >
      - ${lang.discount}: <font class="f4_b">${total.discountFormated}</font>
      </#if>
      <#if ( total.tax > 0  ) >
      + ${lang.tax}: <font class="f4_b">${total.taxFormated}</font>
      </#if>
      <#if ( total.shippingFee > 0  ) >
      + ${lang.shippingFee}: <font class="f4_b">${total.shippingFeeFormated}</font>
      </#if>
      <#if ( total.shippingInsure > 0  ) >
      + ${lang.insureFee}: <font class="f4_b">${total.shippingInsureFormated}</font>
      </#if>
      <#if ( total.payFee > 0  ) >
      + ${lang.payFee}: <font class="f4_b">${total.payFeeFormated}</font>
      </#if>
      <#if ( total.packFee > 0  ) >
      + ${lang.packFee}: <font class="f4_b">${total.packFeeFormated}</font>
      </#if>
      <#if ( total.cardFee > 0  ) >
      + ${lang.cardFee}: <font class="f4_b">${total.cardFeeFormated}</font>
      </#if>    </td>
  </tr>
  <#if ( total.surplus > 0 || total.integral > 0 || total.bonus > 0  ) >
  <tr>
    <td align="right" bgcolor="#ffffff">
      <#if ( total.surplus > 0  ) >
      - ${lang.useSurplus}: <font class="f4_b">${total.surplusFormated}</font>
      </#if>
      <#if ( total.integral > 0  ) >
      - ${lang.useIntegral}: <font class="f4_b">${total.integralFormated}</font>
      </#if>
      <#if ( total.bonus > 0  ) >
      - ${lang.useBonus}: <font class="f4_b">${total.bonusFormated}</font>
      </#if>    </td>
  </tr>
  </#if>
  <tr>
    <td align="right" bgcolor="#ffffff"> ${lang.totalFee}: <font class="f4_b">${total.amountFormated}</font>
  <#if ( isGroupBuy??  ) ><br />
  ${lang.noticeGbOrderAmount}</#if></td>
  </tr>
</table>
</div>