package apache_dubbo27_server;

import apache_dubbo27.GreetingService;

public class GreetingsServiceImpl implements GreetingService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}