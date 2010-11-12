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

<script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/utils.js"></script>

<script language="javascript">
function remove(id, url)
{
  if (document.getCookie("compareItems") != null)
  {
    var obj = document.getCookie("compareItems").parseJSON();
    delete obj[id];
    var date = new Date();
    date.setTime(date.getTime() + 99999999);
    document.setCookie("compareItems", obj.toJSONString());
  }
}
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
<div class="block">
  <h5><span>${lang.goodsCompare}</span></h5>
  <div class="blank"></div>
  <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
              <tr>
                <th width="120" align="center" bgcolor="#ffffff">${lang.goodsName}</th>
                <#list goodsList as goods>
                <td align="center" bgcolor="#ffffff" <#if ( goodsList?size > 3  ) >width="200"<#else></#if>> ${goods.goodsName}</td>
                </#list>
              </tr>
              <tr>
                <th align="left" bgcolor="#ffffff"></th>
                <#list goodsList as goods>
                <td  align="center" bgcolor="#ffffff" style="padding:5px;"><a href="${goods.url}"><img src="${goods.goodsThumb}" alt="${goods.goodsName}" class="ent_img" /></a></td>
                </#list>
              </tr>
              <#if ( goodsList?size > 2  ) >
              <tr>
                <td bgcolor="#ffffff">&nbsp;</td>
                <#list goodsList as goods>
                <th bgcolor="#ffffff">
                  <a href="compare.action?${goods.ids}" onClick="return remove(${goods.goodsId});">${lang.compareRemove}</a>                </th>
                </#list>
              </tr>
              </#if>
              <tr>
                <th align="left" bgcolor="#ffffff">&nbsp;&nbsp;${lang.brand}</th>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;${goods.brandName}</td>
                </#list>
              </tr>
              <tr>
                <th align="left" bgcolor="#ffffff">&nbsp;&nbsp;${lang.shopPrice}</th>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;${goods.shopPrice}</td>
                </#list>
              </tr>
              <tr>
                <th align="left" bgcolor="#ffffff">&nbsp;&nbsp;${lang.goodsWeight}</th>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;${goods.goodsWeight}</td>
                </#list>
              </tr>
              <#list attribute?keys as key> <#assign val = attribute.get(key)>
              <tr>
                <th align="left" bgcolor="#ffffff">&nbsp;&nbsp;${val}</th>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;
                  <#list goods.properties?keys as k> <#assign property = goods.properties.get(k)>
                  <#if ( k == key  ) >
                  ${property.value}
                  </#if>
                  </#list>                </td>
                </#list>
              </tr>
              </#list>
              <tr>
                <td align="left" bgcolor="#ffffff">&nbsp;&nbsp;<strong>${lang.goodsRank}</strong></td>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;<span class="goods-price"><img src="images/stars${goods.commentRank}.gif" width="64" height="12" alt="comment rank ${goods.commentRank}" /></span><br /></td>
                </#list>
              </tr>
              <tr>
                <td align="left" bgcolor="#ffffff">&nbsp;&nbsp;<strong>${lang.brief}</strong></td>
                <#list goodsList as goods>
                <td bgcolor="#ffffff">&nbsp;&nbsp;<a href="${goods.url}" target="_blank"> ${goods.goodsBrief}</a></td>
                </#list>
              </tr>
              <tr>
                <td bgcolor="#ffffff">&nbsp;</td>
                <#list goodsList as goods>
                <td align='center' bgcolor="#ffffff"><a href="javascript:collect(${goods.goodsId});"><img src="images/bnt_colles.gif" alt="${lang.collect}"  style="margin:2px auto;"/></a>
                <a href="javascript:addToCart(${goods.goodsId})"><img src="images/bnt_cat.gif" alt="${lang.addToCart}"  style="margin:2px auto;"/></a></td>
                </#list>
              </tr>
  </table>
</div>
<div class="blank5"></div>
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
</html>
