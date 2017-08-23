# SPARQL-to-GraphFrames
SPARQL-to-GraphFrames is an open-source compiler that allows to run SPARQL queries on large RDF graphs using the Apache Spark GraphFrames package.
The Translator uses RDF datasets in n-triple format and SPARQL query strings as it's input. Test RDF datasets can be generated using the Berlin SPARQL Benchmark (BSBM)-data generator:
http://wifo5-03.informatik.uni-mannheim.de/bizer/berlinsparqlbenchmark/spec/BenchmarkRules/index.html#datagenerator


Introduction
---
Living in a higly connected world, a large amount of related data is created on a daily base. Nevertheless the fact that these relationships provide valuable information, most relationships get lost once the data is stored in a database. The graph data stored in graph databases face this challenge of isolated information by addressing relationships as their first class citizens.
Graphs allow complex queries and to help understand the impact of some related data that otherwise may have never been explored.
GraphFrames, an Apache Spark compatible package uses Spark's lightning fast computing and ability to run on top of various distributed filesystems and databases makes it a great tool for Big Data applications using the graph data model.
For full Blogpost, see:


Architecture
---
Translator consists of 3 main components:
1. A GraphBuilder uses RDF's n-triple format and outputs an edge and vertex Spark SQL DataFrame
2. Apache Jena's ARQ query processor that parses the SPARQL query-string and transforms the String into an Algebra tree(opRoot) by applying a Visitor to each Algebra Expression. For more information about the Visitor Pattern, see https://en.wikipedia.org/wiki/Visitor_pattern
3. A SPARQL-to-GraphFrames Translator(SparqlToGfTranslator) class that walks the Algebra tree bottom up, translating the SPARQL ALgebra into a GraphFrame Algebra

Core functionality of the Query translation is applying a Visitor(see "2." for more information about Visitor pattern) to each Algebra expression by extending ARQ's OpVisitorBase class. That way the main Algebra element classes(e.g OpBGP) don't have to be modified themselves.
If another modification needs to be done on the Algebra tree, just extend the OpVisitorBase class and imply methods for modification.

At this point the following Algebra epressions can be translated into a GraphFrame query:

**BGP:**

* Enable basic pattern usage - DONE
* TBD: double-directed triples with same edge-relation (e.g. "?s ?p ?o . ?o ?p ?s" )

**BGP-filter:**

* Basic Filtering - DONE
* Enable URI usage - DONE
* TBD: PREFIX Mapping 

**Projection**

* Single variable projection - DONE
* TBD: multi-variable projection

Testing:
---
* 2.000.000+ triples have been queried in a single node setup 
* Querying multiple triples in BGP
* Quering multiple Projections in BGP

**Next steps:**

* Test application on HDFS cluster setup
* Test application with larger datasets(goal = 10 Billion triples)










