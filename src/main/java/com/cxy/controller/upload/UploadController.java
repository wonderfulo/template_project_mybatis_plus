package com.cxy.controller.upload;

import com.cxy.common.JsonResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @program: template_project_mybatis_plus
 * @description: 上传控制器
 * @author: 陈翔宇
 * @create_time: 2020-11-17 20:14:14
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/file")
    public JsonResponse<String> file(MultipartFile file) {
        if (file.isEmpty()) {
            return JsonResponse.fail("上传失败，请选择文件");
        }
        String filename = file.getOriginalFilename();
//        String filePath = "E:" + File.separator + "springboot" + File.separator + "image" + File.separator;
        String filePath = "D:" + File.separator + "springboot" + File.separator + "image" + File.separator;
        File dest = new File(filePath + filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResponse.fail("上传失败");
        }
        return JsonResponse.success("上传成功");
    }


    @PostMapping("/multiUpload")
    public String multiUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String filePath = "E:" + File.separator + "springboot" + File.separator + "image" + File.separator;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                return "上传第" + (i++) + "个文件失败";
            }
        }

//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
////        List<MultipartFile> fileList = multipartRequest.getFiles("file");
//        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//        if(CollectionUtils.isEmpty(fileMap)){
//            return "请上传文件,注意文件的name属性为file";
//        }
//        Collection<MultipartFile> files = fileMap.values();
//        for(MultipartFile file:files){
//            System.out.println(file.getName());
//        }

        return "上传成功";

    }
}
