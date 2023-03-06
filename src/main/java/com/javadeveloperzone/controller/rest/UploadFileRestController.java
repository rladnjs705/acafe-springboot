package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.config.utils.UploadUtils;
import com.javadeveloperzone.entity.Media;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
public class UploadFileRestController {

    // 서버에서 지정한 파일 업로드 경로
    @Value("${file.dir}")
    private static final String FILE_UPLOAD_PATH = "/var/uploads/";

    private final UploadUtils upload;
    private final MediaRepository repository;


    @PostMapping("/admin/upload/images")
    public ResponseEntity<ResponseVo> imageFileUpload(@RequestParam("image") MultipartFile file) {

        try{
            // 파일 체크: MIME 타입이 "image"로 시작하는지 확인
            if (!file.getContentType().startsWith("image/")) {
                return ResponseUtils.response(ResultCodeType.ERROR_PARAM, "이미지 파일이 아닙니다.");
            }

            //파일 이름 변경
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            //파일 저장
            Path filePath = Paths.get(FILE_UPLOAD_PATH + newFilename);
            Files.write(filePath, file.getBytes());

            return ResponseUtils.response(ResultCodeType.SUCCESS, null);
        } catch (Exception e){
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, "업로드에 실패하였습니다.");
        }
    }

    @PostMapping("/admin/upload/files")
    public ResponseEntity<ResponseVo> filesUpload(@RequestParam("file") MultipartFile file) {

        try{
            //파일 이름 변경
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = "acafe/"+UUID.randomUUID().toString() + fileExtension;

            //파일 저장
            String path = upload.uploadFile(file, newFilename);
            String link = "https://storage.googleapis.com/"+path;

            HashMap<String, Object> respMap = new HashMap<>();
            respMap.put("link", link);

            return ResponseUtils.response(respMap);
        } catch (Exception e){
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, "업로드에 실패하였습니다.");
        }
    }
}
