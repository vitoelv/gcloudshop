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

<style type="text/css">
p a{color:#006acd; text-decoration:underline;}
</style>
</head>
<body style="padding-top:20%;">
  <div class="box" style="width:500px;">
     <div class="box_1">
      <h3><span>${lang.systemInfo}</span></h3>
      <div style="padding:30px 0; text-align:center;">
        <p style="font-size: 14px; font-weight:bold; color: red;">${message}</p>
        <#if ( virtualCard??  ) >
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
        <#list virtualCard as vgoods>
          <#list vgoods.info as card>
            <tr>
            <td bgcolor="#FFFFFF">${vgoods.goodsName}</td>
            <td bgcolor="#FFFFFF">
            <#if ( card.cardSn??  ) ><strong>${lang.cardSn}:</strong>${card.cardSn}</#if>
            <#if ( card.cardPassword??  ) ><strong>${lang.cardPassword}:</strong>${card.cardPassword}</#if>
            <#if ( card.endDate??  ) ><strong>${lang.endDate}:</strong>${card.endDate}</#if>
            </td>
            </tr>
          </#list>
        </#list>
        </table>
        </#if>
        <a href="${shopUrl}">${lang.backHome}</a>
      </div>
     </div>
    </div>
</body>
</html>
