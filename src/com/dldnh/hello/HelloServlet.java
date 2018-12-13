package com.dldnh.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HelloServlet extends javax.servlet.http.HttpServlet {
    private String getAddress() {
        StringBuilder str = new StringBuilder();
        try {
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                if (iface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addrs = iface.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    byte[] bytes = addr.getAddress();
                    if (bytes.length != 4) {
                        continue;
                    }
                    if (str.length() > 0) {
                        str.append(", ");
                    }
                    for (int i = 0; i < 4; i++) {
                        int n = bytes[i] & 0xff;
                        str.append(n);
                        if (i < 3) {
                            str.append(".");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return str.toString();
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        final PrintWriter w = response.getWriter();
        w.println("Hello, world! " + getAddress());
    }
}
