package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientApp {

    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
//        app.addApplicationListener(new InitApplicationListener());
        app.execute();
    }


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

        System.out.println("[프로젝트 관리 시스템]");

        try {
//            appCtx.getMainMenu().execute();

            // 상대편과 연결을 시도함. 연결되면 객체를 생성 후 리턴
            Socket socket = new Socket("192.168.0.17", 8888);

            // 상대편과 편리하게 입출력할 수 있도록 데코레이터를 붙인다.
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // 서버에게 보낼 문자열을 네트워크 카드 메모리로 전송한다. 그리고 즉시 리턴한다.
            out.writeUTF("머요");

            // 서버에서 문자열을 받을 때까지 기다리다가 문자열이 완전하게 모두 도착하면 String 객체를 만들어 리턴
            String response = in.readUTF();
            System.out.println(response);



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
