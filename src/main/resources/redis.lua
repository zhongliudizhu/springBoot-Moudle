local limitRqNum = tonumber(ARGV[1]) --每秒上限访问个数（获取java调用脚本阶段传入的参数）
local currRqNum = tonumber(redis.call('get', KEYS[1])) --获取当前redis中当前秒内已经存储的请求总数
if currRqNum + 1 > limitRqNum then
    --超出流量限制大小的话
    return 0
else
    redis.call('INCRBY', KEYS[1], "1") --将请求计数+1
    redis.call('EXPIRE', KEYS[1], "2")
    return 1
end
