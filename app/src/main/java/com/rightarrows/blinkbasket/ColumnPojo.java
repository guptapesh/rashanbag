package com.rightarrows.blinkbasket;

import java.util.ArrayList;

public class ColumnPojo {
    String category_name;
    private ArrayList<RowPojo> rowSection;
    public  ColumnPojo()
    {

    }

    public ArrayList<RowPojo> getRowSection() {
        return rowSection;
    }

    public void setRowSection(ArrayList<RowPojo> rowSection) {
        this.rowSection = rowSection;
    }

    public ColumnPojo(String category_name, ArrayList<RowPojo> rowSection) {
        this.category_name = category_name;
        this.rowSection = rowSection;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
