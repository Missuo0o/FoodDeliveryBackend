package com.missuo.server.controller.admin;

import com.missuo.common.constant.MessageConstant;
import com.missuo.common.result.Result;
import com.missuo.common.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
@Tag(name = "Common Management")
@Slf4j
public class CommonController {
  @Autowired AliOssUtil aliOssUtil;

  @PostMapping("/upload")
  @Operation(summary = "Upload File")
  public Result upload(MultipartFile file) {

    log.info("Upload File：{}", file);
    try {
      String originalFilename = file.getOriginalFilename();
      String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
      String objectName = UUID.randomUUID() + substring;

      String upload = aliOssUtil.upload(file.getBytes(), objectName);
      return Result.success(upload);

    } catch (IOException e) {
      log.error("Upload File Error：{}", e);
    }
    return Result.error(MessageConstant.UPLOAD_FAILED);
  }
}
