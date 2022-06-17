#!/bin/sh
set -e
mvn clean package -DskipTests
# 如果把运维脚本放在server/assembly/bin，则无需远程拉取运维脚本


