<#if promotion_info??>
<!-- 促销信息 -->
<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="promotion_info"/></span></h3>
  <div class="boxCenterList RelaArticle">
    <#list promotion_info as item>
    <#if item.type == "snatch">
    <a href="snatch.action" ><@s.text name="snatch_promotion"/></a>
    <#elseif item.action == "group_buy">
    <a href="group_buy.action" ><@s.text name="group_promotion"/></a>
    <#elseif item.type == "auction">
    <a href="auction.action" ><@s.text name="auction_promotion"/></a>
    <#elseif item.type == "favourable">
    <a href="activity.action" ><@s.text name="favourable_promotion"/></a>
    </#if>
    <a href="{$item.url}" style="background:none; padding-left:0px;">${item.act_name}</a><br />
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>