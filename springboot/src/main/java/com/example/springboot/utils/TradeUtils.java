package com.example.springboot.utils;

import com.example.springboot.mapper.BusinessMapper;
import com.example.springboot.mapper.ConsumerMapper;
import com.example.springboot.pojo.business.Business;
import com.example.springboot.pojo.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TradeUtils {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private BusinessMapper businessMapper;

    //存钱
    public String saveMoney(){
        return null;
    }

    //取钱
    public String getMoney(){
        return null;
    }

    /*转账（付款人，金额，收款人）*/

    //（消费者对商家进行付款操作）
    public String consumerSendMoneyToBusiness(String consumerId, double money, int storeId){
        //查看消费者的信息（余额）
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);
        double balance = consumer.getBalance();
        if(balance<money){
            return "余额不足，付款失败";
        }
        //更新消费者的信息
        consumer.setConsumeMoney(consumer.getConsumeMoney() + money);
        consumer.setBalance(balance - money);
        consumerMapper.updateConsumer(consumer);

        //更新商家的信息
        Business business = businessMapper.selectBusinessByStoreId(storeId);
        business.setBalance(business.getBalance() + money);
        business.setProfitMoney(business.getProfitMoney() + money);
        businessMapper.updateBusiness(business);

        return LocalDateTime.now()+"  ----  您向手机号为："+business.getMobile()+"的商家转账成功"+money+"元";
    }

    //商家对消费者进行付款操作
    public String businessSendMoneyToConsumer(int storeId, double money, String consumerId){
        //查看商家的信息（余额）
        Business business = businessMapper.selectBusinessByStoreId(storeId);
        double balance = business.getBalance();
        if(balance<money){
            return "商家余额不足，请联系商家退款";
        }

        //更新商家的信息
        business.setBalance(business.getBalance() - money);
        business.setProfitMoney(business.getProfitMoney() - money);
        businessMapper.updateBusiness(business);

        //更新消费者的信息
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);
        consumer.setConsumeMoney(consumer.getConsumeMoney() - money);
        consumer.setBalance(balance + money);
        consumerMapper.updateConsumer(consumer);


        return LocalDateTime.now()+"  ----  手机号为："+business.getMobile()+"的商家向您退款了"+money+"元";
    }

    //商家向管理员进行付款操作
    public String businessSendMoneyToManager(int storeId, double money, String managerId){
        return null;
    }
}
























