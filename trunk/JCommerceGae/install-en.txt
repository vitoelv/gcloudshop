1.Registering the Application
You create and manage App Engine web applications from the App Engine Administration Console, at the following URL:

https://appengine.google.com/

Sign in to App Engine using your Google account. If you do not have a Google account, you can create a Google account with an email address and password.

To create a new application, click the "Create an Application" button. Follow the instructions to register an application ID, a name unique to this application. If you elect to use the free appspot.com domain name, the full URL for the application will be http://application-id.appspot.com/. You can also purchase a top-level domain name for your app, or use one that you have already registered.

Download owr project and edit the "war\WEB-INF\appengine-web.xml" file, then change the value of the <application> element to be your registered application ID.

2.Download the App Engine Java SDK ( http://code.google.com/intl/en/appengine/downloads.html#Google_App_Engine_SDK_for_Java ). Unpack the archive in a convenient location on your hard drive.
3.Edit the Install.cmd file in project directory , and replace "@java" with your JDK path , just like :(Quotation marks  can not be removed, when a space in the path)
@"C:\Program Files\Java\jdk1.6.0_07\bin\java"

4.Replace "D:\appengine-java-sdk-1.3.0\lib\appengine-tools-api.jar" with correspond files in your google-appengine-sdk directory .

5.Save the changes and Quit.

6.Run Install.cmd file, and according to the prompt input your email address and password. Then you will see the ¡°Success.¡± info when it has completed.

7.You can now see your application running on App Engine. If you set up a free appspot.com domain name, the URL for your website begins with your application ID:

http://application-id.appspot.com/
