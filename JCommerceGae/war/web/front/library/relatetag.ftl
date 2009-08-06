<#if  tagNums??  >
<#list tagList as data>
<#if  key  !=  appid??  >
<#if  data.type  ==  "ecshop"  >
     <div class="box">
     <div class="box_1">
      <h3><span class="text">${data.name}</span></h3>
      <div class="boxCenterList clearfix ie6">
        <#list data.data as tag>
        <img src="${tag.image}" width="100" height="100" /><br />
        <a href="${tag.url}">${tag.goodsName}</a>
        </#list> 
      </div>
     </div>
    </div>
    <div class="blank5"></div>
    <#elseif  data.type  ==  "discuz"  >  
    <div class="box">
     <div class="box_1">
      <h3><span class="text">${data.name}</span></h3>
      <div class="boxCenterList clearfix ie6">
        <#list data.data as tag>
        <a href="${tag.url}">${tag.subject}</a><br />
        </#list>  
      </div>
     </div>
    </div>
    <div class="blank5"></div>
</#if>
</#if>    
</#list>
</#if>  