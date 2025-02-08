package com.jtsolv.jtsolvcurr.kafka;


import com.jtsolv.jtsolvcurr.mysql.JTSolvConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}