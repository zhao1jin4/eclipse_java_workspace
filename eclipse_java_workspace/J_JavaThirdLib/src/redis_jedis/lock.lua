
-- ����ע��
--[[
 ����ע��
û�취��������ͣ
 --]]  
 
 -- SETNX �ɹ����÷���1,ʧ�����÷���0
local  isSet = redis.call('SETNX', KEYS[1], ARGV[1])
 if isSet == 1  then
    redis.call('EXPIRE', KEYS[1], ARGV[2]) 
   return "success"
 end
 return "fail"
 