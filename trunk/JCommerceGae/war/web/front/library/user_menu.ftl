<div class="userMenu">
<a href="user.action" <#if  action  ==  'default'  >class="curs"</#if>><img src="images/u1.gif"> ${lang.labelWelcome}</a>
<a href="user.action?act=profile"<#if  action  ==  'profile'  >class="curs"</#if>><img src="images/u2.gif"> ${lang.labelProfile}</a>
<a href="user.action?act=order_list"<#if  action  ==  'orderList'||  action  ==  'orderDetail'  >class="curs"</#if>><img src="images/u3.gif"> ${lang.labelOrder}</a>
<a href="user.action?act=address_list"<#if  action  ==  'addressList'  >class="curs"</#if>><img src="images/u4.gif"> ${lang.labelAddress}</a>
<a href="user.action?act=collection_list"<#if  action  ==  'collectionList'  >class="curs"</#if>><img src="images/u5.gif"> ${lang.labelCollection}</a>
<#if  affiliate.on  ==  1  ><a href="user.action?act=affiliate"<#if  action  ==  'affiliate'  >class="curs"</#if>><img src="images/u10.gif"> ${lang.labelAffiliate}</a></#if>
<a href="user.action?act=comment_list"<#if  action  ==  'commentList'  >class="curs"</#if>><img src="images/u11.gif"> ${lang.labelComment}</a>
<a href="user.action?act=track_packages"<#if  action  ==  'trackPackages'  >class="curs"</#if>><img src="images/u12.gif"> ${lang.labelTrackPackages}</a>
<a href="user.action?act=logout" style="background:none; text-align:right; margin-right:10px;"><img src="images/bnt_sign.gif"></a>
</div>