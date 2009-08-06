<#if  vote??  >
<script type="text/javascript" src="js/transport.js"></script>


<div id="ECS_VOTE">
<div  class="box">
 <div class="box_1">
  <h3><span>${lang.onlineVote}</span></h3>
  <div  class="boxCenterList">
    <form id="formvote" name="ECS_VOTEFORM" method="post" action="javascript:submit_vote()">
    <#list vote as title>
     ${title.voteName}<br />
     (${lang.voteTimes}:${title.voteCount})<br />
     </#list>
     <#list vote as title>
          <#list title.options as item>
            <#if  title.canMulti  ==  0  >
            <input type="checkbox" name="option_id" value="${item.optionId}" />
            ${item.optionName} (${item.percent}%)<br />
            <#else>
            <input type="radio" name="option_id" value="${item.optionId}" />
            ${item.optionName} (${item.percent}%)<br />
            </#if>
            </#list>
            <input type="hidden" name="type" value="${title.canMulti}" />
     </#list>
     <input type="hidden" name="id" value="${voteId}" />
     <input type="submit" name="submit" style="border:none;" value="${lang.submit}"  class="bnt_bonus" />
     <input type="reset" style="border:none;" value="${lang.reset}" class="bnt_blue" />
     </form>
  </div>
 </div>
</div>
</div>
<div class="blank5"></div>
<script type="text/javascript">
{literal}
/**
 * 处理用户的投票
 */
function submit_vote()
{
  var frm     = document.forms['ECS_VOTEFORM'];
  var type    = frm.elements['type'].value;
  var vote_id = frm.elements['id'].value;
  var option_id = 0;

  if (frm.elements['option_id'].checked)
  {
    option_id = frm.elements['option_id'].value;
  }
  else
  {
    for (i=0; i<frm.elements['option_id'].length; i++ )
    {
      if (frm.elements['option_id'][i].checked)
      {
        option_id = (type == 0) ? option_id + "," + frm.elements['option_id'][i].value : frm.elements['option_id'][i].value;
      }
    }
  }

  if (option_id == 0)
  {
    return;
  }
  else
  {
    Ajax.call('vote.action', 'vote=' + vote_id + '&options=' + option_id + "&type=" + type, voteResponse, 'POST', 'JSON');
  }
}

/**
 * 处理投票的反馈信息
 */
function voteResponse(result)
{
  if (result.message.length > 0)
  {
    alert(result.message);
  }
  if (result.error == 0)
  {
    var layer = document.getElementById('ECS_VOTE');

    if (layer)
    {
      layer.innerHTML = result.content;
    }
  }
}

</script>
</#if>