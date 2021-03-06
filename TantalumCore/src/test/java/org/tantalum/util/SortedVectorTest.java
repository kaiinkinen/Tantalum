/*
 Copyright (c) 2013, Paul Houghton and Futurice Oy
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 - Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 - Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.tantalum.util;

import org.junit.Before;
import org.junit.Test;
import org.tantalum.MockedStaticInitializers;

import static org.junit.Assert.*;

/**
 * User: kink
 * Date: 2013.02.19
 * Time: 00:09
 */
public class SortedVectorTest extends MockedStaticInitializers {

    private SortedVector collection;

    @Before
    public final void fixtureSortedVectorTest() {
        collection = new SortedVector(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Integer int1 = (Integer) o1;
                Integer int2 = (Integer) o2;

                return int1 - int2;
            }
        });
    }

    @Test
    public void collectionIsInitiallyEmpty() {
        assertTrue(collection.isEmpty());
    }

    @Test
    public void collectionMaintainsSortOrder() {
        collection.addElement(2);
        collection.addElement(1);

        assertEquals(1, collection.firstElement());
        assertEquals(2, collection.lastElement());
    }

    @Test
    public void duplicatesAreAllowed() {
        Integer one = new Integer(1);
        Integer two = new Integer(1);

        assertNotSame(one, two);
        assertEquals(one, two);

        collection.addElement(one);
        collection.addElement(two);

        assertEquals(2, collection.size());
    }

    @Test
    public void integerSequenceOrderIsCorrectTest() {
        Object o_1 = new Integer(10);
        Object o_2 = new Integer(20);
        Object o_3 = new Integer(50);
        Object o_4 = new Integer(40);
        Object o_5 = new Integer(30);

        System.out.println("Collection now " + collection);
        collection.addElement(o_1);
        System.out.println("Collection now " + collection);
        collection.addElement(o_2);
        System.out.println("Collection now " + collection);
        collection.addElement(o_3);
        System.out.println("Collection now " + collection);
        collection.addElement(o_4);
        System.out.println("Collection now " + collection);
        collection.addElement(o_5);
        System.out.println("Collection now " + collection);

        Integer[] expected = {new Integer(10), new Integer(20), new Integer(30), new Integer(40), new Integer(50)};
        for (int i = 0; i < collection.size(); i++) {
            assertEquals("sequence test " + (i + 1), expected[i], (Integer) collection.elementAt(i));
        }
    }
    
    /**
     * Test of testInsertElementAt method, of class SortedVector.
     */
    @Test
    public void insertElementAtTest() {
        System.out.println("insertElementAt");
        SortedVector instance = new SortedVector(new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer) o1).intValue() - ((Integer) o2).intValue();
            }
        });
        Object o_1 = null;
        int index_1 = 0;
        try {
            instance.insertElementAt(o_1, index_1);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("testInsertElementAt() should throw an exception.");
    }

    /**
     * Test of testSetElementAt method, of class SortedVector.
     */
    @Test
    public void setElementAtTest() {
        System.out.println("setElementAt");
        SortedVector instance = new SortedVector(new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer) o1).intValue() - ((Integer) o2).intValue();
            }
        });
        Object o_1 = null;
        int index_1 = 0;
        try {
            instance.setElementAt(o_1, index_1);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("testSetElementAt() should throw an exception.");
    }

    /**
     * Test of testAddElement method, of class SortedVector.
     */
    @Test
    public void addElementTest() {
        System.out.println("addElement");
        SortedVector instance = new SortedVector(new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer) o1).intValue() - ((Integer) o2).intValue();
            }
        });
        Object o_1 = new Integer(10);
        Object o_2 = new Integer(1);
        Object o_3 = new Integer(3);
        instance.addElement(o_1);
        instance.addElement(o_2);
        instance.addElement(o_3);
        assertEquals("First", o_2, instance.elementAt(0));
        assertEquals("Second", o_3, instance.elementAt(1));
        assertEquals("Third", o_1, instance.elementAt(2));
    }

    /**
     * Test an error discovered by air-dex to see it does not re-surface
     * <p/>
     * http://projects.developer.nokia.com/Tantalum/ticket/10
     */
    @Test
    public void sequenceTest() {
        System.out.println("testSequence");
        SortedVector instance = new SortedVector(new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer) o1).intValue() - ((Integer) o2).intValue();
            }
        });
        Object o_1 = new Integer(10);
        Object o_2 = new Integer(20);
        Object o_3 = new Integer(50);
        Object o_4 = new Integer(40);
        Object o_5 = new Integer(30);
        instance.addElement(o_1);
        instance.addElement(o_2);
        instance.addElement(o_3);
        instance.addElement(o_4);
        instance.addElement(o_5);

        Integer[] expected = {new Integer(10), new Integer(20), new Integer(30), new Integer(40), new Integer(50)};
        for (int i = 0; i < instance.size(); i++) {
            assertEquals("sequence test " + (i + 1), expected[i], (Integer) instance.elementAt(i));
        }
    }
}
