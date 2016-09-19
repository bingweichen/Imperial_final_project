## bingwei:

146.169.45.108

ssh bc815@cloud-vm-45-108.doc.ic.ac.uk

password: Xu37

## test:

146.169.45.51

ssh bc815@cloud-vm-45-51.doc.ic.ac.uk



## survey:

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



上传solr库

find /Users/chen/Documents/individulal_project/data/json -name '*.json' -exec /Users/chen/project_run/solr/solr-6.1.0_2/bin/post -c gettingstarted {} \;