local key1=KEYS[1]
local key2=KEYS[2]
local key3=KEYS[3]
  redis.call("set",key1,key2)
  redis.call("expire",key1,key3)
  return true
