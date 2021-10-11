package net.shtab.hid.cherkasy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.shtab.hid.LoaderDocument;
import net.shtab.hid.Loader;

public abstract class CherkasyLoader implements Loader {
	public static final String URL = "http://chmr.gov.ua";
	Logger logger = null;
	Document currentPage = null;
	Elements pageFiles;
	int pageCounter = 1;
	int docCounter = 0;

	@Override
	public boolean hasNext() {
		return docCounter < DOC_AMOUNT && pageFiles.size() > 0;
	}

	@Override
	public LoaderDocument next() {
		docCounter++;
		LoaderDocument doc = load();
		pageFiles.remove(0);
		if (pageFiles.size() == 0) {
			loadPage();
			getPageFiles();
		}

		return doc;
	}

	@Override
	public boolean init() {
		logger = Logger.getLogger(getName());
		return loadPage() && getPageFiles();
	}

	@Override
	public void close() {
		pageFiles.clear();
		pageFiles = null;
		currentPage = null;
		logger.info(getName() + " -> closed");
	}

	protected abstract String getUri();

	protected boolean loadPage() {
		String pageName = URL + getUri();
		if (pageCounter > 1)
			pageName = pageName + "&p=" + String.valueOf(pageCounter);
		try {
			currentPage = Jsoup.connect(pageName).get();
			logger.info(getName() + " -> Uploaded page -> " + pageName + " Total posted -> " + docCounter);
			pageCounter++;
			return null != currentPage;
		} catch (IOException e) {
			logger.warning(getName() + " -> " + e.getMessage()); 
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
		if (null == pageFiles || pageFiles.size() <= 0)
			return null;

		LoaderDocument doc = new LoaderDocument();
		doc.title = extractFileName(pageFiles.get(0));
		doc.link = extractFileLink(pageFiles.get(0));
		if (null == doc.link) {
			return doc;
		}
		doc.content = extractFileContent(doc.link);
		doc.extention = extractFileExtention(doc.link);
		return doc;
	}

	protected String extractFileName(Element element) {
		Element tmp = element.getElementsByClass("file_description").first();
		return tmp.select("strong").text();
	}

	protected String extractFileLink(Element element) {
		Element dl = element.getElementsByClass("files_downl_td").first();
		if (null == dl)
			return null;
		String fileName = dl.select("a[href]").first().attr("href").replace("..", "");
		return URL + fileName;
	}

	protected String extractFileExtention(String name) {
		int i = name.lastIndexOf(".");
		if (i > 0)
			return name.substring(i + 1);
		return "";
	}

	protected String extractFileContent(String link) {
		byte[] imageBytes;
		try {
			imageBytes = new java.net.URL(link).openStream().readAllBytes();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (MalformedURLException e) {
			logger.warning(getName() + " -> " + e.getMessage()); 
		} catch (IOException e) {
			logger.warning(getName() + " -> " + e.getMessage()); 
		}
		return null;

	};

}
