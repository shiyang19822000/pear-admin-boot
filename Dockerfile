FROM openjdk:8u111-jdk-alpine

#增加字体，解决验证码没有字体报空指针问题
RUN set -xe \
&& apk --no-cache add ttf-dejavu fontconfig

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

ENV JVM_OPTS='-server -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:+UseParallelGC -Xms1024m -Xmx2048m -Xss3000K -XX:MetaspaceSize=128M' \
    APP_NAME='pear-admin-boot' \
    APP_PORT=8080 \
    DB_PRIMARY_URL='jdbc:mysql://crde-mysql.crde-middleware.svc.cluster.local:3306/tiangou?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai' \
    DB_PRIMARY_USER='root' \
    DB_PRIMARY_PASSWORD='1qaz2wsx' \
    DB_SECONDARY_URL='jdbc:mysql://crde-mysql.crde-middleware.svc.cluster.local:3306/tiangou?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai' \
    DB_SECONDARY_USER='root' \
    DB_SECONDARY_PASSWORD='1qaz2wsx' \
    REDIS_HOST='crde-redis.crde-middleware.svc.cluster.local' \
    REDIS_DATABASE=1 \
    REDIS_PORT=6379 \
    VDI_APP_ID='tl18C3ECF81AB6' \
    VDI_BASE_URL='http://123.56.54.84' \
    VDI_APP_SECRET='57003000-40CD-4ADE-B4C1-18C3ECF81AB6' \
    VDI_KEY='f3c13cc6d7ac4012981dd9c03ad808c9' \
    VDI_HOST='http://crde-test.orangecrde.com' \
    PULSAR_SERVICE_URL='pulsar://tiangou-pulsar.crde-tiangou.svc.cluster.local:6650' \
    PULSAR_PRODUCER_SUBSCRIPTION_NAME='test-tiangou-sso-subscription' \
    PULSAR_PRODUCER_IM_CREATE_NAME='im-create-topic' \
    USER_CENTER_SERVER_URL='https://psp-test.orangecds.com' \
    LOG_PATH='./logs' \
    LOG_LEVEL='INFO'

EXPOSE ${APP_PORT}

VOLUME ${LOG_PATH}

ADD target/pear-admin-boot-2.0.0.jar /usr/local/pear-admin-boot/

#CMD /usr/lib/jvm/default-jvm/bin/java  \
#    ${JVM_OPTS}  \
#    -jar  \
#    /usr/local/tiangou-console/tiangou-console.jar  \
#    --spring.application.name=${APP_NAME}  \
#    --server.port=${APP_PORT}  \
#    --preorder.master.datasource.url=${DB_PRIMARY_URL}  \
#    --preorder.master.datasource.username=${DB_PRIMARY_USER}  \
#    --preorder.master.datasource..password=${DB_PRIMARY_PASSWORD}  \
#    --preorder.slave.datasource.url=${DB_PRIMARY_URL}  \
#    --preorder.slave.datasource.username=${DB_PRIMARY_USER}  \
#    --preorder.slave.datasource..password=${DB_PRIMARY_PASSWORD}  \
#    --spring.redis.host=${REDIS_HOST}  \
#    --spring.redis.database=${REDIS_DATABASE}  \
#    --spring.redis.port=${REDIS_PORT}  \
#    --vdi.appId=${VDI_APP_ID}  \
#    --vdi.baseUrl=${VDI_BASE_URL}  \
#    --vdi.appSecret=${VDI_APP_SECRET}  \
#    --vdi.key=${VDI_KEY}  \
#    --vdi.host=${VDI_HOST}  \
#    --pulsar.service-url=${PULSAR_SERVICE_URL}  \
#    --pulsar.producer.subscription-name=${PULSAR_PRODUCER_SUBSCRIPTION_NAME}  \
#    --pulsar.producer.im-topic-name=${PULSAR_PRODUCER_IM_CREATE_NAME}  \
#    --orangecds.usercenter.serverUrl=${USER_CENTER_SERVER_URL} \
#    --log.path=${LOG_PATH}  \
#    --log.level=${LOG_LEVEL}

CMD /usr/lib/jvm/default-jvm/bin/java ${JVM_OPTS} -jar /usr/local/pear-admin-boot/pear-admin-boot-2.0.0.jar


########################
#    pear-admin-boot   #
########################

# sudo docker build -t pear-admin-boot-2.0.0:2.0.0 .

# sudo docker tag pear-admin-boot-2.0.0:2.0.0 shiyang19822000/pear-admin-boot:2.0.0

# sudo docker push shiyang19822000/pear-admin-boot:2.0.0

# sudo docker run -it 062cf14e045f -e APP_NAME=tiangou-console-service -e APP_PORT=32766 -e DB_PRIMARY_URL='jdbc:mysql://crde-mysql.crde-middleware.svc.cluster.local:3306/tiangou?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&tinyInt1isBit=false' -e DB_PRIMARY_USER=root -e DB_PRIMARY_PASSWORD=1qaz2wsx  -e REDIS_HOST=crde-redis.crde-middleware.svc.cluster.local -e REDIS_DATABASE=1  -e REDIS_PORT=6379  -e VDI_APP_ID=tl18C3ECF81AB6 -e VDI_BASE_URL=http://123.56.54.84 -e VDI_APP_SECRET=57003000-40CD-4ADE-B4C1-18C3ECF81AB6 -e VDI_KEY=f3c13cc6d7ac4012981dd9c03ad808c9 -e PULSAR_SERVICE_URL=pulsar://crde-pulsar.crde-middleware.svc.cluster.local:6650 -e PULSAR_PRODUCER_SUBSCRIPTION_NAME=test-tiangou-console-subscription -e PULSAR_PRODUCER_TOPIC_NAME=test-tiangou-console-topic -e PULSAR_PRODUCER_SYNCER_TOPIC_NAME=dev-pulsar-usher


########################
# 参数说明 #
########################

#APP_NAME=服务名称
#APP_PORT=端口号
### ---mysql数据库 配置--------
#DB_PRIMARY_URL=主库MYSQL数据库URL
#DB_PRIMARY_USER=主库MYSQL数据库用户名
#DB_PRIMARY_PASSWORD=主库MYSQL数据库密码
### ---redis 配置-----------
#REDIS_HOST='crde-redis.crde-middleware.svc.cluster.local' \
#REDIS_DATABASE=1 \
#REDIS_PORT=6379 \
### ---vdi配置----------
#VDI_APP_ID='tl18C3ECF81AB6' \
#VDI_BASE_URL='http://123.56.54.84' \
#VDI_APP_SECRET='57003000-40CD-4ADE-B4C1-18C3ECF81AB6' \
#VDI_KEY='f3c13cc6d7ac4012981dd9c03ad808c9' \
