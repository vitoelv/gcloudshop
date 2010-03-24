<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}</title>
<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/lefttime.js"></script>

<script type="text/javascript">
  <#list lang.jsLanguages?keys as key> <#assign item = lang.jsLanguages.get(key)>
    var ${key} = "${item}";
  </#list>
</script>
</head>
<body>
<#include "library/page_header.ftl">
<!--当前位置 start-->
<div class="block box">
 <div id="ur_here">
  <#include "library/ur_here.ftl">
 </div>
</div>
<!--当前位置 end-->
<div class="blank"></div>
<div class="block clearfix">
  <!--left start-->
  <div class="AreaL">
    <!-- TemplateBeginEditable name="左边区域" -->
    <#include "library/cart.ftl">
    <#include "library/categorys.ftl">
    <#include "library/goods_related.ftl">
    <#include "library/goods_fittings.ftl">
    <#include "library/goods_article.ftl">
    <#include "library/goods_attrlinked.ftl">
    <!-- TemplateEndEditable -->
    <div class="box">
     <div class="box_1">
      <h3><span>${lang.yourChoice}</span></h3>
      <div class="boxCenterList clearfix">
        <ul>
        <#list picks as pick>
        <li style="word-break:break-all;"><a href="${pick.url}">${pick.name}</a></li>
        </#list>
       </ul>
      </div>
     </div>
    </div>
    <div class="blank5"></div>
    <!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
    <!-- TemplateEndEditable -->
    <!--AD end-->
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.pickOut}</span></h3>
    <div class="boxCenterList">
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <#list condition as caption>
      <tr>
        <td bgcolor="#e5ecfb" style="border-bottom: 1px solid #DADADA">
          <img src="images/note.gif" alt="no alt" />&nbsp;&nbsp;<strong class="f_red">${caption.name}</strong></td>
      </tr>
      <#list caption.cat as cat>
      <tr>
        <td bgcolor="#ffffff">&nbsp;&nbsp;<strong>${cat.catName}</strong></td>
      </tr>
      <tr>
        <td bgcolor="#ffffff">&nbsp;&nbsp;
          <#list cat.list as list>
          &nbsp;&nbsp;<a href="${list.url}" class="f6">${list.name}</a>
          </#list>
        </td>
      </tr>
      </#list>
      </#list>
    </table>
    </div>
   </div>
  </div>
   <div class="blank5"></div>
   <div class="box">
   <div class="box_1">
    <h3><span>${lang.searchResult} (${count})</span></h3>
    <div class="boxCenterList clearfix">
     <#list pickoutGoods as goods>
     <div class="goodsItem">
           <a href="${goods.url}"><img src="${goods.thumb}" alt="${goods.name?html}" class="goodsimg" /></a><br />
           <a href="javascript:addToCart(${goods.id})"><img src="images/bnt_buy.gif" /></a> <a href="javascript:collect(${goods.id})"><img src="images/bnt_coll.gif" /></a>
           <p><a href="${goods.url}" title="${goods.name?html}">${goods.shortStyleName}</a></p>
           <font class="f1">
           <#if ( goods.promotePrice != ""  ) >
          ${goods.promotePrice}
          <#else>
          ${goods.shopPrice}
          </#if>
           </font>
        </div>
     </#list>
     <#if ( count > 5  ) >
     <div class="more f_r" style="clear:both;"><a href="${url}"><img src="images/more.gif" /></a></div>
     </#if>
    </div>
   </div>
  </div>

  </div>
  <!--right end-->
</div>
<div class="blank"></div>
<!--帮助-->
<div class="block">
  <div class="box">
   <div class="helpTitBg clearfix">
    <#include "library/help.ftl">
   </div>
  </div>
</div>
<div class="blank"></div>
<!--帮助-->
<!--友情链接 start-->
<#if ( imgLinks?? || txtLinks??  ) >
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="links clearfix">
    <#list imgLinks as link>
    <a href="${link.url}" target="_blank" title="${link.name}"><img src="${link.logo}" alt="${link.name}" border="0" /></a>
    </#list>
    <#if ( txtLinks??  ) >
    <#list txtLinks as link>
    [<a href="${link.url}" target="_blank" title="${link.name}">${link.name}</a>]
    </#list>
    </#if>
  </div>
 </div>
</div>
</#if>
<!--友情链接 end-->
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
<script type="text/javascript">
var gmt_end_time = "${groupBuy.gmtEndDate}";
<#list lang.goodsJs?keys as key> <#assign item = lang.goodsJs.get(key)>
var ${key} = "${item}";
</#list>


onload = function()
{
  try
  {
    onload_leftTime();
  }
  catch (e)
  {}
}

</script>
</html>
