package net.shtab.hid;

import java.util.Iterator;

public interface Loader extends Iterator<LoaderDocument> {
	int DOC_AMOUNT = Integer.parseInt(System.getenv("DOC_QTY"));
	String getName();
	String getUrl();
	boolean init();
	default void close() {};
}
