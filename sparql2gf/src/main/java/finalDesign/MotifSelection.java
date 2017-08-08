package finalDesign;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.sparql.core.Var;

public class MotifSelection {
	
	public List<String> selectionVars = new ArrayList<String>();
	
	public MotifSelection(Var var) {
		this.selectionVars.add(var.toString());
	}
	
	

}
