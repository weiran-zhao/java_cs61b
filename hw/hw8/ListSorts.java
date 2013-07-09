/* ListSorts.java */

import list.*;

public class ListSorts {

    private final static int SORTSIZE = 1000000;

    /** Get the current line number.
     * @return int - Current line number.
     */
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    /**
     *  makeQueueOfQueues() makes a queue of queues, each containing one item
     *  of q.  Upon completion of this method, q is empty.
     *  @param q is a LinkedQueue of objects.
     *  @return a LinkedQueue containing LinkedQueue objects, each of which
     *    contains one object from q.
     **/
    public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
        LinkedQueue qOfQ = new LinkedQueue();
        try {
            while(!q.isEmpty()){
                LinkedQueue tmp = new LinkedQueue();
                tmp.enqueue(q.dequeue());
                qOfQ.enqueue(tmp);
            }
        }
        catch (QueueEmptyException e) {
            System.err.println(e+" at "+getLineNumber());
        }
        return qOfQ;
    }

    /**
     *  mergeSortedQueues() merges two sorted queues into a third.  On completion
     *  of this method, q1 and q2 are empty, and their items have been merged
     *  into the returned queue.
     *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
     *    to largest.
     *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
     *    to largest.
     *  @return a LinkedQueue containing all the Comparable objects from q1 
     *   and q2 (and nothing else), sorted from smallest to largest.
     **/
    public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
        LinkedQueue q = new LinkedQueue();
        try {
            while((!q1.isEmpty()) && (!q2.isEmpty())) {
                Comparable e1 = (Comparable) q1.front();
                Comparable e2 = (Comparable) q2.front();
                if(e1.compareTo(e2) <= 0) {
                    q.enqueue(q1.dequeue());
                } else {
                    q.enqueue(q2.dequeue());
                }
            }
            if(q1.isEmpty()) {
                q.append(q2);
            }
            if(q2.isEmpty()) {
                q.append(q1);
            }
        } catch (QueueEmptyException e) {
            System.err.println(e+" at "+getLineNumber());
        }
        return q;
    }

    /**
     *  partition() partitions qIn using the pivot item.  On completion of
     *  this method, qIn is empty, and its items have been moved to qSmall,
     *  qEquals, and qLarge, according to their relationship to the pivot.
     *  @param qIn is a LinkedQueue of Comparable objects.
     *  @param pivot is a Comparable item used for partitioning.
     *  @param qSmall is a LinkedQueue, in which all items less than pivot
     *    will be enqueued.
     *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
     *    will be enqueued.
     *  @param qLarge is a LinkedQueue, in which all items greater than pivot
     *    will be enqueued.  
     **/   
    public static void partition(LinkedQueue qIn, Comparable pivot, 
            LinkedQueue qSmall, LinkedQueue qEquals, 
            LinkedQueue qLarge) {
        try {
            Object tmp;
            int comVal;
            while(!qIn.isEmpty()) {
                tmp = qIn.dequeue();
                comVal = pivot.compareTo(tmp);
                if(comVal<0) {
                    qLarge.enqueue(tmp);
                } else if(comVal== 0) {
                    qEquals.enqueue(tmp);
                } else {
                    qSmall.enqueue(tmp);
                }
            }
        } catch (QueueEmptyException e) {
            System.err.println(e+" at "+getLineNumber());
        }
    }

    /**
     *  mergeSort() sorts q from smallest to largest using mergesort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void mergeSort(LinkedQueue q) {
        LinkedQueue qOfQ = makeQueueOfQueues(q);
        try {
            while(qOfQ.size()>1) {
                // this can be stablized using additional code
                qOfQ.enqueue(mergeSortedQueues((LinkedQueue) qOfQ.dequeue(),
                            (LinkedQueue) qOfQ.dequeue()));
            }
            if(qOfQ.size() > 0) {
                q.append((LinkedQueue)qOfQ.front());
            }
        } catch (QueueEmptyException e) {
            System.err.println(e+" at "+getLineNumber());
        }
    }

    /**
     *  quickSort() sorts q from smallest to largest using quicksort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void quickSort(LinkedQueue q) {
        if(q.size()<=1) {
            return;
        }
        int idx = (int) (Math.random()*q.size());
        Comparable pivot = (Comparable) (q.nth(idx));
        LinkedQueue qSmall = new LinkedQueue();
        LinkedQueue qLarge = new LinkedQueue();
        LinkedQueue qEqual = new LinkedQueue();
        partition(q, pivot, qSmall, qEqual, qLarge);
        quickSort(qSmall);
        quickSort(qLarge);
        q.append(qSmall);
        q.append(qEqual);
        q.append(qLarge);
    }

    /**
     *  makeRandom() builds a LinkedQueue of the indicated size containing
     *  Integer items.  The items are randomly chosen between 0 and size - 1.
     *  @param size is the size of the resulting LinkedQueue.
     **/
    public static LinkedQueue makeRandom(int size) {
        LinkedQueue q = new LinkedQueue();
        for (int i = 0; i < size; i++) {
            q.enqueue(new Integer((int) ((size+10) * Math.random())));
        }
        return q;
    }

    /**
     *  main() performs some tests on mergesort and quicksort.  Feel free to add
     *  more tests of your own to make sure your algorithms works on boundary
     *  cases.  Your test code will not be graded.
     **/
    public static void main(String [] args) {

        LinkedQueue q = makeRandom(10);
        System.out.println(q.toString());
        mergeSort(q);
        System.out.println(q.toString());

        q = makeRandom(10);
        System.out.println(q.toString());
        quickSort(q);
        System.out.println(q.toString());

        Timer stopWatch = new Timer();
        q = makeRandom(SORTSIZE);
        stopWatch.start();
        mergeSort(q);
        stopWatch.stop();
        System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                stopWatch.elapsed() + " msec.");

        stopWatch.reset();
        q = makeRandom(SORTSIZE);
        stopWatch.start();
        quickSort(q);
        stopWatch.stop();
        System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                stopWatch.elapsed() + " msec.");
    }

}
