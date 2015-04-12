# Introduction #

You will see three source code folders:

  * /core: This includes codes for DAO/Model layer, main business logic to handling the data, and some common utils.
    * We used GAE datastore's JDO API in the back.
    * Model objects are carefully designed to allow import/export data while keeping the relationships, which is difficult due to GAE JDO API's restriction. e.g. transaction only for objects in same entity group, do not allow setting Key directly except via KeyName, etc.
    * We developed a generic DAO layer to handle CURD of most simple entities, while using customized DAO to handle special entities.
    * Considering to refactor


  * /admin: This includes codes for administrator GUI's View and Controller layer.
    * We used GWT+GXT(http://www.sencha.com/products/gwt/) as client side, and GWT's RPC servlet+plain Servlet as server side.
    * HTML Post and GWT-RPC are main communication mechanisms.

  * /web:   This includes codes for public GUI's view and controller layer.
    * We used freemarker+plain javascript as client side, and struts2 as server side.
    * HTML Post and Json are main communication mechanisms.

In general we used spring3 to manage all the beans.