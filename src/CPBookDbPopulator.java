package org.vkedco.mobappdev.book_content_provider_00001;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

public class CPBookDbPopulator {
	
	static final String LOGTAG = CPBookDbPopulator.class.getSimpleName() + "_LOG";
	static final String XML_ENTRY_SEPARATOR = ";";
	
	static final void populateBookInfoDb(Context cntxt, SQLiteDatabase writeDb) {
		populateBookTitles(cntxt, writeDb);
		populateBookAuthors(cntxt, writeDb);
		populateBookCoverImages(cntxt, writeDb);
		populateBookAuthorImages(cntxt, writeDb);
	}
	
	private static void populateBookTitles(Context cntxt, SQLiteDatabase writeDb) {
		// Populate book titles
    	String[] book_title_table  = getXMLTableSpecs(cntxt, R.array.book_title_table);
    	String[] book_entry_parts;
    	ArrayList<BookTitle> bookTitles = new ArrayList<BookTitle>();
    	for(String book_entry: book_title_table) {
    		book_entry_parts = book_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_entry);
    		bookTitles.add(
    				createBookTitleObject(
    						book_entry_parts[0], // title
    						book_entry_parts[1], // author
    						book_entry_parts[2], // translator
    						book_entry_parts[3], // isbn
    						book_entry_parts[4]  // price
    			    )
    		);
    	}
    	CPBookDbAdptr.insertBookTitles(bookTitles, writeDb);
	}
	
	private static void populateBookAuthors(Context cntxt, SQLiteDatabase writeDb) {
		// Populate book titles
		ArrayList<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();
		String[] book_author_table = getXMLTableSpecs(cntxt, R.array.book_author_table);
    	String[] author_entry_parts;
    	for(String author_entry: book_author_table) {
    		author_entry_parts = author_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, author_entry);
    		bookAuthors.add(
    				createBookAuthorObject(
    						author_entry_parts[0], // author's name
 						   	author_entry_parts[1], // author's birth year
 						   	author_entry_parts[2], // author's death year
 						   	author_entry_parts[3]  // author's country
 					)
    		);
    	}
    	CPBookDbAdptr.insertBookAuthors(bookAuthors, writeDb);
	}
	
	private static void populateBookCoverImages(Context cntxt, SQLiteDatabase writeDb) {
		// Populate book cover images
		ArrayList<BookCoverImage> bookCoverImages = new ArrayList<BookCoverImage>();
    	String[] book_cover_image_table = getXMLTableSpecs(cntxt, R.array.book_cover_image_table);
    	String[] book_cover_entry_parts;
    	for(String book_cover_entry: book_cover_image_table) {
    		book_cover_entry_parts = book_cover_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_cover_entry);
    		
    		bookCoverImages.add(
    				createBookCoverImageObject(
    						cntxt,
    						book_cover_entry_parts[0], // book's isbn
    						book_cover_entry_parts[1]  // book's cover image reference
    			)
    		); 									  
    	}
    	CPBookDbAdptr.insertBookCoverImages(bookCoverImages, writeDb);
	}
	
	private static void populateBookAuthorImages(Context cntxt, SQLiteDatabase writeDb) {
		// Populate book author images
		ArrayList<BookAuthorImage> bookAuthorImages = new ArrayList<BookAuthorImage>();
    	String[] book_author_image_table = getXMLTableSpecs(cntxt, R.array.book_author_image_table);
    	String[] book_author_image_entry_parts;
    	for(String book_author_image_entry: book_author_image_table) {
    		book_author_image_entry_parts = book_author_image_entry.trim().split(XML_ENTRY_SEPARATOR);
    		Log.d(LOGTAG, book_author_image_entry);
    		
    		bookAuthorImages.add(
    				createBookAuthorImageObject(
    						cntxt,
    						book_author_image_entry_parts[0], // author's name
    						book_author_image_entry_parts[1]  // book author bytes
    			)
    		); 									  
    	}
    	
    	CPBookDbAdptr.insertBookAuthorImages(bookAuthorImages, writeDb);
	}

    private static String[] getXMLTableSpecs(Context cntxt, int table_name) {
    	Resources mRes = cntxt.getResources();
    	switch ( table_name ) {
    	case R.array.book_title_table:
    		return mRes.getStringArray(R.array.book_title_table);
    	case R.array.book_author_table:
    		return mRes.getStringArray(R.array.book_author_table);
    	case R.array.book_cover_image_table:
    		return mRes.getStringArray(R.array.book_cover_image_table);
    	case R.array.book_author_image_table:
    		return mRes.getStringArray(R.array.book_author_image_table);
    	default:
    			return null;
    	}
    }
    
    private static BookTitle createBookTitleObject(String title, String author, String translator,
    		String isbn, String price) {
    	
    	return new BookTitle(title, 
    	  					 author, 
    	  					 translator, 
    	  					 isbn, 
    	  					 Float.parseFloat(price)); 
    }
    
    private static BookAuthor createBookAuthorObject(String name, String birth_year, 
    		String death_year, String country) {
    	
    	return new BookAuthor(name, 
    	  					  Integer.parseInt(birth_year), 
    	  					  Integer.parseInt(death_year), 
    	  					  country); 
    }
    
    private static BookCoverImage createBookCoverImageObject(Context cntxt, 
    		String isbn, String cover_img_reference) {
		Bitmap bmp = null;
		Resources mRes = cntxt.getResources();
		
    	if ( cover_img_reference.equals(mRes.getString(R.string.essential_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.essential_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.illuminated_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.illuminated_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.year_with_rumi_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.year_with_rumi_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.year_with_hafiz_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.year_with_hafiz_cover);
		}
		else if ( cover_img_reference.equals(mRes.getString(R.string.the_gift_cover)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.the_gift_cover);
		}
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bitmapbytes = null;
    	bmp.compress(CompressFormat.PNG, 0, bos);
		bitmapbytes = bos.toByteArray();
    	bmp.recycle();
    	try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return new BookCoverImage(isbn, bitmapbytes);
    }
    
    private static BookAuthorImage createBookAuthorImageObject(Context cntxt, 
    		String name, String author_img_ref) {
		Bitmap bmp = null;
		Resources mRes = cntxt.getResources();
		
    	if ( author_img_ref.equals(mRes.getString(R.string.rumi_portrait)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.rumi_portrait);
		}
		else if ( author_img_ref.equals(mRes.getString(R.string.hafiz_portrait)) ) {
			bmp = BitmapFactory.decodeResource(mRes, R.drawable.hafez_tomb_ceiling_mosaic);
		}
		
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bitmapbytes = null;
    	bmp.compress(CompressFormat.PNG, 0, bos);
		bitmapbytes = bos.toByteArray();
    	bmp.recycle();
    	try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return new BookAuthorImage(name, bitmapbytes);
    }

}
