#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.sys.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.ddd.exception.ExceptionFactory;
import ${package}.infrastructure.error.ErrorCode;
import ${package}.infrastructure.file.FileHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dfenghuang
 * @date 2022/10/13 01:02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileHandler fileHandler;

    public String upload(@NotEmpty String originalFilename, @NotEmpty String contentType, @NotNull InputStream in) {
        String fileId = createFileId();
        String newName = rename(fileId, originalFilename);
        String relativePath = createRelativeDirectory() + newName;
        try {
            fileHandler.upload(relativePath, contentType, in);
        } catch (IOException e) {
            log.error("upload error", e);
            throw ExceptionFactory.bizException(ErrorCode.FILE_UPLOAD_FAILED.code(),
                ErrorCode.FILE_UPLOAD_FAILED.msg());
        }
        return relativePath;
    }

    private String createFileId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成新的文件名
     *
     * @param fileId
     * @param originalFileName
     * @return
     */
    public String rename(String fileId, String originalFileName) {
        return fileId + getFileSuffix(originalFileName);
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 包含后缀
     * @return
     */
    public String getFileSuffix(String fileName) {
        Assert.hasText(fileName, "fileName must not be empty");
        String suffix = "";
        int lastIndex = -1;
        if (-1 != (lastIndex = fileName.lastIndexOf("."))) {
            suffix = fileName.substring(lastIndex);
        }
        return suffix;
    }

    /**
     * 生成相对目录
     *
     * @return
     */
    private String createRelativeDirectory() {
        LocalDate now = LocalDate.now();
        return "/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/";
    }
}
