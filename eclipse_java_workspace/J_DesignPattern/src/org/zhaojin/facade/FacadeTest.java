package org.zhaojin.facade;
public class FacadeTest 
{
// 通常只有一个Facade类,一个实例,单例
    public static void main(String[] args){
        SecurityFacade sf = new SecurityFacade();
        sf.active();
        sf.inactive();
    }
}


/*
* 子系统角色，是一个功能的集合，一般是很多类的一个集合
*/
 class Camera {

    public void run(){
        System.out.println("摄像机启动");
    }
    public void stop(){
        System.out.println("摄像机停止");
    }
}
 class Ring {
    public void run(){
        System.out.println("门铃启动");
    }
    public void stop(){
        System.out.println("门铃停止");
    }
}

 class Sensor {
    public void run(){
        System.out.println("监视器启动");
    }
    public void stop(){
        System.out.println("监视器停止");
    }
}
 class SecurityFacade {

    private Camera camera;
    private Ring ring;
    private Sensor sensor;
    public SecurityFacade(){
        camera = new Camera();
        ring = new Ring();
        sensor = new Sensor();
    }
    public void active(){
        camera.run();
        ring.run();
        sensor.run();
        
    }
    public void inactive(){
        camera.stop();
        ring.stop();
        sensor.stop();
    }
}

