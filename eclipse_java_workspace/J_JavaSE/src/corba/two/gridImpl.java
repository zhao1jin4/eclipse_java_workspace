package corba.two;

public class gridImpl extends gridPOA{
  short m_height;   // store the height
  short m_width;    // store the width
  int m_array[][];  // a 2D array to hold the grid data
  private int i=0;
  private int j=0;
  private int n=0;
   
  public gridImpl(short width, short height) 
  {
    m_array  = new int[width][height]; // allocate the 2D array
    m_height = height;  // set up height
    m_width  = width;   // set up width
  }
  
  /*
   * Implementation of the method which reads the height attribute
   */
  public short height() 
  {
    return m_height;
  }

  /*
   * Implementation of the method which reads the width attribute
   */
  public short width()
  {
    return m_width;
  }

  /*
   * Implementation of the set operation
   */
  public void set (short x, short y, int value) 
  {
    i++;
    n++;
    System.out.println ("----- "+n+"Server /"+i+" grid.set()----");
    System.out.println ("In grid.set() x = " + x);
    System.out.println ("In grid.set() y = " + y);
    System.out.println ("In grid.set() value = " + value);
    m_array[y][x] = value;
  }
  
  /*
   * Implementation of the get operation
   */
  public int get (short x, short y) 
  {
    j++;
    n++;
    System.out.println ("-----"+n+" Server /  "+j+" : grid.get()----");
    int value = m_array[y][x];
    System.out.println ("In grid.get() x = " + x);
    System.out.println ("In grid.get() y = " + y);
    System.out.println ("In grid.get() value = " + value);
    return value;
  }  
 
}
