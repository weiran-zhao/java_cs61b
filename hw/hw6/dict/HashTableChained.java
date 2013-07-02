/* HashTableChained.java */

package dict;
import list.*;
import java.util.Random;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

    /**
     *  Place any data fields here.
     **/
    private int numBuckets;
    private DList[] table;
    private int numElements;
    private int numDuplicates;


    /**
     *  isPrime(int k) checks if a number 'k' is prime or not
     *  @return true if is, otherwise false
     */
    private static boolean isPrime(int k) {
        if(k <2 )
            return false;
        if(k==2)
            return true;
        if(k%2==0)
            return false;
        if(k==3) 
            return true;
        int limit = (int)(Math.sqrt(k))+1;
        for(int i=3; i<=limit; i+=2) {
            if(k%i==0) 
                return false;
        }
        return true;
    }

    /** 
     *  Find a prime number around a given number 
     *  primeNear(int k)
     *  Always return a number that is prime and >= K
     */
    private static int primeNear(int k) {
        while(true) {
            if(isPrime(k)) {
                return k;
            } else {
                k+=1;
            }

        }
    }

    /** 
     *  Just to report how many duplicates( collision) found yet
     */
    public int collisions() {
        return numDuplicates;
    }

    /** 
     *  Construct a new empty hash table intended to hold roughly sizeEstimate
     *  entries.  (The precise number of buckets is up to you, but we recommend
     *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
     **/

    public HashTableChained(int sizeEstimate) {
        // set load factor
        numBuckets = (int)((double)sizeEstimate/0.75);
        //System.out.println("number of Buckets: "+numBuckets);
        numBuckets = primeNear(numBuckets);
        numElements = 0;
        numDuplicates =0;
        table = new DList[numBuckets];
        for(int i=0; i<numBuckets; i++) {
            table[i] = new DList();
        }
    }

    /** 
     *  Construct a new empty hash table with a default size.  Say, a prime in
     *  the neighborhood of 100.
     **/

    public HashTableChained() {
        this(100);
    }

    /**
     *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
     *  to a value in the range 0...(size of hash table) - 1.
     *
     *  This function should have package protection (so we can test it), and
     *  should be used by insert, find, and remove.
     **/

    int compFunction(int code) {
        //int p = 2147480009;
        //return (((3*code+5)%p)%numBuckets+numBuckets)%numBuckets;
        return ((3*code+5)%numBuckets+numBuckets)%numBuckets;
    }

    /** 
     *  Returns the number of entries stored in the dictionary.  Entries with
     *  the same key (or even the same key and value) each still count as
     *  a separate entry.
     *  @return number of entries in the dictionary.
     **/

    public int size() {
        return numElements;
    }

    /** 
     *  Tests if the dictionary is empty.
     *
     *  @return true if the dictionary has no entries; false otherwise.
     **/

    public boolean isEmpty() {
        return numElements==0;
    }

    /**
     *  Create a new Entry object referencing the input key and associated value,
     *  and insert the entry into the dictionary.  Return a reference to the new
     *  entry.  Multiple entries with the same key (or even the same key and
     *  value) can coexist in the dictionary.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the key by which the entry can be retrieved.
     *  @param value an arbitrary object.
     *  @return an entry containing the key and value.
     **/

    public Entry insert(Object key, Object value) {
        Entry ent = new Entry();
        ent.key=key;
        ent.value=value;
        // find which bucket to put
        int bucketIdx= compFunction(key.hashCode());
        if(table[bucketIdx].length()!=0) {
            numDuplicates+=1;
        }
        table[bucketIdx].insertBack(ent);
        numElements+=1;
        return ent;
    }

    /** 
     *  Search for an entry with the specified key.  If such an entry is found,
     *  return it; otherwise return null.  If several entries have the specified
     *  key, choose one arbitrarily and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     **/

    public Entry find(Object key) {
        Entry ent = new Entry();
        int bucketIdx = compFunction(key.hashCode());
        DList lst = table[bucketIdx];
        DListNode walker = (DListNode)(lst.front());
        try {
            while(walker.isValidNode()) {
                if(((Entry)walker.item()).key.equals(key)) {
                    ent = (Entry)(walker.item());
                    return ent;
                }
                walker=(DListNode)(walker.next());
            }
        }
        catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return null;
    }

    /** 
     *  Remove an entry with the specified key.  If such an entry is found,
     *  remove it from the table and return it; otherwise return null.
     *  If several entries have the specified key, choose one arbitrarily, then
     *  remove and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     */

    public Entry remove(Object key) {
        Entry ent = new Entry();
        int bucketIdx = compFunction(key.hashCode());
        DList lst = table[bucketIdx];
        DListNode walker = (DListNode)(lst.front());
        try {
            while(walker.isValidNode()) {
                if(((Entry)walker.item()).key.equals(key)) {
                    ent = (Entry)(walker.item());
                    // remove this node in DList
                    walker.remove();
                    numElements-=1;
                    return ent;
                }
                walker=(DListNode)(walker.next());
            }
        }
        catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     *  Remove all entries from the dictionary.
     */
    public void makeEmpty() {
        for(int i=0; i < numBuckets; i++) {
            table[i] = new DList();
        }
        numElements =0;
        numDuplicates=0;
    }

    public static void main(String[] argv) {
        System.out.println("testing");
        // test prime number function
        System.out.println("=====================prime number function =====================");
        for (int num=0; num <=20; num++) {
            System.out.println("if "+num+" is prime: "+isPrime(num));
        }
        for (int i=100; i<=2000; i+=100) {
            System.out.println("prime number around "+i+" is: "+primeNear(i));
        }

        //test basic hash table function
        System.out.println("======================basic insert,find,remove=====================");
        HashTableChained htable = new HashTableChained();
        htable.insert(1,"what");
        System.out.println("things at key 1 is :"+htable.find(1));
        System.out.println("tring to remove things at key 1: "+htable.remove(1));
        System.out.println("things at key 1 is :"+htable.find(1));

        // see insert distribution
        System.out.println("================test insert distribution=================");
        int num=80;
        Random randomGenerator = new Random();
        for(int i=0;i<num;i++) {
            htable.insert(randomGenerator.nextInt(10000000),"what");
        }
        System.out.println("After inserting "+num+" elements");
        System.out.println("number of bucket: "+htable.numBuckets);
        System.out.println("number of duplicates: "+htable.numDuplicates);

        // testing makeEmpty, isEmpty, size
        System.out.println("===============test makeEmpty===================");
        System.out.println("Is empty?: "+htable.isEmpty());
        System.out.println("Size before empty: "+htable.size());
        htable.makeEmpty();
        System.out.println("Size after empty: "+htable.size());

        // test another constructor function
        System.out.println("===============test others==================");
        htable = new HashTableChained(200);
    }

}
