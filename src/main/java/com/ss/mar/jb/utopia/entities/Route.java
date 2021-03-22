package com.ss.mar.jb.utopia.entities;

import java.util.Locale;

public class Route {
    private Integer id;

    private String destairportCode;

    private String originairportCode;

    public String getDestairportCode() {
        return destairportCode;
    }

    public void setDestairportCode(String destairportCode) {
        Boolean allLetters = true;
        for (Character c : destairportCode.toCharArray()) {
            if (!c.isLetter(c)){
                allLetters = false;
                break;
            }
        }
        if (destairportCode.length() ==3 && allLetters){
            this.destairportCode = destairportCode.toUpperCase(Locale.ROOT);
        }
    }

    public String getOriginairportCode() {
        return originairportCode;
    }

    public void setOriginairportCode(String originairportCode) {
        Boolean allLetters = true;
        for (Character c : originairportCode.toCharArray()) {
            if (!c.isLetter(c)){
                allLetters = false;
                break;
            }
        }
        if (originairportCode.length() ==3 && allLetters){
            this.originairportCode = originairportCode.toUpperCase(Locale.ROOT);
        }
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


}
