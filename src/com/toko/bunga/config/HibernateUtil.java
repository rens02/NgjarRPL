/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.bunga.config;

import com.toko.bunga.dao.TokoBungaDao;
import com.toko.bunga.dao.TokoBungaImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Dimas
 */
public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY;
    private static final TokoBungaDao TokoBunga_DAO;
    
    static{
        try{
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
            TokoBunga_DAO = new TokoBungaImpl(SESSION_FACTORY);
        }catch(Throwable e){
            System.err.println("Initial SessionFactory Creation Failed : "+e);
            throw new ExceptionInInitializerError(e);   
        }
    }
    
    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
    
    public static TokoBungaDao getTokoBungaDao(){
        return TokoBunga_DAO;
    }

 
    
    
    
}
