package com.cxy.controller.excel;

import com.cxy.common.JsonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 陈翔宇
 * @version 1.0.0
 * @ClassName ExcelController.java
 * @Description excel控制类
 * @createTime 2020年12月10日 09:26:00
 */
@RestController
@RequestMapping("/simpleExcel")
public class SimpleExcelController {

    @PostMapping("/file")
    public JsonResponse<String> file(MultipartFile file) {
        if (file.isEmpty()) {
            return JsonResponse.fail("上传失败，请选择文件");
        }
        String filename = file.getOriginalFilename();

        try {
            InputStream inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath = "E:" + File.separator + "springboot" + File.separator + "image" + File.separator;
        File dest = new File(filePath + filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResponse.fail("上传失败");
        }
        return JsonResponse.success("上传成功");
    }


}
