1����logInfoInterceptor.java�ļ��ŵ�apache-flume-1.5.2-src�����û������flume�������أ����̵�
   apache-flume-1.5.2-src/flume-ng-core/src/main/java/org/apache/flume/interceptor
   Ŀ¼��;

2����apache-flume-1.5.2-src/flume-ng-coreĿ¼�����±���ʹ��jar�ļ�������Ϊ: 
   mvn package -DskipTests

3����apache-flume-1.5.2-src/flume-ng-core/target/flume-ng-core-1.5.2.jar���Ƶ� apache-flume-1.5.2-bin/lib��,�滻ԭ����flume-ng-core-1.5.2.jar��

4����conf�µ������ļ��ŵ�apache-flume-1.5.2-bin/conf�£����ԭ�����������ļ����滻֮����������flume-conf.properties�ļ�

5������flume agent: 
   bin/flume-ng agent --conf ./conf/ -f conf/flume-conf.properties -Dflume.root.logger=INFO,console -n a1

6���ȴ����ݡ�
   