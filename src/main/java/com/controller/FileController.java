package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.Base64Image;
import com.utils.FFMpegUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class FileController {
    @Value("${netAddress}")
    private String host;
    private final static Logger LOGGER= LoggerFactory.getLogger(FileController.class);
    //提交修改页面 入参  为 Items对象
    @ResponseBody
    @RequestMapping(value = "/updateitem.action",method = RequestMethod.POST)
    public Object updateitem(@RequestParam("myPhoto") MultipartFile pictureFile,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception{

        //跨域处理
        response.setHeader("Access-Control-Allow-Origin", "*");
        HashMap<String,Object> hashMap=new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (pictureFile!=null){
                //保存图片到
                String name = UUID.randomUUID().toString().replaceAll("-", "");
                //jpg
                String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
                /**
                 * 　String path = request.getSession().getServletContext().getRealPath("/");
                 * path值如下：
                 * C:\develop\apache-tomcat-7.0.75\webapps\ROOT\
                 */
                String url = request.getSession().getServletContext().getRealPath("/upload");
                //文件上传储存路径
                String odlerFile=url+"/" + name + "." + ext;
                pictureFile.transferTo(new File(odlerFile));
                //用http协议访问，上传原文件
                String fileHttpUrl=host+name + "." + ext;
                System.out.println(fileHttpUrl);
                //新文件名
                String newFikeKey=UUID.randomUUID().toString();
                //新文件的路径
                String newFilePath=url+"/" + newFikeKey + "." +"mp4";
                //讲文件转换为mp4格式的
                FFMpegUtil.convetor(fileHttpUrl,newFilePath);
                //mp4的文件，http访问路径
                String mp4VideoHttpFileUrl=host+newFikeKey + "." +"mp4";
                //从mp4文件切图
                String jpgName= UUID.randomUUID().toString();
                String coverOutputPath =url+ "/"+jpgName+".jpg";
                try {
                    FFMpegUtil.convetor("00:00:01", newFilePath, 1, coverOutputPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //图片访问的httpUrl
                String pictrueHttpUrl=host+jpgName+".jpg";
                hashMap.put("end","OK");
                hashMap.put("videoUrl",mp4VideoHttpFileUrl);
                hashMap.put("imgUrl",pictrueHttpUrl);
            }else {
                hashMap.put("end","do not find file---pictureFile is null");
            }
        }catch (Exception e){
            hashMap.put("end","upload err");
            e.printStackTrace();
        }

        return mapper.writeValueAsString(hashMap);
    }
    //@CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    @RequestMapping(value = "/postBase64Img.action",method = RequestMethod.POST)
    public Object base64Img(@RequestParam("file1") String base64Data,
                            HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        //处理跨域
        response.setHeader("Access-Control-Allow-Origin", "*");

        HashMap<String,Object> hashMap=new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String dataPrix = "";
            String data = "";

            LOGGER.debug("对数据进行判断");
            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }
            LOGGER.debug("对数据进行解析，获取文件名和流数据");
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = ".jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            String imgName=UUID.randomUUID().toString();
            String tempFileName = imgName + suffix;
            LOGGER.debug("生成文件名为："+tempFileName);
            System.out.println(tempFileName);
            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);
            try{
                File file=null;
                //使用apache提供的工具类操作流
                String url = request.getSession().getServletContext().getRealPath("/upload");
                FileUtils.writeByteArrayToFile(new File(url+"/" + tempFileName), bs);

                String pitureUrl=host+tempFileName;
                System.out.println(pitureUrl);
                //System.out.println(pitureUrl);
                hashMap.put("end","OK");
                hashMap.put("url",pitureUrl);
            }catch(Exception ee){

                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }

        }catch (Exception e){
            hashMap.put("end","upload err");
            e.printStackTrace();
        }
        System.out.println(hashMap.toString());
        System.out.println(mapper.writeValueAsString(hashMap).toString());
        System.out.println(System.currentTimeMillis());
        return mapper.writeValueAsString(hashMap);
    }
}
