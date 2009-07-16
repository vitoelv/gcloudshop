<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="bNavList clearfix">
   <div class="f_l">
   <#if navigator_list.bottom??>
   <#list navigator_list.bottom as nav>
        <a href="${nav.url}" <#if nav.opennew> target="_blank" </#if>>${nav.name}</a>
        <#if nav_has_next>
           -
        </#if>
      </#list>
  </#if>
   </div>
   <div class="f_r">
   <a href="#top"><img src="${template_root}/images/bnt_top.gif" /></a> <a href="index.action"><img src="${template_root}/images/bnt_home.gif" /></a>
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
 ${shop_address} ${shop_postcode}
 <#if service_phone??>
      Tel: ${service_phone}
 </#if>
 <#if service_email??>
      E-mail: ${service_email}<br />
 </#if>
 <#if qq??>
 <#list qq as im>
      <#if im??>
      <a href="http://wpa.qq.com/msgrd?V=1&amp;Uin=${im}&amp;Site=${shop_name}&amp;Menu=yes" target="_blank"><img src="http://wpa.qq.com/pa?p=1:{$im}:4" height="16" border="0" alt="QQ" /> {$im}</a>
      </#if>
 </#list>
 </#if>
 <#if ww??>
      <#list ww as im>
      <#if im??>
      <a href="http://amos1.taobao.com/msg.ww?v=2&uid={$im|escape:u8_url}&s=2" target="_blank"><img src="http://amos1.taobao.com/online.ww?v=2&uid={$im|escape:u8_url}&s=2" width="16" height="16" border="0" alt="淘宝旺旺" />{$im}</a>
      </#if>
      </#list>
 </#if>
 <#if ym??>
      <#list ym as im>
      <#if im??>
      <a href="http://edit.yahoo.com/config/send_webmesg?.target={$im}n&.src=pg" target="_blank"><img src="../images/yahoo.gif" width="18" height="17" border="0" alt="Yahoo Messenger" /> {$im}</a>
      </#if>
      </#list>
 </#if>
 <#if msn??>
      <#list msn as im>
      <#if im??>
      <img src="${template_root}/images/msn.gif" width="18" height="17" border="0" alt="MSN" /> <a href="msnim:chat?contact={$im}">{$im}</a>
      </#if>
      </#list>
</#if>
<#if skype??>
      <#list skype as im>
      <#if im??>
      <img src="http://mystatus.skype.com/smallclassic/{$im|escape:url}" alt="Skype" /><a href="skype:{$im|escape:url}?call">{$im}</a>
      </#if>
      </#list>
</#if>
      <br />
  <#if icp_number??>
  <@s.text name="icp_number"/>:<a href="http://www.miibeian.gov.cn/" target="_blank">${icp_number}</a><br />
  </#if>
  {insert name='query_info'}<br />
  <a href="http://www.ecshop.com" target="_blank" style=" font-family:Verdana; font-size:11px;">Powered by <strong><span style="color: #3366FF">IShop</span> <span style="color: #FF9966"><@s.text name="ecs_version"/></span></strong></a> <@s.text name="licensed"/><br />
    <#if stats_code??>
    <div align="left">${stats_code}</div>
    </#if>
    <div align="left"  id="rss"><a href="${feed_url}"><img src="${template_root}/images/xml_rss2.gif" alt="rss" /></a></div>
 </div>
</div>

