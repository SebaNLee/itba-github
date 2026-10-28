package ar.edu.itba.pod.hz.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import ar.edu.itba.pod.hz.api.MaxLengthTokenizerMapper;
import ar.edu.itba.pod.hz.api.WordCountCollator;
import ar.edu.itba.pod.hz.api.WordCountCombinerFactory;
import ar.edu.itba.pod.hz.api.WordCountReducerFactory;
import ar.edu.itba.pod.hz.api.WordCountTokenizerMapper;
import ar.edu.itba.pod.hz.api.First100LinesKeyPredicate;
import ar.edu.itba.pod.hz.api.MaxLengthReducerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		logger.info("hz-word-count Client Starting ...");

		try {
			// Group Config
			GroupConfig groupConfig = new GroupConfig().setName("l64675").setPassword("l64675-pass");

			// Client Network Config
			ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
			clientNetworkConfig.addAddress("127.0.0.1");

			// Client Config
			ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig).setNetworkConfig(clientNetworkConfig);

			// Node Client
			HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

			// Key Value Source
			IMap<String, String> wordsIMap = hazelcastInstance.getMap("words");
			KeyValueSource<String, String> wordsKeyValueSource = KeyValueSource.fromMap(wordsIMap);

			// Job Tracker
			JobTracker jobTracker = hazelcastInstance.getJobTracker("word-count");

			// Text File Reading and Key Value Source Loading
			final AtomicInteger auxKey = new AtomicInteger();
			try (Stream<String> lines = Files.lines(Paths.get(args[0]), StandardCharsets.UTF_8)) {
				lines.forEach(line -> wordsIMap.put(String.valueOf(auxKey.getAndIncrement()), line));
			}

			//
			// Clase 11: Ejercicio 1
			//

			// clase 12, ej1 también, se agrega Predicate, Combiner, Collator

			// MapReduce Job
			Job<String, String> job1 = jobTracker.newJob(wordsKeyValueSource);
			ICompletableFuture<List<Map.Entry<String,Long>>> future1 = job1
					.keyPredicate(new First100LinesKeyPredicate()) // predicate
					.mapper(new WordCountTokenizerMapper())
					.combiner(new WordCountCombinerFactory()) // combiner
					.reducer(new WordCountReducerFactory())
					.submit(new WordCountCollator()); // collator

			// Wait and retrieve the result
			List<Map.Entry<String,Long>> result1 = future1.get();

			// Print directly, sort in the collator
			result1.stream().forEach(IO::println);

			//
			// Clase 11: Ejercicio 2
			//
			Job<String, String> job2 = jobTracker.newJob(wordsKeyValueSource);
			ICompletableFuture<Map<String, String>> future2 = job2
					.mapper(new MaxLengthTokenizerMapper())
					.reducer(new MaxLengthReducerFactory())
					.submit();

			// Wait and retrieve the result
			Map<String, String> result2 = future2.get();

			// Sort entries ascending by count and print
			result2.entrySet().stream()
					.sorted((Map.Entry.comparingByValue()))
					.forEach(IO::println);

			// TODO
		} finally {
			HazelcastClient.shutdownAll();
		}
	}

}