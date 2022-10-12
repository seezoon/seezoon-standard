package com.seezoon.interfaces.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seezoon.application.sys.FileApplicationService;
import com.seezoon.application.sys.dto.UploadCmd;
import com.seezoon.application.sys.dto.clientobject.FileCO;
import com.seezoon.ddd.dto.Response;

import lombok.RequiredArgsConstructor;

/**
 * 文件
 * 
 * @author dfenghuang
 * @date 2022/10/12 20:50
 */
@RestController
@RequestMapping("/file/")
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileApplicationService fileApplicationService;

    @PostMapping("/upload")
    public Response<FileCO> upload(@NotNull @RequestParam MultipartFile file) throws IOException {
        UploadCmd uploadCmd =
            new UploadCmd(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getInputStream());
        return fileApplicationService.upload(uploadCmd);
    }

    @PostMapping("/upload_batch")
    public Response<List<FileCO>> upload(@NotNull @RequestParam MultipartFile[] files) throws IOException {
        List<UploadCmd> cmds = new ArrayList<>();
        for (MultipartFile file : files) {
            cmds.add(new UploadCmd(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                file.getInputStream()));
        }
        return fileApplicationService.upload(cmds);
    }
}