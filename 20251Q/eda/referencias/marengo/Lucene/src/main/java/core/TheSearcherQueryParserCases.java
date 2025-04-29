package core;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


import java.io.IOException;
import java.nio.file.Paths;


public class TheSearcherQueryParserCases {

	private static void runQuery( IndexSearcher searcher, String queryStr )
	{
		try {
			QueryParser queryparser = new QueryParser(null, new StandardAnalyzer() );
			Query query= queryparser.parse(queryStr);

			long startTime = System.currentTimeMillis();
			TopDocs topDocs = searcher.search(query, 20);
			long endTime = System.currentTimeMillis();

			// show the resultset

			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(String.format("QueryStr=> %s", queryStr));
			System.out.println(String.format("Query=> %s\n", query));
			System.out.println(String.format("%d topDocs documents found in %d ms.\n", topDocs.totalHits,
					(endTime - startTime) ) );

			ScoreDoc[] orderedDocs = topDocs.scoreDocs;

			int position= 1;
			System.out.println("Resultset=> \n");

			for (ScoreDoc aD : orderedDocs) {

				// print info about finding
				int docID= aD.doc;
				double score = aD.score;
				System.out.println(String.format("position=%-10d  score= %10.7f", position, score ));

				// print docID, score
				System.out.println(aD);

				// obtain ALL the stored fields
				Document aDoc = searcher.doc(docID);
				System.out.println("Store values " + aDoc);

/*				System.out.println( aDoc.get("path") );
				System.out.println( aDoc.get("content") );*/

/*				System.out.println("Explanation");
				Explanation rta = searcher.explain(query, docID);
	            System.out.println(rta);*/

				position++;
				System.out.println();
			}

		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		System.out.println("*****************************************************");
	}

	private static void TermQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("********************** TERM QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:game" );

			runQuery( searcher, "content:Game" );

			runQuery( searcher, "content:ga" );

			runQuery( searcher, "path:game" );

			runQuery( searcher, "content:game,," );

		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void PrefixQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("******************** PREFIX QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:ga*" );

			runQuery( searcher, "content:game*" );

			runQuery( searcher, "content:Ga*" );

			runQuery( searcher, "path:me*" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void TermRangeQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("**************** TERM RANGE QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:[gam TO gum]" );

			runQuery( searcher, "content:[game TO game]" );

			runQuery( searcher, "content:[game TO game}" );

			runQuery( searcher, "content:[gum TO gam]" );

			runQuery( searcher, "content:{game TO gum]" );

			runQuery( searcher, "content:{gaming TO gum}" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void PhraseQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("******************** PHRASE QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:\"store game\"" );

			runQuery( searcher, "content:\"store,, game\"" );

			runQuery( searcher, "content:\"game store\"" );

			runQuery( searcher, "content:\"game review\"" );

			runQuery( searcher, "content:\"game video game\"" );

			runQuery( searcher, "content:\"game video review\"" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void WildcardQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("****************** WILDCARD QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:s?or*" );

			runQuery( searcher, "content:g*e" );

			runQuery( searcher, "content:g?me" );

			runQuery( searcher, "content:g?m" );

			runQuery( searcher, "content:G??e" );

			//runQuery( searcher, "content:*" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void FuzzyQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("********************* FUZZY QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:gno~2" );

			runQuery( searcher, "content:agen~2" );

			runQuery( searcher, "content:agem~2" );

			runQuery( searcher, "content:hm~2" );

			runQuery( searcher, "content:ham~2" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void BooleanQueryCases( IndexSearcher searcher )
	{
		try {
			System.out.println("*****************************************************");
			System.out.println("******************* BOOLEAN QUERIES *****************");
			System.out.println("*****************************************************");

			runQuery( searcher, "content:store OR content:game" );

			runQuery( searcher, "content:store content:game" );

			runQuery( searcher, "content:store || content:game" );

			runQuery( searcher, "content:store AND content:game" );

			runQuery( searcher, "content:store && content:game" );

			runQuery( searcher, "content:review OR (content:game AND NOT content:yo)" );

			runQuery( searcher, "(content:review OR content:game) AND NOT content:yo" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}



	private static IndexReader getIndexReader() throws IOException {
		
		// target index directory
		Directory indexDir = FSDirectory.open( Paths.get(Utils.getPrefixDir() + "/index/"));
		
		return DirectoryReader.open( indexDir );
		
	}


    public static void main( String[] args ) {
    	
        try {
           	IndexReader index = getIndexReader();
        	IndexSearcher searcher= new IndexSearcher(index);
        	searcher.setSimilarity(new ClassicSimilarity());

			TermQueryCases( searcher );

			PrefixQueryCases( searcher );

			TermRangeQueryCases( searcher );

			PhraseQueryCases( searcher );

			WildcardQueryCases( searcher );

			FuzzyQueryCases( searcher );

			BooleanQueryCases( searcher );

			index.close();
        } 
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    

}