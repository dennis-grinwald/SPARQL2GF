# SPARQL-to-GraphFrames
SPARQL-to-GraphFrames is an open-source compiler that allows to run SPARQL queries on large RDF graphs using the Apache Spark GraphFrames package.
The Translator uses RDF datasets in n-triple format and SPARQL query strings as it's input. Test RDF datasets can be generated using the Berlin SPARQL Benchmark (BSBM)-data generator:
http://wifo5-03.informatik.uni-mannheim.de/bizer/berlinsparqlbenchmark/spec/BenchmarkRules/index.html#datagenerator


Introduction
---
Living in a highly connected world a large amount of related data is created on a daily basis. Nevertheless the fact that these relationships enable us to have an idea of the impact, that some actions cause, they often get lost once the data is stored in common relational tables or other general NoSQL data models.
For full Blogpost, see:
https://medium.com/@dgrinwald93/make-data-connected-again-9a56627601b4

Architecture
---
Translator consists of 3 main components:
1. A GraphBuilder uses RDF's n-triple format and outputs an edge and vertex Spark SQL DataFrame
2. Apache Jena's ARQ query processor that parses the SPARQL query-string and transforms the String into an Algebra tree(opRoot) by applying a Visitor to each Algebra Expression. For more information about the Visitor Pattern, see https://en.wikipedia.org/wiki/Visitor_pattern
3. A SPARQL-to-GraphFrames translator(SparqlToGfTranslator) class that walks the Algebra tree bottom up, translating the SPARQL ALgebra into a GraphFrame Algebra

Core functionality of the Query translator is applying a Visitor(see "2." for more information about Visitor pattern) to each Algebra expression by extending ARQ's OpVisitorBase class. That way the main Algebra element classes(e.g OpBGP) don't have to be modified themselves.
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

Quickstart
---
Please apply following steps to use the translator:

1. Pull repository
2. Run build on pom.xml-file
3. Open class: SPARQL2GF/sparql2gf/src/main/java/sparql2gf_client/TestClient_RDF_noPrefix.java 
4. Run the various test-querystrings starting at line 67

**Next steps:**

* Test application on HDFS cluster setup
* Test application with larger datasets(goal = 10 Billion triples)










