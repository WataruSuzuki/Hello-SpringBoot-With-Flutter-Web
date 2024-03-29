package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Value("${datapath}")
    private String datapath;

    private Map<String, String> getServerData() {
        File path = new File(datapath);

        Map<String, String> result = new HashMap<>();

        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                try {
                    FileInputStream fis = new FileInputStream(files[i]);
                    result.put(files[i].getName(), new BufferedReader(new InputStreamReader(fis)).lines().collect(Collectors.joining("\n")));

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DataService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }

    public List<ServerInfo> getServerInfo() {
        List<ServerInfo> result = new ArrayList<>();
        Map<String, String> serverData = getServerData();
        for (Map.Entry<String, String> info : serverData.entrySet()) {
            result.add(new ServerInfo(info.getKey(), map(info.getValue())));
        }
        return result;
    }

    private static List<ServiceInfo> map(String data) {
        List<ServiceInfo> result = new ArrayList<>();
        if (!data.isEmpty() && !data.startsWith("error")) {
            String[] split = data.split(",");
            for (String service : split) {
                String[] status = service.split(":");
                Boolean b = false;
                if (Integer.valueOf(status[1])>0) {
                    b = true;
                }
                result.add(new ServiceInfo(status[0], b));
            }
        }
        return result;

    }
}