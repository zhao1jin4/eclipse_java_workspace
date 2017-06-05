 
package websocket;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
 
public class MyMessageDecoder implements Decoder.Text<MyMessage> {
    @Override
    public MyMessage decode(String s) {
        String[] tokens = s.split(":");
        return new MyMessage(tokens[0], tokens[1]);
    }

    @Override
    public boolean willDecode(String s) {
        return s.startsWith("obj");
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
