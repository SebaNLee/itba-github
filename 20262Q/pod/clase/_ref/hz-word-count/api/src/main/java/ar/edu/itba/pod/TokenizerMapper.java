package ar.edu.itba.pod;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.StringTokenizer;

//public class TokenizerMapper implements Mapper<String, String, String, Long> {
//    private static final Long ONE = 1L;
//    @Override
//    public void map(String key, String document, Context<String, Long> context) {
//        StringTokenizer tokenizer = new StringTokenizer(document.toLowerCase());
//        while (tokenizer.hasMoreTokens()) {
//            context.emit(tokenizer.nextToken(), ONE);
//        }
//    }
//}

public class TokenizerMapper implements Mapper<String, String, String, String> {

    @Override
    public void map(String key, String document, Context<String, String> context) {
        StringTokenizer tokenizer = new StringTokenizer(document.toLowerCase());
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            if(word.matches("[a-z]+"))
                context.emit(word.getBytes()[0] + "", word);
        }
    }
}
