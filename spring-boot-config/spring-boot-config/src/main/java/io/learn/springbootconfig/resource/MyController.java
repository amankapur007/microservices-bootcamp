package io.learn.springbootconfig.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class MyController {
    @Value("${my.app.name}")
    private String appName;

    @Value("${my.app.list}")
    private List<String> myList;

    @Value("#{${my.app.map}}")
    private Map<String, String> myMap;

    @Autowired
    private DbSettings dbSettings;

    @RequestMapping("/")
    public String controller(){
        return  appName +" "+ myList +" "+ myMap+ " "+dbSettings.getConnection()+" "+dbSettings.getPort();
    }
}
