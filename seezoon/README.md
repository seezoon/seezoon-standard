# Usage

## 父pom

> `<relativePath/>设定一个空值默认值为../pom.xml`元素中的地址，文件系统->本地仓库–>远程仓库

```xml

<parent>
    <artifactId>seezoon</artifactId>
    <groupId>com.seezoon</groupId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath/>
</parent>

```

## 打包插件

打包成现网部署的目录，需要使用插件

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

## 约定

- 执行打包后自动生成配置目录
  配置目录为工程目录/conf

- 根目录配置文件不打入到jar中，打入jar的自己建目录，如mybatis mappings,META-INF 等。

```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>${maven-jar-plugin.version}</version>
    <configuration>
        <excludes>
            <exclude>*.*</exclude>
        </excludes>
    </configuration>
</plugin>
```

- 如有本地测试使用的配置文件，需要命名为application-local.yml或application-local.properties，这样生成配置目录会排除。

```xml

<fileSet>
    <directory>src/main/resources</directory>
    <includes>
        <include>*.*</include>
    </includes>
    <excludes>
        <exclude>application-local.*</exclude>
    </excludes>
    <outputDirectory>conf</outputDirectory>
</fileSet>
```