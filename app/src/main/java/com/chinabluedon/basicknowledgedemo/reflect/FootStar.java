package com.chinabluedon.basicknowledgedemo.reflect;

import com.chinabluedon.basicknowledgedemo.annotation.base.ContentView;

/**
 * @author ht
 * @time 2017/8/17  8:53
 * @desc ${TODD}
 */
@ContentView(11)
public class FootStar  {

    @ContentView(22)
    private String name;

    protected int age;

    @ContentView(22)
    public String club;

    public FootStar () {
    }

    public FootStar (String name, int age, String club) {
        this.name = name;
        this.age = age;
        this.club = club;
    }
    @ContentView(33)
    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public String getClub () {
        return club;
    }

    public void setClub (String club) {
        this.club = club;
    }

}
