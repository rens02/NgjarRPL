/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.bunga.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dimas
 */
public class TokoBungaTabelModel extends AbstractTableModel{
    private List<TokoBunga> listTokoBunga = new ArrayList();
    private final String HEADER[] = {"Kode Bunga","Nama Bunga","Jenis Bunga","Harga Bunga"};

    public TokoBungaTabelModel(List<TokoBunga> listTokoBunga) {
        this.listTokoBunga = listTokoBunga;
    }

    @Override
    public int getRowCount() {
        return listTokoBunga.size();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return HEADER.length;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TokoBunga tokoBunga = listTokoBunga.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return tokoBunga.getKd_bunga();
            case 1:
                return tokoBunga.getNama();
            case 2:
                return tokoBunga.getJenis();
            case 3:
                return tokoBunga.getHarga();
            default:
                return null;
               
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getColumnName(int index){
        return HEADER[index];
    }
    
}
