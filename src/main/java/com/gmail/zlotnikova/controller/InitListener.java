package com.gmail.zlotnikova.controller;

import javax.servlet.ServletContextEvent;

import com.gmail.zlotnikova.service.TableService;
import com.gmail.zlotnikova.service.impl.TableServiceImpl;

public class InitListener implements javax.servlet.ServletContextListener {

    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        tableService.deleteAllTables();
        tableService.createAllTables();
    }

}