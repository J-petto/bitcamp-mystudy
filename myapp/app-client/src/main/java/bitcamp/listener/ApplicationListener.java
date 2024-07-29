package bitcamp.listener;

import bitcamp.context.ApplicationContext;

// 어플리케이션의 상태 변경을 알림 받을 객체를 정의
public interface ApplicationListener {
    void onStart(ApplicationContext ctx);
    void onShutdown(ApplicationContext ctx);
}
