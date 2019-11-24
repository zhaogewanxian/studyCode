package com.wanxian.repository;

import com.wanxian.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userRepositoty = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean save(User user) {
        redisTemplate.opsForValue().set(user.getId(), user);
        return true;
//        return userRepositoty.putIfAbsent(user.getId().toString(), user) == null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User getUser(String id) {
        List<String> keys = new ArrayList<>();
        keys.add("test");
        List<String> arggs = new ArrayList<>();
        arggs.add("6000");
        arggs.add("5");
        System.out.println(redisTemplate.execute(idScript, keys, arggs));
        return (User) redisTemplate.opsForValue().get(id);
//        return userRepositoty.get(id);
    }

    private void luaScript() {


    }

    private static final String LUA_SCRIPT =
            "local l  = redis.call('incr', KEYS[1]);\n" +
                    "if l ==1 then\n" +
                    "redis.call('expire', KEYS[1], ARGV);\n" +
                    "if l > ARGV[1] then\n" +
                    "redis.call('set', flag,1);\n" +
                    "end;\n" +
                    "return redis.call('INCR', KEYS[0]);\n";

    public static final String SCRIPT =
            "local t1 = redis.call('incr',KEYS[1]);" + "\n" +
                    "if t1 == '1' then" + "\n" +
                    "return t1;" + "\n" +
                    "end;" + "\n";


    public static final String SCRIPT1 =
            "local t1 = redis.call('exists',KEYS[1]);" + "\n" +
                    "if t1 == true then" + "\n" +
                    "return t1;" + "\n" +
                    "end;" + "\n";

    public static final String SCRIPT2 =
            "local t1 = redis.call('get',KEYS[1]);" + "\n" +
                    "if t1 == 'null' and t1 >'10' then" + "\n" +
                    "return t1;" + "\n" +
                    "else " +
                    "local t2 = redis.call('INCR',KEYS[1]);" + "\n" +
                    "if  t2 == '1'  then" + "\n" +
                    "redis.call('set','flag','1');" + "\n" +
                    "end;" + "\n";

    private final static String lua = "local num=redis.call('incr',KEYS[1])\n" +
            "if tonumber(num)==1 then\n" +
            "\tredis.call('expire',KEYS[1],ARGV[1])\n" +
            "\treturn 1\n" +
            "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
            "\treturn 0\n" +
            "else \n" +
            "\treturn 1\n" +
            "end";


    private final static String lua1 =
            "local num=redis.call('incr',KEYS[1])\n" +
                    "if tonumber(num) == tonumber(1) then\n" +
                    "\tredis.call('expire',KEYS[1],ARGV[1])\n" +
                    "\treturn 1\n" +
                    "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                    "\treturn 0\n" +
                    "else \n" +
                    "\treturn 1\n" +
                    "end";

    public static final String SCRIPT123 =
            "local t1 = redis.call('incr',KEYS[1]);" + "\n" +
                    "if t1 == 1 then" + "\n" +
                    "redis.call('expire',KEYS[1],ARGV[1]);" + "\n" +
                    "return 111;" + "\n" +
                    "end;\n" +
                    "if t1 > 2 then" + "\n" +
                    "redis.call('set','flag','ARGV[3]');" + "\n" +
                    "return 222;" + "\n" +
                    "end;" + "\n" +
                    "return 333;";

    public static final String SCRIPT1234 =
            "local t1 = redis.call('exists',KEYS[1]);" + "\n" +
                    "end;" + "\n" +
                        "return t1;" + "\n";



    private final static String newLua = "local num=redis.call('incr',KEYS[1])\n" +
            "if tonumber(num)==1 then\n" +
            "\tredis.call('expire',KEYS[1],ARGV[1])\n" +
            "\treturn 1\n" +
            "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
            "\treturn 2\n" +
            "else \n" +
            "\treturn 3\n" +
            "end";

    private final RedisScript<Long> idScript = new DefaultRedisScript<>(newLua, Long.class);

}
