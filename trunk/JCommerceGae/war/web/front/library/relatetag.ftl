<#if ( tagNums??  ) >
<#list tagList as data> <#assign key = data_index >
<#if ( key != appid  ) >
<#if ( data.type == "ecshop"  ) >
     <div class="box">
     <div class="box_1">
      <h3><span class="text">${data.name}</span></h3>
      <div class="boxCenterList clearfix ie6">
        <#list data.data?keys as key> <#assign tag = data.data.get(key)>
        <img src="${tag.image}" width="100" height="100" /><br />
        <a href="${tag.url}">${tag.goodsName}</a>
        </#list> 
      </div>
     </div>
    </div>
    <div class="blank5"></div>
    <#elseif ( data.type == "discuz"  ) >  
    <div class="box">
     <div class="box_1">
      <h3><span class="text">${data.name}</span></h3>
      <div class="boxCenterList clearfix ie6">
        <#list data.data?keys as key> <#assign tag = data.data.get(key)>
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