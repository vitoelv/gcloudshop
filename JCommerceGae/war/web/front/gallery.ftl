<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${gallery.goodsName} - ${shopName}</title>
<link rel="shortcut icon" href="favicon.ico" >
<link rel="icon" href="animated_favicon.gif" type="image/gif" >
<link href="style.css" rel="stylesheet" type="text/css" />
<style>
body{margin:0;}
</style>
<script type="text/javascript">
<#list lang.galleryJs?keys as key> <#assign langJs = lang.galleryJs.get(key)>
var ${key} = '${langJs}';
</#list>
</script>
</head>
<body>
<div id="show-pic">
<embed src="daimages/pic-view.swf" quality="high" id="picview" flashvars="copyright=shopex&xml=<products name='${gallery.goodsName}' shopname='${shopName}' water_mark_img_path='${watermark}' water_mark_position='3' water_mark_alpha='0.5'><#list gallery.list as photo><smallpic<#if ( gallery.list?first  ) > selected='selected'</#if>>${photo.galleryThumb}</smallpic><photo_desc>${photo.imgDesc}</photo_desc><bigpic<#if ( gallery.list?first  ) > selected='selected'</#if>>${photo.gallery}</bigpic></#list></products>" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="100%" height="100%"></embed>
<script>
function windowClose()
{
    if(window.confirm(close_window))
    {
        window.close();
    }
}
</script>
</div>
</body>
</html>