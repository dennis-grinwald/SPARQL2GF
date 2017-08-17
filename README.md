# SPARQL-to-GraphFrames
SPARQL-to-GraphFrames is an open-source compiler that allows to run SPARQL queries on large RDF graphs using the Apache Spark GraphFrames package.
The Translator uses RDF datasets in n-triple format and SPARQL query strings as it's input. Test RDF datasets can be generated using the Berlin SPARQL Benchmark (BSBM)-data generator, see http://wifo5-03.informatik.uni-mannheim.de/bizer/berlinsparqlbenchmark/spec/BenchmarkRules/index.html#datagenerator

testing: 
* 2.000.000+ triples have been queried in a single node setup
* BGP, Projection queries are 

current status:
* Application is being tested on HDFS cluster setup

Introduction
---
Living in a higly connected world, a large amount of related data is created on a daily base. Nevertheless the fact that these relationships provide valuable information, most relationships get lost once the data is stored in a database. The graph data stored in graph databases face this challenge of isolated information by addressing relationships as their first class citizens.
Graphs allow complex queries and to help understand the impact of some related data that otherwise may have never been explored.
GraphFrames, an Apache Spark compatible package uses Spark's lightning fast computing and ability to run on top of various distributed filesystems and databases makes it a great tool for Big Data applications using the graph data model.


Architecture
---
The SPARQL-to-GraphFrames Translator queries and RDF graph using the SPARQL querying language:
1. The RDF graph, which is represented in n-triple format is split into and vertex and edge Spark SQL DataFrame and concatenated to a GraphFrame, representing the Graph of data
2. A SPARQL-query created by the client is translated into GraphFrames Query Language
3. The GraphFrame is queried and shows the output as a DataFrame - another GraphFrame can be created using that DataFrame


MotifPattern:
---
* Enable basic pattern usage - DONE
* TBD: enable double-directed triples (e.g. "?s ?p ?o . ?o ?p ?s" )

MotifFilter:
---
* Basic Filtering - DONE
* Enable URI usage - DONE
* TBD: PREFIX Mapping 

MotifProjection 
---
* Single variable projection works!
* TBD: multi-variable projection
