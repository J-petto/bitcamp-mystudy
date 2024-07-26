package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.util.*;

public class App {

    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        App app = new App();
        app.addApplicationListener(new InitApplicationListener());
        app.execute();
    }

//    public void init() {
//        for (ApplicationListener listener : listeners) {
//            try {
//                listener.onStart();
//            } catch (Exception e) {
//                System.out.println("리스너 실행 중 오류 발생");
//            }
//        }
//    }

    private void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }

    private void removeApplicationListener(ApplicationListener listener) {
        listeners.remove(listener);
    }

    void execute() {
        for (ApplicationListener listener : listeners) {
            try {
                listener.onStart(appCtx);
            } catch (Exception e) {
                System.out.println("리스너 실행 중 오류 발생");
            }
        }

        try {
            appCtx.getMainMenu().execute();
        } catch (Exception ex) {
            System.out.println("실행 오류");
            ex.printStackTrace();
        }

        System.out.println("종료합니다.");

        Prompt.close();

        for (ApplicationListener listener : listeners) {
            try {
                listener.onShutdown(appCtx);
            } catch (Exception e) {
                System.out.println("리스너 종료 중 오류 발생");
            }
        }
    }

}
