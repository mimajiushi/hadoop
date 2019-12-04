package T1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCount {
    public WordCount() {
    }

    public static boolean rm(Configuration conf, Path output) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        boolean result = fs.delete(output, false);
        fs.close();
        System.out.println("删除输出目录完成");
        return result;
    }

    /**
     * 删除目录
     */
    public static boolean rmDir(Configuration conf, Path remoteDir, boolean recursive) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        /* 第二个参数表示是否递归删除所有文件 */
        boolean result = fs.delete(remoteDir, recursive);
        fs.close();
        return result;
    }

    public static void main(String[] args) throws Exception {
//        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.5.0-cdh5.3.6");
        Configuration conf = new Configuration();
//        String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
//        if(otherArgs.length < 2) {
//            System.err.println("Usage: wordcount <in> [<in>...] <out>");
//            System.exit(2);
//        }

        Job job = Job.getInstance(conf, "wordcount");
//        job.setJarByClass(WordCount.class);
        job.setJar("D:\\javaweb\\hadoop\\WordCount\\target\\WordCount-1.0-SNAPSHOT.jar");
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//        for(int i = 0; i < otherArgs.length - 1; ++i) {
//            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
            FileInputFormat.addInputPath(job, new Path("/user/hadoopClass/input"));
//        }


        Path output = new Path("/user/hadoopClass/output");
        rmDir(conf, output, true);
        FileOutputFormat.setOutputPath(job, output);
        System.exit(job.waitForCompletion(true)?0:1);
    }

}
