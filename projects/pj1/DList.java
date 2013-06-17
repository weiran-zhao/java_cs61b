/* DList.java */
// Modified by Ryan (Weiran) Zhao 
// Tue,Jun 11th 2013 08:30:25 PM EDT
// History
// Thu,Jun 13th 2013 02:33:39 PM EDT: Modified for project 1

/**
 *  A DList is a mutable doubly-linked list.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList {

    /**
     *  head references the sentinel node.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    protected DListNode head;
    protected long size;

    /* DList invariants:
     *  1)  head != null.
     *  2)  For any DListNode x in a DList, x.next != null.
     *  3)  For any DListNode x in a DList, x.prev != null.
     *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
     *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
     *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
     *      (denoted by "head"), that can be accessed from the sentinel by
     *      a sequence of "next" references.
     */

    /**
     *  DList() constructor for an empty DList.
     */
    public DList() {
        // creating sentinel
        head = new DListNode(null,null,null);
        head.next = head;
        head.prev = head;
        size = 0;
    }

    /**
     *  insertFront() inserts an item at the front of a DList.
     */
    public void insertFront(Object item) {
        DListNode tmp= new DListNode(item,head,head.next);
        head.next.prev=tmp;
        head.next=tmp;
        size+=1;
    }

    /** 
     *  isertBack() inserts an item at the back of a DList
     */
    public void insertBack(Object item) {
        DListNode tmp = new DListNode(item,head.prev,head);
        head.prev.next=tmp;
        head.prev=tmp;
        size+=1;
    }

    /** 
     *  back() return the last element
     */
    public Object back() {
        return head.prev.item;
    }

    /**
     *  removeFront() removes the first item (and first non-sentinel node) from
     *  a DList.  If the list is empty, do nothing.
     */
    public void removeFront() {
        if(size==0)
            return;
        head.next.next.prev=head;
        head.next=head.next.next;
        size-=1;
    }

    /**
     *  next(target) returns the node next to 'target' in DList
     *  we have to trust the user input a node that is in the DList
     */
    public DListNode next(DListNode target) {
        return target.next;
    }

    /** 
     *  insertAfter(DListNode, Object) insert a node after the given node
     */
    public void insertAfter(DListNode node, Object item) {
        DListNode tmp = new DListNode(item, node, node.next);
        node.next.prev=tmp;
        node.next=tmp;
        size+=1;
    }

    /** 
     *  insertBefore(DListNode, Object) insert a node after the given node
     */
    public void insertBefore(DListNode node, Object item) {
        DListNode tmp = new DListNode(item, node.prev, node);
        node.prev.next=tmp;
        node.prev=tmp;
        size+=1;
    }

    /** 
     *  remove(DListNode) removes the node from DList
     */
    public void remove(DListNode node) {
        node.next.prev=node.prev;
        node.prev.next=node.next;
        size-=1;
    }


    /**
     *  toString() returns a String representation of this DList.
     *
     *  DO NOT CHANGE THIS METHOD.
     *
     *  @return a String representation of this DList.
     */
    public String toString() {
        String result = "[  ";
        DListNode current = head.next;
        while (current != head) {
            result = result + current.item + "  ";
            current = current.next;
        }
        return result + "]";
    }

    public static void main(String[] args) {
        DList lst = new DList();
        System.out.println("empty"+lst);
        int[] toInsert= new int[2];
        toInsert[0]=2;
        toInsert[1]=3;
        lst.insertFront(toInsert);
        System.out.println("insert [2,5] " + lst);
    }
}
