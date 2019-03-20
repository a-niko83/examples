package org.grabber.lg.service.lj;

import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.util.SAXParsers;
import org.grabber.lg.config.LJProperties;
import org.springframework.stereotype.Service;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.*;

@Service
@Slf4j
public class LJClientImpl implements LJClient {
    private static final String GET_EVENTS_METHOD = "LJ.XMLRPC.getevents";
    private LJProperties props;
    private XmlRpcClient xmlRpcClient;

    public LJClientImpl(LJProperties props, XmlRpcClient xmlRpcClient) throws Exception {
        this.props = props;
        this.xmlRpcClient = xmlRpcClient;

        log.info("Config LJ service client:");
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        log.info("URL: {}", props.getUrl());
        config.setServerURL(new URL(props.getUrl()));
        xmlRpcClient.setConfig(config);
    }

    @Override
    public String load(int year, int month, int day) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("ver", 1);
        params.put("selecttype", "day");
        params.put("noprops", Boolean.TRUE);
        params.put("journal", props.getJournal());
        params.put("year", year);
        params.put("month", month);
        params.put("day", day);

        log.info("Method getevents on {}", props.getUrl());
        List<Object> register_params = new ArrayList<>();
        register_params.add(params);
        try {
            Object[] res = (Object[])xmlRpcClient.execute(GET_EVENTS_METHOD, register_params);
            Map items = (Map)res[0];
            log.info(items.toString());
            return items.toString();
        } catch (XmlRpcException e) {
            if (e.getMessage().equals("Invalid password")) {
                return "Invalid Password";
            } else {
                throw e;
            }
        }

    }
}
