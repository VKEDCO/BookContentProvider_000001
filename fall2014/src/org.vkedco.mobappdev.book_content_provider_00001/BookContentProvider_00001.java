package org.vkedco.mobappdev.book_content_provider_00001;

/*
 ************************************************************ 
 * Bugs to vladimir dot kulyukin at gmail dot com
 ************************************************************
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class BookContentProvider_00001 extends ContentProvider {

	private static SQLiteDatabase mReadDb = null;
	private static SQLiteDatabase mWriteDb = null;

	static final String AUTHORITY = "org.vkedco.mobappdev.content_providers.books";
	static final String LOGTAG = BookContentProvider_00001.class
			.getSimpleName() + "_LOG";

	// uri matching codes
	static final int BOOK_ID_ALL = 0;
	static final int BOOK_ID_SINGLE = 1;
	static final int BOOK_TITLE_ALL = 2;
	static final int BOOK_TITLE_SINGLE = 3;
	static final int BOOK_TITLE_QUERY = 4;
	static final int BOOK_AUTHOR_ALL = 5;
	static final int BOOK_AUTHOR_SINGLE = 6;
	static final int BOOK_AUTHOR_QUERY = 7;
	static final int BOOK_COVER_IMAGE_ALL = 8;
	static final int BOOK_COVER_IMAGE_SINGLE = 9;
	static final int BOOK_COVER_IMAGE_QUERY = 10;
	static final int BOOK_AUTHOR_IMAGE_QUERY = 11;

	static final String BOOK_TITLE_ID_ALL_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_id_all";
	static final String BOOK_TITLE_ID_ONE_MIME = "vnd.android.cursor.item/vnd.vkedco.mobappdev.book_id_one";

	static final String BOOK_TITLE_ALL_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_title_all";
	static final String BOOK_TITLE_ONE_MIME = "vnd.android.cursor.item/vnd.vkedco.mobappdev.book_title_one";
	static final String BOOK_TITLE_QUERY_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_title_query";

	static final String BOOK_AUTHOR_ALL_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_author_all";
	static final String BOOK_AUTHOR_ONE_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_author_one";
	static final String BOOK_AUTHOR_QUERY_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_author_query";

	static final String BOOK_COVER_IMAGE_ALL_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_cover_all";
	static final String BOOK_COVER_IMAGE_ONE_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_cover_one";
	static final String BOOK_COVER_IMAGE_QUERY_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_cover_query";

	static final String BOOK_AUTHOR_IMAGE_QUERY_MIME = "vnd.android.cursor.dir/vnd.vkedco.mobappdev.book_author_query";

	static final String BOOK_ID_PATH = CPBookDbAdptr.BOOK_TITLE_TABLE + "/id";
	static final String BOOK_TITLE_PATH = CPBookDbAdptr.BOOK_TITLE_TABLE
			+ "/title";
	static final String BOOK_AUTHOR_PATH = CPBookDbAdptr.BOOK_AUTHOR_TABLE
			+ "/author";
	static final String BOOK_COVER_IMAGE_PATH = CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE
			+ "/cover_image";
	static final String BOOK_COVER_IMAGE_QUERY_PATH = CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE
			+ "/query";
	static final String BOOK_AUTHOR_IMAGE_QUERY_PATH = CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TABLE
			+ "/query";

	static final String SLASH_HASH = "/#";
	static final String EQLS = "=";

	static final String AUTHOR_PARAMETER = "author";
	static final String TITLE_PARAMETER = "title";
	static final String ISBN_PARAMETER = "isbn";

	private static final UriMatcher mUriMatcher;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		// content://org.vkedco.mobappdev.content_providers.books/book_title/id
		mUriMatcher.addURI(AUTHORITY, BOOK_ID_PATH, BOOK_ID_ALL);
		// content://org.vkedco.mobappdev.content_providers.books/book_title/id/#
		mUriMatcher
				.addURI(AUTHORITY, BOOK_ID_PATH + SLASH_HASH, BOOK_ID_SINGLE);

		// content://org.vkedco.mobappdev.content_providers.books/book_title/title
		mUriMatcher.addURI(AUTHORITY, BOOK_TITLE_PATH, BOOK_TITLE_ALL);
		// content://org.vkedco.mobappdev.content_providers.books/book_title/title/#
		mUriMatcher.addURI(AUTHORITY, BOOK_TITLE_PATH + SLASH_HASH,
				BOOK_TITLE_SINGLE);
		// content://org.vkedco.mobappdev.content_providers.books/book_title/query?title=<title_name>
		// or
		// content://org.vkedco.mobappdev.content_providers.books/book_title/query?title=<title>&author=<author>
		mUriMatcher.addURI(AUTHORITY, BOOK_TITLE_PATH, BOOK_TITLE_QUERY);

		// content://org.vkedco.mobappdev.content_providers.books/book_author/author
		mUriMatcher.addURI(AUTHORITY, BOOK_AUTHOR_PATH, BOOK_AUTHOR_ALL);
		// content://org.vkedco.mobappdev.content_providers.books/book_author/author/#
		mUriMatcher.addURI(AUTHORITY, BOOK_AUTHOR_PATH + SLASH_HASH,
				BOOK_AUTHOR_SINGLE);
		// content://org.vkedco.mobappdev.content_providers.books/book_author/query?author=<author>
		mUriMatcher.addURI(AUTHORITY, BOOK_AUTHOR_PATH, BOOK_AUTHOR_QUERY);

		// content://org.vkedco.mobappdev.content_providers.books/book_cover_image/cover_image
		mUriMatcher.addURI(AUTHORITY, BOOK_COVER_IMAGE_PATH,
				BOOK_COVER_IMAGE_ALL);
		// content://org.vkedco.mobappdev.content_providers.books/book_cover_image/cover_image/#
		mUriMatcher.addURI(AUTHORITY, BOOK_COVER_IMAGE_PATH + SLASH_HASH,
				BOOK_COVER_IMAGE_SINGLE);
		// content://org.vkedco.mobappdev.content_providers.books/book_cover_image/query?isbn=<isbn>
		mUriMatcher.addURI(AUTHORITY, BOOK_COVER_IMAGE_QUERY_PATH,
				BOOK_COVER_IMAGE_QUERY);
		// content://org.vkedco.mobappdev.content_providers.books/book_author_image/query?isbn=<isbn>
		mUriMatcher.addURI(AUTHORITY, BOOK_AUTHOR_IMAGE_QUERY_PATH,
				BOOK_AUTHOR_IMAGE_QUERY);
	}

	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
		case BOOK_ID_SINGLE:
			return BOOK_TITLE_ID_ONE_MIME;
		case BOOK_ID_ALL:
			return BOOK_TITLE_ID_ALL_MIME;
		case BOOK_TITLE_SINGLE:
			return BOOK_TITLE_ONE_MIME;
		case BOOK_TITLE_ALL:
			return BOOK_TITLE_ALL_MIME;
		case BOOK_AUTHOR_SINGLE:
			return BOOK_AUTHOR_ONE_MIME;
		case BOOK_AUTHOR_ALL:
			return BOOK_AUTHOR_ALL_MIME;
		case BOOK_COVER_IMAGE_SINGLE:
			return BOOK_COVER_IMAGE_ONE_MIME;
		case BOOK_COVER_IMAGE_ALL:
			return BOOK_COVER_IMAGE_ALL_MIME;
		case BOOK_COVER_IMAGE_QUERY:
			return BOOK_COVER_IMAGE_QUERY_MIME;
		case BOOK_AUTHOR_IMAGE_QUERY:
			return BOOK_AUTHOR_IMAGE_QUERY_MIME;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		CPBookDbAdptr.createBookInfoDbOpenHelper(getContext());
		mReadDb = CPBookDbAdptr.getReadDb();
		mWriteDb = CPBookDbAdptr.getWriteDb();
		if (mReadDb == null)
			return false;
		if (!mReadDb.isOpen())
			return false;
		if (mWriteDb == null)
			return false;
		if (!mWriteDb.isOpen())
			return false;
		return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		synchronized (this) {
			return null;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		synchronized (this) {
			return 0;
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		synchronized (this) {

			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			Cursor rslt = null;
			String title = null;
			String author = null;
			String isbn = null;

			Log.d(LOGTAG, uri.toString());

			switch (mUriMatcher.match(uri)) {
			// if this is a row query, add the where clause with the
			// appropriate row number
			case BOOK_ID_SINGLE:
				qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
				// set the book_title_tbl_id_col_name to the last path segment
				qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_ID_COL_NAME + EQLS
						+ uri.getLastPathSegment());
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_TITLE_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_ID_ALL:
				// should do this only on small database.
				qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_TITLE_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_TITLE_SINGLE:
				qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
				// set the book_title_tbl_id_col_name to the last path segment
				qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_ID_COL_NAME + EQLS
						+ uri.getLastPathSegment());
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_TITLE_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
			case BOOK_TITLE_ALL:
				// content://org.vkedco.mobappdev.content_providers.books/book_title/
				qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_TITLE_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
			case BOOK_TITLE_QUERY:
				qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
				// content://org.vkedco.mobappdev.content_providers.books/book_title/query?title=<title>
				// or
				// content://org.vkedco.mobappdev.content_providers.books/book_title/query?title=<title>&author=<author>
				title = uri.getQueryParameter(TITLE_PARAMETER);
				author = uri.getQueryParameter(AUTHOR_PARAMETER);
				if (title != null) {
					qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_TITLE_COL_NAME
							+ EQLS + title);
				}
				if (author != null) {
					qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_AUTHOR_COL_NAME
							+ EQLS + author);
				}
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_TITLE_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_AUTHOR_SINGLE:
				qb.setTables(CPBookDbAdptr.BOOK_AUTHOR_TABLE);
				qb.appendWhere(CPBookDbAdptr.BOOK_AUTHOR_TBL_ID_COL_NUM + EQLS
						+ uri.getLastPathSegment());
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_AUTHOR_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_AUTHOR_ALL:
				qb.setTables(CPBookDbAdptr.BOOK_AUTHOR_TABLE);
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_AUTHOR_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_AUTHOR_QUERY:
				qb.setTables(CPBookDbAdptr.BOOK_AUTHOR_TABLE);
				// content://org.vkedco.mobappdev.content_providers.books/book_author/query?author=<author>
				author = uri.getQueryParameter(AUTHOR_PARAMETER);
				if (author != null) {
					if (author.equals("jalal_addin_rumi"))
						qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_AUTHOR_COL_NAME
								+ EQLS + "Jalal adDin Rumi");
					else if (author.equals("hafiz"))
						qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_AUTHOR_COL_NAME
								+ EQLS + "Hafiz");
					else
						qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_AUTHOR_COL_NAME
								+ EQLS + "UKNOWN");
				}
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_AUTHOR_TBL_COLUMN_NAMES, selection,
						selectionArgs, null, null, sortOrder);
				break;
			case BOOK_COVER_IMAGE_SINGLE:
				qb.setTables(CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE);
				qb.appendWhere(CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_ISBN_COL_NUM
						+ EQLS + uri.getLastPathSegment());
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_COL_NAMES,
						selection, selectionArgs, null, null, sortOrder);
				break;
			case BOOK_COVER_IMAGE_ALL:
				qb.setTables(CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE);
				Log.d(LOGTAG,
						" BOOK_COVER_IMAGE_ALL MATCH on " + uri.toString());
				rslt = qb.query(mReadDb,
						CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_COL_NAMES,
						selection, selectionArgs, null, null, sortOrder);
				break;
			case BOOK_COVER_IMAGE_QUERY:
				qb.setTables(CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE);
				// content://org.vkedco.mobappdev.content_providers.books/book_cover_image/query?isbn=<isbn>
				Log.d(LOGTAG,
						" BOOK_COVER_IMAGE_QUERY MATCH on " + uri.toString());
				isbn = uri.getQueryParameter(ISBN_PARAMETER);
				if (isbn != null) {
					Log.d(LOGTAG, "ISBN=" + isbn);
					Log.d(LOGTAG, "WHERE="
							+ CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME
							+ EQLS + isbn);

					qb.appendWhere(CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME
							+ EQLS + "\"" + isbn + "\"");
					rslt = qb.query(mReadDb,
							CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_COL_NAMES,
							selection, selectionArgs, null, null, sortOrder);
				}
				title = uri.getQueryParameter(TITLE_PARAMETER);
				if (title != null) {
					Log.d(LOGTAG, "TITLE=" + author);
					Log.d(LOGTAG, "WHERE="
							+ CPBookDbAdptr.BOOK_TITLE_TBL_TITLE_COL_NAME
							+ EQLS + title);
					qb = new SQLiteQueryBuilder();
					qb.setTables(CPBookDbAdptr.BOOK_TITLE_TABLE);
					if (title != null) {
						qb.appendWhere(CPBookDbAdptr.BOOK_TITLE_TBL_TITLE_COL_NAME
								+ EQLS + "\"" + title + "\"");
						rslt = qb
								.query(mReadDb,
										new String[] { CPBookDbAdptr.BOOK_TITLE_TBL_ISBN_COL_NAME },
										selection, selectionArgs, null, null,
										sortOrder);
						if (rslt != null) {
							rslt.moveToFirst();
							isbn = rslt
									.getString(rslt
											.getColumnIndex(CPBookDbAdptr.BOOK_TITLE_TBL_ISBN_COL_NAME));
							rslt.close();
							rslt = null;
							qb = new SQLiteQueryBuilder();
							qb.setTables(CPBookDbAdptr.BOOK_COVER_IMAGE_TABLE);
							qb.appendWhere(CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_ISBN_COL_NAME
									+ EQLS + "\"" + isbn + "\"");
							rslt = qb
									.query(mReadDb,
											CPBookDbAdptr.BOOK_COVER_IMAGE_TBL_COL_NAMES,
											selection, selectionArgs, null,
											null, sortOrder);
						}
					}
				}
				break;
			case BOOK_AUTHOR_IMAGE_QUERY:
				qb.setTables(CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TABLE);
				// content://org.vkedco.mobappdev.content_providers.books/book_author_image/query?author=<author>
				Log.d(LOGTAG,
						" BOOK_AUTHOR_IMAGE_QUERY MATCH on " + uri.toString());
				author = uri.getQueryParameter(AUTHOR_PARAMETER);
				Log.d(LOGTAG, "AUTHOR=" + author);
				Log.d(LOGTAG, "WHERE="
						+ CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME
						+ EQLS + author);
				if (author != null) {
					qb.appendWhere(CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TBL_NAME_COL_NAME
							+ EQLS + "\"" + author + "\"");
					rslt = qb.query(mReadDb,
							CPBookDbAdptr.BOOK_AUTHOR_IMAGE_TBL_COL_NAMES,
							selection, selectionArgs, null, null, sortOrder);
				}
				break;
			default:
				break;
			}

			// notify the content resolver about the change
			if (rslt != null)
				rslt.setNotificationUri(getContext().getContentResolver(), uri);

			return rslt;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		synchronized (this) {
			return 0;
		}
	}

}
