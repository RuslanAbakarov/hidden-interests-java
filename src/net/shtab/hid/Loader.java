package net.shtab.hid;

import java.util.Iterator;

public interface Loader extends Iterator<LoaderDocument> {
	String getName();
	String getUrl();
	boolean init();
	default void close() {};
}
