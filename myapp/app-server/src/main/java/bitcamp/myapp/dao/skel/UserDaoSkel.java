package bitcamp.myapp.dao.skel;

import bitcamp.myapp.dao.UserDao;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class UserDaoSkel {
    private UserDao userDao;

    public UserDaoSkel(UserDao userDao) {
        this.userDao = userDao;
    }

    public void service(DataInputStream in, DataOutputStream out) throws Exception {

    }
}
