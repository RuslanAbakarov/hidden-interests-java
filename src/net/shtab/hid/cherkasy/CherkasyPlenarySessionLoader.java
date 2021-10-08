package net.shtab.hid.cherkasy;

public class CherkasyPlenarySessionLoader extends CherkasyLoader {
	public static final String NAME = "CHERKASY_PLENARY_SESSION";
	public static final String DESCRIPTION = "Черкаська міська рада. Протоколи пленарних засідань сесій";
	public static final String URI = "/ua/files.php?s=2&s1=168&s2=391";

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getUrl() {
		return URL + URI;
	}

	@Override
	protected String getUri() {
		return URI;
	}
}
