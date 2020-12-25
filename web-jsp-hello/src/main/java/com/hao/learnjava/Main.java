package com.hao.learnjava;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot res = new StandardRoot(ctx);
        res.addPreResources(
                new DirResourceSet(res, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/")
        );
        ctx.setResources(res);
        tomcat.start();
        tomcat.getServer().await();
    }
}
