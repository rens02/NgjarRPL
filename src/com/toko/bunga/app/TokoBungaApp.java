/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.bunga.app;

import com.toko.bunga.service.TokoBungaService;
import com.toko.bunga.view.TokoBungaView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Dimas
 */
public class TokoBungaApp {
    private static ApplicationContext appContext;
    
    public static void main(String[] args){
        appContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        new TokoBungaView().setVisible(true);
    }
    
    public static TokoBungaService getTokoBungaService(){
        return (TokoBungaService)appContext.getBean("TokoBungaService");
    }
    
    
}
