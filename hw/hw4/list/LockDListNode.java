// By Ryan (Weiran) Zhao 
// Sat,Jun 15th 2013 02:24:04 PM EDT
package list;

public class LockDListNode extends DListNode {
    protected boolean locked;
    public LockDListNode(Object i, DListNode p, DListNode n) {
        super(i,p,n);
        locked=false;
    }
}
