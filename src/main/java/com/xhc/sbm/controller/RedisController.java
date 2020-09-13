package com.xhc.sbm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhc.sbm.bean.bo.StudentAddBo;
import com.xhc.sbm.bean.bo.StudentCache;
import com.xhc.sbm.entity.Student;
import com.xhc.sbm.exception.SbmException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis数据结构：
 * string:
 * hash: key计算hash值，一个hash下可以保存多个人 String类型的 field 、value对。
 * list: 字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部或者尾部
 * set: 无序集合，不能有重复
 * sorted set:有序集合，通过给每个元素 一个分数值，群定排序，元素不可重复
 *
 * @Author: xhc
 * @Date: 2020/4/2 17:00
 * @Description:
 */
@RestController
@RequestMapping("redis")
@Api(tags = "redis test controll")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "传入一个字符串，进行缓存20秒，返回当前缓存的内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "要缓存的值", defaultValue = "", required = true)
    })
    @GetMapping(value = "/cacheStr")
    public String test(String str){
        String test = stringRedisTemplate.opsForValue().get("test");
        if(StringUtils.isNotEmpty(test)){
            return test;
        }else{
            stringRedisTemplate.opsForValue().set("test", str, 20, TimeUnit.SECONDS);
            return str;
        }
    }

    @ApiOperation(value = "传入一个Student对象进行缓存，key是id, value是Student的json格式", notes="")
    @PostMapping(value = "/cacheStudent")
    public StudentCache cacheStudent(@RequestBody StudentCache student){
        if(student == null || student.getId() == null){
            throw new SbmException(0, "id必填");
        }
        redisTemplate.opsForValue().set(student.getId().toString(), student, 60, TimeUnit.SECONDS);
        return student;
    }

    @ApiOperation(value = "根据id从缓存中查Student对象", notes="")
    @GetMapping(value = "/getStudent/{id}")
    public StudentCache getStudent(@PathVariable Long id){
        if(id == null){
            throw new SbmException(0, "id必填");
        }
        JSONObject jsonObject = (JSONObject)redisTemplate.opsForValue().get(id.toString());
        return jsonObject.toJavaObject(StudentCache.class);
    }


    @ApiOperation(value = "传入一个Student对象进行缓存，是用hash数据结构缓存，key是id，field是属性，value是值", notes="")
    @PostMapping(value = "/cacheStudentHash")
    public StudentCache cacheStudentHash(@RequestBody StudentCache student) throws Exception{
        String id = student.getId().toString();
        Field[] fields = student.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(student);
            if(value instanceof Date){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                value = sdf.format((Date)value);
            }
            redisTemplate.opsForHash().put(id, fieldName, value);
            redisTemplate.expire(id, 60, TimeUnit.SECONDS);
        }

        return student;
    }

    @ApiOperation(value = "查询hash。通过key=id, fieldName属性名，获取属性值。对应redis的hash数据存储", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", defaultValue = "", required = true),
            @ApiImplicitParam(name = "filedName", value = "属性名", defaultValue = "", required = true)
    })
    @GetMapping(value = "/getCacheStudentHashById")
    public Object getCacheStudentHash(@RequestParam(value = "id") long id, @RequestParam(value = "filedName") String fieldName){
        Object o = redisTemplate.opsForHash().get(String.valueOf(id), fieldName);
        return o;
    }

    @ApiOperation(value = "查询全部hash，通过key=id", notes="")
    @GetMapping(value = "/getCacheStudentAllHash/{id}")
    public Map getCacheStudentAllFiled(@PathVariable Long id){
        Map entries = redisTemplate.opsForHash().entries(id.toString());
        return entries;
    }



    @ApiOperation(value = "将一个字符串存入缓存List，返回从左侧起的List内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "direction", value = "方向，l左，r右", defaultValue = "", required = true),
            @ApiImplicitParam(name = "key", value = "List的key", defaultValue = "", required = true),
            @ApiImplicitParam(name = "str", value = "放入的值", defaultValue = "", required = true)
    })
    @PostMapping(value = "/cacheStrToList")
    public List<String> cacheStrToList(String direction, String key, String str){
        ListOperations oper = redisTemplate.opsForList();
        if(direction.equalsIgnoreCase("l")){
            oper.leftPush(key, str);
        }else{
            oper.rightPush(key, str);
        }
        List range = oper.range(key, 0, oper.size(key));
        return range;
    }


    @ApiOperation(value = "从缓存List中弹出一个元素", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "direction", value = "方向，l左，r右", defaultValue = "", required = true),
            @ApiImplicitParam(name = "key", value = "List的key", defaultValue = "", required = true)
    })
    @GetMapping(value = "/popStrFromList")
    public String popStrFromList(String direction, String key){
        ListOperations oper = redisTemplate.opsForList();
        if(direction.equalsIgnoreCase("l")){
            return (String)oper.leftPop(key, 10, TimeUnit.SECONDS);
        }else{
            return (String)oper.rightPop(key, 10, TimeUnit.SECONDS);
        }
    }


    @ApiOperation(value = "缓存一个字符创到Set，返回当前set内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "缓存的key", defaultValue = "", required = true),
            @ApiImplicitParam(name = "str", value = "缓存的字符串", defaultValue = "", required = true)
    })
    @PostMapping(value = "/cacheSetStr")
    public Set cacheSetStr(String key, String str){
        redisTemplate.opsForSet().add(key, str);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        Set members = redisTemplate.opsForSet().members(key);
        return members;
    }

    @ApiOperation(value = "删除缓存Set中的一个元素，返回当前set内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "缓存的key", defaultValue = "", required = true),
            @ApiImplicitParam(name = "str", value = "要删的字符串", defaultValue = "", required = true)
    })
    @PostMapping(value = "/delCacheSet")
    public Set delCacheSet(String key, String str){
        redisTemplate.opsForSet().remove(key, str);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        Set members = redisTemplate.opsForSet().members(key);
        return members;
    }


    @ApiOperation(value = "缓存一个字符创到Sorted Set，返回当前set内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "缓存的key", defaultValue = "", required = true),
            @ApiImplicitParam(name = "str", value = "缓存的字符串", defaultValue = "", required = true),
            @ApiImplicitParam(name = "score", value = "缓存字符串的分数", defaultValue = "", required = true)
    })
    @PostMapping(value = "/cacheSortedSet")
    public Set cacheSortedSet(String key, String str, Double score){
        redisTemplate.opsForZSet().add(key, str, score);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        Set set = redisTemplate.opsForZSet().rangeWithScores(key, 0, Long.MAX_VALUE);
        return set;
    }


    @ApiOperation(value = "删除一个Sorted Set的值，返回当前set内容", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "缓存的key", defaultValue = "", required = true),
            @ApiImplicitParam(name = "str", value = "删除的字符串", defaultValue = "", required = true)
    })
    @PostMapping(value = "/delCacheStoredSet")
    public Set delCacheStoredSet(String key, String str){
        redisTemplate.opsForZSet().remove(key, str);
        Set set = redisTemplate.opsForZSet().rangeWithScores(key, 0, Long.MAX_VALUE);
        return set;
    }


    @ApiOperation(value = "spring cache-@Cacheable 获得一个输入字符串一样的字符串", notes="")
    @GetMapping(value = "/getSameStr")
    @Cacheable(value = "getSameStr", key = "#p0+#p1")  //#root.targetClass.getName() + #root.methodName + #str
    public String getSameStr(String str1, String str2){
        System.out.println("调用了getSameStr方法");
        return String.format("str1=%s,str2=%s", str1, str2);
    }

    @ApiOperation(value = "spring cache-@CachePut 获得一个输入字符串一样的字符串，每次都写入缓存", notes="")
    @GetMapping(value = "/putSameStr")
    @CachePut(value = "putSameStr", key = "#p0+#p1")
    public String putSameStr(String str1, String str2){
        System.out.println("putSameStr");
        return String.format("str1=%s,str2=%s", str1, str2);
    }

    @ApiOperation(value = "spring cache-@CacheEvict 获得一个输入字符串一样的字符串，删除缓存", notes="")
    @GetMapping(value = "/removeSameStr")
    @CacheEvict(value = "removeSameStr", key = "#p0+#p1")
    public String removeSameStr(String str1, String str2){
        System.out.println("removeSameStr");
        return String.format("str1=%s,str2=%s", str1, str2);
    }
}
