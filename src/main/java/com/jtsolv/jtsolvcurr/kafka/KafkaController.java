package com.jtsolv.jtsolvcurr.kafka;


import com.jtsolv.jtsolvcurr.mysql.JTSolvConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class KafkaController {


    private final KafkaProducer messageProducer;
    private final JTSolvConnectionService connectionService;

    @Autowired
    public KafkaController(KafkaProducer messageProducer,
                           JTSolvConnectionService connectionService){
        this.messageProducer = messageProducer;
        this.connectionService = connectionService;
    }

    @PostMapping("/kafka/send-post")
    public String sendMessage(@RequestParam("message") String topic,
                              @RequestParam("message") String message) {
        messageProducer.sendMessage("jtsolv-test-topic-4", message);
        return "Message sent: " + message;
    }

    @GetMapping("/kafka/send-get")
    public String sendMessageGet(@RequestParam("topic") String topic,
                                 @RequestParam("message") String message) {
        messageProducer.sendMessage(topic, message);
        return "Message sent: " + message;
    }

    @GetMapping("/kafka/send-get-test")
    public String sendMessageTestByGet(@RequestParam("topic") String topic,
                                       @RequestParam("message") String message) {
        return "Message sent: " + topic + " " + message;
    }

    @GetMapping("/kafka/conn-test-2")
    public String connTest2(@RequestParam("topic") String topic,
                                       @RequestParam("message") String message) {
        return "Message sent: " + topic + " " + message;
    }

    @GetMapping("/kafka/conn-test")
    public String connectionToMysqlTest(@RequestParam("url") String url,
                                        @RequestParam("user") String user,
                                       @RequestParam("password") String password) {

        String info = connectionService.executeTestConnection(url,user,password);
        return "test: " + info;
    }

    @GetMapping("/kafka/conn-p")
    public String connectionToMysqlTestByParam(
            @RequestParam("dbhost") String dbhost,
            @RequestParam("dbport") String dbport,
            @RequestParam("db") String db,
            @RequestParam("port") String port,
            @RequestParam("dbuser") String dbuser,
            @RequestParam("dbpass") String dbpass,
            @RequestParam("p0") String p0,
            @RequestParam("v0") String v0,
            @RequestParam("p1") String p1,
            @RequestParam("v1") String v1,
            @RequestParam("p2") String p2,
            @RequestParam("v2") String v2,
            @RequestParam("p3") String p3,
            @RequestParam("v3") String v3,
            @RequestParam("p4") String p4,
            @RequestParam("v4") String v4,
            @RequestParam("p5") String p5,
            @RequestParam("v5") String v5,
            @RequestParam("p6") String p6,
            @RequestParam("v6") String v6
    ) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("DB_HOST",dbhost);
        params.put("DB_PORT",dbport);
        params.put("DB_NAME",db);
        params.put("DB_USER",dbuser);
        params.put("DB_PASSWORD",dbpass);
        String info = connectionService.executeTestConnectionByParams(params);
        return "test: " + info;
    }

}