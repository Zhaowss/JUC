package org.example.test4;

/**
 * @ClassNAME test01
 * @Description TODO
 * @Author zhaoweishan
 * @Date 2024/7/8 09:37
 * @Version 1.0
 */
public class test01 {

    public static void main(String[] args) throws InterruptedException {
        Room room=new Room();
        Thread thread=new Thread(()->{
            for (int i = 0; i < 6000; i++) {
              room.incre();
            }
        });

        Thread thread1=new Thread(()->{
            for (int i = 0; i < 6000; i++) {
             room.decre();
            }
        });
        thread.start();
        thread1.start();
        Thread.sleep(1000);
        System.out.println("多线程并发访问的结果为："+room.getCounter());
    }
}


class  Room{
    private  int counter=0;
  public void incre(){
      synchronized (this){
          counter++;
      }
  }
  public void decre(){
      synchronized (this){
          counter--;
      }
  }
  public int getCounter() {
      synchronized (this) {
          return counter;
      }
  }
}