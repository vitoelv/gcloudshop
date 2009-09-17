<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}-${action}</title>
<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/user.js"></script>

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
    <div class="box">
     <div class="box_1">
      <div class="userCenterBox">
        <#include "library/user_menu.ftl">
      </div>
     </div>
    </div>
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
    <div class="box">
     <div class="box_1">
      <div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
         <!-- *用户中心默认显示页面 start-->
         <#if  action  ==  'default'  >
          <font class="f5"><b class="f4">${info.username}</b> ${lang.welcomeTo} ${info.shopName}！</font><br />
          <div class="blank"></div>
          ${lang.lastTime}: ${info.lastTime}<br />
          <div class="blank5"></div>
          ${rankName} ${nextRankName}<br />
          <div class="blank5"></div>
           <#if  info.isValidate  ==  0  >
          ${lang.notValidated} <a href="javascript:sendHashMail()" style="color:#006bd0;">${lang.resendHashMail}</a><br />
           </#if>
           <div style="margin:5px 0; border:1px solid #d5f1ff;padding:10px 20px; background-color:#f7fcff;">
           <img src="images/note.gif" alt="note" />&nbsp;${userNotice}
           </div>
           <br /><br />
          <div class="f_l" style="width:350px;">
          <h5><span>${lang.yourAccount}</span></h5>
          <div class="blank"></div>
          ${lang.yourSurplus}:<a href="user.action?act=account_log" style="color:#006bd0;">${info.surplus}</a><br />
          <#if  (info.creditLine  >  0)  >
          ${lang.creditLine}:${info.formatedCreditLine}<br />
          </#if>
          ${lang.yourBonus}:<a href="user.action?act=bonus" style="color:#006bd0;">${info.bonus}</a><br />
          ${lang.yourIntegral}:${info.integral}<br />
          </div>
          <div class="f_r" style="width:350px;">
          <h5><span>${lang.yourNotice}</span></h5>
          <div class="blank"></div>
           <#list prompt as item>
          ${item.text}<br />
          </#list>
          ${lang.lastMonthOrder}${info.orderCount}${lang.orderUnit}<br />
          <#if  info.shippedOrder??  >
          ${lang.pleaseReceived}<br />
          <#list info.shippedOrder as item>
          <a href="user.action?act=order_detail&order_id=${item.orderId}" style="color:#006bd0;">${item.orderSn}</a>
          </#list>
          </#if>
          </div>
         </#if>
         <!-- #用户中心默认显示页面 end-->
         <!-- *我的留言 start-->
         <#if  action  ==  'messageList'  >
          <h5><span>${lang.labelMessage}</span></h5>
          <div class="blank"></div>
           <#list messageList as message>
          <div class="f_l">
          <b>${message.msgType}:</b>&nbsp;&nbsp;<font class="f4">${message.msgTitle}</font> (${message.msgTime})
          </div>
          <div class="f_r">
          <a href="user.action?act=del_msg&amp;id=${key}&amp;order_id=${message.orderId}" title="${lang.drop}" onclick="if (!confirm('${lang.confirmRemoveMsg}')) return false;" class="f6">${lang.drop}</a>
          </div>
          <div class="msgBottomBorder">
          ${message.msgContent}
           <#if  message.messageImg??  >
           <div align="right">
           <a href="data/feedbackimg/${message.messageImg}" target="_bank" class="f6">${lang.viewUploadFile}</a>
           </div>
           </#if>
           <br />
           <#if  message.reMsgContent??  >
           <a href="mailto:${message.reUserEmail}" class="f6">${lang.shopmanReply}</a> (${message.reMsgTime})<br />
           ${message.reMsgContent}
           </#if>
          </div>
          </#list>
          <#if  messageList??  >
          <div class="f_r">
          <#include "library/pages.ftl">
          </div>
          </#if>
          <div class="blank"></div>
          <form action="user.action" method="post" enctype="multipart/form-data" name="formMsg" onSubmit="return submitMsg()">
                  <table width="100%" border="0" cellpadding="3">
                    <#if  orderInfo??  >
                    <tr>
                      <td align="right">${lang.orderNumber}</td>
                      <td>
                      <a href ="${orderInfo.url}"><img src="images/note.gif" />${orderInfo.orderSn}</a>
                      <input name="msg_type" type="hidden" value="5" />
                      <input name="order_id" type="hidden" value="${orderInfo.orderId}" class="inputBg" />
                      </td>
                    </tr>
                    <#else>
                    <tr>
                      <td align="right">${lang.messageType}：</td>
                      <td><input name="msg_type" type="radio" value="0" checked="checked" />
                        ${lang.type[0]}
                        <input type="radio" name="msg_type" value="1" />
                        ${lang.type[1]}
                        <input type="radio" name="msg_type" value="2" />
                        ${lang.type[2]}
                        <input type="radio" name="msg_type" value="3" />
                        ${lang.type[3]}
                        <input type="radio" name="msg_type" value="4" />
                        ${lang.type[4]} </td>
                    </tr>
                    </#if>
                    <tr>
                      <td align="right">${lang.messageTitle}：</td>
                      <td><input name="msg_title" type="text" size="30" class="inputBg" /></td>
                    </tr>
                    <tr>
                      <td align="right" valign="top">${lang.messageContent}：</td>
                      <td><textarea name="msg_content" cols="50" rows="4" wrap="virtual" class="B_blue"></textarea></td>
                    </tr>
                    <tr>
                      <td align="right">${lang.uploadImg}：</td>
                      <td><input type="file" name="message_img"  size="45"  class="inputBg" /></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td><input type="hidden" name="act" value="act_add_message" />
                        <input type="submit" value="${lang.submit}" class="bnt_bonus" />
                      </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td>
                      ${lang.imgTypeTips}<br />
                      ${lang.imgTypeList}
                      </td>
                    </tr>
                  </table>
                </form>
         </#if>
         <!--#我的留言 end-->
         <!-- *我的评论 start-->
          <#if  action  ==  'commentList'  >
          <h5><span>${lang.labelComment}</span></h5>
          <div class="blank"></div>
          <#list commentList as comment>
          <div class="f_l">
          <b><#if  comment.commentType  ==  '0'  >${lang.goodsComment}<#else>${lang.articleComment}</#if>: </b><font class="f4">${comment.cmtName}</font>&nbsp;&nbsp;(${comment.formatedAddTime})
          </div>
          <div class="f_r">
          <a href="user.action?act=del_cmt&amp;id=${comment.commentId}" title="${lang.drop}" onclick="if (!confirm('${lang.confirmRemoveMsg}')) return false;" class="f6">${lang.drop}</a>
          </div>
          <div class="msgBottomBorder">
          ${comment.content}<br />
          <#if  comment.replyContent??  >
          <b>${lang.replyComment}：</b><br />
          ${comment.replyContent}
           </#if>
          </div>
          </#list>
          <#if  commentList??  >
          <#include "library/pages.ftl">
          <#else>
          ${lang.noComments}
          </#if>
          </#if>
    <!--#我的评论 end-->
    <!--#我的标签 start-->
    <#if  action  ==  'tagList'  >
    <h5><span>${lang.labelTag}</span></h5>
    <div class="blank"></div>
     <#if  tags??  >
    <#list tags as tag>
    <a href="search.action?keywords=${tag.tagWords}" class="f6">${tag.tagWords?html}</a> <a href="user.action?act=act_del_tag&amp;tag_words=${tag.tagWords}" onclick="if (!confirm('${lang.confirmDropTag}')) return false;" title="${lang.drop}"><img src="images/drop.gif" alt="${lang.drop}" /></a>&nbsp;&nbsp;
    </#list>
    <#else>
    <span style="margin:2px 10px; font-size:14px; line-height:36px;">${lang.noTag}</span>
    </#if>
    </#if>
    <!--#我的标签 end-->
    <!--*收藏商品列表页面 start-->
    <#if  action  ==  'collectionList'  >
  <script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/utils.js"></script>

    <h5><span>${lang.labelCollection}</span></h5>
    <div class="blank"></div>
     <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <tr align="center">
        <th width="35%" bgcolor="#ffffff">${lang.goodsName}</th>
        <th width="30%" bgcolor="#ffffff">${lang.price}</th>
        <th width="35%" bgcolor="#ffffff">${lang.handle}</th>
      </tr>
      <#list goodsList as goods>
      <tr>
        <td bgcolor="#ffffff"><a href="${goods.url}" class="f6">${goods.goodsName?html}</a></td>
        <td bgcolor="#ffffff"><#if  goods.promotePrice  !=  ""  >
          ${lang.promotePrice}<span class="goods-price">${goods.promotePrice}</span>
          <#else>
          ${lang.shopPrice}<span class="goods-price">${goods.shopPrice}</span>
          </#if>        </td>
        <td align="center" bgcolor="#ffffff">
          <#if  goods.isAttention??  >
          <a href="javascript:if (confirm('${lang.delAttention}')) location.href='user.action?act=del_attention&rec_id=${goods.recId}'" class="f6">${lang.noAttention}</a>
          <#else>
          <a href="javascript:if (confirm('${lang.addToAttention}')) location.href='user.action?act=add_to_attention&rec_id=${goods.recId}'" class="f6">${lang.attention}</a>
          </#if>
           <a href="javascript:addToCart(${goods.goodsId})" class="f6">${lang.addToCart}</a> <a href="javascript:if (confirm('${lang.removeCollectionConfirm}')) location.href='user.action?act=delete_collection&collection_id=${goods.recId}'" class="f6">${lang.drop}</a>
        </td>
      </tr>
      </#list>
    </table>
    <#include "library/pages.ftl">
     <div class="blank5"></div>

    <h5><span>${lang.labelAffiliate}</span></h5>
    <div class="blank"></div>
     <form name="theForm" method="post" action="">
     <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
    <tr>
      <td align="right" bgcolor="#ffffff">${lang.labelNeedImage}</td>
      <td bgcolor="#ffffff">
        <select name="need_image" id="need_image" class="inputBg">
          <option value="true" selected>${lang.need}</option>
          <option value="false">${lang.needNot}</option>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#ffffff">${lang.labelGoodsNum}</td>
      <td bgcolor="#ffffff"><input name="goods_num" type="text" id="goods_num" value="6" class="inputBg" /></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#ffffff">${lang.labelArrange}</td>
      <td bgcolor="#ffffff"><select name="arrange" id="arrange" class="inputBg">
        <option value="h" selected>${lang.horizontal}</option>
        <option value="v">${lang.verticle}</option>
      </select></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#ffffff">${lang.labelCharset}</td>
      <td bgcolor="#ffffff"><select name="charset" id="charset">
        TODO: htmlOptions CLAUSE
      </select></td>
    </tr>
    <tr>
      <td colspan="2" align="center" bgcolor="#ffffff"><input type="button" name="gen_code" value="${lang.generate}" onclick="genCode()" class="submit" />        </td>
  </tr>
    <tr>
      <td colspan="2" align="center" bgcolor="#ffffff"><textarea name="code" cols="80" rows="5" id="code" class="B_blue"></textarea></td>
  </tr>
  </table>
     </form>
      <script language="JavaScript">
      var elements = document.forms['theForm'].elements;
      var url = '${url}';
      var u   = '${userId}';
      /**
       * 生成代码
       */
      function genCode()
      {
          // 检查输入
          if (isNaN(parseInt(elements['goods_num'].value)))
          {
              alert('${lang.goodsNumMustBeInt}');
              return;
          }
          if (elements['goods_num'].value < 1)
          {
              alert('${lang.goodsNumMustOver_0}');
              return;
          }

          // 生成代码
          var code = '\<script src=\"' + url + 'goods_script.action?';
          code += 'need_image=' + elements['need_image'].value + '&';
          code += 'goods_num=' + elements['goods_num'].value + '&';
          code += 'arrange=' + elements['arrange'].value + '&';
          code += 'charset=' + elements['charset'].value + '&u=' + u;
          code += '\"\>\</script\>';
          elements['code'].value = code;
          elements['code'].select();
          if (Browser.isIE)
          {
              window.clipboardData.setData("Text",code);
          }
      }
  </script>
  </#if>
    <!--#收藏商品列表页面 end-->
    <!--*缺货登记列表页面 start-->
    <#if  action  ==  'bookingList'  >
    <h5><span>${lang.labelBooking}</span></h5>
    <div class="blank"></div>
     <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <tr align="center">
        <td width="20%" bgcolor="#ffffff">${lang.bookingGoodsName}</td>
        <td width="10%" bgcolor="#ffffff">${lang.bookingAmount}</td>
        <td width="20%" bgcolor="#ffffff">${lang.bookingTime}</td>
        <td width="35%" bgcolor="#ffffff">${lang.processDesc}</td>
        <td width="15%" bgcolor="#ffffff">${lang.handle}</td>
      </tr>
      <#list bookingList as item>
      <tr>
        <td align="left" bgcolor="#ffffff"><a href="${item.url}" target="_blank" class="f6">${item.goodsName}</a></td>
        <td align="center" bgcolor="#ffffff">${item.goodsNumber}</td>
        <td align="center" bgcolor="#ffffff">${item.bookingTime}</td>
        <td align="left" bgcolor="#ffffff">${item.disposeNote}</td>
        <td align="center" bgcolor="#ffffff"><a href="javascript:if (confirm('${lang.confirmRemoveAccount}')) location.href='user.action?act=act_del_booking&id=${item.recId}'" class="f6">${lang.drop}</a> </td>
      </tr>
      </#list>
    </table>
    </#if>
    <div class="blank5"></div>
   <!--#缺货登记列表页面 -->
  <#if  action  ==  'addBooking'  >
    <script type="text/javascript" src="js/utils.js"></script>

    <script type="text/javascript">
    <#list lang.bookingJs as item>
    var ${key} = "${item}";
    </#list>
    </script>
    <h5><span>${lang.add}${lang.labelBooking}</span></h5>
    <div class="blank"></div>
     <form action="user.action" method="post" name="formBooking" onsubmit="return addBooking();">
     <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.bookingGoodsName}</td>
        <td bgcolor="#ffffff">${info.goodsName}</td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.bookingAmount}:</td>
        <td bgcolor="#ffffff"><input name="number" type="text" value="${info.goodsNumber}" class="inputBg" /></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.describe}:</td>
        <td bgcolor="#ffffff"><textarea name="desc" cols="50" rows="5" wrap="virtual" class="B_blue">${info.goodsDesc}</textarea>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.contactUsername}:</td>
        <td bgcolor="#ffffff"><input name="linkman" type="text" value="${info.consignee}" size="25"  class="inputBg"/>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.emailAddress}:</td>
        <td bgcolor="#ffffff"><input name="email" type="text" value="${info.email}" size="25" class="inputBg" /></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">${lang.contactPhone}:</td>
        <td bgcolor="#ffffff"><input name="tel" type="text" value="${info.tel}" size="25" class="inputBg" /></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#ffffff">&nbsp;</td>
        <td bgcolor="#ffffff"><input name="act" type="hidden" value="act_add_booking" />
          <input name="id" type="hidden" value="${info.id}" />
          <input name="rec_id" type="hidden" value="${info.recId}" />
          <input type="submit" name="submit" class="submit" value="${lang.submitBookingGoods}" />
          <input type="reset" name="reset" class="reset" value="${lang.buttonReset}" />
        </td>
      </tr>
    </table>
     </form>
    </#if>
    <!-- *我的推荐 -->
    <#if  affiliate.on  ==  1  >
     <#if  action  ==  'affiliate'  >
      <#if  !goodsid??  ||  goodsid??  ==  0  >
      <h5><span>${lang.affiliateDetail}</span></h5>
      <div class="blank"></div>
     ${affiliateIntro}
    <#if  affiliate.config.separateBy  ==  0  >
    <!-- 下线人数、分成 -->
    <div class="blank"></div>
    <h5><span><a name="myrecommend">${lang.affiliateMember}</a></span></h5>
    <div class="blank"></div>
   <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
    <tr align="center">
      <td bgcolor="#ffffff">${lang.affiliateLever}</td>
      <td bgcolor="#ffffff">${lang.affiliateNum}</td>
      <td bgcolor="#ffffff">${lang.levelPoint}</td>
      <td bgcolor="#ffffff">${lang.levelMoney}</td>
    </tr>
    <#list affdb.keys as level>
    <#assign val=affdb.get(level)>
    <tr align="center">
      <td bgcolor="#ffffff">${level}</td>
      <td bgcolor="#ffffff">${val.num}</td>
      <td bgcolor="#ffffff">${val.point}</td>
      <td bgcolor="#ffffff">${val.money}</td>
    </tr>
    </#list>
  </table>
<!-- /下线人数、分成 -->
<#else>
<!-- 介绍订单数、分成 -->
<!-- /介绍订单数、分成 -->
</#if>
<!-- 我的推荐清单 -->
<div class="blank"></div>
<h5><span>分成规则</span></h5>
<div class="blank"></div>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
    <tr align="center">
      <td bgcolor="#ffffff">${lang.orderNumber}</td>
      <td bgcolor="#ffffff">${lang.affiliateMoney}</td>
      <td bgcolor="#ffffff">${lang.affiliatePoint}</td>
      <td bgcolor="#ffffff">${lang.affiliateMode}</td>
      <td bgcolor="#ffffff">${lang.affiliateStatus}</td>
    </tr>
    <#list logdb as val>
    <tr align="center">
      <td bgcolor="#ffffff">${val.orderSn}</td>
      <td bgcolor="#ffffff">${val.money}</td>
      <td bgcolor="#ffffff">${val.point}</td>
      <td bgcolor="#ffffff"><#if  val.separateType  ==  1  ||  val.separateType  ==  0  >${lang.affiliateType.val.separateType}<#else>${lang.affiliateType.affiliateType}</#if></td>
      <td bgcolor="#ffffff">${lang.affiliateStats[val.isSeparate]}</td>
    </tr>
    
<tr><td colspan="5" align="center" bgcolor="#ffffff">${lang.noRecords}</td>
</tr>
    </#list>
    <#if  logdb??  >
    <tr>
    <td colspan="5" bgcolor="#ffffff">
 <form action="${smarty.server.PHP_SELF}" method="get">
  <div id="pager"> ${lang.pager_1}${pager.recordCount}${lang.pager_2}${lang.pager_3}${pager.pageCount}${lang.pager_4} <span> <a href="${pager.pageFirst}">${lang.pageFirst}</a> <a href="${pager.pagePrev}">${lang.pagePrev}</a> <a href="${pager.pageNext}">${lang.pageNext}</a> <a href="${pager.pageLast}">${lang.pageLast}</a> </span>
    <select name="page" id="page" onchange="selectPage(this)">
    TODO: htmlOptions CLAUSE
    </select>
    <input type="hidden" name="act" value="affiliate" />
  </div>
</form>
    </td>
    </tr>
    </#if>
  </table>
 <script type="text/javascript" language="JavaScript">
<!--
{literal}
</script>
<!-- /我的推荐清单 -->
<div class="blank"></div>
<h5><span>${lang.affiliateCode}</span></h5>
<div class="blank"></div>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
<tr>
<td width="30%" bgcolor="#ffffff"><a href="${shopurl}?u=${userid}" target="_blank" class="f6">${shopname}</a></td>
<td bgcolor="#ffffff"><input size="40" onclick="this.select();" type="text" value="&lt;a href=&quot;${shopurl}?u=${userid}&quot; target=&quot;_blank&quot;&gt;${shopname}&lt;/a&gt;" style="border:1px solid #ccc;" /> ${lang.recommendWebcode}</td>
</tr>
<tr>
<td bgcolor="#ffffff"><a href="${shopurl}?u=${userid}" target="_blank" title="${shopname}"  class="f6"><img src="${shopurl}${logosrc}" /></a></td>
<td bgcolor="#ffffff"><input size="40" onclick="this.select();" type="text" value="&lt;a href=&quot;${shopurl}?u=${userid}&quot; target=&quot;_blank&quot; title=&quot;${shopname}&quot;&gt;&lt;img src=&quot;${shopurl}${logosrc}&quot; /&gt;&lt;/a&gt;" style="border:1px solid #ccc;" /> ${lang.recommendWebcode}</td>
</tr>
<tr>
<td bgcolor="#ffffff"><a href="${shopurl}?u=${userid}" target="_blank" class="f6">${shopname}</a></td>
<td bgcolor="#ffffff"><input size="40" onclick="this.select();" type="text" value="[url=${shopurl}?u=${userid}]${shopname}[/url]" style="border:1px solid #ccc;" /> ${lang.recommendBbscode}</td>
</tr>
<tr>
<td bgcolor="#ffffff"><a href="${shopurl}?u=${userid}" target="_blank" title="${shopname}" class="f6"><img src="${shopurl}${logosrc}" /></a></td>
<td bgcolor="#ffffff"><input size="40" onclick="this.select();" type="text" value="[url=${shopurl}?u=${userid}][img]${shopurl}${logosrc}[/img][/url]" style="border:1px solid #ccc;" /> ${lang.recommendBbscode}</td>
</tr>
</table>

        <#else>
        <!-- 单个商品推荐 -->
        <style type="text/css">
        .types a{text-decoration:none; color:#006bd0;}
        </style>
    <h5><span>${lang.affiliateCode}</span></h5>
    <div class="blank"></div>
  <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
    <tr align="center">
      <td bgcolor="#ffffff">${lang.affiliateView}</td>
      <td bgcolor="#ffffff">${lang.affiliateCode}</td>
    </tr>
    <#list types as val>
    <tr align="center">
      <td bgcolor="#ffffff" class="types"><script src="${shopurl}affiliate.action?charset=${ecsCharset}&gid=${goodsid}&u=${userid}&type=${val}"></script></td>
      <td bgcolor="#ffffff">javascript ${lang.affiliateCodetype}<br>
        <textarea cols=30 rows=2 id="txt${smarty.foreach.types.iteration}" style="border:1px solid #ccc;"><script src="${shopurl}affiliate.action?charset=${ecsCharset}&gid=${goodsid}&u=${userid}&type=${val}"></script></textarea>[<a href="#" title="Copy To Clipboard" onClick="Javascript:copyToClipboard(document.getElementById('txt${smarty.foreach.types.iteration}').value);alert('${lang.copyToClipboard}');"  class="f6">^</a>]
<br>iframe ${lang.affiliateCodetype}<br><textarea cols=30 rows=2 id="txt${smarty.foreach.types.iteration}_iframe"  style="border:1px solid #ccc;"><iframe width="250" height="270" src="${shopurl}affiliate.action?charset=${ecsCharset}&gid=${goodsid}&u=${userid}&type=${val}&display_mode=iframe" frameborder="0" scrolling="no"></iframe></textarea>[<a href="#" title="Copy To Clipboard" onClick="Javascript:copyToClipboard(document.getElementById('txt${smarty.foreach.types.iteration}_iframe').value);alert('${lang.copyToClipboard}');" class="f6">^</a>]</td>
    </tr>
    </#list>
  </table>
<script language="Javascript">
copyToClipboard = function(txt)
{
 if(window.clipboardData)
 {
    window.clipboardData.clearData();
    window.clipboardData.setData("Text", txt);
 }
 else if(navigator.userAgent.indexOf("Opera") != -1)
 {
   //暂时无方法:-(
 }
 else if (window.netscape)
 {
  try
  {
    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
  }
  catch (e)
  {
    alert("${lang.firefoxCopyAlert}");
    return false;
  }
  var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
  if (!clip)
    return;
  var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
  if (!trans)
    return;
  trans.addDataFlavor('text/unicode');
  var str = new Object();
  var len = new Object();
  var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
  var copytext = txt;
  str.data = copytext;
  trans.setTransferData("text/unicode",str,copytext.length*2);
  var clipid = Components.interfaces.nsIClipboard;
  if (!clip)
  return false;
  clip.setData(trans,null,clipid.kGlobalClipboard);
 }
}
                </script>
            <!-- /单个商品推荐 -->
            </#if>
        </#if>

    </#if>

  <!-- /我的推荐 -->
      </div>
     </div>
    </div>
  </div>
  <!--right end-->
</div>
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
<script type="text/javascript">
<#list lang.clipsJs?keys as key>
<#assign item = lang.clipsJs.get(key)>
var ${key} = "${item}";
</#list>
</script>
</html>
