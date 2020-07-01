package com.example.projects.services;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tools {

    public static void log(Integer type, String message){
        Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()");
        switch(type){
            case 1:
                logger.log(Level.INFO, message);
                break;
            case 2:
                logger.log(Level.WARNING, message);
                break;
            case 3:
                logger.log(Level.SEVERE, message);
                break;
        }
    }
}
