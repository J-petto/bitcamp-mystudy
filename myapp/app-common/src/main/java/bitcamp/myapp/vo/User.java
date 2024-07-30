package bitcamp.myapp.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.StringTokenizer;

// 메모리 설계도
// Serializable 인터페이스를 구현해  serializable 허락함
// Serializable 인터페이스는 직렬화/역 직렬화 승인 표시 용도이기 때문에 내부에 구현되어있는 추상 메서드가 없음.
// 유사한 예) Cloneable 인터페이스
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int no;
    private String name;
    private String email;
    private String password;
    private String tel;

    public User() {
    }

    public User(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return no == user.no;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(no);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
