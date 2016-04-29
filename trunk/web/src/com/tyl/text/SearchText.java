/**
*@author tyl
*@date Apr 19, 2016 4:44:09 PM
*@email buaatyl@qq.com
**/
package com.tyl.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.MinimalHTMLWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.tyl.util.Const;

public class SearchText {
	//private String index = "D:\\apache-tomcat-8.0.14\\webapps\\lucenceWeb_data\\lucenceWeb_text_index";
	public List<Map<String, String>> search(String queryString,String index){
		int hitNumber = 20;
		int retNumber = 100;
		return search(queryString,hitNumber,retNumber,index);
	}
	public List<Map<String, String>> search(String queryString,int hitNumber,int retNumber,String index){
		String field = "contents";		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			//Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
			Analyzer analyzer = new MMSegAnalyzer(Const.cnDataPath);

			
			QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
			queryString = queryString.trim();
			if (queryString.length() == 0) {
				System.out.println("============query 为空");
				searcher.close();
			    reader.close();
		        return null;
		    }
			Query query = parser.parse(queryString);
			TopDocs results = searcher.search(query, hitNumber);
			ScoreDoc[] hits = results.scoreDocs;
		    
		    int numTotalHits = results.totalHits;
		    
		    hits = searcher.search(query, numTotalHits).scoreDocs;
		    
		    retNumber =  Math.min(retNumber,hits.length);
		    
		    
		    
		    for(int i = 0; i < retNumber;i++){
		    	Map<String,String> map = new HashMap<String, String>();
		    	Document doc = searcher.doc(hits[i].doc);
		        String path = doc.get("path");
		        if (path != null) {
		          System.out.println((i+1) + ". " + path + "  hits[i].score"+hits[i].score);
		          map.put("name", path);
		          map.put("score", hits[i].score+"");		          
		          String title = doc.get("title");
		          if (title != null) {
		            System.out.println("   Title: " + doc.get("title"));
		          }
		          list.add(map);
		        }
		    }
		    searcher.close();
		    reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}
	public static void main(String[] a){
		new SearchText().search("一灯","D:\\apache-tomcat-8.0.14\\webapps\\lucenceWeb_data\\lucenceWeb_text_index");
	}
}
