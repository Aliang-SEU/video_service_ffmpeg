package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: mac
 * @Date: 2019-05-18 12:04
 * @Description: 沈泽鹏写点注释吧
 */
public class FFMpegUtil {
//Windows下 ffmpeg.exe的路径
// private static String ffmpegEXE = "D:\\Downloads\\ffmpeg-20180528-ebf85d3-win64-static\\bin\\ffmpeg.exe";

    //Linux与mac下  ffmpeg的路径
    private static String ffmpegEXE = "/usr/local/Cellar/ffmpeg/4.1.3_1/bin/ffmpeg";


    /**
     * @param videoInputPath 视频的输入路径
     * @param videoOutPath   视频的输出路径
     * @throws Exception
     */
    // 拷贝视频，并指定新的视频的名字以及格式
    // ffmpeg.exe -i old.mp4 new.avi
    public static void convetor(String videoInputPath, String videoOutPath) throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

    /**
     * @param videoInputPath 原视频的路径
     * @param audioInputPath 音频的路径
     * @param videoOutPath   视频与音频结合之后的视频的路径
     * @param time           视频的长度 ,单位为 s
     * @throws Exception
     */
    // 将视频和音频结合，并指定视频的长度，同时生成结合之后的视频文件
    // ffmpeg.exe -i tsd.mp4 -i "周笔畅+-+最美的期待.mp3" -t 7 -y new.avi
    public static void convetor(String videoInputPath, String audioInputPath, String videoOutPath, double time)
            throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(audioInputPath);
        command.add("-t");
        command.add(String.valueOf(time));
        command.add("-y");
        command.add(videoOutPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }


    /**
     * @param time_coverimg   视频的第几秒作为封面图
     * @param videoInputPath  视频的路径
     * @param frame           帧数
     * @param coverOutputPath 视频的封面图的路径
     * @throws Exception
     */
    // ffmpeg.exe -ss 00:00:01 -y -i 视频.mp4 -vframes 1 new.jpg
    public static void convetor(String time_coverimg, String videoInputPath, int frame, String coverOutputPath)
            throws Exception {

        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-ss");
        command.add(time_coverimg);
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-vframes");
        command.add(String.valueOf(frame));
        command.add(coverOutputPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }

 public static void main(String[] args) {
    String videoInputPath = "/Users/mac/Desktop/DB765156634172168249B3491B5B1E99.mp4";
    String jpgName= UUID.randomUUID().toString();
    String coverOutputPath = "/Users/mac/Desktop/"+jpgName+".jpg";
    try {
       convetor("00:00:01", videoInputPath, 1, coverOutputPath);
    } catch (Exception e) {
       e.printStackTrace();
    }
 }

}
