package sparql2gf_query_translator;

public class PrefixBuilder {
	
	String uri;
	static String uriValue;
	
	
	public PrefixBuilder(String uri) {
		this.uri = uri;
		PrefixBuilder.uriValue = uri.substring(uri.lastIndexOf("/")+1, uri.length());
	}

	
	//TBD: enable namespaces and prefix-binding!
	public static String getUriValue() {
		return uriValue;
	}
}
