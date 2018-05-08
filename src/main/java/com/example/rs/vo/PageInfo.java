package com.example.rs.vo;

import com.example.rs.util.CalcIndex;

import java.io.Serializable;

public class PageInfo implements Serializable {
    private int pageNum;
    private int pageSize;
    private int index;
    private String typeChooser;
    private String radioChooser;
    private String keyWord;

    public PageInfo() {
    }

    public PageInfo(int pageNum, int pageSize, String typeChooser, String radioChooser) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.index = CalcIndex.parseIndex(pageNum, pageSize);
        this.typeChooser = typeChooser;
        this.radioChooser = radioChooser;
    }

    public PageInfo(int pageNum, int pageSize, String keyWord) {
        setPageNum(pageNum);
        setPageSize(pageSize);
        setIndex(CalcIndex.parseIndex(pageNum, pageSize));
        setKeyWord(keyWord);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTypeChooser() {
        return typeChooser;
    }

    public void setTypeChooser(String typeChooser) {
        this.typeChooser = typeChooser;
    }

    public String getRadioChooser() {
        return radioChooser;
    }

    public void setRadioChooser(String radioChooser) {
        this.radioChooser = radioChooser;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    
}
