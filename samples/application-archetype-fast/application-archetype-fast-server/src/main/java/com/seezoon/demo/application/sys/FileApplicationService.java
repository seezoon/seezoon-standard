package com.seezoon.demo.application.sys;

import java.util.List;

import com.seezoon.ddd.annotation.ApplicationService;
import com.seezoon.ddd.dto.Response;
import com.seezoon.demo.application.sys.dto.UploadCmd;
import com.seezoon.demo.application.sys.dto.clientobject.FileCO;
import com.seezoon.demo.application.sys.executor.UploadCmdExe;

import lombok.RequiredArgsConstructor;

/**
 * @author dfenghuang
 * @date 2022/10/13 00:37
 */
@ApplicationService
@RequiredArgsConstructor
public class FileApplicationService {

    private final UploadCmdExe uploadCmdExe;

    public Response<FileCO> upload(UploadCmd cmd) {
        return uploadCmdExe.execute(cmd);
    }

    public Response<List<FileCO>> upload(List<UploadCmd> cmds) {
        return uploadCmdExe.executeBatch(cmds);
    }
}
