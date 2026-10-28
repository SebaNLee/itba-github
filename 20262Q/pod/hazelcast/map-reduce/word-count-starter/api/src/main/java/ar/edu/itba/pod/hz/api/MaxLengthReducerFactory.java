package ar.edu.itba.pod.hz.api;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class MaxLengthReducerFactory implements ReducerFactory<String, String, String>{
    @Override 
    public Reducer<String, String> newReducer(String key) {
        return new WordCountReducer();
    }

    private static class WordCountReducer extends Reducer<String, String> {
        private String longest = "";

        @Override 
        public void reduce(String value) {
            if (value.length() > longest.length()) { // TODO demás restricciones
                longest = value;
            }
        }

        @Override 
        public String finalizeReduce(){
            return longest;
        }
    }
}
