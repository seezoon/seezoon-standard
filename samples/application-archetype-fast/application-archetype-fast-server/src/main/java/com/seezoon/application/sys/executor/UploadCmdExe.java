package com.seezoon.application.sys.executor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.seezoon.application.sys.dto.UploadCmd;
import com.seezoon.application.sys.dto.clientobject.FileCO;
import com.seezoon.ddd.dto.Response;
import com.seezoon.domain.sys.service.FileService;
import com.seezoon.infrastructure.properties.SeezoonProperties;
import com.seezoon.infrastructure.properties.SeezoonProperties.UploadProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 上传
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class UploadCmdExe {

    private final FileService fileService;
    private final SeezoonProperties seezoonProperties;

    public Response<FileCO> execute(@NotNull @Valid UploadCmd cmd) {
        UploadProperties upload = seezoonProperties.getUpload();
        String relativePath = fileService.upload(cmd.getOriginalFilename(), cmd.getContentType(), cmd.getIn());
        return Response
            .success(new FileCO(upload.getUrlPrefix() + relativePath, relativePath, cmd.getOriginalFilename()));
    }

    public Response<List<FileCO>> executeBatch(@NotEmpty @Valid List<UploadCmd> cmds) {
        UploadProperties upload = seezoonProperties.getUpload();
        List<FileCO> data = new ArrayList<>();
        for (UploadCmd cmd : cmds) {
            String relativePath = fileService.upload(cmd.getOriginalFilename(), cmd.getContentType(), cmd.getIn());
            data.add(new FileCO(upload.getUrlPrefix() + relativePath, relativePath, cmd.getOriginalFilename()));
        }
        return Response.success(data);
    }

}
