package apache_mina.example;

import java.io.Serializable;
 public abstract class AbstractMessage implements Serializable {
      private int sequence;
  
      public int getSequence() {
          return sequence;
      }
  
      public void setSequence(int sequence) {
          this.sequence = sequence;
      }
  }