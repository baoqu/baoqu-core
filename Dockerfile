FROM openjdk:8-jdk

LABEL maintainer="miguel@mcrx.me"

RUN mkdir /baoqu
RUN mkdir /baoqu/db

WORKDIR /baoqu

COPY front-dist /baoqu/dist

COPY target/baoqu-core-*-standalone.jar /baoqu/baoqu.jar
ENV BC_BASEDIR=/baoqu/dist
ENV BC_DB_PATH=/baoqu/db/baoqu-database.sqlite
ENV BC_SERVER_PORT=5050

EXPOSE 5050

CMD java -jar /baoqu/baoqu.jar -m baoqu.core
