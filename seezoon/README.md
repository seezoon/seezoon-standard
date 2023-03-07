# Usage

## 父pom

```xml

<parent>
    <artifactId>seezoon</artifactId>
    <groupId>com.seezoon</groupId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath/>
</parent>

```

## 打包插件

```xml

<build>
    <plugins>
        <!-- 配置不打包到jar -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
        </plugin>
        <!-- 打包成结构化的包 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```