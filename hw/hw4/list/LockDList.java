package list;

public class LockDList extends DList {

    /** 
     *  Overide newNode function
     */
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
        return new LockDListNode(item, prev, next);
    }

    public void lockNode(DListNode node) {
        if(node instanceof LockDListNode) {
            ((LockDListNode)node).locked = true;
        }
    }

    /**
     *  Overide remove function
     */
    public void remove(DListNode node) {
        if((node!=null)&&(node instanceof LockDListNode) && !((LockDListNode)node).locked) {
            super.remove(node);
        }
    }
    public static void main(String[] argv) {
      System.out.println("testing");
      LockDList lst = new LockDList();
      System.out.println("length :"+lst.length());
      System.out.println("is empty? "+lst.isEmpty());
      System.out.println("Testing empty: " + lst);
      lst.insertFront(3);
      System.out.println("Testing insert front"+lst);
      lst.insertBack(55.55);
      System.out.println("Testing insert back"+ lst);
      lst.insertFront(1000);
      System.out.println("Testing insert front with 1000"+lst);
      System.out.println("Testing front "+lst.front().item);
      System.out.println("Testing back() "+lst.back().item);
      // get node with 3
      System.out.println("Testing next(), should be 3: "+lst.next(lst.head.next).item);
      DListNode midNode=lst.next(lst.head.next);
      // test insertAfter and insertBefore
      lst.insertBefore("hello",midNode);
      System.out.println("Testing insertBefore(): "+lst);
      lst.insertAfter("what-ever",midNode);
      System.out.println("Testing insertAfter(): "+lst);
      // print length
      System.out.println("Length :"+lst.length());
      System.out.println("is empty?"+lst.isEmpty());

      // try remove head
      System.out.println("Trying to remove head");
      lst.remove(lst.prev(lst.front()));
      System.out.println(lst);

      // testing prev
      System.out.println("Testing prev(), should be hello:"+lst.prev(midNode).item);
      // testing lock
      lst.lockNode(lst.back());
      // testing remove
      for(int i=0; i<6;i++) {
          lst.remove(lst.head.next);
          System.out.println(lst+ "length is "+lst.length());
      }

    }
}
