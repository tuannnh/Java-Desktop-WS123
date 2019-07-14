/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autosave.pkg;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import javax.swing.JPanel;

/**
 *
 * @author Hung Tuan
 * @param <E>
 */
public class EmployeeTableModel <E> extends AbstractTableModel{
    String [] header;
    int [] index;
    Vector<Employee> data;

    public EmployeeTableModel(String[] header, int[] index) {
        int i;
        this.header = new String[header.length];
        for (i = 0; i < header.length; i++) this.header[i] = header[i];
        this.index = new int[index.length];
        for (i = 0; i < header.length; i++) this.index[i] = index[i];
        this.data = new Vector<Employee>();
    }
    
    public Vector<Employee> getData(){
        return data;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }
    
    @Override
    public String getColumnName(int column){
        return (column>=0 && column<header.length)?header[column]:"";
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row<0 || row >= data.size() || 
                column<0 || column >= header.length)
            return null;
        Employee emp = data.get(row);
        switch (index[column]) {
            case 0: return emp.getCode();
            case 1: return emp.getName();
            case 2: return emp.getSalary();
        }
        return null;
    }
    
    public void sort(){
       Collections.sort(data,new EmployeeComparator());
    }
}

