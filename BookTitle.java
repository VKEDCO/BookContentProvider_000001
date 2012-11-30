package org.vkedco.mobappdev.book_content_provider_00001;

/*
 * ***************************************************
 * BookTitle.java
 * OO Model of the Book Title table of book_info.db
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookTitle {
	
	protected String mTitle;
	protected String mAuthor;
	protected String mTranslator;
	protected String mISBN;
	protected float mPrice;
	
	public BookTitle(String title, String author, String translator, String isbn, float price) {
		mTitle = title;
		mAuthor = author;
		mTranslator = translator;
		mISBN = isbn;
		mPrice = price;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getAuthor() {
		return mAuthor;
	}
	
	public String getISBN() {
		return mISBN;
	}
	
	public String getTranslator() {
		return mTranslator;
	}
	
	public float getPrice() {
		return mPrice;
	}

}

