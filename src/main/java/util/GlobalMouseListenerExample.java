package util;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListenerExample implements NativeMouseInputListener {
    // 点击次数
    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    // 监听鼠标动作时的坐标， 输出样式为tagui命令文件
    public void nativeMousePressed(NativeMouseEvent e) {
        int button = e.getButton();
        if (1 == button) {// 左键点击
            System.out.println("click (" + e.getX() + "," + e.getY() + ")");
        } else if (2 == button) {// 右键点击
            System.out.println("rclick (" + e.getX() + "," + e.getY() + ")");
        } else if (3 == button) {// 滚轮
//            System.out.println("rclick (" + e.getX() + "," + e.getY() + ")");
        } else {
            System.out.println("error " + button);
        }
    }

    // 松开
//    public void nativeMouseReleased(NativeMouseEvent e) {
//        System.out.println("Mouse Released: " + e.getButton());
//    }
//
//    public void nativeMouseMoved(NativeMouseEvent e) {
////        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
//    }

    // 拖动
//    public void nativeMouseDragged(NativeMouseEvent e) {
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
//    }

    // 监听鼠标坐标/点击事件
    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Construct the example object.
        GlobalMouseListenerExample example = new GlobalMouseListenerExample();

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
    }
}
