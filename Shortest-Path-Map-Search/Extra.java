interface Extrainterface { public String apply(String x); }



public class Extra {
  public static void main (String args[]) {
      Extrainterface f = x -> x + "1";
      RuntimeException e = new RuntimeException( f.apply("2") ) {
        private static final long serialVersionUID = 3L;
        public String getMessage() { return f.apply("4"); }
      };
      System.out.println( e.getMessage() );
  }
}
