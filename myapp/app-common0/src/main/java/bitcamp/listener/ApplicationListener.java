package bitcamp.listener;

import bitcamp.context.ApplicationContext;

// 어플리케이션의 상태 변경을 알림 받을 객체를 정의
public interface ApplicationListener {
    // default 구현하기 싫으면 구현하지 않아도 됨
    default void onStart(ApplicationContext ctx) throws Exception {};
    default void onShutdown(ApplicationContext ctx) throws Exception {};
}