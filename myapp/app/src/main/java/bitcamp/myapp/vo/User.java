package bitcamp.myapp.vo;

import java.util.Objects;

// 메모리 설계도
public class User {

    static int seqNo;

    private int no;
    private String name;
    private String email;
    private String password;
    private String tel;

    public User(){
    }

    public User(int no){
        this.no = no;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return no == user.no;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(no);
    }

    public static int getNextSeqNo(){
        return ++seqNo;
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
