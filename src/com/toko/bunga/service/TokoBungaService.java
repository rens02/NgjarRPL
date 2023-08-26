/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.bunga.service;

import com.toko.bunga.model.TokoBunga;
import java.util.List;

/**
 *
 * @author Dimas
 */
public interface TokoBungaService {
    public void save(TokoBunga tokobunga);
    public void update(TokoBunga tokobunga);
    public void delete(TokoBunga tokobunga);
    //public TokoBunga get(String kd_bunga);
    public List<TokoBunga> getList();
}
