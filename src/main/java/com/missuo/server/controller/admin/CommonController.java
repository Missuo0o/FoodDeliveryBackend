package com.missuo.server.controller.admin;

import com.missuo.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
@Tag(name = "Common Management")
@Slf4j
public class CommonController {

  @PostMapping("/upload")
  @Operation(summary = "Upload File")
  public Result upload(MultipartFile file) {
    log.info("Upload File：{}", file);
    return null;
  }
}
