package com.jtsolv.jtsolvcurr.mysql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;


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
            url = "";
            url = url + "jdbc:mysql:";

            // jtsolv-kafka-mysql-service-instance-01.jtsolv-namespace-kafka-instance-01:3306 TCP

            url = url + "//jtsolv-kafka-mysql-service-instance-01";
            url = url + "." ;
            url = url + "jtsolv-namespace-kafka-instance-01";
            url = url + ":" ;
            url = url + "3306" ;
            url = url + "/" ;
            url = url + "jtsolv_gamedices_01";
            url = url + "?" ;
            url = url + "useUnicode=true&characterEncoding=utf8&useSSL=false";
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

        String st = sm + " url:" + url + " user:" + user + " password:" + password;

        trace(st + "-check-connection--");

        // Try connecting to MySQL
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                return trace(st + "Successfully connected to MySQL database!");
            }else {
                return trace(st + "Connection is null");
            }
        } catch (Exception e) {
            trace(st + "-exception-:" + e.getMessage() + " " + getStackTraceAsString(e));
            error(st + "-exception-:" + e.getMessage() + " " + getStackTraceAsString(e));
            return (st + "-connection-failed: " + e.getMessage() + " " + getStackTraceAsString(e));
        }
    }

    private String trace(String txt) {
        logger.trace(txt);
        return txt;
    }

    public  String executeTestConnectionByParams(Map<String,String> params ) {
        String sm = getCn() + ":connectByParams:";

        String dbHost = params.get("DB_HOST"); // mysql-service
        String dbPort = params.get("DB_PORT"); // 3306
        String dbName = params.get("DB_NAME"); // mydatabase
        String dbUser = params.get("DB_USER"); // myuser
        String dbPassword = params.get("DB_PASSWORD"); // mypassword

        String jdbcUrl = "";
        jdbcUrl = jdbcUrl + "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        jdbcUrl = jdbcUrl + "?useSSL=false&serverTimezone=UTC";

        String st = sm + " url:" + jdbcUrl + " user:" + dbUser + " password:" + dbPassword;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            if (conn != null) {
                return trace(st + "Successfully connected to MySQL database!");
            }else {
                return trace(st + "Connection is null");
            }
        } catch (Exception e) {
            trace(st + "-exception-:" + e.getMessage() + " " + getStackTraceAsString(e));
            error(st + "-exception-:" + e.getMessage() + " " + getStackTraceAsString(e));
            return (st + "-connection-failed: " + e.getMessage() + " " + getStackTraceAsString(e));
        }
    }

    private String error(String txt) {
        logger.error(txt);
        return txt;
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}