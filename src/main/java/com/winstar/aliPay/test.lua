if (redis.call('get', 'name') == 'stu1') then
    local a = redis.call("hmset", "account1", "address", "beijing");
    return a;
else
    return 0;


end
