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
Translator consists of 3 main components:
1. A GraphBuilder uses RDF's n-triple format and outputs an edge and vertex Spark SQL DataFrame
2. Apache Jena's ARQ query processor that parses the SPARQL query-string and transforms the String to an Algebra Tree (opRoot)
3. A SPARQL to GraphFrames Translator class that walks the Algebra tree bottom up, translating the SPARQL ALgebra to GraphFrames to a GraphFrame Algebra 


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
