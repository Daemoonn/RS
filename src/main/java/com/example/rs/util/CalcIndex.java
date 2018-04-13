package com.example.rs.util;

public class CalcIndex {
    public static int parseIndex(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }
}
