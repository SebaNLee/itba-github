package ar.edu.itba.pod;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

//public class WordCountReducerFactory implements ReducerFactory<String, Long, Long> {
//    @Override
//    public Reducer<Long, Long> newReducer(String key) {
//        return new WordCountReducer();
//    }
//    private class WordCountReducer extends Reducer<Long, Long> {
//        private Long sum = 0L;
//        @Override
//        public void reduce(Long value) {
//            sum += value;
//        }
//        @Override
//        public Long finalizeReduce() {
//            return sum;
//        }
//    }}

public class WordCountReducerFactory implements ReducerFactory<String, String, String> {
    @Override
    public Reducer<String, String> newReducer(String key) {
        return new WordCountReducer();
    }

    private class WordCountReducer extends Reducer<String, String> {
        private String longest = "";

        @Override
        public void reduce(String value) {
            if(value.length() > longest.length())
                longest = value;
        }

        @Override
        public String finalizeReduce() {
            return longest;
        }
    }
}
