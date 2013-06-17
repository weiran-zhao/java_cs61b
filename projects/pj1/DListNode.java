/* DListNode.java */
// Modified from DListNode2.java from lab4
// By Ryan (Weiran) Zhao 
// Thu,Jun 13th 2013 02:31:33 PM EDT

/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

public class DListNode {

    /**
     *  item references the item stored in the current node.
     *  prev references the previous node in the DList.
     *  next references the next node in the DList.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    public Object item;
    protected DListNode prev;
    protected DListNode next;

    /**
     *  DListNode() constructor.
     */
    DListNode(Object i, DListNode p, DListNode n) {
        item = i;
        prev = p;
        next = n;
    }

}
