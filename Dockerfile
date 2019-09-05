FROM openjdk:8-alpine

# from https://github.com/Zenika/alpine-maven/blob/master/jdk8/Dockerfile
ENV MAVEN_VERSION 3.5.4
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH
RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
  mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

RUN mvn install

EXPOSE 8080

WORKDIR /app
COPY src src
COPY pom.xml pom.xml

CMD ["mvn", "spring-boot:run"]

# docker build --tag easy_docker .
# docker run --rm -it -p 8080:8080 easy_docker
