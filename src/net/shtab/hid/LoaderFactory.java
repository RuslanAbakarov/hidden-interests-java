package net.shtab.hid;

import net.shtab.hid.cherkasy.CherkasyByNameVotingLoader;
import net.shtab.hid.cherkasy.CherkasyPlenarySessionLoader;

public final class LoaderFactory {
	public static Loader factory(String name) {
		switch (name) {
		case CherkasyPlenarySessionLoader.NAME:
			return new CherkasyPlenarySessionLoader();
		case CherkasyByNameVotingLoader.NAME:
			return new CherkasyByNameVotingLoader();
		default:
			return null;
		} 
	}
}
