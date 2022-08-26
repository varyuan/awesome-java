package com.varyuan.awesome.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public final class IpUtil {

    /**
     * @return 本机在所有网卡的所有ip
     * @throws SocketException
     */
    public static List<String> getLocalIpList() throws SocketException {
        List<String> list = new LinkedList<>();
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            // 网络适配器/网卡 包含网络接口名称与IP地址列表
            NetworkInterface network = enumeration.nextElement();
            // 遍历该网卡下所有ip
            Enumeration<InetAddress> addresses = network.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                // 只使用ipv4
//                    if ((address instanceof Inet4Address || address instanceof Inet6Address)) {
                if ((address instanceof Inet4Address)) {
                    String hostAddress = address.getHostAddress();
                    // 去除
                    if ("127.0.0.1".equals(hostAddress)) {
                        continue;
                    }
                    list.add(hostAddress);
                }
            }
        }
        return list;
    }

    /**
     * 从http请求头中获取客户端ip
     *
     * @param request http请求
     * @return 客户端ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
