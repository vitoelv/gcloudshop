<!--用户评论 START-->
     <div class="box">
     <div class="box_1">
      <h3><span class="text">${lang.userComment}</span>(${lang.total}<font class="f1">${pager.recordCount}</font>${lang.userCommentNum})</h3>
      <div class="boxCenterList clearfix" style="height:1%;">
       <ul class="comments">
       <#if  comments??  >
       <#list comments as comment>
        <li class="word">
        <font class="f2"><#if  comment.username??  >${comment.username?html}<#else>${lang.anonymous}</#if></font> <font class="f3">( ${comment.addTime} )</font><br />
        <img src="../images/stars${comment.rank}.gif" alt="${comment.commentRank}" />
        <p>${comment.content}</p>
				<#if  comment.reContent??  >
        <p><font class="f1">${lang.adminUsername}</font>${comment.reContent}</p>
				</#if>
        </li>
        </#list>
        <#else>
        <li>${lang.noComments}</li>
        </#if>
       </ul>
       <!--翻页 start-->
       <div id="pagebar" class="f_r">
        <form name="selectPageForm" action="${smarty.server.PHP_SELF}" method="get">
        <#if  pager.styleid  ==  0  >
        <div id="pager">
          ${lang.pager_1}${pager.recordCount}${lang.pager_2}${lang.pager_3}${pager.pageCount}${lang.pager_4} <span> <a href="${pager.pageFirst}">${lang.pageFirst}</a> <a href="${pager.pagePrev}">${lang.pagePrev}</a> <a href="${pager.pageNext}">${lang.pageNext}</a> <a href="${pager.pageLast}">${lang.pageLast}</a> </span>
            <#list  as >
            <input type="hidden" name="${key}" value="${item}" />
            </#list>
        </div>
        <#else>

        <!--翻页 start-->
         <div id="pager" class="pagebar">
          <span class="f_l f6" style="margin-right:10px;">${lang.total} <b>${pager.recordCount}</b> ${lang.userCommentNum}</span>
          <#if  pager.pageFirst??  ><a href="${pager.pageFirst}">1 ...</a></#if>
          <#if  pager.pagePrev??  ><a class="prev" href="${pager.pagePrev}">${lang.pagePrev}</a></#if>
          <#list  as >
                <#if  pager.page  ==  key??  >
                <span class="page_now">${key}</span>
                <#else>
                <a href="${item}">[${key}]</a>
                </#if>
            </#list>

          <#if  pager.pageNext??  ><a class="next" href="${pager.pageNext}">${lang.pageNext}</a></#if>
          <#if  pager.pageLast??  ><a class="last" href="${pager.pageLast}">...${pager.pageCount}</a></#if>
          <#if  pager.pageKbd??  >
            <#list  as >
            <input type="hidden" name="${key}" value="${item}" />
            </#list>
            <kbd style="float:left; margin-left:8px; position:relative; bottom:3px;"><input type="text" name="page" onkeydown="if(event.keyCode==13)selectPage(this)" size="3" class="B_blue" /></kbd>
            </#if>
        </div>
        <!--翻页 END-->

        </#if>
        </form>
        <script type="Text/Javascript" language="JavaScript">
        <!--
        {literal}
        </script>
      </div>
      <!--翻页 END-->
      <div class="blank5"></div>
      <!--评论表单 start-->
      <div class="commentsList">
      <form action="javascript:;" onsubmit="submitComment(this)" method="post" name="commentForm" id="commentForm">
       <table width="710" border="0" cellspacing="5" cellpadding="0">
        <tr>
          <td width="64" align="right">${lang.username}：</td>
          <td width="631"<#if  !enabledCaptcha??  ></#if>><#if  smarty.session.userName??  >${smarty.session.userName}<#else>${lang.anonymous}</#if></td>
        </tr>
        <tr>
          <td align="right">E-mail：</td>
          <td>
          <input type="text" name="email" id="email"  maxlength="100" value="${smarty.session.email}" class="inputBorder"/>
          </td>
        </tr>
        <tr>
          <td align="right">${lang.commentRank}：</td>
          <td>
          <input name="comment_rank" type="radio" value="1" id="comment_rank1" /> <img src="../images/stars1.gif" />
          <input name="comment_rank" type="radio" value="2" id="comment_rank2" /> <img src="../images/stars2.gif" />
          <input name="comment_rank" type="radio" value="3" id="comment_rank3" /> <img src="../images/stars3.gif" />
          <input name="comment_rank" type="radio" value="4" id="comment_rank4" /> <img src="../images/stars4.gif" />
          <input name="comment_rank" type="radio" value="5" checked="checked" id="comment_rank5" /> <img src="../images/stars5.gif" />
          </td>
        </tr>
        <tr>
          <td align="right" valign="top">${lang.commentContent}：</td>
          <td>
          <textarea name="content" class="inputBorder" style="height:50px; width:620px;"></textarea>
          <input type="hidden" name="cmt_type" value="${commentType}" />
          <input type="hidden" name="id" value="${id}" />
          </td>
        </tr>
        <tr>
          <td colspan="2">
          <#if  enabledCaptcha??  >
          <div style="padding-left:15px; text-align:left; float:left;">
          ${lang.commentCaptcha}：<input type="text" name="captcha"  class="inputBorder" style="width:50px; margin-left:5px;"/>
          <img src="captcha.action?${rand}" alt="captcha" onClick="this.src='captcha.action?'+Math.random()" class="captcha">
          </div>
          </#if>
          <input name="" type="submit"  value="" class="f_r" style="border:none; background:url(../images/commentsBnt.gif); width:75px; height:21px; margin-right:8px;">
          </td>
        </tr>
      </table>
      </form>
      </div>
      <!--评论表单 end-->
      </div>
     </div>
    </div>
    <div class="blank5"></div>
  <!--用户评论 END-->
<script type="text/javascript">
//<![CDATA[
<#list lang.cmtLang as item>
var ${key} = "${item}";
</#list>
{literal}
/**
 * 提交评论信息
*/
function submitComment(frm)
{
  var cmt = new Object;

  //cmt.username        = frm.elements['username'].value;
  cmt.email           = frm.elements['email'].value;
  cmt.content         = frm.elements['content'].value;
  cmt.type            = frm.elements['cmt_type'].value;
  cmt.id              = frm.elements['id'].value;
  cmt.enabled_captcha = frm.elements['enabled_captcha'] ? frm.elements['enabled_captcha'].value : '0';
  cmt.captcha         = frm.elements['captcha'] ? frm.elements['captcha'].value : '';
  cmt.rank            = 0;

  for (i = 0; i < frm.elements['comment_rank'].length; i++)
  {
    if (frm.elements['comment_rank'][i].checked)
    {
       cmt.rank = frm.elements['comment_rank'][i].value;
     }
  }

//  if (cmt.username.length == 0)
//  {
//     alert(cmt_empty_username);
//     return false;
//  }

  if (cmt.email.length > 0)
  {
     if (!(Utils.isEmail(cmt.email)))
     {
        alert(cmt_error_email);
        return false;
      }
   }
   else
   {
        alert(cmt_empty_email);
        return false;
   }

   if (cmt.content.length == 0)
   {
      alert(cmt_empty_content);
      return false;
   }

   if (cmt.enabled_captcha > 0 && cmt.captcha.length == 0 )
   {
      alert(captcha_not_null);
      return false;
   }

   Ajax.call('comment.action', 'cmt=' + cmt.toJSONString(), commentResponse, 'POST', 'JSON');
   return false;
}

/**
 * 处理提交评论的反馈信息
*/
  function commentResponse(result)
  {
    if (result.message)
    {
      alert(result.message);
    }

    if (result.error == 0)
    {
      var layer = document.getElementById('ECS_COMMENT');

      if (layer)
      {
        layer.innerHTML = result.content;
      }
    }
  }

//]]>
</script>