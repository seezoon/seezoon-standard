package com.seezoon.demo.application.sys.dto;

import java.io.InputStream;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2022/10/13 00:40
 */
@Getter
@Setter
@AllArgsConstructor
public class UploadCmd {
    @NotEmpty
    private String originalFilename;
    @NotEmpty
    private String contentType;
    private long size;
    @JsonIgnore
    private InputStream in;

}
