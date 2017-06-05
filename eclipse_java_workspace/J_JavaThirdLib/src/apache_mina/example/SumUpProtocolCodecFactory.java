package apache_mina.example; 

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class SumUpProtocolCodecFactory extends DemuxingProtocolCodecFactory {

	   public SumUpProtocolCodecFactory(boolean server) 
	   {
	       if (server) {
	           super.addMessageDecoder(AddMessageDecoder.class);//implements MessageDecoder
	           super.addMessageEncoder(ResultMessage.class, ResultMessageEncoder.class);//自己的类T   Serializable , implements MessageEncoder<T>
	       } else // Client
	       {
	           super.addMessageEncoder(AddMessage.class, AddMessageEncoder.class);
	           super.addMessageDecoder(ResultMessageDecoder.class);
	       }
	   }
}