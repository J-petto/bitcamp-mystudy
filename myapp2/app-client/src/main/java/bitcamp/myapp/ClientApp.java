package bitcamp.myapp;

import bitcamp.myapp.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientApp {

  public static void main(String[] args) {
    ClientApp app = new ClientApp();

    app.execute();
  }

  void execute() {

      String host = Prompt.input("서버?");
      int port = Prompt.inputInt("포트번호?");
    try(Socket socket = new Socket(host, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {


    } catch (Exception e) {
      System.out.println("실행 오류!");
      e.printStackTrace();
    }

    System.out.println("종료합니다.");

    Prompt.close();
  }
}
