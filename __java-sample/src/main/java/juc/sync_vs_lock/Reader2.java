package juc.sync_vs_lock;

/**
 * @author Eric Cui
 * <p>
 * Created by Intellij IDEA.
 * Date  : 2018/2/26 21:03
 * Desc  : 描述信息
 */
public class Reader2 extends Thread {
    private BufferInterruptibly buff;

    public Reader2(BufferInterruptibly buff) {
        this.buff = buff;
    }

    @Override
    public void run() {

        try {
            buff.read();//可以收到中断的异常，从而有效退出
        } catch (InterruptedException e) {
            System.out.println("我不读了");
        }

        System.out.println("读结束");

    }
}
