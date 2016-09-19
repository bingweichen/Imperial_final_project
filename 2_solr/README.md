###Commands
Start
`bin/solr start -e cloud -noprompt`

Delete All
`bin/post -c gettingstarted -d "<delete><query> * : *</query></delete>"`

Delete
`bin/post -c gettingstarted -d "<delete><id>40</id></delete>"`

Stop All
`bin/solr stop -all`

Post
`bin/post -c gettingstarted /Users/chen/Documents/individulal_project/data/testjson/*.json`

###API
Search
```
http://54.171.189.58:8983/solr/gettingstarted/select?indent=on&q=*:*&wt=json
```

Update
```
curl -X POST -H 'Content-type:application/json' --data-binary '[
{
  "id": "bsdsds"
  }
]' http://54.171.189.58:8983/solr/gettingstarted/update
```

Delete ALL
```
   curl -X POST -H 'Content-type:text/xml' --data-binary "<delete><query>*:*</query></delete>"  http://54.171.189.58:8983/solr/gettingstarted/update
```

Delete
```
curl -X POST -H 'Content-type:text/xml' --data-binary "<delete><query>id:5555 6666</query></delete>"  http://54.171.189.58:8983/solr/gettingstarted/update
```

###Normal mode

`$ bin/solr start`

`$ bin/solr start -p 8984`

`$ bin/solr create -c <name>`


###Solution for Commands Argument too long
`find /Users/chen/Documents/individulal_project/data/json -name '*.json' -exec /Users/chen/project_run/solr/solr-6.1.0_2/bin/post -c  gettingstarted {} \;`

Using for upload all JSON file 