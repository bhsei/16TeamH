/**
*@author tyl
*@date Apr 18, 2016 6:44:33 PM
*@email buaatyl@qq.com
**/
package com.tyl.image;

import org.apache.lucene.document.Document;


public class SimpleResult implements Comparable {
    private float distance;
    private Document document;

    public SimpleResult(float distance, Document document) {
        this.distance = distance;
        this.document = document;
    }

    public float getDistance() {
        assert(distance>=0);
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int compareTo(Object o) {
        if (!(o instanceof SimpleResult)) {
            return 0;
        } else {
            int compareValue = (int) Math.signum(distance - ((SimpleResult) o).distance);
            if (compareValue == 0 && !(document.equals(((SimpleResult) o).document)))
                compareValue = document.hashCode() - ((SimpleResult) o).document.hashCode();
            return compareValue;
        }
    }
}