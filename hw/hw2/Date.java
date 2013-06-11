/* Date.java */

import java.io.*;

class Date {

  /* Put your private data fields here. */
    private int month;
    private int day;
    private int year;

  /** Constructs a date with the given month, day and year.   If the date is
   *  not valid, the entire program will halt with an error message.
   *  @param month is a month, numbered in the range 1...12.
   *  @param day is between 1 and the number of days in the given month.
   *  @param year is the year in question, with no digits omitted.
   */
  public Date(int month, int day, int year) {
      // check first if the input construct a valid date
      if(isValidDate(month, day, year))
      {
          this.month=month;
          this.day=day;
          this.year=year;
      }
      else
      {
          System.out.println("invalid date input");
          System.exit(0);
      }
  }

  /** Constructs a Date object corresponding to the given string.
   *  @param s should be a string of the form "month/day/year" where month must
   *  be one or two digits, day must be one or two digits, and year must be
   *  between 1 and 4 digits.  If s does not match these requirements or is not
   *  a valid date, the program halts with an error message.
   */
  public Date(String s) {
      /** Trying to support vague input with extra space in front 
       * and/or trailing with AD/A.D.
       * String method used: matches, trim, split
       * Integer method constructor also used to get year,month,day
       */

      // get rid of trailing AD/A.D.
      String[] s1 = s.split("AD|A.D.");
      String target1=null;
      // target month/day/year regular expression pattern
      String pattern="\\d{1,2}/\\d{1,2}/\\d{1,4}";
      // find "[space]month/day/year" pattern
      for(int i=0;i<s1.length;i++)
      {
          if(s1[i].matches(pattern))
          {
              target1=s1[i];
              break;
          }
      }
      // get rid of prefix space
      String target2=target1.trim();
      // get month, day, year out
      String[] s2 = target2.split("/");
      int[] num=new int[s2.length]; 
      for(int i=0;i<s2.length;i++)
      {
          num[i]= Integer.parseInt(s2[i],10);
      }
      // check first if the input construct a valid date
      int month=num[0], day=num[1], year=num[2];
      if(isValidDate(month, day, year))
      {
          this.month=month;
          this.day=day;
          this.year=year;
      }
      else
      {
          System.out.println("invalid date input");
          System.exit(0);
      }
       
  }

  /** Checks whether the given year is a leap year.
   *  @return true if and only if the input year is a leap year.
   */
  public static boolean isLeapYear(int year) {
      if(year % 400 ==0)            // such as 1600 is leap year
          return true;
      else if (year % 100 == 0)     // 1900 not leap year
          return false;
      else if (year % 4 == 0)       // 1996 leap year
          return true;
      else                          // otherwise
          return false;
  }

  /** Returns the number of days in a given month.
   *  @param month is a month, numbered in the range 1...12.
   *  @param year is the year in question, with no digits omitted.
   *  @return the number of days in the given month.
   */
  public static int daysInMonth(int month, int year) {
      switch(month)
      {
          // for month with 31 days
          case 1:
          case 3:
          case 5:
          case 7:
          case 8:
          case 10:
          case 12:
              return 31;
          // special month Feb.
          case 2:
              if(isLeapYear(year))
                  return 29;
              else 
                  return 28;
          // month with 30 days
          case 4:
          case 6:
          case 9:
          case 11:
              return 30;
          default:
              return 0;
      }
  }


  /** Checks whether the given date is valid.
   *  @return true if and only if month/day/year constitute a valid date.
   *
   *  Years prior to A.D. 1 are NOT valid.
   */
  public static boolean isValidDate(int month, int day, int year) {
      // years before A.D. 1 not valid
      if(year < 1)
          return false;
      // check month, should between 1 and 12
      if((month <1) || (month > 12))
          return false;
      // check month and days correspondance
      if((day < 1) || (day > daysInMonth(month, year)))
          return false;
      return true;
  }

  /** Returns a string representation of this date in the form month/day/year.
   *  The month, day, and year are expressed in full as integers; for example,
   *  12/7/2006 or 3/21/407.
   *  @return a String representation of this date.
   */
  public String toString() {
    return month+"/"+day+"/"+year;
  }

  /** Determines whether this Date is before the Date d.
   *  @return true if and only if this Date is before d. 
   */
  public boolean isBefore(Date d) {
      // check year
      if(this.year < d.year)
          return true;
      // check month when year equals
      if((this.year==d.year) && (this.month < d.month))
          return true;
      // check days if month equals
      if((this.month==d.month) && (this.day < d.day))
          return true;
      return false;
  }

  /** Determines whether this Date is after the Date d.
   *  @return true if and only if this Date is after d. 
   */
  public boolean isAfter(Date d) {
      // three cases: before, equal, after
      // after = NOT (before or equal)
    return !(((d.year==this.year) && (d.month==this.month) && (d.day==this.day)) || this.isBefore(d));
  }

  /** Returns the number of this Date in the year.
   *  @return a number n in the range 1...366, inclusive, such that this Date
   *  is the nth day of its year.  (366 is used only for December 31 in a leap
   *  year.)
   */
  public int dayInYear() {
      int tmp=this.day;
      for(int i=1;i<this.month;i++)
          tmp+=daysInMonth(i,this.year);
      return tmp;
  }

  /** report days in a year, either 365 or 366
   */
  private int year2Days(int year)
  {
      if(isLeapYear(year))
          return 366;
      else
          return 365;
  }
  /** Determines the difference in days between d and this Date.  For example,
   *  if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1.
   *  If this Date occurs before d, the result is negative.
   *  @return the difference in days between d and this date.
   */
  public int difference(Date d) {
      Date startDate, endDate;
      int sign; // 1 means positive diff; -1 means negative
      // check which date is 'smaller'
      if(this.isBefore(d))
      {
          startDate=this;
          endDate=d;
          sign=-1;
      }
      else
      {
          startDate=d;
          endDate=this;
          sign=1;
      }

      // *********************************************
      // say this = 2010/5/18
      // d = 2012/2/22
      // difference = year2Days(2010)+year2Days(2011)
      // +dayInYear(2012)- dayInYear(2010)
      // *********************************************
      int diff=0;
      diff+=endDate.dayInYear();
      for(int i=startDate.year; i<endDate.year;i++)
          diff+=year2Days(i);
      diff-=startDate.dayInYear();
      return diff*sign;
  }

  public static void main(String[] argv) {
    System.out.println("\nTesting constructors.");
    Date d1 = new Date(1, 1, 1);
    System.out.println("Date should be 1/1/1: " + d1);
    d1 = new Date("2/4/2");
    System.out.println("Date should be 2/4/2: " + d1);
    d1 = new Date("2/29/2000");
    System.out.println("Date should be 2/29/2000: " + d1);
    d1 = new Date("2/29/1904");
    System.out.println("Date should be 2/29/1904: " + d1);

    d1 = new Date(12, 31, 1975);
    System.out.println("Date should be 12/31/1975: " + d1);
    //Date d2 = new Date("1/1/1976");
    Date d2 = new Date(1,1,1976);
    System.out.println("Date should be 1/1/1976: " + d2);
    Date d3 = new Date("1/2/1976");
    System.out.println("Date should be 1/2/1976: " + d3);

    Date d4 = new Date("2/27/1977");
    Date d5 = new Date("8/31/2110");
    /* I recommend you write code to test the isLeapYear function! */

    System.out.println("\nTesting before and after.");
    System.out.println(d2 + " after " + d1 + " should be true: " + 
                       d2.isAfter(d1));
    System.out.println(d3 + " after " + d2 + " should be true: " + 
                       d3.isAfter(d2));
    System.out.println(d1 + " after " + d1 + " should be false: " + 
                       d1.isAfter(d1));
    System.out.println(d1 + " after " + d2 + " should be false: " + 
                       d1.isAfter(d2));
    System.out.println(d2 + " after " + d3 + " should be false: " + 
                       d2.isAfter(d3));

    System.out.println(d1 + " before " + d2 + " should be true: " + 
                       d1.isBefore(d2));
    System.out.println(d2 + " before " + d3 + " should be true: " + 
                       d2.isBefore(d3));
    System.out.println(d1 + " before " + d1 + " should be false: " + 
                       d1.isBefore(d1));
    System.out.println(d2 + " before " + d1 + " should be false: " + 
                       d2.isBefore(d1));
    System.out.println(d3 + " before " + d2 + " should be false: " + 
                       d3.isBefore(d2));

    System.out.println("\nTesting difference.");
    System.out.println(d1 + " - " + d1  + " should be 0: " + 
                       d1.difference(d1));
    System.out.println(d2 + " - " + d1  + " should be 1: " + 
                       d2.difference(d1));
    System.out.println(d3 + " - " + d1  + " should be 2: " + 
                       d3.difference(d1));
    System.out.println(d3 + " - " + d4  + " should be -422: " + 
                       d3.difference(d4));
    System.out.println(d5 + " - " + d4  + " should be 48762: " + 
                       d5.difference(d4));

    Date dd = new Date(1,1,1);
    // checking leap year
    System.out.println("==========Testing leap year function========");
    System.out.println("1992 leap year should be true: "+dd.isLeapYear(1992));
    System.out.println("1990 leap year should be false: "+dd.isLeapYear(1900));
    System.out.println("2000 leap year should be true: "+dd.isLeapYear(2000));
    System.out.println("1993 leap year should be false: "+dd.isLeapYear(1993));
    // private field can be access from main() in the same class
    // different from C++
    System.out.println("=======Other tests========");
    System.out.println(dd.year);
  }

}
