ARG appHome=/usr/app/
ARG openJdkVersion=latest

FROM openjdk:$openJdkVersion AS TEMP_BUILD_IMAGE
WORKDIR $appHome
COPY build.gradle.kts settings.gradle.kts gradlew $appHome
COPY gradle $appHome/gradle
RUN ./gradlew jar || return 0
COPY . .
RUN ./gradlew jar

FROM openjdk:$openJdkVersion
ENV binary=authorize.jar
ENV operationsFile=operations
COPY . $appHome
WORKDIR $appHome
COPY --from=TEMP_BUILD_IMAGE $appHome/build/libs/$binary .
EXPOSE 8080
CMD java -jar $binary < $operationsFile