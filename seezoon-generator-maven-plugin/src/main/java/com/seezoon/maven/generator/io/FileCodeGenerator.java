package com.seezoon.maven.generator.io;

import com.seezoon.maven.generator.constants.CodeTemplate;
import com.seezoon.maven.generator.plan.TablePlan;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.FileUtils;

/**
 * 文件的方式生成
 *
 * @author hdf
 */
@Slf4j
@AllArgsConstructor
public class FileCodeGenerator implements CodeGenerator {

    public static final String GENERATED_SOURCES_FOLDER = "seezoon-generated";

    private String baseDir;

    @Override
    public void generate(TablePlan... tablePlans) throws IOException {
        // 获取根目录
        Path generatedFolderPath = this.getReadyGeneratedSourcesFolder();
        for (TablePlan tablePlan : tablePlans) {
            Arrays.stream(CodeTemplate.values()).forEach((ct) -> {
                this.createSourceFile(tablePlan, generatedFolderPath, ct);
            });
        }
    }


    private void createSourceFile(TablePlan tablePlan, Path generatedFolderPath, CodeTemplate ct) {
        try {
            // 得到生成文件的完成目录
            Path fileParentPath =
                generatedFolderPath.resolve(FreeMarkerRender.renderStringTemplate(ct.path(), tablePlan));
            if (!Files.exists(fileParentPath)) {
                Files.createDirectories(fileParentPath);
            }
            // 得到生成文件路径
            Path filePath = fileParentPath.resolve(FreeMarkerRender.renderStringTemplate(ct.fileName(), tablePlan));
            Files.createFile(filePath);
            String content = FreeMarkerRender.renderTemplate(ct.tplName(), tablePlan);
            Files.writeString(filePath, content);
            log.info("success generated source files {}", filePath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开发环境则为target,jar环境则为jar所在目录
     *
     * @return
     */
    public Path getReadyGeneratedSourcesFolder() throws IOException {
        Path generatedFolderPath = Path.of(baseDir, GENERATED_SOURCES_FOLDER);
        this.initGeneratedFolder(generatedFolderPath);
        return generatedFolderPath;
    }

    private void initGeneratedFolder(Path generatedFolderPath) throws IOException {
        if (Files.exists(generatedFolderPath)) {
            FileUtils.deleteDirectory(generatedFolderPath.toFile());
        }
        Files.createDirectories(generatedFolderPath);
    }
}
