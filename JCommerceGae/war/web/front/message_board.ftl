<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<meta name="Description" content="${description}" />
<#if ( autoRedirect??  ) >
<meta http-equiv="refresh" content="3;URL=${message.href}" />
</#if>
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}</title>
<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/common.js"></script>

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
    <#include "library/category_tree.ftl">
    <#include "library/goods_related.ftl">
    <#include "library/goods_fittings.ftl">
    <#include "library/goods_article.ftl">
    <#include "library/goods_attrlinked.ftl">
    <!-- TemplateEndEditable -->
    <!-- TemplateBeginEditable name="左边广告区域（宽200px）" -->
    <!-- TemplateEndEditable -->
    <!--AD end-->
    <#include "library/history.ftl">
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
  <#include "library/message_list.ftl">
  <#include "library/pages.ftl">
  <div class="blank5"></div>
    <div class="box">
     <div class="box_1">
      <h3><span>${lang.postMessage}</span></h3>
      <div class="boxCenterList">
          <form action="message.action" method="post" name="formMsg" onSubmit="return submitMsgBoard()">
            <table width="100%" border="0" cellpadding="3">
              <tr>
                <td align="right">${lang.username}</td>
                <td>
                <#if ( smarty.session.userName??  ) >
                <font class="f4_b">${username}</font><label for="anonymous" style="margin-left:8px;"><input type="checkbox" name="anonymous" value="1" id="anonymous" />${lang.messageAnonymous}</label>
                <#else>
                <input name="user_name" class="inputBg" type="text" size="15" value="${lang.anonymous}" />
                </#if>
                </td>
              </tr>
              <tr>
                <td align="right">${lang.email}</td>
                <td><input name="user_email" type="text" class="inputBg" size="20" value="${smarty.session.email}" /></td>
              </tr>
              <tr>
                <td align="right">${lang.messageBoardType}</td>
                <td><input name="msg_type" type="radio" value="0" checked="checked" />
                  ${lang.messageType[0]}
                  <input type="radio" name="msg_type" value="1" />
                  ${lang.messageType[1]}
                  <input type="radio" name="msg_type" value="2" />
                  ${lang.messageType[2]}
                  <input type="radio" name="msg_type" value="3" />
                  ${lang.messageType[3]}
                  <input type="radio" name="msg_type" value="4" />
                  ${lang.messageType[4]} </td>
              </tr>
              <tr>
                <td align="right">${lang.messageTitle}</td>
                <td><input name="msg_title" type="text" class="inputBg" size="30" /></td>
              </tr>
            <#if ( enabledCaptcha??  ) >
              <tr>
                <td align="right">${lang.commentCaptcha}</td>
                <td><input type="text" size="8" name="captcha"  class="inputBg" />
                <img src="captcha.action?${rand}" alt="captcha" style="vertical-align: middle;cursor: pointer;" onClick="this.src='captcha.action?'+Math.random()" /> </td>
              </tr>
            </#if>
              <tr>
                <td align="right" valign="top">${lang.messageContent}</td>
                <td><textarea name="msg_content" cols="50" rows="4" wrap="virtual" style="border:1px solid #ccc;"></textarea></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><input type="hidden" name="act" value="act_add_message" />
                  <input type="submit" value="${lang.postMessage}" class="bnt_blue_1" />
                </td>
              </tr>
            </table>
          </form>
        <script type="text/javascript">
        <#list lang.messageBoardJs?keys as key> <#assign item = lang.messageBoardJs.get(key)>
        var ${key} = "${item}";
        </#list>
        
        /**
         * 提交评论信息
        */
        function submitMsgBoard(frm)
        {
            var frm         = document.forms['formMsg'];
            var msg_title   = frm.elements['msg_title'].value;
            var msg_content = frm.elements['msg_content'].value;
            var msg = '';

            if (msg_title.length == 0)
            {
                msg += msg_title_empty + '\n';
            }
            if (frm.elements['captcha'] && frm.elements['captcha'].value.length==0)
            {
                msg += msg_captcha_empty + '\n'
            }
            if (msg_content.length == 0)
            {
                msg += msg_content_empty + '\n'
            }
            if (msg_title.length > 200)
            {
                msg += msg_title_limit + '\n';
            }

            if (msg.length > 0)
            {
                alert(msg);
                return false;
            }
            else
            {
                return true;
            }
        }
        
        </script>
      </div>
     </div>
    </div>
  </div>  
  <!--right end-->
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
