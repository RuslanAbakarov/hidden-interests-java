package net.shtab.hid;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LoaderDocument {
	public String title;
	public String link;
	@JsonProperty("file_content")
	public String content;
	@JsonProperty("file_type")
	public String extention;
}
