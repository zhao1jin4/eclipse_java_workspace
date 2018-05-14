package hadoop.avro.transfer; 

import java.util.ArrayList;
import java.util.List;


public class DemoServiceImpl implements DemoService {

    public String ping() {
        System.out.println("ping()");
        return "pong";
    }

    public List<Person> getPersonList(QueryParameter parameter) {
        //System.out.println(parameter.getAgeStart() + " - " + parameter.getAgeEnd());

        List<Person> list = new ArrayList<Person>(10);
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setAge(i);
            p.setChildrenCount(i);
            p.setName("test" + i);
            p.setSalary(10000D);
            p.setSex(true);
            list.add(p);
        }
        return list;
    }
}