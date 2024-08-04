module com.example.nc1_test_task {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.web;
    requires spring.beans;
    requires spring.core;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.jsoup;
    requires org.seleniumhq.selenium.api;
    requires net.bytebuddy;
    requires dev.failsafe.core;
    requires org.seleniumhq.selenium.support;
    requires org.checkerframework.checker.qual;
    requires jakarta.annotation;
    requires htmlunit;
    requires htmlunit.driver;

    exports com.example.nc1_test_task;
    exports com.example.nc1_test_task.entity;
    exports com.example.nc1_test_task.repository;
    exports com.example.nc1_test_task.services;
    exports com.example.nc1_test_task.controllers;

    opens com.example.nc1_test_task.entity to spring.core, org.hibernate.orm.core, net.bytebuddy, lombok;
    opens com.example.nc1_test_task.repository to spring.core;
    opens com.example.nc1_test_task.services to spring.core, org.seleniumhq.selenium.api,
            org.seleniumhq.selenium.chrome_driver, dev.failsafe.core;
    opens com.example.nc1_test_task to javafx.base, javafx.controls, net.bytebuddy, org.hibernate.orm.core, spring.core, javafx.fxml;
    opens com.example.nc1_test_task.controllers to javafx.base, lombok, javafx.controls, javafx.fxml, net.bytebuddy, org.hibernate.orm.core, spring.core;

}