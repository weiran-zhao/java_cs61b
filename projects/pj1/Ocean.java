/* Ocean.java */
// Skeleton from cs61b at Berkeley
// Filled by Ryan (Weiran) Zhao 
// Wed,Jun 12th 2013 01:43:11 PM EDT

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

    /**
     *  Do not rename these constants.  WARNING:  if you change the numbers, you
     *  will need to recompile Test4.java.  Failure to do so will give you a very
     *  hard-to-find bug.
     */

    public final static int EMPTY = 0;
    public final static int SHARK = 1;
    public final static int FISH = 2;

    /**
     *  Define any variables associated with an Ocean object here.  These
     *  variables MUST be private.
     */
    private class Cell {
        /** 
         * cell represent a cell in ocean, indexed by x,y coordinates
         * It can be empty, shark or fish
         * for shark, there's a field representing it's hungerness,
         * when hungerness > starveTime, it dies
         */
        public int type;     // can be shark, fish or empty
        public int hungerness; // for shark only

        public Cell() {
            type =0;      // empty as default
            hungerness=-1;    // means not a shark
        }


        /**
         *  sharkFull() set hungerness of shark to 0;
         *  but first it needs to check if this is a shark;
         */
        public void sharkFull() {
            if(type != SHARK) {
                System.out.println("You should not call this function, FAKE shark!");
            } else {
                hungerness=0;
            }
        }

        /** 
         *  sharkHunger() set hungerness of shark +1;
         *  in case of hungerness > starveTime, shark dies and replaced by 
         *  an empty cell
         */
        public void sharkHunger() {
            if(type != SHARK) {
                System.out.println("You should not call this function, FAKE shark!");
            } else {
                hungerness+=1;
                // shark dies
                if(hungerness>starveTime) {
                    type = EMPTY;
                    hungerness =-1;
                }
            }
        }

        /**
         *  fishDie() set type to EMPTY and hungerness to -1
         */
        public void fishDie() {
            if(type !=FISH) {
                System.out.println("YOu should not call this function, FAKE fish!");
            } else {
                type=EMPTY;
                hungerness=-1;
            }
        }
    }
    private int width;    //width of ocean AKA x-coord
    private int height;   // height of ocean AKA y-coord
    private Cell[][] contents;  // 2-D array representing contents of ocean
    private int starveTime;   // starveTime for shark

    /**
     *  xCoord() takes in x-coordinate and make it within 0~(width-1);
     *  @param x is the x-coordinate of ocean (can be any value)
     *  @return value between 0~(width-1)
     */
    private int xCoord(int x) {
        return (x%width+width)%width;
    }

    /**
     *  yCoord() takes in y-coordinate and make it within 0~(height-1);
     *  @param y is the y-coordinate of ocean (can be any value)
     *  @return value between 0~(height-1)
     */
    private int yCoord(int y) {
        return (y%height+height)%height;
    }


    /**
     *  types of neighbour of a cell possible
     *  NOTHING   =       0;      // no fish at all
     *  ONEFISH   =       1;
     *  TWOFISHPL =       2;      // two fishes or more
     *  ONESHARK  =       3;
     *  TWOSHKPL  =       4;
     *  TWOFSPL   =       5;      // two fishes and sharks or more
     */
    private final static int NOTHING   =       0;
    private final static int ONEFISH   =       1;
    private final static int TWOFISHPL =       2;      // two fishes or more
    private final static int ONESHARK  =       3;
    private final static int TWOSHKPL  =       4;
    private final static int TWOFSPL   =       5;      // two fishes and sharks or more
    private final static int TWOFONESPL=       6;      // 2 fishes one shark at most

    /**
     *  checkNeighbor() checks neighbor of a cell and return a state (int)
     *  one of which comes from above definitions
     *  @param x x-coord;
     *  @param y y-coord;
     *  @return state of neighbor
     */
    private int checkNeighbor(int x, int y) {
        int fish_cnt=0, shark_cnt=0, empty_cnt=0;
        // simply count neighbor
        for(int yidx=y-1; yidx<=y+1;yidx++){
            for(int xidx=x-1; xidx<=x+1;xidx++) {
                if((yidx==y) && (xidx==x))
                    continue;
                //System.out.println(yidx+" "+xidx);
                //System.out.println(yCoord(yidx));
                if(contents[yCoord(yidx)][xCoord(xidx)].type==EMPTY) {
                    empty_cnt+=1;
                } else if (contents[yCoord(yidx)][xCoord(xidx)].type==FISH) {
                    fish_cnt+=1;
                } else {
                    shark_cnt+=1;
                }
            }
        }
        assert (fish_cnt+shark_cnt+empty_cnt)==8;
        // now decide what state to return
        if(fish_cnt==1)
            return ONEFISH;
        if(shark_cnt==1)
            return ONESHARK;
        if((shark_cnt>=2) && (fish_cnt>=2))
            return TWOFSPL;
        if((shark_cnt<=1) && (fish_cnt>=2))
            return TWOFONESPL;
        if(shark_cnt>=2)
            return TWOSHKPL;
        if(fish_cnt>=2)
            return TWOFISHPL;
        if(fish_cnt==0)
            return NOTHING;

        // code should not run here
        assert 0>1;
        return -1;
    }

    /**
     *  cellUnchanged() copy cell content from pre_ocean to next_ocean
     *  at (x,y) coordinate. This is object content copy
     *  @param x x-coord
     *  @param y y-coord
     *  @param preOcean previous ocean state
     */
    private void cellUnchanged(int x, int y, Ocean preOcean) {
        this.contents[yCoord(y)][xCoord(x)].type=
            preOcean.contents[yCoord(y)][xCoord(x)].type;
        this.contents[yCoord(y)][xCoord(x)].hungerness
            =preOcean.contents[yCoord(y)][xCoord(x)].hungerness;
    }

    /** 
     *  empty a cell indexed by (x,y)
     *  @param x x-coord
     *  @param y y-coord
     */
    private void emptyCell(int x, int y) {
        this.contents[yCoord(y)][xCoord(x)].type=EMPTY;
        this.contents[yCoord(y)][xCoord(x)].hungerness=-1;
    }

    /** 
     *  cellNextState() takes in (x,y) coord, calculate state for (x,y)  
     *  and return that state to nextOcean. This function fulfill rule
     *  (1) to (8) at readme Part I
     *  @param x x-coord
     *  @param y y-coord
     *  @param nextOcean next Ocean 
     */
    private void cellNextState(int x, int y, Ocean nextOcean) {
        // figure out neighbor of target cell (x,y)
        int fish_cnt=0, shark_cnt=0, empty_cnt=0;
        // simply count neighbor
        for(int xidx=x-1; xidx<=x+1;xidx++) {
            for(int yidx=y-1; yidx<=y+1;yidx++){
                if((yidx==y) && (xidx==x))
                    continue;
                if(contents[yCoord(yidx)][xCoord(xidx)].type==EMPTY) {
                    empty_cnt+=1;
                } else if (contents[yCoord(yidx)][xCoord(xidx)].type==FISH) {
                    fish_cnt+=1;
                } else {
                    shark_cnt+=1;
                }
            }
        }
        assert (fish_cnt+shark_cnt+empty_cnt)==8;
        // ============================================================
        // assign value to nextOcean, follow rule (1) to (8)
        // ============================================================
        
        // always copy old value first
        nextOcean.cellUnchanged(x,y,this); 
        // pre_ocean cell is shark
        if(contents[yCoord(y)][xCoord(x)].type==SHARK) {
            // rule 1
            if(fish_cnt>=1) {
                nextOcean.contents[yCoord(y)][xCoord(x)].sharkFull();
                return;
            }
            // rule 2
            if(fish_cnt==0) {
                nextOcean.contents[yCoord(y)][xCoord(x)].sharkHunger();
                return;
            }
        }
        // pre_ocean cell is fish
        if(contents[yCoord(y)][xCoord(x)].type==FISH) {
            // rule 3
            if(shark_cnt==0) {
                // content unchanged
                return;
            }
            // rule 4
            if(shark_cnt==1) {
                // fish dies
                nextOcean.contents[yCoord(y)][xCoord(x)].fishDie();
                return;
            }
            // rule 5
            if(shark_cnt>=2) {
                // new shark born
                nextOcean.addShark(x,y);
                return;
            }
        }
        // pre_ocean cell is empty
        if(contents[yCoord(y)][xCoord(x)].type==EMPTY) {
            // rule 6
            if(fish_cnt<2) {
                // nothing magical happens
                return;
            }
            // rule 7
            if((fish_cnt>=2) && (shark_cnt<=1)) {
                nextOcean.addFish(x,y);
                return;
            }
            // rule 8
            if((fish_cnt>=2) && (shark_cnt>=2)) {
                nextOcean.addShark(x,y);
                return;
            }
        }
        // code should not run here
        assert 1<0;
    }

    /** 
     *  cellShark() takes in coordinates and calculate what happens
     *  after 1 timestep of simulation at that cell
     *  This function corresponds to rule (1) and (2) in Readme
     *  @param x x-coord
     *  @param y y-coord
     *  @param nextOcean next timestep ocean
     */
    private void cellShark(int x, int y, Ocean nextOcean) {
        int stateNeighbor = checkNeighbor(x,y);
        // copy pre_ocean state at (x,y) to next_ocean
        nextOcean.cellUnchanged(x,y,this);
        // not hungary
        if((stateNeighbor==ONEFISH) || (stateNeighbor==TWOFISHPL)) {
            nextOcean.contents[yCoord(y)][xCoord(x)].sharkFull();
            return;
        }
        // nothing to eat
        if(stateNeighbor==NOTHING) {
            System.out.println("here");
            nextOcean.contents[yCoord(y)][xCoord(x)].sharkHunger();
            return;
        }
    }

    /** 
     *  cellFish() takes in coordinates and calculate what happens
     *  after 1 timestep of simulation at that cell
     *  This function corresponds to rule (3), (4) and (5) in Readme
     *  @param x x-coord
     *  @param y y-coord
     *  @param nextOcean next timestep ocean
     */
    private void cellFish(int x, int y, Ocean nextOcean) {
        int stateNeighbor = checkNeighbor(x,y);
        // fish stays
        if((stateNeighbor==NOTHING) || (stateNeighbor==TWOFISHPL)
                || (stateNeighbor==ONEFISH)) {
            nextOcean.cellUnchanged(x,y,this);
            return;
                }
        // fish Eaten empty
        if(stateNeighbor==ONESHARK) {
            nextOcean.emptyCell(x,y);
            return;
        }
        // new shark born
        if(stateNeighbor==TWOSHKPL) {
            addShark(x,y);
            return;
        }
    }

    /** 
     *  cellEmpty() takes in coordinates and calculate what happens
     *  after 1 timestep of simulation at that cell
     *  This function corresponds to rule (6), (7) and (8) in Readme
     *  @param x x-coord
     *  @param y y-coord
     *  @param nextOcean next timestep ocean
     */
    private void cellEmpty(int x, int y, Ocean nextOcean ) {
        int stateNeighbor = checkNeighbor(x,y);
        // Nothing happens
        if((stateNeighbor==NOTHING) || (stateNeighbor==ONEFISH)) {
            nextOcean.cellUnchanged(x,y,this);
            return;
        }
        // new shark
        if(stateNeighbor==TWOFSPL) {
            nextOcean.addShark(x,y);
            return;
        }
        // new fish born
        if(stateNeighbor==TWOFONESPL) {
            nextOcean.addFish(x,y);
            return;
        }
    }

    /**
     *  The following methods are required for Part I.
     */

    /**
     *  Ocean() is a constructor that creates an empty ocean having width i and
     *  height j, in which sharks starve after starveTime timesteps.
     *  @param i is the width of the ocean.
     *  @param j is the height of the ocean.
     *  @param starveTime is the number of timesteps sharks survive without food.
     */

    public Ocean(int i, int j, int starveTime) {
        width=i;
        height=j;
        this.starveTime=starveTime;
        // web said initial value is always 0, no need to do more
        contents = new Cell[height][width];
        // have to initialize each element of 2-D array contents
        for(int yidx=0;yidx<height;yidx++) {
            for(int xidx=0;xidx<width;xidx++) {
                contents[yidx][xidx]=new Cell();
            }
        }
    }

    /**
     *  width() returns the width of an Ocean object.
     *  @return the width of the ocean.
     */

    public int width() {
        return width;
    }

    /**
     *  height() returns the height of an Ocean object.
     *  @return the height of the ocean.
     */

    public int height() {
        return height;
    }

    /**
     *  starveTime() returns the number of timesteps sharks survive without food.
     *  @return the number of timesteps sharks survive without food.
     */

    public int starveTime() {
        return starveTime;
    }

    /**
     *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
     *  cell is already occupied, leave the cell as it is.
     *  @param x is the x-coordinate of the cell to place a fish in.
     *  @param y is the y-coordinate of the cell to place a fish in.
     */

    public void addFish(int x, int y) {
        contents[yCoord(y)][xCoord(x)].type=FISH;
    }

    /**
     *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
     *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
     *  just eaten.  If the cell is already occupied, leave the cell as it is.
     *  @param x is the x-coordinate of the cell to place a shark in.
     *  @param y is the y-coordinate of the cell to place a shark in.
     */

    public void addShark(int x, int y) {
        contents[yCoord(y)][xCoord(x)].type=SHARK;
        contents[yCoord(y)][xCoord(x)].hungerness=0;
    }

    /**
     *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
     *  a fish, and SHARK if it contains a shark.
     *  @param x is the x-coordinate of the cell whose contents are queried.
     *  @param y is the y-coordinate of the cell whose contents are queried.
     */

    public int cellContents(int x, int y) {
        return contents[yCoord(y)][xCoord(x)].type;
    }

    /**
     *  timeStep() performs a simulation timestep as described in README.
     *  @return an ocean representing the elapse of one timestep.
     */

    public Ocean timeStep() {
        Ocean nextOcean = new Ocean(width,height,starveTime);
        for(int yidx=0; yidx <height; yidx++) {  // height index, y coord
            for(int xidx =0; xidx<width; xidx++) {     // width index, x coord
                // empty in original ocean
                if(contents[yCoord(yidx)][xCoord(xidx)].type==EMPTY) {
                    cellEmpty(xidx,yidx,nextOcean);
                }
                // fish in original ocean
                if(contents[yCoord(yidx)][xCoord(xidx)].type==FISH) {
                    cellFish(xidx,yidx,nextOcean);
                }
                // shark in original ocean
                if(contents[yCoord(yidx)][xCoord(xidx)].type==SHARK) {
                    cellShark(xidx,yidx,nextOcean);
                }
            }
        }

        return nextOcean;
    }

    /**
     *  The following method is required for Part II.
     */

    /**
     *  addShark() (with three parameters) places a shark in cell (x, y) if the
     *  cell is empty.  The shark's hunger is represented by the third parameter.
     *  If the cell is already occupied, leave the cell as it is.  You will need
     *  this method to help convert run-length encodings to Oceans.
     *  @param x is the x-coordinate of the cell to place a shark in.
     *  @param y is the y-coordinate of the cell to place a shark in.
     *  @param feeding is an integer that indicates the shark's hunger.  You may
     *         encode it any way you want; for instance, "feeding" may be the
     *         last timestep the shark was fed, or the amount of time that has
     *         passed since the shark was last fed, or the amount of time left
     *         before the shark will starve.  It's up to you, but be consistent.
     */

    public void addShark(int x, int y, int feeding) {
        // Your solution here.
    }

    /**
     *  The following method is required for Part III.
     */

    /**
     *  sharkFeeding() returns an integer that indicates the hunger of the shark
     *  in cell (x, y), using the same "feeding" representation as the parameter
     *  to addShark() described above.  If cell (x, y) does not contain a shark,
     *  then its return value is undefined--that is, anything you want.
     *  Normally, this method should not be called if cell (x, y) does not
     *  contain a shark.  You will need this method to help convert Oceans to
     *  run-length encodings.
     *  @param x is the x-coordinate of the cell whose contents are queried.
     *  @param y is the y-coordinate of the cell whose contents are queried.
     */

    public int sharkFeeding(int x, int y) {
        // Replace the following line with your solution.
        return 0;
    }

    /** 
     *  this is mostly for module testing 
     */
    public static void main(String[] argv) {
        System.out.println("testing");
        Ocean sea= new Ocean(5,2,1);
        System.out.println(sea.contents[0][0].type);
    }

}
