/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.test.simple;

import com.example.test.controller.MessagingProducer;
import com.example.test.data.SimpleRequest;
import com.example.test.dto.UploadRequest;
import com.example.test.dto.UploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.test.simple.service.HelloWorldService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.test")
@RestController
public class SampleSimpleApplication {
    private final static String TOPIC_NAME = "first-topic";
    @Autowired
    private HelloWorldService helloWorldService;
    @Autowired
    private MessagingProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(SampleSimpleApplication.class, args);
    }

    @RequestMapping(value = "/store")
    public String upload() {
        try {
            System.out.println("Attempting to send message");
            producer.sendMessage("TEST ME", TOPIC_NAME);
            SimpleRequest request = new SimpleRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Uploaded";
    }
    @RequestMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public UploadResponse uploadData(@Valid @RequestBody UploadRequest uploadRequest){
        System.out.println("Recieved Request " + uploadRequest);
        UploadResponse response = new UploadResponse();
        response.setMessage("PROCESSING");
        return response;
    }

    @RequestMapping(value = "/")
    public String health() {
        return "OK!";
    }
}
