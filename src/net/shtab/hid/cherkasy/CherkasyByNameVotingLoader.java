package net.shtab.hid.cherkasy;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import net.shtab.hid.LoaderDocument;

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

	protected boolean loadPage() {
		String pageName = URL + URI;
		if (pageCounter > 1)
			pageName = pageName + "&p=" + String.valueOf(pageCounter);
		try {
			currentPage = Jsoup.connect(pageName).get();
			logger.info("Uploaded page ->" + pageName);
			pageCounter++;
			return null != currentPage;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;

	}

	protected boolean getPageFiles() {
		if (null == currentPage)
			return false;
		pageFiles = currentPage.body().getElementsByClass("filestable");
		return null != pageFiles && pageFiles.size() > 0;
	}

	protected LoaderDocument load() {
		if(null == pageFiles || pageFiles.size() <= 0)
			return null;
		
		LoaderDocument doc = new LoaderDocument();
		doc.title = extractFileName(pageFiles.get(0));
		doc.link = extractFileLink(pageFiles.get(0));
		doc.content = extractFileContent(doc.link);
		doc.extention = extractFileExtention(doc.link);
		pageFiles.remove(0);
		if (pageFiles.size() == 0) {
			loadPage();
			getPageFiles();
		}
		return doc;
	}
	
	protected String extractFileName(Element element) {
		Element tmp = element.getElementsByClass("file_description").first();
		return tmp.select("strong").text();
	}
	
	protected String extractFileLink(Element element) {
		String fileName = element.getElementsByClass("files_downl_td").first().select("a[href]").first().attr("href").replace("..", "");
		return URL + fileName;
	}

	@Override
	protected String extractFileExtention(String name) {
		int i = name.lastIndexOf(".");
		if(i > 0)
			return name.substring(i + 1);
		return "";
	}	
}
