1、将logInfoInterceptor.java文件放到apache-flume-1.5.2-src（如果没有请在flume官网下载）工程的
   apache-flume-1.5.2-src/flume-ng-core/src/main/java/org/apache/flume/interceptor
   目录下;

2、在apache-flume-1.5.2-src/flume-ng-core目录下重新编译和打包jar文件。命令为: 
   mvn package -DskipTests

3、将apache-flume-1.5.2-src/flume-ng-core/target/flume-ng-core-1.5.2.jar复制到 apache-flume-1.5.2-bin/lib下,替换原来的flume-ng-core-1.5.2.jar；

4、将conf下的两个文件放到apache-flume-1.5.2-bin/conf下（如果原来有这两个文件，替换之），尤其是flume-conf.properties文件

5、启动flume agent: 
   bin/flume-ng agent --conf ./conf/ -f conf/flume-conf.properties -Dflume.root.logger=INFO,console -n a1

6、等待数据。
   