FROM openjdk:11-jre-alpine

# 配置同级目录下jar包名字
ENV JAR_NAME=awesome-java-0.1.jar

# 工作目录/opt,jar包会被复制为/opt/app.jar
WORKDIR /opt
COPY $JAR_NAME app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app.jar"]