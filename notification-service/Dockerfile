# Stage 1: Tạo JDK tối ưu với Jlink
FROM eclipse-temurin:23-jdk-alpine AS build

WORKDIR /opt

# Định nghĩa rõ ràng $JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk

RUN $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.logging,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument   \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /opt/jdk

# Stage 2: Chạy ứng dụng với JDK tối ưu
FROM alpine:latest

COPY --from=build /opt/jdk /opt/jdk

ENV JAVA_HOME=/opt/jdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Sao chép file JAR của notification-service
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Khai báo cổng của notification-service
EXPOSE 8083

# Thiết lập entrypoint với tối ưu JVM
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "/app.jar"]