package apache_mina.example;

   import org.apache.mina.core.buffer.IoBuffer;
   import org.apache.mina.core.session.IoSession;
   import org.apache.mina.filter.codec.demux.MessageEncoder;
   
   /**
    * A {@link MessageEncoder} that encodes {@link AddMessage}.
    *
    * @author <a href="http://mina.apache.org">Apache MINA Project</a>
    */
   public class AddMessageEncoder<T extends AddMessage> extends AbstractMessageEncoder<T> {
       public AddMessageEncoder() {
           super(Constants.ADD);
       }
   
       @Override
       protected void encodeBody(IoSession session, T message, IoBuffer out) {
           out.putInt(message.getValue());
       }
   
       public void dispose() throws Exception {
       }
   }

