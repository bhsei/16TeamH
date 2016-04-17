/**
*@author tyl
*@date Apr 17, 2016 7:18:50 PM
*@email buaatyl@qq.com
**/
package com.tyl.util;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;


public class AnalyzerUtil {
	
	    /**
	     * 展示语汇单元的分词单元信息
	     *
	     * @param str
	     * @param analyzer
	     * @param analyzerName
	     */
	    public static void displayToken(String str, Analyzer analyzer, String analyzerName) {
	        try {
	            // fieldName 暂时可任意
	            TokenStream stream = analyzer.tokenStream("content", new StringReader(str));

	            //创建一个属性，这个属性会添加流中，随着这个TokenStream增加
	            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);

				StringBuilder sb = new StringBuilder();
				sb.append("analyzer=" + analyzerName);
	            while (stream.incrementToken()) {

					sb.append("["+cta+"] , ");
	            }
				System.out.println("分词信息：" + sb.toString());
	        } catch (Exception e) {
	        	System.out.println("展示失败");
	        }
	    }

	    /**
	     * 展示语汇单元所有信息
	     *
	     * @param str
	     * @param analyzer
	     * @param analyzerName
	     */
	    public static void displayAllTokenInfo(String str, Analyzer analyzer, String analyzerName) {
	        try {

	            TokenStream stream = analyzer.tokenStream("content", new StringReader(str));

	            //位置增量的属性，存储语汇单元之间的距离
	            PositionIncrementAttribute pia = stream.addAttribute(PositionIncrementAttribute.class);

	            //每个语汇单元的位置偏移量
	            OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);

	            //存储每一个语汇单元的信息（分词单元信息）
	            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);

	            //使用的分词器的类型信息
	            TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
	            StringBuilder sb = new StringBuilder(analyzerName);
	            for ( ; stream.incrementToken(); ) {
	                sb.append(": pia=" + pia.getPositionIncrement() + ", cta=" + cta + "["+oa.startOffset()+", "+oa.endOffset()+"], " + "type=" + ta.type());
	            }
	            System.out.println(sb);
	        } catch (Exception e) {
	        	System.out.println("展示失败");
	        }
	    }
}
