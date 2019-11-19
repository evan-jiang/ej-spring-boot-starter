package com.ej.redisson.spring.boot.starter.properties;

import com.ej.redisson.spring.boot.starter.enums.RedisType;

public class RedisConfig {
    private RedisType redisType;
    private String alias;
    private int dataBase = 0;

    /**哨兵**/
    private String sentinelAddress;
    private String masterName;
    private String passWord;
    /**主从**/
    private String masterAddress;
    private String slaveAddress;

    public RedisType getRedisType() {
        return redisType;
    }

    public void setRedisType(RedisType redisType) {
        this.redisType = redisType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getDataBase() {
        return dataBase;
    }

    public void setDataBase(int dataBase) {
        this.dataBase = dataBase;
    }

    public String getSentinelAddress() {
        return sentinelAddress;
    }

    public void setSentinelAddress(String sentinelAddress) {
        this.sentinelAddress = sentinelAddress;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public String getSlaveAddress() {
        return slaveAddress;
    }

    public void setSlaveAddress(String slaveAddress) {
        this.slaveAddress = slaveAddress;
    }
}
