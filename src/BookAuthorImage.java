package org.vkedco.mobappdev.book_content_provider_00001;

/*
 * ***************************************************
 * BookAuthorImage.java
 * OO Model of the Book Author Image table in book_info.db
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class BookAuthorImage {
	
	protected String mName;
	protected byte[] mImgBytes;
	
	public BookAuthorImage(String name, byte[] imgBytes) {
		mName = name;
		mImgBytes = imgBytes;
	}
	
	public String getName() {
		return mName;
	}
	
	public byte[] getImgBytes() {
		return mImgBytes;
	}
}
