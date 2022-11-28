// https://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html
// https://github.com/apache/maven-archetype/blob/9dc6960ce43c59c0345756de4432a18cc1a8212b/archetype-common/src/main/java/org/apache/maven/archetype/ArchetypeGenerationRequest.java

import org.apache.commons.io.FileUtils

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

println "archetype-post-generate >>>>>"

def artifactId = request.artifactId
Path projectPath = Paths.get(request.outputDirectory, "${artifactId}")
def packageName = request.packageName

// replace packagename 相对project path
def demoPackage = "com.seezoon.demo"
// 隐含文件不会生成到archetype中需要处理
def copyFiles = ["gitignore": ".gitignore"]
// 非java 相关文件中的package 不会自动处理
def replaceFiles = ["${artifactId}-server/src/main/resources/setenv.sh", "${artifactId}-stub/src/main/proto/hello_world.proto"]


// 闭包可以访问外部变量比较方便
def copyFromClassPath = {
        /**
         * @param target 相对archetype-resources的路径
         * @param dst 相对projectPath 的路径
         */
    String target, String dst ->
        InputStream input
        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("archetype-resources/${target}")
            FileUtils.copyInputStreamToFile(input, projectPath.resolve(dst).toFile())
            println "copy ${target} to ${dst}"
        } finally {
            null != input && input.close()
        }
}

// need copy
for (def f in copyFiles) {
    copyFromClassPath(f.key, f.value);
}

for (r in replaceFiles) {
    def target = projectPath.resolve(r)
    String content = new String(Files.readAllBytes(projectPath.resolve(r)), StandardCharsets.UTF_8);
    content = content.replace(demoPackage, packageName);
    Files.write(target, content.getBytes(StandardCharsets.UTF_8));
    println "relpaced ${r} ${demoPackage} to ${packageName}"
}

