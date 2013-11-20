package org.vkedco.mobappdev.book_content_provider_00001;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/*
 * ***************************************************
 * CPBookDbAdptr.java
 * Class responsible for programmatically creating, maintaining, and quering
 * book_info.db 
 * 
 * Bugs to vladimir dot kulyukin at gmail dot com
 * ***************************************************
 */

public class CPBookDbAdptr {
	
	static final String ADPTR_LOGTAG = CPBookDbAdptr.class.getSimpleName() + "_TAG";
	static final String QUERY_LOGTAG = "QUERY_TAG";
	
	static final String DB_NAME            		= "book_info.db";
	static final int    DB_VERSION         		= 1;
	static final String BOOK_TITLE_TABLE   		= "book_title";
	static final String BOOK_AUTHOR_TABLE  		= "book_author";
	static final String BOOK_COVER_IMAGE_TABLE 	= "book_cover_image";
	static final String BOOK_AUTHOR_IMAGE_TABLE = "book_author_image";
	// ********** book_title constants ******************
	// constants for book_title table column names
	static final String BOOK_TITLE_TBL_ID_COL_NAME         	= "ID";
	static final String BOOK_TITLE_TBL_TITLE_COL_NAME      	= "Title";
	static final String BOOK_TITLE_TBL_AUTHOR_COL_NAME 	 	= "BookAuthor";
	static final String BOOK_TITLE_TBL_TRANSLATOR_COL_NAME 	= "Translator";
	static final String BOOK_TITLE_TBL_ISBN_COL_NAME		= "ISBN";
	static final String BOOK_TITLE_TBL_PRICE_COL_NAME	    = "Price";
	static final String[] BOOK_TITLE_TBL_COLUMN_NAMES     	= 
	{ 
		BOOK_TITLE_TBL_ID_COL_NAME, 
		BOOK_TITLE_TBL_TITLE_COL_NAME, 
		BOOK_TITLE_TBL_AUTHOR_COL_NAME, 
		BOOK_TITLE_TBL_TRANSLATOR_COL_NAME, 
		BOOK_TITLE_TBL_ISBN_COL_NAME, 
		BOOK_TITLE_TBL_ISBN_COL_NAME, 
		BOOK_TITLE_TBL_PRICE_COL_NAME 
	};
	
	// constants for book_title table column numbers
	static final int BOOK_TITLE_TBL_ID_COL_NUM         	= 0;
	static final int BOOK_TITLE_TBL_TITLE_COL_NUM      	= 1;
	static final int BOOK_TITLE_TBL_AUTHOR_COL_NUM     	= 2;
	static final int BOOK_TITLE_TBL_TRANSLATOR_COL_NUM	= 3;
	static final int BOOK_TITLE_TBL_ISBN_COL_NUM		= 4;
	static final int BOOK_TITLE_TBL_PRICE_COL_NUM		= 5;
	
	// ********** book_author table constants ******************
	// constants for book_author table column names
	static final String BOOK_AUTHOR_TBL_ID_COL_NAME 	  	  	= "ID";
	static final String BOOK_AUTHOR_TBL_NAME_COL_NAME   	  	= "AuthorName";
	static final String BOOK_AUTHOR_TBL_BIRTH_YEAR_COL_NAME 	= "BirthYear";
	static final String BOOK_AUTHOR_TBL_DEATH_YEAR_COL_NAME 	= "DeathYear";
	static final String BOOK_AUTHOR_TBL_COUNTRY_COL_NAME    	= "Country";
	static final String[] BOOK_AUTHOR_TBL_COLUMN_NAMES          = 
	{
		BOOK_AUTHOR_TBL_ID_COL_NAME, 
		BOOK_AUTHOR_TBL_NAME_COL_NAME, 
		BOOK_AUTHOR_TBL_BIRTH_YEAR_COL_NAME,
		BOOK_AUTHOR_TBL_DEATH_YEAR_COL_NAME, 
		BOOK_AUTHOR_TBL_COUNTRY_COL_NAME
	};
	
	// constants for book_author table column numbers
	static final int BOOK_AUTHOR_TBL_ID_COL_NUM 		  		= 0;
	static final int BOOK_AUTHOR_TBL_NAME_COL_NUM       		= 1;
	static final int BOOK_AUTHOR_TBL_BIRTH_YEAR_COL_NUM 		= 2;
	static final int BOOK_AUTHOR_TBL_DEATH_YEAR_COL_NUM 		= 3;
	static final int BOOK_AUTHOR_TBL_COUNTRY_COL_NUM    		= 4;
	
	// ********** book_cover_image table constants ******************
	// constants for book_cover_image column names
	static final String BOOK_COVER_IMAGE_TBL_ID_COL_NAME		= "ID";
	static final String BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME		= "ISBN";
	static final String BOOK_COVER_IMAGE_TBL_IMG_COL_NAME		= "CoverIMG";
	static final String[] BOOK_COVER_IMAGE_TBL_COL_NAMES        = 
	{
		BOOK_COVER_IMAGE_TBL_ID_COL_NAME, 
		BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME,
		BOOK_COVER_IMAGE_TBL_IMG_COL_NAME
	};
	
	// constants for book cover image column numbers
	static final int BOOK_COVER_IMAGE_TBL_ID_COL_NUM		= 0;
	static final int BOOK_COVER_IMAGE_TBL_ISBN_COL_NUM		= 1;
	static final int BOOK_COVER_IMAGE_TBL_IMG_COL_NUM		= 2;
	
	// ********** book_author_image table constants ******************
	// constants for book_author_image column names
	static final String BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME		= "ID";
	static final String BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME		= "AuthorName";
	static final String BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME		= "AuthorIMG";
	static final String[] BOOK_AUTHOR_IMAGE_TBL_COL_NAMES       = 
	{
		BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME, 
		BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME,
		BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME
	};
	
	// constants for book author image column numbers
	static final int BOOK_AUTHOR_IMAGE_TBL_ID_COL_NUM			= 0;
	static final int BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NUM			= 1;
	static final int BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NUM			= 2;
	
	
	private static BookInfoDBOpenHelper mDbHelper = null;
	
	// BookInfoDBOpenHelper class creates the table in the database
	private static class BookInfoDBOpenHelper extends SQLiteOpenHelper {
		static final String HELPER_LOGTAG = BookInfoDBOpenHelper.class.getSimpleName() + "_TAG";
		
		public BookInfoDBOpenHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		// table creation string constant
		static final String BOOK_TITLE_TABLE_CREATE =
			"create table " + BOOK_TITLE_TABLE + 
			" (" + 
			BOOK_TITLE_TBL_ID_COL_NAME         + " integer primary key autoincrement, " + 
			BOOK_TITLE_TBL_TITLE_COL_NAME      + " text not null, " + 
			BOOK_TITLE_TBL_AUTHOR_COL_NAME     + " text not null, " +
			BOOK_TITLE_TBL_TRANSLATOR_COL_NAME + " text not null, " + 
			BOOK_TITLE_TBL_ISBN_COL_NAME	   + " text not null, " +
			BOOK_TITLE_TBL_PRICE_COL_NAME      + " float not null " + 
			");";
		
		static final String BOOK_AUTHOR_TABLE_CREATE =
			"create table " + BOOK_AUTHOR_TABLE + 
			" (" + 
			BOOK_AUTHOR_TBL_ID_COL_NAME    		+ " integer primary key autoincrement, " + 
			BOOK_AUTHOR_TBL_NAME_COL_NAME  		+ " text not null, " + 
			BOOK_AUTHOR_TBL_BIRTH_YEAR_COL_NAME + " integer not null, " +
			BOOK_AUTHOR_TBL_DEATH_YEAR_COL_NAME + " integer not null, " +
			BOOK_AUTHOR_TBL_COUNTRY_COL_NAME 	+ " text not null " + 
			");";
		
		static final String BOOK_COVER_IMG_TABLE_CREATE = 
			"create table " + BOOK_COVER_IMAGE_TABLE + 
			" (" + 
			BOOK_COVER_IMAGE_TBL_ID_COL_NAME   + " integer primary key autoincrement, " + 
			BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME + " text not null, " +
			BOOK_COVER_IMAGE_TBL_IMG_COL_NAME  + " blob not null " +
			");";
		
		static final String BOOK_AUTHOR_IMG_TABLE_CREATE =
			"create table " + BOOK_AUTHOR_IMAGE_TABLE + 
			" (" + 
			BOOK_AUTHOR_IMAGE_TBL_ID_COL_NAME   + " integer primary key autoincrement, " + 
			BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME + " text not null, " +
			BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME  + " blob not null " +
			");";
			
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(HELPER_LOGTAG, BOOK_TITLE_TABLE_CREATE);
			db.execSQL(BOOK_TITLE_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_AUTHOR_TABLE_CREATE);
			db.execSQL(BOOK_AUTHOR_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_COVER_IMG_TABLE_CREATE);
			db.execSQL(BOOK_COVER_IMG_TABLE_CREATE);
			
			Log.d(HELPER_LOGTAG, BOOK_AUTHOR_IMG_TABLE_CREATE);
			db.execSQL(BOOK_AUTHOR_IMG_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, 
				int newVersion) {
			Log.d(ADPTR_LOGTAG, "Upgrading from version " +
					oldVersion + " to " +
					newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_TITLE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_AUTHOR_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_COVER_IMAGE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + BOOK_AUTHOR_IMAGE_TABLE);
			onCreate(db);
		}
	} // end of BookInfoDBOpenHelper class

	
	static final void createBookInfoDbOpenHelper(Context cntxt) {
		mDbHelper = new BookInfoDBOpenHelper(cntxt, DB_NAME, null, DB_VERSION);
	}
	
	static final SQLiteDatabase getWriteDb() {
		try {
			return mDbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			Log.d(CPBookDbAdptr.ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}

	static final SQLiteDatabase getReadDb() {
		try {
			return mDbHelper.getReadableDatabase();
		} catch (SQLiteException ex) {
			Log.d(CPBookDbAdptr.ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	
	// Strongly typed insertion method. Insert the book if and only if it is 
	// not already in the database.
	private static final long insertUniqueBookTitle(BookTitle bt, SQLiteDatabase writeDb) {
		ContentValues newBook = new ContentValues();
		newBook.put(BOOK_TITLE_TBL_TITLE_COL_NAME, bt.getTitle());
		newBook.put(BOOK_TITLE_TBL_AUTHOR_COL_NAME, bt.getAuthor());
		newBook.put(BOOK_TITLE_TBL_ISBN_COL_NAME, bt.getISBN());
		newBook.put(BOOK_TITLE_TBL_TRANSLATOR_COL_NAME, bt.getTranslator());
		newBook.put(BOOK_TITLE_TBL_PRICE_COL_NAME, bt.getPrice());

		Cursor rslt = writeDb.query(BOOK_TITLE_TABLE,
				new String[] { BOOK_TITLE_TBL_ISBN_COL_NAME }, 
				BOOK_TITLE_TBL_ISBN_COL_NAME + "=" + bt.getISBN(), 
				null, null, null, null);
		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst())) {
			insertedRowIndex = writeDb.insertWithOnConflict(
					BOOK_TITLE_TABLE, null, newBook,
					SQLiteDatabase.CONFLICT_REPLACE);
		}

		rslt.close();
		Log.d(ADPTR_LOGTAG, "Inserted book record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	private static final long insertUniqueBookCoverImage(BookCoverImage bci, SQLiteDatabase writeDb) {
		long insertedRowIndex = -1;
		byte[] bitmapbytes = bci.getImgBytes();
		if ( bitmapbytes != null ) {
			Log.d(ADPTR_LOGTAG, "bitmapbytes length = " + bitmapbytes.length);
		
			ContentValues newBookCoverRef = new ContentValues();
			newBookCoverRef.put(CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME, bci.getISBN());
			newBookCoverRef.put(CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_IMG_COL_NAME, bitmapbytes);
		
			Cursor rslt = 
				writeDb.query(BOOK_COVER_IMAGE_TABLE, 
					new String[] { BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME }, 
					BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME + "=" + "\"" + bci.getISBN() + "\"", 
					null, null, null, null);

			if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
				insertedRowIndex = 
					writeDb.insertWithOnConflict(BOOK_COVER_IMAGE_TABLE, 
						null, 
						newBookCoverRef, 
						SQLiteDatabase.CONFLICT_REPLACE);	
			}
			rslt.close();
		}
		
		Log.d(ADPTR_LOGTAG, "Inserted book cover image record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	private static final long insertUniqueBookAuthorImage(BookAuthorImage bai, SQLiteDatabase writeDb) {
		long insertedRowIndex = -1;
		byte[] bitmapbytes = bai.getImgBytes();
		if ( bitmapbytes != null ) {
			Log.d(ADPTR_LOGTAG, "bitmapbytes length = " + bitmapbytes.length);
		
			ContentValues newBookAuthorVals = new ContentValues();
			newBookAuthorVals.put(CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME, bai.getName());
			newBookAuthorVals.put(CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TBL_IMG_COL_NAME, bitmapbytes);
		
			Cursor rslt = 
				writeDb.query(BOOK_AUTHOR_IMAGE_TABLE, 
					new String[] { BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME }, 
					BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME + "=" + "\"" + bai.getName() + "\"", 
					null, null, null, null);

			if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
				insertedRowIndex = 
					writeDb.insertWithOnConflict(BOOK_AUTHOR_IMAGE_TABLE, 
						null, 
						newBookAuthorVals, 
						SQLiteDatabase.CONFLICT_REPLACE);	
			}
			rslt.close();
		}
		
		Log.d(ADPTR_LOGTAG, "Inserted book author image record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	

	private static final long insertUniqueBookAuthor(BookAuthor author, SQLiteDatabase writeDb) {
		ContentValues newBook = new ContentValues();
		newBook.put(CPBookDbAdptr.BOOK_AUTHOR_TBL_NAME_COL_NAME, author.getName());
		newBook.put(CPBookDbAdptr.BOOK_AUTHOR_TBL_BIRTH_YEAR_COL_NAME,
				author.getBirthYear());
		newBook.put(CPBookDbAdptr.BOOK_AUTHOR_TBL_DEATH_YEAR_COL_NAME,
				author.getDeathYear());
		newBook.put(CPBookDbAdptr.BOOK_AUTHOR_TBL_COUNTRY_COL_NAME, author.getCountry());
		Cursor rslt = writeDb.query(BOOK_AUTHOR_TABLE,
				new String[] { BOOK_AUTHOR_TBL_NAME_COL_NAME }, BOOK_AUTHOR_TBL_NAME_COL_NAME
						+ "=" + "\"" + author.getName() + "\"", null, null,
				null, null);

		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst())) {
				insertedRowIndex = writeDb.insertWithOnConflict(
						BOOK_AUTHOR_TABLE, null, newBook,
						SQLiteDatabase.CONFLICT_REPLACE);
		}
		
		rslt.close();
		Log.d(ADPTR_LOGTAG, "Inserted author record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	// retrieve books by title
	static final String[] QUERY_01_COLNAMES = BOOK_TITLE_TBL_COLUMN_NAMES;
	static final String   QUERY_01_WHERE_CLAUSE = BOOK_TITLE_TBL_TITLE_COL_NAME + "=?";
	static final Cursor retrieveBookByTitle(final String title, SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 01 Readable DB");
			Cursor crsr =
				readDb.query(BOOK_TITLE_TABLE,  // table name 
						QUERY_01_COLNAMES, 		// column names 
						QUERY_01_WHERE_CLAUSE, 	// where clause
						new String[]{title},    // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve books by author
	static final String[] QUERY_02_COLNAMES = BOOK_TITLE_TBL_COLUMN_NAMES;
	static final String   QUERY_02_WHERE_CLAUSE = BOOK_TITLE_TBL_AUTHOR_COL_NAME + "=?";
	static final Cursor retrieveBookByAuthor(final String author, final SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 02");
			Cursor crsr =
				readDb.query(BOOK_TITLE_TABLE,  // table name 
						QUERY_02_COLNAMES, 		// column names 
						QUERY_02_WHERE_CLAUSE, 	// where clause
						new String[]{author},    // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve books by translator
	static final String[] QUERY_03_COLNAMES = BOOK_TITLE_TBL_COLUMN_NAMES;
	static final String   QUERY_03_WHERE_CLAUSE = BOOK_TITLE_TBL_TRANSLATOR_COL_NAME + "=?";
	static final Cursor retrieveBookByTranslator(final String translator, final SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 03");
			Cursor crsr =
				readDb.query(BOOK_TITLE_TABLE,    // table name 
						QUERY_03_COLNAMES, 		  // column names 
						QUERY_03_WHERE_CLAUSE, 	  // where clause
						new String[]{translator}, // selection args
						null, 			    	  // group by
						null, 					  // having
						null); 					  // order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve books by price
	static final String[] QUERY_04_COLNAMES = BOOK_TITLE_TBL_COLUMN_NAMES;
	static final String   QUERY_04_WHERE_CLAUSE = BOOK_TITLE_TBL_PRICE_COL_NAME + "=?";
	static final Cursor retrieveBookByPrice(final String price, final SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 04");
			Cursor crsr =
				readDb.query(BOOK_TITLE_TABLE,    // table name 
						QUERY_04_COLNAMES, 		  // column names 
						QUERY_04_WHERE_CLAUSE, 	  // where clause
						new String[]{price}, 	  // selection args
						null, 			    	  // group by
						null, 					  // having
						null); 					  // order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve all book titles
	static final String[] QUERY_05_COLNAMES 	= null;
	static final String   QUERY_05_WHERE_CLAUSE = null;
	static final Cursor retrieveAllBookTitles(final String price, final SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 04");
			Cursor crsr =
				readDb.query(BOOK_TITLE_TABLE,    // table name 
						QUERY_05_COLNAMES, 		  // column names 
						QUERY_04_WHERE_CLAUSE, 	  // where clause
						null, 	  				  // selection args
						null, 			    	  // group by
						null, 					  // having
						null); 					  // order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve author by name
	static final String[] QUERY_06_COLNAMES = BOOK_AUTHOR_TBL_COLUMN_NAMES;
	static final String   QUERY_06_WHERE_CLAUSE = BOOK_TITLE_TBL_TITLE_COL_NAME + "=?";
	static final Cursor retrieveAuthorByName(final String name, SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 06");
			Cursor crsr =
				readDb.query(BOOK_AUTHOR_TABLE, // table name 
						QUERY_06_COLNAMES, 		// column names 
						QUERY_06_WHERE_CLAUSE, 	// where clause
						new String[]{name},     // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve author by name
	static final String[] QUERY_07_COLNAMES = BOOK_AUTHOR_TBL_COLUMN_NAMES;
	static final String   QUERY_07_WHERE_CLAUSE = BOOK_TITLE_TBL_TITLE_COL_NAME + "=?";
	static final Cursor retrieveAuthorByCountry(final String country, SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 07");
			Cursor crsr =
				readDb.query(BOOK_AUTHOR_TABLE, // table name 
						QUERY_07_COLNAMES, 		// column names 
						QUERY_07_WHERE_CLAUSE, 	// where clause
						new String[]{country},  // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	// retrieve author by name
	static final String[] QUERY_08_COLNAMES = BOOK_AUTHOR_TBL_COLUMN_NAMES;
	static final String   QUERY_08_WHERE_CLAUSE = BOOK_TITLE_TBL_TITLE_COL_NAME + "=?";
	static final Cursor retrieveBookCoverByISBN(final String isbn, SQLiteDatabase readDb) {
		try {
			Log.d(ADPTR_LOGTAG, "QUERY 08");
			Cursor crsr =
				readDb.query(BOOK_AUTHOR_TABLE, // table name 
						QUERY_08_COLNAMES, 		// column names 
						QUERY_08_WHERE_CLAUSE, 	// where clause
						new String[]{isbn},     // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	static final void insertBookTitles(ArrayList<BookTitle> bookTitles, SQLiteDatabase writeDb) {
		for(BookTitle bt : bookTitles) {
			insertUniqueBookTitle(bt, writeDb);
		}
	}
	
	static final void insertBookAuthors(ArrayList<BookAuthor> bookAuthors, SQLiteDatabase writeDb) {
		for(BookAuthor ba : bookAuthors) {
			insertUniqueBookAuthor(ba, writeDb);
		}
	}
	
	static final void insertBookCoverImages(ArrayList<BookCoverImage> bookCoverImages, SQLiteDatabase writeDb) {
		for(BookCoverImage bci : bookCoverImages) {
			insertUniqueBookCoverImage(bci, writeDb);
		}
	}
	
	static final void insertBookAuthorImages(ArrayList<BookAuthorImage> bookAuthorImages, SQLiteDatabase writeDb) {
		for(BookAuthorImage bai : bookAuthorImages) {
			insertUniqueBookAuthorImage(bai, writeDb);
		}
	}
	
	static final Bitmap retrieveBookCoverImage(String isbn, SQLiteDatabase db) {
		byte[] image_bytes = null;
		Cursor rslt = 
			db.query(BOOK_COVER_IMAGE_TABLE, 
				new String[] { BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME, BOOK_COVER_IMAGE_TBL_IMG_COL_NAME }, 
				BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME + "=" + "\"" + isbn + "\"", 
				null, null, null, null);
		if ( rslt.getCount() != 0 ) {
			rslt.moveToFirst();
			
			image_bytes = rslt.getBlob(rslt.getColumnIndex(BOOK_COVER_IMAGE_TBL_IMG_COL_NAME));
			Bitmap bmp = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length);
			return bmp;
		}
		else {
			return null;
		}
	}	
}
