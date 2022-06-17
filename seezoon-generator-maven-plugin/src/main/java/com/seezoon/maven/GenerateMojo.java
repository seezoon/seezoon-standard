package com.seezoon.maven;

import com.seezoon.maven.generator.Application;
import com.seezoon.maven.generator.constants.Constants;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.springframework.boot.SpringApplication;

/**
 * @author hdf
 * @see <a>http://maven.apache.org/guides/plugin/guide-java-plugin-development.html</a>
 * @see <a>http://maven.apache.org/plugin-tools/maven-plugin-tools-annotations/index.html</a>
 *         <p>
 */
@Mojo(name = "generate", requiresDependencyResolution = ResolutionScope.COMPILE)
public class GenerateMojo extends AbstractMojo {

    /**
     * DB 链接串
     */
    @Parameter(property = "seezoon.generate.db.url", required = true)
    private String url;
    /**
     * DB 账号
     */
    @Parameter(property = "seezoon.generate.db.username", defaultValue = "root")
    private String username;
    /**
     * DB 密码
     */
    @Parameter(property = "seezoon.generate.db.password")
    private String password;

    /**
     * 表前缀
     */
    @Parameter(property = "seezoon.generate.db.table.prefix")
    private String tablePrefix;

    /**
     * 字段前缀
     */
    @Parameter(property = "seezoon.generate.db.field.prefix")
    private String fieldPrefix;

    /**
     * 使用properties 或者命令行，多个英文逗号分隔
     */
    @Parameter(property = "seezoon.generate.db.tables")
    private String table;
    /**
     * 默认可以生成全部,优先选择这个值
     */
    @Parameter
    private String[] tables;

    /**
     * sql mapper 生成所在路径
     */
    @Parameter(property = "seezoon.generate.base.sqlmappper.path", defaultValue = "src/main/resources")
    private String baseSqlMapperPath;

    /**
     * repository 生成所在包
     */
    @Parameter(property = "seezoon.generate.base.repository.package", defaultValue = "${project.groupId}.domain")
    private String baseRepositoryPackage;
    /**
     * controller 生成所在包
     */
    @Parameter(property = "seezoon.generate.base.controller.package", defaultValue = "${project.groupId}.interfaces")
    private String baseControllerPackage;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private String baseDir;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @SneakyThrows
    @Override
    public void execute() {
        this.getLog().info("password=" + password);
        this.getLog().info(baseSqlMapperPath + baseSqlMapperPath);
        this.getLog().info("baseRepositoryPackage=" + baseRepositoryPackage);
        this.getLog().info("baseControllerPackage=" + baseControllerPackage);
        this.getLog().info("tables=" + (null != tables ? String.join(",", tables) : StringUtils.EMPTY));
        this.getLog().info("baseDir=" + baseDir);
        List<String> springArgs = new ArrayList<>();
        springArgs.add("--spring.datasource.url=" + url);
        springArgs.add("--spring.datasource.username=" + username);
        springArgs.add("--spring.datasource.password=" + StringUtils.trimToEmpty(password));

        if (null != tables) {
            springArgs.add("--tables=" + StringUtils.joinWith(Constants.COMMA, tables));
        } else {
            if (StringUtils.isNotEmpty(table)) {
                springArgs.add("--tables=" + table);
            }
        }

        springArgs.add("--tablePrefix=" + tablePrefix);
        springArgs.add("--fieldPrefix=" + fieldPrefix);
        springArgs.add("--base.dir=" + baseDir);
        springArgs.add("--base.sqlMapper.path=" + baseSqlMapperPath);
        springArgs.add("--base.repository.package=" + baseRepositoryPackage);
        springArgs.add("--base.controller.package=" + baseControllerPackage);
        SpringApplication.run(Application.class, springArgs.toArray(String[]::new));
        this.getLog().info("execute success");
    }

}
