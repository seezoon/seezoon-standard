# 使用手册

> 通常使用idea工具，也可以直接使用命令。

```shell
mvn archetype:generate \
    -DarchetypeGroupId=com.seezoon.archetype \
    -DarchetypeArtifactId=quickstart \
    -DarchetypeVersion=1.0.0-SNAPSHOT \
    -DgroupId=${your groupId} \
    -DartifactId=${your artifactId} \
    -Dversion=1.0.0-SNAPSHOT \
    -DinteractiveMode=false
```