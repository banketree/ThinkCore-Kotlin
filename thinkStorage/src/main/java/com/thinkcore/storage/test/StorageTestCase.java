package com.thinkcore.storage.test;

import android.content.Context;
//import android.test.InstrumentationTestCase;

import com.thinkcore.storage.Storage;
import com.thinkcore.storage.StorageConfiguration;
import com.thinkcore.storage.TStorage;
import com.thinkcore.storage.helpers.OrderType;

import java.io.File;
import java.util.List;

//public class StorageTestCase extends InstrumentationTestCase {
//
//	private Storage storage;
//
//	private final static String DIR_NAME = "Storage Test";
//	private final static String FILE_NAME = "test.txt";
//	private final static String FILE_CONTENT = "some file content";
//
//	private final static String FILE_SECURE_NAME = "test_secure.txt";
//	private final static String FILE_SECURE_CONTENT = "something very secret";
//
//	@Override
//	protected void setUp() throws Exception {
//		Context context = getInstrumentation().getContext();
//
//		// set a storage
//		storage = null;
//		if (TStorage.getInstance().isExternalStorageWritable()) {
//			storage = TStorage.getInstance().getExternalStorage();
//		} else {
//			storage = TStorage.getInstance().getInternalStorage(context);
//		}
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//
//		// delete dir if exists
//		storage.deleteDirectory(DIR_NAME);
//
//		super.tearDown();
//	}
//
//	/**
//	 * Create directory and check that the directory was created
//	 */
//	public void testCreateDirectory() {
//
//		// TEST: create dir
//		boolean wasCreated = storage.createDirectory(DIR_NAME, true);
//		assertEquals(true, wasCreated);
//
//	}
//
//	/**
//	 * Create directory and check that the directory was created
//	 */
//	public void testCreateFile() {
//
//		// create dir
//		testCreateDirectory();
//
//		// TEST: create file
//		boolean wasCreated = storage.createFile(DIR_NAME, FILE_NAME,
//				FILE_CONTENT);
//		assertEquals(true, wasCreated);
//
//	}
//
//	/**
//	 * Create directory and check that the directory was created
//	 */
//	public void testReadFile() {
//
//		// create file with content
//		testCreateFile();
//
//		// TEST: read the content and test
//		String content = storage.readTextFile(DIR_NAME, FILE_NAME);
//		assertEquals(FILE_CONTENT, content);
//
//	}
//
//	/**
//	 * Create directory and check that the directory was created
//	 */
//	public void testAppendFile() {
//
//		// create file with content
//		testCreateFile();
//
//		String newData = "new added data";
//
//		// TEST: append new data and test
//		storage.appendFile(DIR_NAME, FILE_NAME, newData);
//		String content = storage.readTextFile(DIR_NAME, FILE_NAME);
//		assertTrue(content.contains(newData));
//	}
//
//	/**
//	 * Create file with encrypted data
//	 */
//	public void testEncryptContent() {
//
//		// create dir
//		testCreateDirectory();
//
//		// set encryption
//		final String IVX = "abcdefghijklmnop";
//		final String SECRET_KEY = "secret1234567890";
//
//		StorageConfiguration configuration = new StorageConfiguration.Builder()
//				.setEncryptContent(IVX, SECRET_KEY).build();
//		TStorage.updateConfiguration(configuration);
//
//		// create file
//		storage.createFile(DIR_NAME, FILE_SECURE_NAME, FILE_SECURE_CONTENT);
//
//		// TEST: check the content of the file to be encrypted
//		String content = storage.readTextFile(DIR_NAME, FILE_SECURE_NAME);
//		assertEquals(FILE_SECURE_CONTENT, content);
//
//		// TEST: check after reseting the configuration to default
//		TStorage.resetConfiguration();
//		content = storage.readTextFile(DIR_NAME, FILE_SECURE_NAME);
//		assertNotSame(FILE_SECURE_CONTENT, content);
//	}
//
//	public void testRename() {
//
//		// create file
//		testCreateFile();
//
//		// rename
//		File file = storage.getFile(DIR_NAME, FILE_NAME);
//		storage.rename(file, "new_" + FILE_NAME);
//		boolean isExist = storage.isFileExist(DIR_NAME, "new_" + FILE_NAME);
//		assertEquals(true, isExist);
//	}
//
//	public void testCopy() {
//
//		// create file
//		testCreateFile();
//
//		// copy file
//		File fileSource = storage.getFile(DIR_NAME, FILE_NAME);
//		storage.copy(fileSource, DIR_NAME, FILE_NAME + "C");
//
//		// validate existence
//		boolean isExist = storage.isFileExist(DIR_NAME, FILE_NAME + "C");
//		assertEquals(true, isExist);
//
//		// validate content
//		assertEquals(storage.readTextFile(DIR_NAME, FILE_NAME),
//				storage.readTextFile(DIR_NAME, FILE_NAME + "C"));
//	}
//
//	public void testMove() {
//
//		// create file
//		testCreateFile();
//
//		// copy file
//		File fileSource = storage.getFile(DIR_NAME, FILE_NAME);
//		storage.move(fileSource, DIR_NAME, FILE_NAME + "C");
//
//		// validate existence destination
//		boolean isExist = storage.isFileExist(DIR_NAME, FILE_NAME + "C");
//		assertEquals(true, isExist);
//
//		// validate existence source (it shouldn't exist)
//		isExist = storage.isFileExist(DIR_NAME, FILE_NAME);
//		assertEquals(false, isExist);
//	}
//
//	public void testGetFilesByRegex() {
//
//		// create dir
//		testCreateDirectory();
//
//		// create 5 files
//		storage.createFile(DIR_NAME, "file1.txt", "");
//		storage.createFile(DIR_NAME, "file2.txt", "");
//		storage.createFile(DIR_NAME, "file3.log", "");
//		storage.createFile(DIR_NAME, "file4.log", "");
//		storage.createFile(DIR_NAME, "file5.txt", "");
//
//		// get files that ends with *.txt only. should be 3 of them
//		String TXT_PATTERN = "([^\\s]+(\\.(?i)(txt))$)";
//		List<File> filesTexts = storage.getFiles(DIR_NAME, TXT_PATTERN);
//		assertEquals(3, filesTexts.size());
//
//		// create more log files and check for *.log. should be 4 of them
//		String LOG_PATTERN = "([^\\s]+(\\.(?i)(log))$)";
//		storage.createFile(DIR_NAME, "file6.log", "");
//		storage.createFile(DIR_NAME, "file7.log", "");
//		List<File> filesLogs = storage.getFiles(DIR_NAME, LOG_PATTERN);
//		assertEquals(4, filesLogs.size());
//
//		// create dir and add files to dir. check again for *.log files. should
//		// be 4 of them.
//		storage.createDirectory(DIR_NAME + File.separator + "New Dir");
//		storage.createFile(DIR_NAME + File.separator + "New Dir", "file8.log",
//				"");
//		storage.createFile(DIR_NAME + File.separator + "New Dir", "file9.log",
//				"");
//		storage.createFile(DIR_NAME + File.separator + "New Dir",
//				"file10.txt", "");
//		List<File> filesLogs2 = storage.getFiles(DIR_NAME, LOG_PATTERN);
//		assertEquals(4, filesLogs2.size());
//
//		// check inside new dir for *.log files. should be 2 of them
//		List<File> filesLogs3 = storage.getFiles(DIR_NAME + File.separator
//				+ "New Dir", LOG_PATTERN);
//		assertEquals(2, filesLogs3.size());
//	}
//
//	public void testGetFilesByOrder() {
//
//		// create dir
//		testCreateDirectory();
//
//		// TEST - Order by SIZE
//		storage.createFile(DIR_NAME, "file1.txt", "111222333");
//		storage.createFile(DIR_NAME, "file2.txt", "");
//		storage.createFile(DIR_NAME, "file3.log", "111");
//		List<File> filesSize = storage.getFiles(DIR_NAME, OrderType.SIZE);
//		assertEquals("file2.txt", filesSize.get(0).getName());
//		assertEquals("file3.log", filesSize.get(1).getName());
//		assertEquals("file1.txt", filesSize.get(2).getName());
//
//		// refresh directory
//		storage.deleteDirectory(DIR_NAME);
//		testCreateDirectory();
//
//		// TEST - Order by NAME
//		storage.createFile(DIR_NAME, "bbb.txt", "111222333");
//		storage.createFile(DIR_NAME, "ccc.txt", "");
//		storage.createFile(DIR_NAME, "aaa.log", "111");
//		List<File> filesName = storage.getFiles(DIR_NAME, OrderType.NAME);
//		assertEquals("aaa.log", filesName.get(0).getName());
//		assertEquals("bbb.txt", filesName.get(1).getName());
//		assertEquals("ccc.txt", filesName.get(2).getName());
//
//		// refresh directory
//		storage.deleteDirectory(DIR_NAME);
//		testCreateDirectory();
//
//		// TEST - Order by DATE
//		storage.createFile(DIR_NAME, "aaa.txt", "123456789");
//		sleep(1000);
//		storage.createFile(DIR_NAME, "bbb.txt", "123456789");
//		sleep(1000);
//		storage.createFile(DIR_NAME, "ccc.log", "123456789");
//		sleep(1000);
//		storage.appendFile(DIR_NAME, "bbb.txt", "some new content");
//		List<File> files = storage.getFiles(DIR_NAME, OrderType.DATE);
//		assertEquals("bbb.txt", files.get(0).getName());
//		assertEquals("ccc.log", files.get(1).getName());
//		assertEquals("aaa.txt", files.get(2).getName());
//	}
//
//	private void sleep(long millis) {
//		try {
//			Thread.sleep(millis);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//}
