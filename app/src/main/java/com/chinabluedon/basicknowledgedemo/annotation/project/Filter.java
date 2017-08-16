package com.chinabluedon.basicknowledgedemo.annotation.project;

/**
 * @author 胡腾
 * @time 2017/8/16  22:03
 * @desc ${TODD}
 */
@Table("user")
public class Filter {

    @Column("id")
    private int id;

    @Column("user_name")
    private String userName;

    @Column("nick_name")
    private String nickName;

    @Column("age")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
