package websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


 
public class MyMessageEncoder implements Encoder.Text<MyMessage> {

    @Override
    public String encode(MyMessage object) throws EncodeException {
        return object.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
