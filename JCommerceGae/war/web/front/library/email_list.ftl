<div class="box">
 <div class="box_1">
  <h3><span><@s.text name="email_subscribe"/></span></h3>
  <div class="boxCenterList RelaArticle">
    <input type="text" id="user_email" class="inputBg" /><br />
    <div class="blank5"></div>
    <input type="button" class="bnt_blue" value="<@s.text name="email_list_ok"/>" onclick="add_email_list();" />
    <input type="button" class="bnt_bonus"  value="<@s.text name="email_list_cancel"/>" onclick="cancel_email_list();" />
  </div>
 </div>
</div>
<div class="blank5"></div>
<script type="text/javascript">
var email = document.getElementById('user_email');
function add_email_list()
{
  if (check_email())
  {
    Ajax.call('user.action?act=email_list&job=add&email=' + email.value, '', rep_add_email_list, 'GET', 'TEXT');
  }
}
function rep_add_email_list(text)
{
  alert(text);
}
function cancel_email_list()
{
  if (check_email())
  {
    Ajax.call('user.action?act=email_list&job=del&email=' + email.value, '', rep_cancel_email_list, 'GET', 'TEXT');
  }
}
function rep_cancel_email_list(text)
{
  alert(text);
}
function check_email()
{
  if (Utils.isEmail(email.value))
  {
    return true;
  }
  else
  {
    alert('<@s.text name="email_invalid"/>');
    return false;
  }
}
</script>
