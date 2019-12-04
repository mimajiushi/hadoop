package T1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class HDFSFileIfExist {
        public static void main(String[] args){
            try{
                // 配置了环境变量就不需要这条了
                // System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.5.0-cdh5.3.6");
                String fileName = "gsdsdfsdf";
                Configuration conf = new Configuration();
                URI uri = URI.create("hdfs://hserver2:8020");
                FileSystem fs = FileSystem.get(uri,conf, "root");
                if(fs.exists(new Path(fileName))){
                    System.out.println("文件存在");
                }else{
                    System.out.println("文件不存在");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
}

