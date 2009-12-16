package com.jcommerce.core.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.jcommerce.core.util.DataStoreUtils;

/*
 * This class can only be compiled in a non-google project
 */
public class TestZipImExport extends BaseDAOTestCase {

	
	String PATH2 = "D:/JCommerce/liveDSData";
	
	String PATH = "D:/JCommerce/JCommerceGae/liveDSData";
	String FILE = "liveDSData.zip";

	
	@Override
    public String getDbStorePath() {
//    	return "D:/JCommerce/JCommerceGae/war";
    	return "D:/JCommerce/JCommerceGae/testdatastore";
    }
	private boolean isIncremental() {
		// false if want to clear before importing
		return false;
		// true if want to import without clearing old data
//		return true;
	}
	
	@Override
	public boolean needCleanOnStartup() {
    	return false;
    }
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testZipOutput() {
		System.out.println("start of testZipOutput");
		try {
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(new File(PATH2, FILE)));

			
			ZipEntry entry = new ZipEntry("mydata.txt");
			zout.putNextEntry(entry);
			zout.write("abc".getBytes());
			zout.closeEntry();
			
			entry = new ZipEntry("files/abc.jpg");
			zout.putNextEntry(entry);
			zout.closeEntry();
			zout.close();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end of testZipOutput");
		
		
	}
	
	public void testZipInput() {
		System.out.println("start of testZipInput");
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(new File(PATH, FILE)));
			
			while(true) {
				ZipEntry entry = zin.getNextEntry();
				if(entry == null) {
					break;
				}
				String entryName = entry.getName();
				System.out.println("entryName: "+entryName+", size: "+entry.getSize());
				
				if(entry.isDirectory()) {
					// files
					File dir = new File(PATH2, entryName);
					if(dir.exists()) {
						// clear existing
						for(File file : dir.listFiles()) {
							file.delete();
						}
					} else {
						dir.mkdir();
					}
					continue;
					
				}
				else {
					// my data
					if(entryName.endsWith(".txt")) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(zin, ENCODING));
						String newline = null;
			    		while(true) {
			    			newline = reader.readLine();
			    			System.out.println("newline: "+newline);
			    			if(newline==null) {
			    				break;
			    			}
			    		}						
					}
					else {
						OutputStream out = new FileOutputStream(new File(PATH2, entryName));
						byte[] buffer = new byte[4012];
						while(true) {
							int bytesRead = zin.read(buffer);
							if(bytesRead==-1) {
								break;
							}
							out.write(buffer, 0, bytesRead);
						}
						out.flush();
						out.close();
						
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end of testZipInput");
	
	}
	
	
	public void testZipInputOutput() {
		
		System.out.println("start of testZipInputOutput");
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(new File(PATH, FILE)));
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(new File(PATH2, FILE)));
			while(true) {
				ZipEntry entry = zin.getNextEntry();
				if(entry == null) {
					break;
				}
				String entryName = entry.getName();
				System.out.println("entryName: "+entryName+", size: "+entry.getSize());
				
				if(entry.isDirectory()) {
					continue;
				}
				else {
					// my data
					byte[] bytes = readStreamAsBytes(zin);
					zout.putNextEntry(new ZipEntry(entryName));
					zout.write(bytes);
					zout.closeEntry();
				}

			}

			zin.close();
			zout.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end of testZipInputOutput");
	
	}
	
	private byte[] readStreamAsBytes(InputStream in) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4012];
			while(true) {
				int bytesRead = in.read(buffer);
				if(bytesRead==-1) {
					break;
				}
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void testImport() {
		
		System.out.println("start of testImport");
		try {
			
			if(!isIncremental()) {
				clearDS();
			}
			InputStream in = new FileInputStream(new File(PATH, FILE));
			DataStoreUtils.importDSFromZip(in, getDefaultManager());
						
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end of testImport");
	
	}
	
	
	public void testExport() {
		
		System.out.println("start of testExport");
		try {
			OutputStream out = new FileOutputStream(new File(PATH2, FILE));
			DataStoreUtils.exportDS2Zip(out, getDefaultManager());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end of testExport");
	
	}
}
