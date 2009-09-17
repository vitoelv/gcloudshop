<div class="userMenu">
<a href="user.action" <#if  action  ==  'default'  >class="curs"</#if>><img src="images/u1.gif"> ${lang.labelWelcome}</a>
<a href="user.action?act=profile"<#if  action  ==  'profile'  >class="curs"</#if>><img src="images/u2.gif"> ${lang.labelProfile}</a>
<a href="user.action?act=order_list"<#if  action  ==  'orderList'||  action  ==  'orderDetail'  >class="curs"</#if>><img src="images/u3.gif"> ${lang.labelOrder}</a>
<a href="user.action?act=address_list"<#if  action  ==  'addressList'  >class="curs"</#if>><img src="images/u4.gif"> ${lang.labelAddress}</a>
<a href="user.action?act=collection_list"<#if  action  ==  'collectionList'  >class="curs"</#if>><img src="images/u5.gif"> ${lang.labelCollection}</a>
<a href="user.action?act=message_list"<#if  action  ==  'messageList'  >class="curs"</#if>><img src="images/u6.gif"> ${lang.labelMessage}</a>
<a href="user.action?act=tag_list"<#if  action  ==  'tagList'  >class="curs"</#if>><img src="images/u7.gif"> ${lang.labelTag}</a>
<a href="user.action?act=booking_list"<#if  action  ==  'bookingList'  >class="curs"</#if>><img src="images/u8.gif"> ${lang.labelBooking}</a>
<a href="user.action?act=bonus"<#if  action  ==  'bonus'  >class="curs"</#if>><img src="images/u9.gif"> ${lang.labelBonus}</a>
<#if  affiliate.on  ==  1  ><a href="user.action?act=affiliate"<#if  action  ==  'affiliate'  >class="curs"</#if>><img src="images/u10.gif"> ${lang.labelAffiliate}</a></#if>
<a href="user.action?act=comment_list"<#if  action  ==  'commentList'  >class="curs"</#if>><img src="images/u11.gif"> ${lang.labelComment}</a>
${lang.labelGroupBuy}</a>-->
<a href="user.action?act=track_packages"<#if  action  ==  'trackPackages'  >class="curs"</#if>><img src="images/u12.gif"> ${lang.labelTrackPackages}</a>
<a href="user.action?act=account_log"<#if  action  ==  'accountLog'  >class="curs"</#if>><img src="images/u13.gif"> ${lang.labelUserSurplus}</a>
<#if  showTransformPoints??  >
<a href="user.action?act=transform_points"<#if  action  ==  'transformPoints'  >class="curs"</#if>><img src="images/u14.gif"> ${lang.labelTransformPoints}</a>
</#if>
<a href="user.action?act=logout" style="background:none; text-align:right; margin-right:10px;"><img src="images/bnt_sign.gif"></a>
</div>