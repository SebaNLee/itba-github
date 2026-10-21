package ar.edu.itba.pod.hz.api;

import java.util.regex.Pattern;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TokenizerMapper implements Mapper<String, String, String, Long> {
    private static final Long ONE = 1L;

    @Override
    public void map(String key, String value, Context<String, Long> context) {
        Pattern.compile("\\s+")
            .splitAsStream(value. toLowerCase())
            .forEach(word -> context.emit(word, ONE));
    }
}


