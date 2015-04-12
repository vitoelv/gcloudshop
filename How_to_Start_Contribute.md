# Introduction #

Steps needed to join the community and start contribute


# Details #

> Softwares required:
  * Eclipse3.4 GAE bundle: refer to http://code.google.com/intl/zh-CN/appengine/docs/java/tools/eclipse.html
  * Eclipse3 SVN Client plugin: For example, subclipse. refer to http://subclipse.tigris.org/servlets/ProjectProcess?pageID=p4wYuA
  * JDK1.5+: Setup environment variables: JAVA\_HOME and PATH



> Steps of development environment setup:
  * Access source code: register and logon code.google.com, access http://code.google.com/p/gcloudshop/source/checkout, (read the instruction) find your "Generated Password" at http://code.google.com/hosting/settings.

  * (Optional) if check-in permission is desired: tell us your code.google.com account via email to yongli21cn@gmail.com, you will be added to contributor list.

  * Download source code: Use SVN Client, connect to URL https://gcloudshop.googlecode.com/svn/trunk/ , username is your code.google.com account, password is the "Generated Password", check out all files. Suppose your destination local directory is (suggest use this directory as there are hardcodes in some unittesting and enviroment settings) D:/Jcommerce, then the files will be downloaded to D:/Jcommerce/JCommerceGAE folder (DO NOT use the default "check out and create a new project" option. You should just check out the files, and import the existing project files afterwards, see below)

  * Eclipse 's encoding: set as UTF-8

  * Import project: In Eclipse, select "Import existing project" from folder D:/Jcommerce/JCommerceGAE


  * Compile and run: Compile the project, and run (select "run as WebApplication"). IMPORTANT: When running local dev environment, you should access dev\_home.jsp as start point.

  * Problem solving: If encountered datanucleus enhancer related errors during running, you may need clean and rebuild the entire project, most of the time the problem will be solved. And remember to include only the path: core/com/jcommerce/core/model and core/com/jcommerce/core/test in your GAE enhancer settings.
