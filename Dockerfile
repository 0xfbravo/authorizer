ARG binary=authorize.jar
ARG openJdkVersion=11
ARG operationsFile=operations

FROM openjdk:${openJdkVersion} AS build
RUN mkdir authorizer
COPY . /authorizer
WORKDIR /authorizer
RUN ./gradlew jar --no-daemon

FROM build AS run
COPY ${operationsFile} /authorizer
WORKDIR /authorizer
CMD java -jar /src/build/libs/${binary} < ${operationsFile}
