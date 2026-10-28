package ar.edu.itba.pod.hz.api;

import java.util.regex.Pattern;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class MaxLengthTokenizerMapper implements Mapper<String, String, String, String> {
    private static final Long ONE = 1L;

    @Override
    public void map(String key, String value, Context<String, String> context) {
        Pattern.compile("\\s+")
            .splitAsStream(value. toLowerCase())
            .filter(word -> word.matches("[a-z]+")) // ej2
            .forEach(word -> context.emit(String.valueOf(word.charAt(0)), word)); // ej2
    }
}


