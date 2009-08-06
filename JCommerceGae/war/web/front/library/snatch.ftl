<#if  myprice.isEnd  ==  false  >
<div class="box">
   <div class="box_1">
    <h3><span>${lang.meBid}</span></h3>
    <div class="boxCenterList">
  <form action="javascript:bid()" method="post" name="formBid" id="formBid">
   ${lang.myIntegral}：
    ${myprice.payPoints}<br />
    ${lang.bid}：
    ${id}" /><input name="price" type="text" id="price" />-->
    <input type="hidden" name="snatch_id" value="${id}" /><input name="price" type="text" class="inputBg" >
    <input type="submit" name="Submit" class="bnt_blue_1" value="${lang.meBid}" style="vertical-align:middle;" />
  </form>
  </div>
</div>
</div>
<div class="blank5"></div>
<div class="box">
   <div class="box_1">
    <h3><span>${lang.meNowBid}</span></h3>
    <div class="boxCenterList">
    <#list myprice.bidPrice as item>
      ${item.price}
      <#if  item.isOnly??  >
      (${lang.onlyPrice})
      </#if>
      <br>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
<#else>


<div class="box">
   <div class="box_1">
    <h3><span>${lang.viewSnatchResult}</span></h3>
    <div class="boxCenterList">
<#if  result??  >
<form name="buy" action="snatch.action" method="get">
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
  <tr>
    <td bgcolor="#FFFFFF">${lang.victoryUser}
</td>
    <td bgcolor="#FFFFFF">${result.userName}
      <input type="hidden" name="act" value="buy" />
      <input type="hidden" name="id" value="${id}" />
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">${lang.priceBid}</td>
    <td bgcolor="#FFFFFF">${result.formatedBidPrice} &nbsp;&nbsp;
    <#if  result.orderCount  ==  0  &&  result.userId??  ==  smarty.session.userId??  >
      <input type="submit" name="bug" class="bnt_blue_1" title="${lang.buttonBuy}" />
    </#if>
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">${lang.bidTime}</td>
    <td bgcolor="#FFFFFF">${result.bidTime}</td>
  </tr>
</table>
</form>
<#else>
${lang.notVictoryUser}
</#if>
</div>
</div>
</div>
</#if>
