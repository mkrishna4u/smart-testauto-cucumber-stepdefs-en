package org.uitnet.testing.smartfwk.core.stepdefs.en;

import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

public class Test {
	public static void main(String[] args) {
		String str = "ab dn dfd f f dsfs fs\n ${new;} jhsakjd ads asdbsa ${new;}\n sdsd  fdsfds f dsf dsf sdf ${new;}";
		
		String[] queries = str.replace("\n", " ").split("\\$\\{new;}");
		for(String q : queries) {
			if(StringUtil.isEmptyAfterTrim(q)) { continue; }
			System.out.println(q);
		}
	}
}
