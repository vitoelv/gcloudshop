<div class="box">
     <div class="box_1">
      <h3><span>${lang.messageBoard}</span></h3>
      <div class="boxCenterList">
      <#list msgLists as msg> <#assign key = msg_index >
      <div class="f_l">
      [<b>${msg.msgType}</b>]&nbsp;${msg.userName}：&nbsp;&nbsp;<font class="f4">${msg.msgTitle}</font> (${msg.msgTime})
      </div>
      <div class="msgBottomBorder word">
      ${msg.msgContent}<br>
      <#if ( msg.reMsgContent??  ) >
       <font class="f2">${lang.shopmanReply}</font><br />
       ${msg.reMsgContent}
      </#if>
      </div>
      </#list>  
    </div>
  </div>
</div>
<div class="blank5"></div>



