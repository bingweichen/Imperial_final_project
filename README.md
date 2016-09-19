####porject title: Scientific survey creation
####author: bingwei chen 
####keywords: storyline,coherence graph, serach engine, web application, visualization

This is a project which can show stroyline graph of search keywords on web application. 

![](https://raw.githubusercontent.com/bingweichen/Imperial_final_project/master/image/flowchart/imp_overview.png )

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
In last year, the data of ACL was crawled and processed to XML by Lingyu Lu.
In this step, all data of paper in XML will be convert to JSON and the information of date and url of each paper will be added.

XML&&JSON Download url:
https://www.dropbox.com/sh/aqy4k19az24b0in/AADeJucNMZE2UcgY2nME6F0-a?dl=0

XML: SectLabel,PharseHed,ParsCit

JSON: id,title,author,url,date,content,abstract, references

download:

​	 json-simple-1.1.1.jar

​	mysql-connector-java-5.1.39-bin.jar

###Solr
All commands and API in README.md of Solr file.

![](https://raw.githubusercontent.com/bingweichen/Imperial_final_project/master/image/flowchart/Implementataion_overview.png)

1. Download Solr from: http://www.apache.org/dyn/closer.lua/lucene/solr/6.2.0
2. using commands and API
     1. construct Solr 
     2. upload schema
     3. post all papers of JSON to Solr
3. change the webdefault.xml of jetty to solve cross domain problem

After finish this, the solr api can be used to get required paper in JSON.
###Coherence graph java

1. get vaild paper data in JSON from Solr
2. construct graph by adding each paper to vertex
3. adding citation label edges
4. adding same author label edges
5. normalize
6. adding ancestral label edges
7. construct coherence graph using divide-and-conquer algorithm

download: 

​	apache-httpcomponents-httpclient.jar

​	apache-httpcomponents-httpcore.jar

​	httpclient-4.3-beta1.jar

​	java-json.jar

​	json-simple-1.1.1.jar

detail:

1. get vaild paper data in JSON from Solr
   1. sent http request to Solr using the search query
   2. store the returned JSON object which contains all vaild paper
2. construct graph by adding each paper to vertex
   1. create MyGraph object which contains Vertex and Node
   2. Vertex used for store the object of paper and all Nodes of this Vertex
   3. Nodes used for edge between two Vertex
3. adding citation label edges
   1. compare the title and reference title
   2. using word segmentation and compare alogrithm to return a score
   3. if score exceed threshold value, add edge with citation label from cited paper to citing paper
   4. the weight of the edge depend on the score of paper cited
4. adding same author label edges
   1. compare the authors of each paper
   2. add edge with same author label from earlier paper to latest if they have at least one same author
   3. the weight of the edge depend on the score of paper published earlier.
5. normalize
   1. for each Vertex, add all weight of citation label point to this Vertex to Z(normalization constant)
   2. for each Vertex, calculate the number of edges l point to this Vertex with same author label.
   3. add the weight of same author label divided by l to Z
   4. for each edge of this Vertex, 
6. adding ancestral label edges
   1. monteCarloSimulation
   2. generateRandomsubgraph
7. construct coherence graph using divide-and-conquer algorithm
   1. get all pairs which coherence exceed the threshold value
   2. using chain object to store these pair of paper and put the chain into priorty queue
   3. collect the chain with highest coherence and extend it if it is short than length of sub-chain
   4. connect chains in same storyline

###Java API Server

1. set up Spark

    https://sparktutorials.github.io/2015/04/02/setting-up-a-spark-project-with-maven.html

2. implement CORS in spark

    https://sparktutorials.github.io/2016/05/01/cors.html

###Web

React

1. download ant-design

    https://github.com/ant-design/ant-design

2. install npm

3. install node.js

4. `npm install antd`

5. `npm start`

D3.js

1.   download from 

     https://github.com/d3/d3.git

2.   using force layout





debug

1. change same author label function

   edge from earlier to latest

2. change monte carlo function

   same ancestor x->c1 and x->c2






