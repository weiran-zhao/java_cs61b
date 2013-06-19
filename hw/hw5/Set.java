/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elementsList stored in sorted order.
 *  Duplicate elementsList are not permitted in a Set.
 **/
public class Set {
    /* Fill in the data fields here. */
    private List elementsList;          // store elementsList of a set


    /**
     * Set ADT invariants:
     *  1)  The Set's elementsList must be precisely the elementsList of the List.
     *  2)  The List must always contain Comparable elementsList, and those elementsList 
     *      must always be sorted in ascending order.
     *  3)  No two elementsList in the List may be equal according to compareTo().
     **/

    /**
     *  Constructs an empty Set. 
     *
     *  Performance:  runs in O(1) time.
     **/
    public Set() { 
        // Your solution here.
        elementsList = new DList();
    }

    /**
     *  cardinality() returns the number of elementsList in this Set.
     *
     *  Performance:  runs in O(1) time.
     **/
    public int cardinality() {
        return elementsList.length();
    }

    /**
     *  insert() inserts a Comparable element into this Set.
     *
     *  Sets are maintained in sorted order.  The ordering is specified by the
     *  compareTo() method of the java.lang.Comparable interface.
     *
     *  Performance:  runs in O(this.cardinality()) time.
     **/
    public void insert(Comparable c) {
        // Your solution here.
        // null is not comparable
        if(c==null) {
            return;
        }
        // there's no element in the list
        if(elementsList.length()==0) {
            elementsList.insertFront(c);
            return;
        }
        ListNode walker= elementsList.front();
        try {
            while(walker.isValidNode()) {
                int sign=c.compareTo(walker.item());
                if(sign==0){
                    return;
                } else if (sign<0) {
                    walker.insertBefore(c);
                    return;
                } else {
                    walker=walker.next();
                }
            }
            // this is the biggest element
            elementsList.insertBack(c);
        }
        catch(InvalidNodeException e) {
            System.err.println(e);
        }
    }

    /**
     *  union() modifies this Set so that it contains all the elementsList it
     *  started with, plus all the elementsList of s.  The Set s is NOT modified.
     *  Make sure that duplicate elementsList are not created.
     *
     *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     *
     *  Your implementation should NOT copy elementsList of s or "this", though it
     *  will copy _references_ to the elementsList of s.  Your implementation will
     *  create new _nodes_ for the elementsList of s that are added to "this", but
     *  you should reuse the nodes that are already part of "this".
     *
     *  DO NOT MODIFY THE SET s.
     *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
     **/
    public void union(Set s) {
        ListNode p1 = this.elementsList.front();
        ListNode p2 = s.elementsList.front();
        Comparable c1, c2;
        int sign;
        try {
            while((p1.isValidNode()) && (p2.isValidNode())) {
                c1 = (Comparable)(p1.item());
                c2 = (Comparable)(p2.item());
                sign = c1.compareTo(c2);
                if(sign==0) {
                    p2=p2.next();
                    p1=p1.next();
                } else if (sign < 0) {
                    p1=p1.next();
                } else { // this is the case we need to copy 's' element to 'this'
                    p1.insertBefore(p2.item());
                    p2 = p2.next();
                }
            }
            // just we scanned over 'this' set first
            // we need to copy rest of 's' set to 'this'
            while(p2.isValidNode()) {
                this.elementsList.insertBack(p2.item());
                p2=p2.next();
            }
        } 
        catch(InvalidNodeException e) {
            System.err.println(e);
        }
    }

    /**
     *  intersect() modifies this Set so that it contains the intersection of
     *  its own elementsList and the elementsList of s.  The Set s is NOT modified.
     *
     *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     *
     *  Do not construct any new ListNodes during the execution of intersect.
     *  Reuse the nodes of "this" that will be in the intersection.
     *
     *  DO NOT MODIFY THE SET s.
     *  DO NOT CONSTRUCT ANY NEW NODES.
     *  DO NOT ATTEMPT TO COPY ELEMENTS.
     **/
    public void intersect(Set s) {
        ListNode p1=this.elementsList.front();
        ListNode p2=s.elementsList.front();
        ListNode tmp;
        Comparable c1, c2;
        int sign;
        try{
            while((p1.isValidNode()) && p2.isValidNode()) {
                c1 = (Comparable)(p1.item());
                c2 = (Comparable)(p2.item());
                sign = c1.compareTo(c2);
                if(sign==0) {
                    p1=p1.next();
                    p2=p2.next();
                } else if(sign <0) {
                    tmp=p1;
                    p1=p1.next();
                    tmp.remove();
                } else { // this is 's' set, not to move any element
                    p2=p2.next();
                }
            }
            // in case we scanned over set 's' first
            // we need to remove the reset of elements in this 'set'
            while(p1.isValidNode()) {
                tmp=p1;
                p1=p1.next();
                tmp.remove();
            }
        }
        catch(InvalidNodeException e) {
            System.err.println(e);
        }
    }

    /**
     *  toString() returns a String representation of this Set.  The String must
     *  have the following format:
     *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
     *            between them.
     *    {  1  2  3  } for a Set of three Integer elementsList.  No spaces before
     *            "{" or after "}"; two spaces before and after each element.
     *            Elements are printed with their own toString method, whatever
     *            that may be.  The elementsList must appear in sorted order, from
     *            lowest to highest according to the compareTo() method.
     *
     *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
     *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
     *            DEVIATIONS WILL LOSE POINTS.
     **/
    public String toString() {
        // Replace the following line with your solution.
        String str_set="{ ";
        ListNode walker= elementsList.front();
        try {
            while(walker.isValidNode()) {
                str_set+=walker.item()+" ";
                walker=walker.next();
            }
        }
        catch(InvalidNodeException e) {
            System.err.println(e);
        }
        str_set+="}";
        return str_set;
    }

    public static void main(String[] argv) {
        Set s = new Set();
        System.out.println("s.cardinality() = " + s.cardinality());
        s.insert(new Integer(3));
        s.insert(new Integer(4));
        s.insert(new Integer(3));
        s.insert(new Integer(1));
        s.insert(new Integer(9));
        s.insert(new Integer(10));
        s.insert(new Integer(11));
        System.out.println("Set s = " + s);
        System.out.println("s.cardinality() = " + s.cardinality());

        Set s2 = new Set();
        s2.insert(new Integer(4));
        s2.insert(new Integer(5));
        s2.insert(new Integer(5));
        System.out.println("Set s2 = " + s2);
        System.out.println("s2.cardinality() = " + s2.cardinality());

        Set s3 = new Set();
        s3.insert(new Integer(5));
        s3.insert(new Integer(3));
        s3.insert(new Integer(8));
        System.out.println("Set s3 = " + s3);
        System.out.println("s3.cardinality() = " + s3.cardinality());

        //s.union(s2);
        //System.out.println("After s.union(s2), s = " + s);
        //System.out.println("s.cardinality() = " + s.cardinality());

        s2.union(s);
        System.out.println("After s2.union(s), s2 = " + s2);
        System.out.println("s2.cardinality() = " + s2.cardinality());
        System.out.println("s "+s);

        //s.intersect(s3);
        //System.out.println("After s.intersect(s3), s = " + s);
        s3.intersect(s);
        System.out.println("After s3.intersect(s), s3 = " + s3);
        System.out.println("s "+s);

        System.out.println(s);
        System.out.println("s.cardinality() = " + s.cardinality());
        // You may want to add more (ungraded) test code here.
    }
}
