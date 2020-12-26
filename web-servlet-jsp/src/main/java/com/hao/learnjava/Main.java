package com.hao.learnjava;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tm = new Tomcat();
        tm.setPort(Integer.getInteger("port", 8080));
        tm.getConnector();
        Context ctx = tm.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot res = new StandardRoot(ctx);
        res.addPreResources(
                new DirResourceSet(res, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/")
        );
        ctx.setResources(res);
        tm.start();
        tm.getServer().await();
    }
}
