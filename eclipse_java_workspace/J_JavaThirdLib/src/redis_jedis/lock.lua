
-- 单行注释
--[[
 多行注释
没办法在这里暂停
 --]]  
 
 -- SETNX 成功设置返回1,失败设置返回0
local  isSet = redis.call('SETNX', KEYS[1], ARGV[1])
 if isSet == 1  then
    redis.call('EXPIRE', KEYS[1], ARGV[2]) 
   return "success"
 end
 return "fail"
 