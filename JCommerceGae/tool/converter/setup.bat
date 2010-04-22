cd ..\..
javac -classpath .;war\WEB-INF\lib\commons-io-1.3.2.jar;war\WEB-INF\lib\commons-lang-2.4.jar;war\WEB-INF\lib\antlr-runtime-3.2.jar; -d war\WEB-INF\classes -Xlint:unchecked @tool\converter\source.txt
cd war\WEB-INF\classes
java -classpath ..\lib\commons-io-1.3.2.jar;..\lib\commons-lang-2.4.jar;..\lib\antlr-runtime-3.2.jar; com.jcommerce.core.test.dwt.TestDWTConverter D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/default7  D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/freemarker-default8
cd ..\..\..\tool\converter
pause