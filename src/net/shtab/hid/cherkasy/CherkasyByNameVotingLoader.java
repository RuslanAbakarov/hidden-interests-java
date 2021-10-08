package net.shtab.hid.cherkasy;

public class CherkasyByNameVotingLoader extends CherkasyLoader {
	public static final String NAME = "CHERKASY_BY_NAME_VOTING";
	public static final String URI = "/ua/files.php?s=2&s1=168&s2=477";

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
