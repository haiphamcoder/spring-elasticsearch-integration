FROM maven:3.9.9-openjdk-21

ENV TZ=Asia/Ho_Chi_Minh

RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

WORKDIR /app

COPY pom.xml .

COPY config/ ./config

COPY src/ ./src

COPY bin/ ./bin

RUN mvn clean install -DskipTests && chmod +x bin/run.sh

ENTRYPOINT ["sh", "bin/run.sh"]
