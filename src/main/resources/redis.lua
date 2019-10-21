
local current=redis.call('setNX',keys[1],ARGV[1])
if current == 1 then
   redis.call('expire', KEYS[1], ARGV[2],ARGV[3])
   return true
end
return false


