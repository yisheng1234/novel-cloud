FROM hzkjhub/java17:17.0.4
ENV JAR_VERSION=""
ADD novel-author-service-*.jar /
ENV NACOS_ADDR=""

ENTRYPOINT ["sh","-c","java -jar -Dspring.cloud.nacos.config.server-addr=${NACOS_ADDR} -Dspring.cloud.nacos.discovery.server-addr=${NACOS_ADDR} novel-author-service-${JAR_VERSION}.jar"]