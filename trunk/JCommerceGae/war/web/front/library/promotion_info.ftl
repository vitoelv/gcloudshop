<#if  promotionInfo??  >
<!-- 促销信息 -->
<div class="box">
 <div class="box_1">
  <h3><span>${lang.promotionInfo}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list promotionInfo as item>
    <#if  item.type  ==  "snatch"  >
    <a href="snatch.action" title="${lang.item.type}">${lang.snatchPromotion}</a>
    <#elseif  item.type  ==  "groupBuy"  >
    <a href="group_buy.action" title="${lang.item.type}">${lang.groupPromotion}</a>
    <#elseif  item.type  ==  "auction"  >
    <a href="auction.action" title="${lang.item.type}">${lang.auctionPromotion}</a>
    <#elseif  item.type  ==  "favourable"  >
    <a href="activity.action" title="${lang.item.type}">${lang.favourablePromotion}</a>
    </#if>
    <a href="${item.url}" title="${lang.item.type} ${item.actName}${item.time}" style="background:none; padding-left:0px;">${item.actName}</a><br />
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>