/**
*@author tyl
*@date Apr 18, 2016 6:50:16 PM
*@email buaatyl@qq.com
**/
package com.tyl.image;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.document.Document;

public class SimpleImageSearchHits {
	int maximumHits = 2500;
	float maxDistance = -1;
    ArrayList<SimpleResult> results;

    public SimpleImageSearchHits(Collection<SimpleResult> results, float maxDistance,int maximumHits) {
        this.maximumHits = maximumHits;
    	this.results = new ArrayList<SimpleResult>(results.size());
        this.results.addAll(results);
        if(maxDistance > this.maxDistance) {
        	this.maxDistance = maxDistance; 
        }
    }
    /**
     * Returns the size of the result list.
     *
     * @return the size of the result list.
     */
    public int length() {
        return results.size();
    }

    /**
     * Returns the score of the document at given position.
     * Please note that the score in this case is a distance,
     * which means a score of 0 denotes the best possible hit.
     * The result list starts with position 0 as everything
     * in computer science does.
     *
     * @param position defines the position
     * @return the score of the document at given position. The lower the better (its a distance measure).
     */
    public float score(int position) {
        return results.get(position).getDistance();
    }

    /**
     * Returns the document at given position
     *
     * @param position defines the position.
     * @return the document at given position.
     */
    public Document doc(int position) {
        return results.get(position).getDocument();
    }
    public void Merge(SimpleImageSearchHits data) {
    	for(int j=0; j<data.length(); j++) {
    		int i = 0;
    		for(i=0;i<length();i++)
    			if(score(i) > data.score(j))
    				break;
    		SimpleResult element = new SimpleResult(data.score(j), data.doc(j));
    		results.add(i, element);
    	}
    	for(int i = maximumHits; i < results.size();) {
    		results.remove(i);
    	}
    	if (results.size() > 0) {
    		maxDistance = results.get(length()-1).getDistance();
    	}
    }
    public float getDistance(double[] a, double[] b){
    	double Result = 0;
    	double Temp1 = 0;
    	double Temp2 = 0;
    	double TempCount1 = 0, TempCount2 = 0, TempCount3 = 0;
    	 if ((a.length != b.length))
	            throw new UnsupportedOperationException("Histogram lengths or color spaces do not match");
    	for (int i = 0; i < a.length; i++) {
            Temp1 += a[i];
            Temp2 += b[i];
        }
    	if (Temp1 == 0 || Temp2 == 0) Result = 100;
    	
        if (Temp1 == 0 && Temp2 == 0) Result = 0;
        if (Temp1 > 0 && Temp2 > 0) {
            for (int i = 0; i < a.length; i++) {
                TempCount1 += (a[i] / Temp1) * (b[i] / Temp2);
                TempCount2 += (b[i] / Temp2) * (b[i] / Temp2);
                TempCount3 += (a[i] / Temp1) * (a[i] / Temp1);

            }

            Result = (100 - 100 * (TempCount1 / (TempCount2 + TempCount3
                    - TempCount1))); //Tanimoto
        }
    	return (float)  Result;
    }
    
}
