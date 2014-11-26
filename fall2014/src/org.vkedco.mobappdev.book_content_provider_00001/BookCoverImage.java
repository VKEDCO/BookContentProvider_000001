package org.vkedco.mobappdev.book_content_provider_00001;

/*
 * ***************************************************
 * BookCoverImage.java
 * OO Model of the Book Cover Image table in book_info.db
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookCoverImage {
	
	protected String mISBN;
	protected byte[] mImgBytes;
	
	public BookCoverImage(String isbn, byte[] img_bytes) {
		mISBN = isbn;
		mImgBytes = img_bytes;
	}
	
	public String getISBN() {
		return mISBN;
	}
	
	public byte[] getImgBytes() {
		return mImgBytes;
	}
}
