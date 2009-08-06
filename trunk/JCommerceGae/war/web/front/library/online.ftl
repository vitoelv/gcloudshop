<div class="box">
 <div class="box_1">
  <h3><span>&nbsp;</span></h3>
  <div class="boxCenterList">
    ${info}<br>
    <#if  member??  >
    <#list member as val>
      ${val.userName} 
    </#list>
    </#if>
  </div>
 </div>
</div>
<div class="blank5"></div>
