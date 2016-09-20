## Final site:

146.169.47.62

ssh bc815@cloud-vm-47-62.doc.ic.ac.uk

1. install java in ubuntu

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
```

2. download Solr

   wget http://mirror.catn.com/pub/apache/lucene/solr/6.2.0/solr-6.2.0.tgz

   tar -zxvf solr-6.2.0.tgz

3. download JSON

   In my dropbox  bingwei.chen11@gmail.com

    [https://www.dropbox.com/sh/aqy4k19az24b0in/AADeJucNMZE2UcgY2nME6F0-a?dl=0](https://www.dropbox.com/sh/aqy4k19az24b0in/AADeJucNMZE2UcgY2nME6F0-a?dl=0)

    https://www.dropbox.com/s/mjtbdfduwp9nb1l/json_final.zip?dl=0

Eg.

```
scp -r /media/disk/summer_pics/ mike@192.168.1.1:"/var/www/Summer 2008/"
```

4. index paper in JSON to Solr

   `find /Users/chen/Documents/individulal_project/data/json -name '*.json' -exec /Users/chen/project_run/solr/solr-6.1.0_2/bin/post -c gettingstarted {} \;`

5. CORS of solr

   /solr/solr-6.1.0_2/server/etc/webdefault.xml

   add this to webdefault.xml before the </web-app>

   ​

   <filter>

         <filter-name>cross-origin</filter-name>
         <filter-class>org.eclipse.jetty.servlets.CrossOriginFilter</filter-class>
         <init-param>
             <param-name>chainPreflight</param-name>
             <param-value>false</param-value>
         </init-param>

     </filter>
     <filter-mapping>
         <filter-name>cross-origin</filter-name>
         <url-pattern>/*</url-pattern>
     </filter-mapping>

   ​

6. install web

   `git clone https://github.com/bingweichen/Search_Web.git`

   install node.js

   ```
   curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
   sudo apt-get install -y nodejs
   ```

   `npm install`

   `npm start`

7. download java_api_VM_1.0.jar from VM file

   `java -jar java_api_VM_1.0.jar`

now you can access the website through the URL 146.169.47.62.8989



## bingwei:

146.169.45.108

ssh bc815@cloud-vm-45-108.doc.ic.ac.uk

password: Xu37

## test:

146.169.45.51

ssh bc815@cloud-vm-45-51.doc.ic.ac.uk

