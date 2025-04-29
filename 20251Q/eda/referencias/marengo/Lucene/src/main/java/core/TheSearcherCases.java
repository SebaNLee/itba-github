package core;

import org.apache.lucene.search.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;


public class TheSearcherCases {
	
	private static IndexReader getIndexReader() throws IOException {
		
		// target index directory
		Directory indexDir = FSDirectory.open( Paths.get(Utils.getPrefixDir() + "/index/"));
		
		return DirectoryReader.open( indexDir );
		
	}

	private static void runQuery( IndexSearcher searcher, Query query )
	{
		try {
		long startTime = System.currentTimeMillis();
		TopDocs topDocs = searcher.search(query, 20);
		long endTime = System.currentTimeMillis();

		// show the resultset

		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++");
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

	/*NO es una buena practica usar variables como query1 query2, etc en el codigo porque es muy
	* facil en el copy-paste de un caso al otro que queden mal los prefijos numericos.
	* Lo correcto es hacer un metodo como TermQueryCase y llamarlo con:
	*
	* TermQueryCase( searcher, "content", "game" );
	*
	* En estos ejemplos esta hecho con query1, query2, etc. porque para esta clase
	* se pierde un poco lo que queremos ver que es como se arma el query en cada caso.
	* */

	private static void TermQueryCase( IndexSearcher searcher, String fieldName, String text ){
		Query query= new TermQuery( new Term(fieldName, text) );
		runQuery( searcher, query );
	}


	private static void TermQueryCases( IndexSearcher searcher )
	{
		try {
		System.out.println("*****************************************************");
		System.out.println("********************** TERM QUERIES *****************");
		System.out.println("*****************************************************");

		Query query1= new TermQuery( new Term("content", "game") );
		runQuery( searcher, query1 );

		Query query2= new TermQuery( new Term("content", "Game") );
		runQuery( searcher, query2 );

		Query query3= new TermQuery( new Term("content", "ga") );
		runQuery( searcher, query3 );

		Query query4= new TermQuery( new Term("path", "game") );
		runQuery( searcher, query4 );
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

			Query query1= new PrefixQuery( new Term("content", "game") );
			runQuery( searcher, query1 );

			Query query2= new PrefixQuery( new Term("content", "ga") );
			runQuery( searcher, query2 );

			Query query3= new PrefixQuery( new Term("content", "Ga") );
			runQuery( searcher, query3 );

			Query query4= new PrefixQuery( new Term("content", "me") );
			runQuery( searcher, query4 );
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

			Query query1= new TermRangeQuery( "content",
					new BytesRef("gam" ), new BytesRef("gum" ),
					true, true );
			runQuery( searcher, query1 );

			Query query2= new TermRangeQuery( "content",
					new BytesRef("game" ), new BytesRef("game" ),
					true, true );
			runQuery( searcher, query2 );

			Query query3= new TermRangeQuery( "content",
					new BytesRef("game" ), new BytesRef("game" ),
					true, false );
			runQuery( searcher, query3 );

			Query query4= new TermRangeQuery( "content",
					new BytesRef("gum" ), new BytesRef("gam" ),
					true, true );
			runQuery( searcher, query4 );

			Query query5= new TermRangeQuery( "content",
					new BytesRef("game" ), new BytesRef("gum" ),
					false, true );
			runQuery( searcher, query5 );

			Query query6= new TermRangeQuery( "content",
					new BytesRef("gaming" ), new BytesRef("gum" ),
					false, false );
			runQuery( searcher, query6 );
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

			Query query1= new PhraseQuery( "content", "store", "game");
			runQuery( searcher, query1 );

			Query query2= new PhraseQuery( "content", "store,,", "game");
			runQuery( searcher, query2 );

			Query query3= new PhraseQuery( "content", "game", "store");
			runQuery( searcher, query3 );

			Query query4= new PhraseQuery( "content", "store game");
			runQuery( searcher, query4 );

			Query query5= new PhraseQuery( "content", "store,, game");
			runQuery( searcher, query5 );

			Query query6= new PhraseQuery( "content", "game", "review");
			runQuery( searcher, query6 );

			Query query7= new PhraseQuery( "content", "game", "video", "game");
			runQuery( searcher, query7 );

			Query query8= new PhraseQuery( "content", "game", "video", "review");
			runQuery( searcher, query8 );
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

			Query query1= new WildcardQuery( new Term("content", "g*e") );
			runQuery( searcher, query1 );

			Query query2= new WildcardQuery( new Term("content", "g?me") );
			runQuery( searcher, query2 );

			Query query3= new WildcardQuery( new Term("content", "g?m") );
			runQuery( searcher, query3 );

			Query query4= new WildcardQuery( new Term("content", "G??e") );
			runQuery( searcher, query4 );

			Query query5= new WildcardQuery( new Term("content", "g??e") );
			runQuery( searcher, query5 );

			Query query6= new WildcardQuery( new Term("content", "*") );
			runQuery( searcher, query6 );
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

			Query query1= new FuzzyQuery( new Term("content", "gno") );
			runQuery( searcher, query1 );

			Query query2= new FuzzyQuery( new Term("content", "gem") );
			runQuery( searcher, query2 );

			Query query3= new FuzzyQuery( new Term("content", "agem") );
			runQuery( searcher, query3 );

			Query query4= new FuzzyQuery( new Term("content", "hm") );
			runQuery( searcher, query4 );

			Query query5= new FuzzyQuery( new Term("content", "ham") );
			runQuery( searcher, query5 );

			Query query6= new FuzzyQuery( new Term("content", "agmw") );
			runQuery( searcher, query6 );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
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

			index.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    

}