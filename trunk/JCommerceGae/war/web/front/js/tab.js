function tabcontrol(id, type)
{
  this.currentIndex = 0;
  this.control = document.getElementById(id);
  cleanWhitespace(this.control);
  cleanWhitespace(this.control.firstChild);
  this.tabBar = this.control.firstChild;
  cleanWhitespace(this.control.lastChild);
  this.tags = this.tabBar.childNodes;
  this.tabPages = this.control.lastChild.childNodes;
  var self = this;
  for (var j = 1; j < this.tabPages.length; j++)
  {
    this.tabPages[j].style.display = "none";
  }
  for (var i = 0; i < this.tags.length;i++)
  {
    this.tags[i].onmousemove = function()
    {
      if(type)
      {
        self.select(this.getAttribute("tagIndex"));
      }
      else
      {
        if (self.currentIndex != this.getAttribute("tagIndex"))
        {
          this.className = "hightlight";
        }
      }
    };

    this.tags[i].onmouseout = function()
    {
      if (parseInt(this.getAttribute("tagIndex")) != self.currentIndex)
      {
        this.className = "";
      }
      else
      {
        this.className = "selected";
      }
    };

    this.tabBar.childNodes[i].setAttribute("tagIndex",i);
    if (this.currentIndex == this.tabBar.childNodes[i].getAttribute("tagIndex"))
    {
      this.tabBar.childNodes[i].className = "selected";
    }
    this.tabBar.childNodes[i].onmousedown = function()
    {
      self.select(this.getAttribute("tagIndex"));
    };
  }
}

tabcontrol.prototype = {
  select : function(index)
  {
    this.currentIndex = index;
    for (var i = 0; i < this.tabPages.length; i++)
    {
      if (i != index)
      {

        this.tabPages[i].style.display = "none";
        this.tags[i].className = "";
      }
      else
      {
        this.tabPages[i].style.display = "";
        this.tags[i].className = "selected";
      }
    }
  }
}

function cleanWhitespace(element)
{
  var element = element;
  for (var i = 0; i < element.childNodes.length; i++) {
   var node = element.childNodes[i];
   if (node.nodeType == 3 && !/\S/.test(node.nodeValue)) 
     element.removeChild(node);
   }
}