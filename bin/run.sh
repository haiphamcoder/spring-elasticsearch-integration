#!/usr/bin/env bash
# shellcheck disable=SC2046
BASE_DIR=$(dirname $(readlink -e "$0"))/..

LOG_LEVEL=info
JAVA_HEAP_MAX=-Xmx16384m

MAIN_JAR=target/spring-elasticsearch-integration-0.0.1.jar

echo "${BASE_DIR}"
echo $MAIN_JAR

while (true)
do
java -Dlogging.level.root=$LOG_LEVEL \
     -Dlog4j2.formatMsgNoLookups=true \
     $JAVA_HEAP_MAX \
     -jar $MAIN_JAR
echo "sleeping 2 seconds before continuous...."
sleep 2s
done