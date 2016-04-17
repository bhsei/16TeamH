/**
*@author tyl
*@date Apr 17, 2016 8:32:05 PM
*@email buaatyl@qq.com
**/
package com.tyl.image;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;








import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.tyl.image.feature.CEDD;
import com.tyl.image.feature.LireFeature;
import com.tyl.util.Const;

public class IndexImages {

	public static void main(String[] args) {
		String docsPath = "F:\\stoneforest\\work\\workspace\\luc\\images";
		File docDir = new File(docsPath);
		Directory dir = null;
		try {
			dir = FSDirectory.open(new File("imageIndex"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		Analyzer analyzer = new MMSegAnalyzer(Const.cnDataPath);
	     
	      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31, analyzer);
	      if (1==1) {
	        // Create a new index in the directory, removing any
	        // previously indexed documents:
	        iwc.setOpenMode(OpenMode.CREATE);
	      } else {
	        // Add new documents to an existing index:
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	      }

	      // Optional: for better indexing performance, if you
	      // are indexing many documents, increase the RAM
	      // buffer.  But if you do this, increase the max heap
	      // size to the JVM (eg add -Xmx512m or -Xmx1g):
	      //
	      // iwc.setRAMBufferSizeMB(256.0);

	      try {
			IndexWriter writer = new IndexWriter(dir, iwc);
			String[] files = docDir.list();
			for (int i = 0; i < files.length; i++) {
	            indexDocs(writer, new File(docDir, files[i]));
	        }
			//indexDocs(writer, docDir);

			  // NOTE: if you want to maximize search performance,
			  // you can optionally call forceMerge here.  This can be
			  // a terribly costly operation, so generally it's only
			  // worth it when your index is relatively static (ie
			  // you're done adding documents to it):
			  //
			  // writer.forceMerge(1);

			  writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void indexDocs(IndexWriter writer,File file){
		
		BufferedImage bimg;
		try {
			bimg = ImageIO.read(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
	    LireFeature lireFeature = null;
	    Class descriptorClass = CEDD.class;


	    try {
			lireFeature = (LireFeature) descriptorClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    lireFeature.extract(bimg);
	   // float[] f = lireFeature.getFloatFeatureVector();
	    String f = lireFeature.getStringRepresentation();
		try {
			//DocumentBuilder builder = DocumentBuilderFactory.getCEDDDocumentBuilder();// .getFullDocumentBuilder();
			Document doc = new Document();

			// Add the path of the file as a field named "path".  Use a
			// field that is indexed (i.e. searchable), but don't tokenize 
			// the field into separate words and don't index term frequency
			// or positional information:
			doc.add(new Field("featureCEDD", f, Field.Store.YES, Field.Index.NO));
			doc.add(new Field("descriptorImageIdentifier",file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			//Field pathField = new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
			//pathField.setIndexOptions(IndexOptions.DOCS_ONLY);
			//doc.add(pathField);
			
			//doc.add(new Field("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
			
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
			  // New index, so we just add the document (no old document can be there):
			  System.out.println("adding " + file);
			  writer.addDocument(doc);
			} else {
			  // Existing index (an old copy of this document may have been indexed) so 
			  // we use updateDocument instead to replace the old one matching the exact 
			  // path, if present:
			  System.out.println("updating " + file);
			  writer.updateDocument(new Term("path", file.getPath()), doc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
