     <div class="box">
     <div class="box_1">
      <h3><span class="text"><@s.text name="goods_tag"/></span></h3>
      <div class="boxCenterList clearfix ie6">
       <form name="tagForm" action="javascript:;" onSubmit="return submitTag(this)" id="tagForm">
        <p id="ECS_TAGS" style="margin-bottom:5px;">
          <#list tags as tag>
          <a href="search.action?keywords=${tag.tagWords}" style="color:#006ace; text-decoration:none; margin-right:5px;">${tag.tagWords}[${tag.tagCount}]</a>
          </#list>
        </p>
        <p>
          <input type="text" name="tag" id="tag" class="inputBg" size="35" />
          <input type="submit" value="添 加" class="bnt_blue" style="border:none;" />
          <input type="hidden" name="goods_id" value="${goods.id}"  />
        </p>
                <script type="text/javascript">
                //<![CDATA[
                /**
                 * 用户添加标记的处理函数
                 */
                function submitTag(frm)
                {
                  try
                  {
                    var tag = frm.elements['tag'].value;
                    var idx = frm.elements['goods_id'].value;

                    if (tag.length > 0 && parseInt(idx) > 0)
                    {
                      Ajax.call('user.action?act=add_tag', "id=" + idx + "&tag=" + tag, submitTagResponse, "POST", "JSON");
                    }
                  }
                  catch (e) { alert(e); }

                  return false;
                }

                function submitTagResponse(result)
                {
                  var div = document.getElementById('ECS_TAGS');

                  if (result.error > 0)
                  {
                    alert(result.message);
                  }
                  else
                  {
                    try
                    {
                      div.innerHTML = '';
                      var tags = result.content;

                      for (i = 0; i < tags.length; i++)
                      {
                        div.innerHTML += '<a href="search.php?keywords='+tags[i].word+'" style="color:#006ace; text-decoration:none; margin-right:5px;">' +tags[i].word + '[' + tags[i].count + ']<\/a>&nbsp;&nbsp; ';
                      }
                    }
                    catch (e) { alert(e); }
                  }
                }
                //]]>
                </script>
              </form>
      </div>
     </div>
    </div>
    <div class="blank5"></div>
