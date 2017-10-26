package sparql2gf_query_translator;

import org.apache.jena.sparql.core.Var;

public class MotifSelection {

	String selectVar;

	public MotifSelection(Var var) {
		this.selectVar = var.getName();
	}

	public String createSelection(Var var) {
		return selectVar;
	}

}
