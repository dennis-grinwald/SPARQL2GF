package sparql2gf;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.algebra.OpVisitorBase;
import org.apache.jena.sparql.algebra.op.OpBGP;

public class AlgebraToMotifTransformer extends OpVisitorBase {
	
	public void visit(final OpBGP opBGP) {
		{
			final List<Triple> triples = opBGP.getPattern().getList();
	//		final Traversal[] matchTraversals = new Traversal[triples.size()]; 
			for (final Triple triple : triples) {

				ArrayList<String> motif = MotifBuilder.transform(triple);
		//		ArrayList<String> motif = 
				     // add ready motif Strings
			    // add operational strings, e.g: "s.name=''" //
			}

		}

}
