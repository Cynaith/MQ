package com.ly.messagequeue.Topic;

import cn.hutool.core.util.NetUtil;

import javax.swing.*;

public class TopicUtils {
    public static void main(String[] args) {
        checkServer();
    }
    public static void checkServer() {
        if(NetUtil.isUsableLocalPort(15672)) {
            JOptionPane.showMessageDialog(null, "RabbitMQ 服务器未启动 ");
            System.exit(1);
        }
    }
}
