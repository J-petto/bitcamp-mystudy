package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.dao.skel.BoardDaoSkel;
import bitcamp.myapp.dao.skel.ProjectDaoSkel;
import bitcamp.myapp.dao.skel.UserDaoSkel;
import bitcamp.myapp.listener.InitApplicationListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerApp {

    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    UserDaoSkel userDaoSkel;
    BoardDaoSkel boardDaoSkel;
    ProjectDaoSkel projectDaoSkel;

    public static void main(String[] args) {
        ServerApp app = new ServerApp();
        app.addApplicationListener(new InitApplicationListener());
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

        // 서버에서 사용할 Dao Skeloton 객체를 준비
        userDaoSkel = (UserDaoSkel) appCtx.getAttribute("userDaoSkel");
        boardDaoSkel = (BoardDaoSkel) appCtx.getAttribute("boardDaoSkel");
        System.out.println("서버 프로젝트 관리 시스템 시작");

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("서버 실행 중...");

            while (true) {
                processRequest(serverSocket.accept());
            }


        } catch (Exception e) {
            System.out.println("통신 중 오류 발생");
            e.printStackTrace();
        }

        System.out.println("종료합니다.");

        for (ApplicationListener listener : listeners) {
            try {
                listener.onShutdown(appCtx);
            } catch (Exception e) {
                System.out.println("리스너 종료 중 오류 발생");
            }
        }
    }

    void processRequest(Socket s) throws Exception {
        try (Socket socket = s) {
            System.out.println("서버 연결 됨");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String dataName = in.readUTF();
            switch (dataName) {
                case "users":
                    userDaoSkel.service(in, out);
                    break;
                case "projects":
                    projectDaoSkel.service(in, out);
                    break;
                case "boards":
                    boardDaoSkel.service(in, out);
                    break;
                default:
            }
        }
    }
}

