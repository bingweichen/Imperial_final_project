###本地指令
开启
bin/solr start -e cloud -noprompt

删除所有
bin/post -c gettingstarted -d "<delete><query> * : *</query></delete>"

删除
bin/post -c gettingstarted -d "<delete><id>40</id></delete>"

停止所有
bin/solr stop -all

本地上传
bin/post -c gettingstarted /Users/chen/Documents/individulal_project/data/testjson/*.json

###API
搜索
```
http://54.171.189.58:8983/solr/gettingstarted/select?indent=on&q=*:*&wt=json
```

上传
```
curl -X POST -H 'Content-type:application/json' --data-binary '[
{
  "id": "bsdsds"
  }
]' http://54.171.189.58:8983/solr/gettingstarted/update
```

删除所有
```
   curl -X POST -H 'Content-type:text/xml' --data-binary "<delete><query>*:*</query></delete>"  http://54.171.189.58:8983/solr/gettingstarted/update
```

删除
```
curl -X POST -H 'Content-type:text/xml' --data-binary "<delete><query>id:5555 6666</query></delete>"  http://54.171.189.58:8983/solr/gettingstarted/update
```

###非网络模式

$ bin/solr start

$ bin/solr start -p 8984

$ bin/solr create -c <name>


###指令Argument too long解决方案
find /Users/chen/Documents/individulal_project/data/json -name '*.json' -exec /Users/chen/project_run/solr/solr-6.1.0_2/bin/post -c  gettingstarted {} \;

用于一次性上传所有JSON文件