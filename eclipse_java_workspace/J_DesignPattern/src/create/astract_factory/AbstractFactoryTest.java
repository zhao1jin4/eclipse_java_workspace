package create.astract_factory;
//抽像工厂  　　多个抽像产品类(可多个实现),一个抽像工厂类和多个实现工厂类,抽像工厂类(多个方法)可以交叉创建不同的抽像产品的实现
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  抽象工厂角色：工厂接口
 */
interface Gardener {
    public Fruit createFruit(String name);
    public Veggie createVeggie(String name);
}
 
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  抽象产品角色：蔬菜接口
 */
interface Veggie {
}
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：热带水果
 */
class TropicalFruit implements Fruit {
    private String name;
    public TropicalFruit(String name) {
        System.out.println("热带工厂为您创建了：热带水果－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：热带蔬菜
 */
class TropicalVeggie implements Veggie {
    private String name;
    public TropicalVeggie(String name) {
        System.out.println("热带工厂为您创建了：热带水果－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：亚热带水果
 */
 class NorthernFruit implements Fruit {
    private String name;
    public NorthernFruit(String name) {
        System.out.println("亚热带工厂为您创建了：亚热带水果－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：亚热带蔬菜
 */
 class NorthernVeggie implements Veggie {
    private String name;
    public NorthernVeggie(String name) {
        System.out.println("亚热带工厂为您创建了：亚热带蔬菜－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体工厂角色：热带工厂
 */
 class TropicalGardener implements Gardener {
    public Fruit createFruit(String name) {
        return new TropicalFruit(name);
    }
    public Veggie createVeggie(String name) {
        return new TropicalVeggie(name);
    }
}
 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体工厂角色：亚热带工厂
 */
 class NorthernGardener implements Gardener {
    public Fruit createFruit(String name) {
        return new NorthernFruit(name);
    }
    public Veggie createVeggie(String name) {
        return new NorthernVeggie(name);
    }
}

 
/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  测试类（客户端）
 */
 public class AbstractFactoryTest {
    private void test(){
        Veggie tv,nv;
        Fruit tf,nf;
        TropicalGardener tg=new TropicalGardener();
        NorthernGardener ng=new NorthernGardener();
        tv=tg.createVeggie("热带菜叶");
        nv=ng.createVeggie("东北甜菜");
        tf=tg.createFruit("海南椰子");
        nf=ng.createFruit("雪梨");
    }
    public static void main(String args[]){
    	AbstractFactoryTest test=new AbstractFactoryTest();
        test.test();
    }
}


/**
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  抽象产品角色：水果接口
 */
interface Fruit {
}
