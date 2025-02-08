package com.jtsolv.jtsolvcurr.mysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;



@Component
public class JTSolvConnectionService {
    Logger logger = LoggerFactory.getLogger(JTSolvConnectionService.class.getName());

    private String getCn() {
        return "JTSolvConnectionService";
    }

    public String executeTestConnection(
            String purl,
            String puser,
            String ppassword) {

        String sm = getCn() + ":executeTestConnection";

        trace(sm + " start");
        // Database connection details

        String url = purl;
        String user = puser;
        String password = ppassword;
        if(purl.equals("def-k8s")){
            url = "jdbc:mysql://jtsolv-kafka-mysql-service-instance-01:3306/jtsolv_gamedices_01";
            user = "root";
            password = "jtsolvp";
        }

        if(purl.equals("def-local")){
            url = "jdbc:mysql://localhost:3306/jtsolv_gamedices_01";
            user = "admin1";
            password = "password";
        }

        if(purl.equals("def-local2")){
            url = "jdbc:mysql://localhost:3306/lakida_game_studio_v6?useUnicode=true&characterEncoding=utf8&useSSL=false";
            user = "admin1";
            password = "password";
        }

        // Try connecting to MySQL
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                return trace(sm + "Successfully connected to MySQL database!");
            }else {
                return trace(sm + "Connection is null");
            }

        } catch (SQLException e) {
            trace("EXCEPTION:" + e.getMessage());
            return ("Connection failed: " + e.getMessage());
        }
    }

    private String trace(String txt) {
        logger.trace(txt);
        return txt;
    }

}