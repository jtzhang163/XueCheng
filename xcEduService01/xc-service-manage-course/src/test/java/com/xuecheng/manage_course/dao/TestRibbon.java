package com.xuecheng.manage_course.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRibbon {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void ribbonTest() {

        String serviceId = "XC-SERVICE-MANAGE-CMS";

        //http://localhost:31001/cms/page/get/5a795ac7dd573c04508f3a56
        ResponseEntity<Map> map = restTemplate.getForEntity("http://" + serviceId + "/cms/page/get/5a795ac7dd573c04508f3a56", Map.class);

        System.out.println(map);
    }
}
