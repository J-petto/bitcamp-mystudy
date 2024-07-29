package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerApp {

    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        ServerApp app = new ServerApp();
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

        System.out.println("서버 프로젝트 관리 시스템 시작");

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("서버 실행 중...");

            // 클라이언트 연결을 기다림 -> 대기열에 클라이언트가 등록되는 순간 즉시 소켓 연결
            try (Socket socket = serverSocket.accept()) {
                System.out.println("서버 연결 됨");

                // 상대편과 데이터를 주고 받기 위해 스트림 객체에 적절한 데코레이터 붙임
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // 클라이언트가 문자열을 보낼 때까지 기다리고 있다가 클라이언트가 문자열을 보내면 즉시 읽어 String 객체로 리턴
                String message = in.readUTF();

                // 클라이언트에게 보낼 문자열을 네트워크 카드의 메모리에 전송해둔다.
                // 데이터가 전송하는 것은 OS가 할 일. 데이터가 도착하는지 확인하지않고 즉시 리턴 함
                out.writeUTF(message + "(민지)");
            }

        } catch (Exception e) {
            System.out.println("통신 중 오류 발생");
            e.printStackTrace();
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
