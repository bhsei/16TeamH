/**
*@author tyl
*@date Apr 17, 2016 10:42:02 PM
*@email buaatyl@qq.com
**/
package com.tyl.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;




import com.tyl.image.feature.CEDD;
import com.tyl.image.feature.LireFeature;

public class SearchImages {
	private TreeSet<SimpleResult> docs;
	private int maxHits = 20;
	public SearchImages(){
		docs = new TreeSet<SimpleResult>();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String index = "imageIndex";
		String reseachPath = "1.jpg";
		int maxHits = 20;
		try {
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)));
			File file = new File(reseachPath);
			BufferedImage bimg;
			try {
				bimg = ImageIO.read(file);
				SimpleImageSearchHits sish = new SearchImages().search(bimg, reader);
				System.out.println(sish.length() + " sish.length() ");
				for(int i = 0; i < sish.length(); i++){
					Document d = sish.doc(i);
					System.out.println(sish.score(i));
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}			
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param bimg ,the image buffer of searched picture
	 * @param reader, index reader 
	 * @return result hits
	 */
	public SimpleImageSearchHits search(BufferedImage bimg, IndexReader reader){
		LireFeature lireFeature = null;
		
		SimpleImageSearchHits searchHits = null;
		try {
			lireFeature = (LireFeature) CEDD.class.newInstance();
			lireFeature.extract(bimg);
			float maxDistance = findSimilar(reader, lireFeature);
			searchHits = new SimpleImageSearchHits(this.docs, maxDistance,this.maxHits);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchHits;
	}
	private float findSimilar(IndexReader reader, LireFeature lireFeature) throws IOException {
        float maxDistance = -1f, overallMaxDistance = -1f;
        boolean hasDeletions = reader.hasDeletions();

        // clear result set ...
        docs.clear();
        int docs = reader.numDocs() + reader.numDeletedDocs();
        for (int i = 0; i < docs; i++) {
            // bugfix by Roman Kern
            if (hasDeletions && reader.isDeleted(i)) {
                continue;
            }

            Document d = reader.document(i);
            float distance = getDistance(d, lireFeature);
            assert (distance >= 0);
            // calculate the overall max distance to normalize score afterwards
            if (overallMaxDistance < distance) {
                overallMaxDistance = distance;
            }
            // if it is the first document:
            if (maxDistance < 0) {
                maxDistance = distance;
            }
            // if the array is not full yet:
            if (this.docs.size() < maxHits) {
                this.docs.add(new SimpleResult(distance, d));
                if (distance > maxDistance) maxDistance = distance;
            } else if (distance < maxDistance) {
                // if it is nearer to the sample than at least on of the current set:
                // remove the last one ...
                this.docs.remove(this.docs.last());
                // add the new one ...
                this.docs.add(new SimpleResult(distance, d));
                // and set our new distance border ...
                maxDistance = this.docs.last().getDistance();
            }
        }
        return maxDistance;
    }
	  private float getDistance(Document d, LireFeature lireFeature) {
	        float distance = 0f;
	        LireFeature lf;
	        try {
	            lf = (LireFeature) CEDD.class.newInstance();
	            String[] cls = d.getValues("CEDD");
	            if (cls != null && cls.length > 0) {
	                lf.setStringRepresentation(cls[0]);

	                distance = lireFeature.getDistance(lf);
	            } else {
	                //logger.warning("No feature stored in this document!");
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 
	        
            
	        System.out.println("getDistance(Document d, LireFeature lireFeature) " + distance);
	        return distance;
	    }

}
