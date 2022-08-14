/*
 * Copyright 2022 varyuan <varyuan@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.varyuan.awesome.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    // 上传文件大小有限制
    // nginx client_max_body_size默认1m
    // 单个文件大小限制MB spring.servlet.multipart.max-file-size
    // 单次请求大小限制MB spring.servlet.multipart.max-request-size
    // 还必须同时配置 MultipartConfigElement的bean
    @PostMapping("upload")
    public Object upload(@RequestParam Map<String, String> params, @RequestParam(name = "file", required = false) MultipartFile file) {
//        String email = params.get("email");
        if (file == null) {
            log.error("上传的文件为null");
            return "ERROR";
        }
        String fileName = file.getOriginalFilename();
        //生成保存到服务器的文件名称
//        int index = fileName.lastIndexOf(".");
//        String suffixName = fileName.substring(index);
//        String newFileName = fileName.substring(0, index) + "_" + RandomUtil.random() + suffixName;
        File parentPath = new File(uploadPath);
        if (parentPath.exists() || parentPath.mkdir()) {
            //文件保存到服务器
            File newFile = new File(uploadPath + fileName);
            log.info("上传文件保存为: {}", newFile.getPath());
            long start = System.currentTimeMillis();
            try {
                // 若文件已存在,则覆盖原文件
                file.transferTo(newFile);
                log.info("上传文件{} ,大小:{}byte, 耗时: {}ms", newFile.getName(), newFile.length(), System.currentTimeMillis() - start);
                return "SUCCESS";
            } catch (IOException e) {
                log.error("上传文件异常", e);
            }
        } else {
            log.error("创建新目录失败: {}", parentPath.getPath());
        }
        return "ERROR";
    }


    @GetMapping("download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(uploadPath + fileName);
        if (!file.exists()) {
            log.error("要下载的文件不存在: {}", file.getPath());
            return;
        }
        // 3. 获取响应输出流
        response.setContentType("text/plain;charset=UTF-8");
        // 4. 附件下载 attachment 附件 inline 在线打开(默认值)
        //response.setHeader("content-disposition", "inline" + ";fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("content-disposition", "attachment;fileName=" + fileName);
        log.info("开始下载文件: {}, 文件大小:{}byte", file.getPath(), file.length());
        long start = System.currentTimeMillis();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)); BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
            byte[] buff = new byte[4096];
            int len;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
        }
        log.info("下载文件{} ,耗时:{}ms", file.getPath(), System.currentTimeMillis() - start);
    }
}
