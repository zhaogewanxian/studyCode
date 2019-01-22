package com.wanxian.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicConsumerDemo {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://www.rockrolltime.cn:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("testTopic");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            System.out.printf("consumer收到消息:" + textMessage.getText());
            session.commit();
            session.rollback();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
