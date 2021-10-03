package net.shtab.hid.cherkasy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	@Override
	public boolean hasNext() {
		return pageFiles.size() > 0;
	}

	@Override
	public LoaderDocument next() {
		return load();
	}

	@Override
	public boolean init() {
		logger = Logger.getLogger(getName());
		return loadPage() && getPageFiles();
	}

	@Override
	public void close() {
		logger.info(getName() + " closed");
	}

	protected abstract boolean loadPage();

	protected abstract boolean getPageFiles();

	protected abstract LoaderDocument load();

	protected abstract String extractFileName(Element element);

	protected abstract String extractFileExtention(String name);

	protected abstract String extractFileLink(Element element);

	protected String extractFileContent(String link) {
		byte[] imageBytes;
		try {
			imageBytes = new java.net.URL(link).openStream().readAllBytes();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return null;

	};

}
