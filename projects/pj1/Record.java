
public class Record {
    /** 
     * used to represent 1 record in run-length encoding
     * each record contains fields:
     *          type: either empty, fish or shark; 
     *          hungerness: to distinguish different hunger level sharks;
     *          length:     length of this type
     */
    public int type;
    public int hungerness;
    public int length;

    public Record(int type, int hungerness, int length) {
        this.type=type;
        this.hungerness=hungerness;
        this.length=length;
    }

    public Record() {
        this(-1,-1,-1);
    }

    public String toString(){
        String tmp="{ ";
        tmp+=type+" ";
        tmp+=hungerness+" ";
        tmp+=length+" ";
        tmp+="}";
        return tmp;
    }
}
