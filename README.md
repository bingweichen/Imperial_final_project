####porject title: Scientific survey creation
####author: bingwei chen 
####keywords: storyline,coherence graph, serach engine, web application, visualization


This is a project which can show stroyline graph of search keywords on web application. 

![image](https://raw.githubusercontent.com/bingweichen/Imperial_final_project/master/image/flowchart/overview%20-%20Page%201.png =500x)

this is the overview of this project.

process:

1. get the query of user input from website
2. sent the query to solr server return vaild papers sorted by score in JSON 
3. the web application show the information of these papers to users
4. sent the query to Java Restful API server. after get the papers' information from Solr, the Java progrom will process the data and constract coherence graph which will be return to website.
5. after receive the coherence graph, the information will be visualized by D3

it will be implemented by fellow 5 steps:

1. Pre-process
2. Solr
3. Coherence graph java
4. Java api server
5. Web

###Pre-process
In last year, the data of ACL was crawed and processed to XML by Lingyu Lu.
In this step, all data of paper in XML will be convert to JSON and the information of date and url of each paper will be added.

XML&&JSON Download url:
https://www.dropbox.com/sh/aqy4k19az24b0in/AADeJucNMZE2UcgY2nME6F0-a?dl=0

XML: SectLabel,PharseHed,ParsCit

JSON: id,title,author,url,date,content,abstract, references
###Solr
All commands and API in README.md of Solr file.

![image](https://raw.githubusercontent.com/bingweichen/Imperial_final_project/master/image/flowchart/solr%20-%20Page%201%20(1).png=500x)

1. download solr from: http://www.apache.org/dyn/closer.lua/lucene/solr/6.2.0
2. using commands and API
 		1. construct Solr 
		2. upload schema
		3. post all papers of JSON to Solr		
3. change the webdefault.xml of jetty to solve CROS problem

After finish this, the solr api can be used to get required paper in JSON.
###Coherence graph java






