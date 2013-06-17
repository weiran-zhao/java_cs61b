import java.util.Arrays;
/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

    /**
     *  Define any variables associated with a RunLengthEncoding object here.
     *  These variables MUST be private.
     */
    private DList record;
    private int width;
    private int height;
    private int starveTime;
    // this walker walk through the DList, used by nextRun() and restartRuns();
    private DListNode walker;
    // feedings is the feedings of the node walker is pointing to
    private int feedings;



    /**
     *  The following methods are required for Part II.
     */

    /**
     *  RunLengthEncoding() (with three parameters) is a constructor that creates
     *  a run-length encoding of an empty ocean having width i and height j,
     *  in which sharks starve after starveTime timesteps.
     *  @param i is the width of the ocean.
     *  @param j is the height of the ocean.
     *  @param starveTime is the number of timesteps sharks survive without food.
     */

    public RunLengthEncoding(int i, int j, int starveTime) {
        record = new DList();
        this.width=i;
        this.height=j;
        this.starveTime=starveTime;
        walker=record.head.next;
        feedings=-1;
    }

    /**
     *  RunLengthEncoding() (with five parameters) is a constructor that creates
     *  a run-length encoding of an ocean having width i and height j, in which
     *  sharks starve after starveTime timesteps.  The runs of the run-length
     *  encoding are taken from two input arrays.  Run i has length runLengths[i]
     *  and species runTypes[i].
     *  @param i is the width of the ocean.
     *  @param j is the height of the ocean.
     *  @param starveTime is the number of timesteps sharks survive without food.
     *  @param runTypes is an array that represents the species represented by
     *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
     *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
     *         sharks (which are equivalent to sharks that have just eaten).
     *  @param runLengths is an array that represents the length of each run.
     *         The sum of all elements of the runLengths array should be i * j.
     */

    public RunLengthEncoding(int i, int j, int starveTime,
            int[] runTypes, int[] runLengths) {
        this(i,j,starveTime);
        if(runTypes.length!=runLengths.length) {
            return;
        }
        // if this is first element just insert
        int hungerness=(runTypes[0]==Ocean.SHARK)?0:-1;
        record.insertBack(new Record(runTypes[0],hungerness,runLengths[0]));
        //System.out.println("length "+ runLengths.length);
        for(int arrIdx=1;arrIdx<runLengths.length;arrIdx++) {
            // not the same type as previous
            if (((Record)(record.back())).type!=runTypes[arrIdx]) { 
                hungerness=(runTypes[arrIdx]==Ocean.SHARK)?0:-1;
                record.insertBack(new Record(runTypes[arrIdx],hungerness,runLengths[arrIdx]));
            } else {
                ((Record)(record.back())).length+=runLengths[arrIdx];
            }
        }
        restartRuns();
    }

    /**
     *  restartRuns() and nextRun() are two methods that work together to return
     *  all the runs in the run-length encoding, one by one.  Each time
     *  nextRun() is invoked, it returns a different run (represented as an
     *  array of two ints), until every run has been returned.  The first time
     *  nextRun() is invoked, it returns the first run in the encoding, which
     *  contains cell (0, 0).  After every run has been returned, nextRun()
     *  returns null, which lets the calling program know that there are no more
     *  runs in the encoding.
     *
     *  The restartRuns() method resets the enumeration, so that nextRun() will
     *  once again enumerate all the runs as if nextRun() were being invoked for
     *  the first time.
     *
     *  (Note:  Don't worry about what might happen if nextRun() is interleaved
     *  with addFish() or addShark(); it won't happen.)
     */

    /**
     *  restartRuns() resets the enumeration as described above, so that
     *  nextRun() will enumerate all the runs from the beginning.
     */

    public void restartRuns() {
        walker=record.head.next;
        feedings=-1;
    }

    /**
     *  nextRun() returns the next run in the enumeration, as described above.
     *  If the runs have been exhausted, it returns null.  The return value is
     *  an array of two ints (constructed here), representing the type and the
     *  size of the run, in that order.
     *  @return the next run in the enumeration, represented by an array of
     *          two ints.  The int at index zero indicates the run type
     *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
     *          indicates the run length (which must be at least 1).
     */

    public int[] nextRun() {
        // end of record, return null
        if(walker==record.head)
            return null;
        // update feedings
        feedings=((Record)walker.item).hungerness;
        int[] tmp = new int[2];
        tmp[0]=((Record)(walker.item)).type;
        tmp[1]=((Record)(walker.item)).length;
        walker=record.next(walker);
        return tmp;
    }

    /**
     *  toOcean() converts a run-length encoding of an ocean into an Ocean
     *  object.  You will need to implement the three-parameter addShark method
     *  in the Ocean class for this method's use.
     *  @return the Ocean represented by a run-length encoding.
     */

    public Ocean toOcean() {
        // makesure walker is reset
        restartRuns();
        Ocean oc = new Ocean(width,height,starveTime);
        int[] runs=nextRun();
        int xidx=0,yidx=0,totalIdx=0;
        while(runs!=null) {
            if(runs[0]==Ocean.EMPTY) {
                totalIdx+=runs[1];
                runs=nextRun();
                continue;
            } 
            for(int ii=totalIdx; ii<totalIdx+runs[1]; ii++) {
                // make sure ii >=0
                assert ii>=0;
                xidx=ii%width;
                yidx=ii/width;
                if(runs[0]==Ocean.FISH) {
                    oc.addFish(xidx,yidx);
                } else {
                    oc.addShark(xidx,yidx,feedings);
                }
            }
            totalIdx+=runs[1];
            runs=nextRun();
        }
        assert totalIdx==width*height;
        return oc;
    }

    /**
     *  The following method is required for Part III.
     */

    /**
     *  RunLengthEncoding() (with one parameter) is a constructor that creates
     *  a run-length encoding of an input Ocean.  You will need to implement
     *  the sharkFeeding method in the Ocean class for this constructor's use.
     *  @param sea is the ocean to encode.
     */

    public RunLengthEncoding(Ocean sea) {
        this(sea.width(),sea.height(),sea.starveTime());
        int width=sea.width();
        int height=sea.height();
        int starveTime=sea.starveTime();
        int curType = sea.cellContents(0,0);
        int curLength = 1;
        int curHunger =sea.sharkFeeding(0,0);
        int xidx,yidx;
        //loop through rows, left to right
        for(int idx=1;idx<width*height;idx++) {
            xidx=idx%width;
            yidx=idx/width;
            if((sea.cellContents(xidx,yidx)==curType) && 
               (curHunger == sea.sharkFeeding(xidx,yidx))) {
                curLength+=1;
            } else { // add a node to record
                record.insertBack(new Record(curType,curHunger,curLength));
                curType=sea.cellContents(xidx,yidx);
                curLength=1;
                curHunger=sea.sharkFeeding(xidx,yidx);
            }
        }
        // insert the last one
        record.insertBack(new Record(curType,curHunger,curLength));
        restartRuns();
        check();
    }

    /**
     *  The following methods are required for Part IV.
     */

    /** 
     *  locateNode(int idx) locates a node in the record.
     *  input idx: is given (x,y) coord, idx=y*width+x+1;
     *  It returns an bundled object 
     *  [ node, idx_in_node];
     *  node is the reference to the node;
     *  idx_in_node is say there's 7 Fish in this node,
     *  idx-previous_sum = 5; then idx_in_node is 5;
     *  It is a helper function to addFish and addShark
     */
    private Object[] locateNode(int idx) {
        restartRuns();
        Object[] ret = new Object[2];
        DListNode node=walker;
        int[] tmp;
        while(true) {
            tmp=nextRun();
            if(idx<=tmp[1]) {
                ret[0]=node;
                ret[1]=idx;
                break;
            } else {
                idx-=tmp[1];
                node=walker;
            }
        }
        restartRuns();
        return ret;
    }

    /**
     *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
     *  cell is already occupied, leave the cell as it is.  The final run-length
     *  encoding should be compressed as much as possible; there should not be
     *  two consecutive runs of sharks with the same degree of hunger.
     *  @param x is the x-coordinate of the cell to place a fish in.
     *  @param y is the y-coordinate of the cell to place a fish in.
     */

    public void addFish(int x, int y) {
        int idx=y*width+x+1;
        // get the node that is index by idx
        Object[] ob=locateNode(idx);
        DListNode node=(DListNode)ob[0];
        int idx_in_node=(Integer)ob[1];
        Record item=(Record)(node.item);
        if(item.type==Ocean.EMPTY) {
            DListNode prev_node, post_node;
            Record prev_item=null, post_item=null;
            // if this node is the first node
            if(idx==1) {
                prev_node=null;
            } else {
                ob=locateNode(idx-1);
                prev_node=(DListNode)ob[0];
                prev_item=(Record)(prev_node.item);
            }
            // if this is the last node
            if(idx==width*height) {
                post_node=null;
            } else {
                ob=locateNode(idx+1);
                post_node=(DListNode)ob[0];
                post_item=(Record)(post_node.item);
            }
            // Here is the real chanllege
            if(prev_node==null) {
                if(post_node==null) {
                    item.type=Ocean.FISH;
                    item.length=1;
                    item.hungerness=-1;
                } else if(post_node==node) {
                    record.insertBefore(node,new Record(Ocean.FISH,-1,1));
                    item.length-=1;
                } else { // post_node!=node
                    if(post_item.type==Ocean.FISH) {
                        post_item.length+=1;
                        record.remove(node);
                    } else { // post_item.type!=Fish
                        item.type=Ocean.FISH;
                        item.hungerness=-1;
                        item.length=1;
                    }
                }
            } else if(prev_node==node) {
                if(post_node==null) {
                    record.insertAfter(node,new Record(Ocean.FISH,-1,1));
                    item.length-=1;
                } else if(post_node==node) {
                    record.insertAfter(node,new Record(Ocean.FISH,-1,1));
                    record.insertAfter(record.next(node),new Record(Ocean.EMPTY,
                                       -1, item.length-idx_in_node));
                    item.length=idx_in_node-1;
                } else { // post_node!=node
                    if(post_item.type==Ocean.FISH) {
                        post_item.length+=1;
                        item.length-=1;
                    } else { // post_item.type!=Fish) 
                        record.insertAfter(node,new Record(Ocean.FISH,-1,1));
                        item.length-=1;
                    }
                }
            } else { // prev_node != node
                if(post_node==null) {
                    if(prev_item.type==Ocean.FISH) {
                        prev_item.length+=1;
                        record.remove(node);
                    } else { // prev_item.type!=Fish
                        item.type=Ocean.FISH;
                        item.hungerness=-1;
                        item.length=1;
                    }
                } else if(post_node==node) {
                    if(prev_item.type==Ocean.FISH) {
                        item.length-=1;
                        prev_item.length+=1;
                    } else { // prev_item.type!=Fish
                        record.insertBefore(node,new Record(Ocean.FISH,-1,1));
                        item.length-=1;
                    }
                } else { //post_node!=node
                    if((prev_item.type==Ocean.FISH) && (post_item.type==Ocean.FISH)) {
                        prev_item.length+=(item.length+post_item.length);
                        record.remove(post_node);
                        record.remove(node);
                    } else if((prev_item.type==Ocean.FISH) && (post_item.type!=Ocean.FISH)) {
                        prev_item.length+=1;
                        record.remove(node);
                    } else if((prev_item.type!=Ocean.FISH) && (post_item.type==Ocean.FISH)) {
                        post_item.length+=1;
                        record.remove(node);
                    } else {
                        item.type=Ocean.FISH;
                        item.length=1;
                        item.hungerness=-1;
                    }
                }
            }
        }

        check();
    }

    /**
     *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
     *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
     *  just eaten.  If the cell is already occupied, leave the cell as it is.
     *  The final run-length encoding should be compressed as much as possible;
     *  there should not be two consecutive runs of sharks with the same degree
     *  of hunger.
     *  @param x is the x-coordinate of the cell to place a shark in.
     *  @param y is the y-coordinate of the cell to place a shark in.
     */

    public void addShark(int x, int y) {
        int idx=y*width+x+1;
        // get the node that is index by idx
        Object[] ob=locateNode(idx);
        DListNode node=(DListNode)ob[0];
        int idx_in_node=(Integer)ob[1];
        Record item=(Record)(node.item);
        if(item.type==Ocean.EMPTY) {
            DListNode prev_node, post_node;
            Record prev_item=null, post_item=null;
            // if this node is the first node
            if(idx==1) {
                prev_node=null;
            } else {
                ob=locateNode(idx-1);
                prev_node=(DListNode)ob[0];
                prev_item=(Record)(prev_node.item);
            }
            // if this is the last node
            if(idx==width*height) {
                post_node=null;
            } else {
                ob=locateNode(idx+1);
                post_node=(DListNode)ob[0];
                post_item=(Record)(post_node.item);
            }
            // Here is the real chanllege
            if(prev_node==null) {
                if(post_node==null) {
                    item.type=Ocean.SHARK;
                    item.length=1;
                    item.hungerness=0;
                } else if(post_node==node) {
                    record.insertBefore(node,new Record(Ocean.SHARK,0,1));
                    item.length-=1;
                } else { // post_node!=node
                    if((post_item.type==Ocean.SHARK) && (post_item.hungerness==0)){
                        post_item.length+=1;
                        record.remove(node);
                    } else { // post_item.type!=Fish
                        item.type=Ocean.SHARK;
                        item.hungerness=0;
                        item.length=1;
                    }
                }
            } else if(prev_node==node) {
                if(post_node==null) {
                    record.insertAfter(node,new Record(Ocean.SHARK,0,1));
                    item.length-=1;
                } else if(post_node==node) {
                    record.insertAfter(node,new Record(Ocean.SHARK,0,1));
                    record.insertAfter(record.next(node),new Record(Ocean.EMPTY,
                                       -1, item.length-idx_in_node));
                    item.length=idx_in_node-1;
                } else { // post_node!=node
                    if((post_item.type==Ocean.SHARK) && (post_item.hungerness==0)) {
                        post_item.length+=1;
                        item.length-=1;
                    } else { // post_item.type!=Fish) 
                        record.insertAfter(node,new Record(Ocean.SHARK,0,1));
                        item.length-=1;
                    }
                }
            } else { // prev_node != node
                if(post_node==null) {
                    if((prev_item.type==Ocean.SHARK) && (prev_item.hungerness==0)) {
                        prev_item.length+=1;
                        record.remove(node);
                    } else { // prev_item.type!=Fish
                        item.type=Ocean.SHARK;
                        item.hungerness=0;
                        item.length=1;
                    }
                } else if(post_node==node) {
                    if((prev_item.type==Ocean.SHARK) && (prev_item.hungerness==0)) {
                        item.length-=1;
                        prev_item.length+=1;
                    } else { // prev_item.type!=Fish
                        record.insertBefore(node,new Record(Ocean.SHARK,0,1));
                        item.length-=1;
                    }
                } else { //post_node!=node
                    if((prev_item.type==Ocean.SHARK) && (post_item.type==Ocean.SHARK) &&
                       (prev_item.hungerness==0) && (post_item.hungerness==0)) {
                        prev_item.length+=(item.length+post_item.length);
                        record.remove(post_node);
                        record.remove(node);
                    } else if((prev_item.type==Ocean.SHARK) && (prev_item.hungerness==0) &&
                              ((post_item.type!=Ocean.SHARK) || (post_item.hungerness!=0))) {
                        prev_item.length+=1;
                        record.remove(node);
                    } else if(((prev_item.type!=Ocean.SHARK) || (prev_item.hungerness!=0)) &&
                              (post_item.type==Ocean.SHARK) && (post_item.hungerness==0)) {
                        post_item.length+=1;
                        record.remove(node);
                    } else {
                        item.type=Ocean.SHARK;
                        item.length=1;
                        item.hungerness=0;
                    }
                }
            }
        }
        check();
    }

    /**
     *  check() walks through the run-length encoding and prints an error message
     *  if two consecutive runs have the same contents, or if the sum of all run
     *  lengths does not equal the number of cells in the ocean.
     */

    private void check() {
        if(record.size<1) {
            System.out.println("runs less than 1");
            return;
        }
        if(record.size==1) {
            return;
        }
        restartRuns();
        int total_cnts =0;
        int[] prev= nextRun();
        total_cnts+=prev[1];
        int prevFeedings=feedings;
        int[] cur=nextRun();
        while(cur!=null) {
            if((cur[0]==prev[0]) && (feedings==prevFeedings)) {
                System.out.println("consecutive runs have the same contents");
                restartRuns();
                return;
            }
            prev=cur;
            prevFeedings=feedings;
            cur=nextRun();
            total_cnts+=prev[1];
        }
        if(total_cnts!=width*height) {
            System.out.println("record's total length does NOT equal to total sea cells");
            restartRuns();
            return;
        }
        // reset the walker
        restartRuns();
    }

    public static void main(String[] argv) {
        RunLengthEncoding encode=new RunLengthEncoding(50,25,2);
        System.out.println("width: "+encode.width+" height: "+
                encode.height+" starve time: "+ encode.starveTime);
        System.out.println("records: "+encode.record);

        int[] type = new int[5];
        type[0]=1;
        type[1]=2;
        type[2]=0;
        type[3]=0;
        type[4]=2;
        int[] length = new int[5];
        length[0]=5;
        length[1]=10;
        length[2]=3;
        length[3]=4;
        length[4]=3;
        System.out.println("OK");
        System.out.println("types: "+Arrays.toString(type));
        System.out.println("length: "+Arrays.toString(length));
        RunLengthEncoding enc2 = new RunLengthEncoding(50,25,2,type,length);
        System.out.println("records: "+enc2.record);
        enc2.check();
        System.out.println("adding the same content as before to the record");
        enc2.record.insertBack(new Record(2,-1,3));
        enc2.record.insertBack(new Record(2,1,3));
        enc2.check();
        System.out.println("records: "+enc2.record);
        // test locateNode
        System.out.println("Testing locate 11");
        Object[] ob= enc2.locateNode(11);
        System.out.println("node is: "+((DListNode)ob[0]).item);
        System.out.println("index is: "+((Integer)ob[1]));
        System.out.println("Testing locate 5");
        ob= enc2.locateNode(5);
        System.out.println("node is: "+((DListNode)ob[0]).item);
        System.out.println("index is: "+((Integer)ob[1]));
        System.out.println("Testing locate 16");
        ob= enc2.locateNode(16);
        System.out.println("node is: "+((DListNode)ob[0]).item);
        System.out.println("index is: "+((Integer)ob[1]));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("restarting");
        enc2.restartRuns();
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
        System.out.println("next run "+Arrays.toString(enc2.nextRun()));
    }

}
