package com.wanxian.repository;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class LuaDemo {

    private final static String lua = "local num=redis.call('incr',KEYS[1])\n" +
            "if tonumber(num)==1 then\n" +
            "\tredis.call('expire',KEYS[1],ARGV[1])\n" +
            "\treturn 1\n" +
            "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
            "\treturn 2\n" +
            "else \n" +
            "\treturn 3\n" +
            "end";
    /**
     * 这是将脚本提取到外面为常量，用 jedis.evalsha()加载
     */
    public static void main(String[] args) {
        Jedis jedis = RedisManager.getJedis();
        List<String> keys = new ArrayList<>();
        keys.add("test");
        List<String> arggs = new ArrayList<>();
        arggs.add("6000");
        arggs.add("5");
        String luaLoad = jedis.scriptLoad(lua);
        System.out.println(luaLoad);
        Object obj = jedis.evalsha(luaLoad,keys,arggs);
        System.out.println(obj);


    }

    /**
     * 这是直接将脚本写死在代码中，用 jedis.eval()
     */
    public static void method(){
        Jedis jedis = RedisManager.getJedis();
        String lua = "local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "\tredis.call('expire',KEYS[1],ARGV[1])\n" +
                "\treturn 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "\treturn 0\n" +
                "else \n" +
                "\treturn 1\n" +
                "end";
        List<String> keys = new ArrayList<>();
        keys.add("ip:limit:127.0.0.1");
        List<String> arggs = new ArrayList<>();
        arggs.add("6000");
        arggs.add("10");
        Object obj = jedis.eval(lua,keys,arggs);
        System.out.println(obj);
    }
}