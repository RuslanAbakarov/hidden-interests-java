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

	public static void showLoaderList() {
		System.out.println("Available loaders");
		System.out.println(CherkasyPlenarySessionLoader.NAME); // Черкаси. Протоколи пленарних засідань сесій
		System.out.println(CherkasyByNameVotingLoader.NAME); // Черкаси. Поіменне голосування міської ради;
	}
}
