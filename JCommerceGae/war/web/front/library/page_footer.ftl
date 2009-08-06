<!--底部导航 start-->
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="bNavList clearfix">
   <div class="f_l">
   <#if  navigatorList.bottom??  >
   <#list navigatorList.bottom as nav>
        <a href="${nav.url}" <#if  nav.opennew  ==  1  > target="_blank" </#if>>${nav.name}</a> 
        <#if  nav_has_next??  >
           -   
        </#if>
      </#list>
  </#if>  
   </div>
   <div class="f_r">
   <a href="#top"><img src="images/bnt_top.gif" /></a> <a href="../index.action"><img src="images/bnt_home.gif" /></a>
   </div>
  </div>
 </div>
</div>
<!--底部导航 end-->
<div class="blank"></div>
<!--版权 start-->
<div id="footer">
 <div class="text">
 ${copyright}<br />
 ${shopAddress} ${shopPostcode}
 <#if  servicePhone??  >
      Tel: ${servicePhone}
 </#if>
 <#if  serviceEmail??  >
      E-mail: ${serviceEmail}<br />
 </#if>
 <#list qq as im>
      <#if  im??  >
      <a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${im}&amp;Site=${shopName}&amp;Menu=yes" target="_blank"><img src="http://wpa.qq.com/pa?p=1:${im}:4" height="16" border="0" alt="QQ" /> ${im}</a>
      </#if>
      </#list>
      <#list ww as im>
      <#if  im??  >
			<a href="http://amos1.taobao.com/msg.ww?v=2&uid=${im}&s=2" target="_blank"><img src="http://amos1.taobao.com/online.ww?v=2&uid=${im}&s=2" width="16" height="16" border="0" alt="淘宝旺旺" />${im}</a>
      </#if>
      </#list>
      <#list ym as im>
      <#if  im??  >
      <a href="http://edit.yahoo.com/config/send_webmesg?.target=${im}n&.src=pg" target="_blank"><img src="../images/yahoo.gif" width="18" height="17" border="0" alt="Yahoo Messenger" /> ${im}</a>
      </#if>
      </#list>
      <#list msn as im>
      <#if  im??  >
      <img src="../images/msn.gif" width="18" height="17" border="0" alt="MSN" /> <a href="msnim:chat?contact=${im}">${im}</a>
      </#if>
      </#list>
      <#list skype as im>
      <#if  im??  >
      <img src="http://mystatus.skype.com/smallclassic/${im}" alt="Skype" /><a href="skype:${im}?call">${im}</a>
      </#if>
  </#list><br />
  <#if  icpNumber??  >
  ${lang.icpNumber}:<a href="http://www.miibeian.gov.cn/" target="_blank">${icpNumber}</a><br />
  </#if>
  TODO query info<br />
  <a href="http://www.ecshop.com" target="_blank" style=" font-family:Verdana; font-size:11px;">Powered by <strong><span style="color: #3366FF">ECShop</span> <span style="color: #FF9966">${ecsVersion}</span></strong></a> ${licensed}<br />
    <#if  statsCode??  >
    <div align="left">${statsCode}</div>
    </#if>
		<div align="left"  id="rss"><a href="${feedUrl}"><img src="../images/xml_rss2.gif" alt="rss" /></a></div>
 </div>
</div>

