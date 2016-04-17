/**
*@author tyl
*@date Apr 17, 2016 7:14:35 PM
*@email buaatyl@qq.com
**/
package com.tyl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.tyl.util.AnalyzerUtil;


public class TestCN {
	public void testChineseSysAnalyzer() {
        Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);

        String dicUrl = "F:\\stoneforest\\work\\workspace\\lucence_github\\src\\test\\resources\\mydata";
        System.out.println(dicUrl);
        Analyzer a5 = new MMSegAnalyzer(dicUrl);

       // String txt = "我是来自中国山东的小伙子";
        String txt = "VisualSVN Server allows you to easily install and manage a fully-functional Subversion server on the Windows platform. Thanks to its robustness, unbeatable usability and unique enterprise-grade features, VisualSVN Server is useful both for small business and corporate users.";
        AnalyzerUtil.displayToken(txt, a1, "StandardAnalyzer");
        AnalyzerUtil.displayToken(txt, a2, "StopAnalyzer");
        AnalyzerUtil.displayToken(txt, a3, "SimpleAnalyzer");
        AnalyzerUtil.displayToken(txt, a4, "WhitespaceAnalyzer");
        AnalyzerUtil.displayToken(txt, a5, "MMSegAnalyzer");
    }
	public static void main(String[] args){
		new TestCN().testChineseSysAnalyzer();
	}
}
