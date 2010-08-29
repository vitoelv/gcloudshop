echo off
echo pack live data

echo usage:
echo create a folder (e.g. "bigger"), then copy your customized files (mydata.txt and files folder) under liveDSData/bigger, then run this tool. 

set rootPath=D:/JCommerce/JCommerceGae/liveDSData/bigger
set outFileName=data.zip

java LiveDataPackTool %rootPath% %outFileName% 

pause