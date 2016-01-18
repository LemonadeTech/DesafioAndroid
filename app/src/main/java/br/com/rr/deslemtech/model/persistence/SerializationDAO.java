package br.com.rr.deslemtech.model.persistence;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

import br.com.rr.deslemtech.utils.WrapperLog;

public final class SerializationDAO {

	private static final Object SYNC_OBJECT = new Object();

	private SerializationDAO() {
		super();
	}

	/**
	 * @return boolean
	 * @throws IOException 
	 */
	public static boolean persistData(final Serializable data) throws IOException {
		return persistData(data, "");
	}
	public static boolean persistData(final Serializable data, final String filenameAppend) throws IOException {
		
		boolean saved = false;
		
		synchronized (SYNC_OBJECT) {
			
			String fileName = Serialization.getFileName(data, filenameAppend);
			
			final ObjectOutputStream saver = Serialization.createDataOutputStream(
					fileName, 
					Serialization.getFolderPath());
			
			try {
				saver.writeObject(data);
				saver.flush();
				saver.close();
				saved = true;
				WrapperLog.error("saved on persistData: " + fileName);
			} catch (IOException e) {
				WrapperLog.error("Could not save on persistData." + fileName, e);
				saved = false;
			}
			
			return saved;
		}
	}
	
	/**
	 * @return
	 * @throws OptionalDataException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object loadData(final Serializable data) throws OptionalDataException, ClassNotFoundException, IOException {
		return loadData(data, "");
	}
	public static Object loadData(final Serializable data, final String filenameAppend) throws OptionalDataException, ClassNotFoundException, IOException {
		
		ObjectInputStream loader;
		
		Object ret = null;
		
		String fileName = Serialization.getFileName(data, filenameAppend);
		
		
		if( existData(fileName, Serialization.getFolderPath()) ) {
			
			loader = Serialization.createDataInputStream( fileName, Serialization.getFolderPath() );
			
			if(loader != null) {
				
				try {
					ret = loader.readObject();
					WrapperLog.error("loaded on loadData: " + fileName);
				} catch (IOException e) {
					WrapperLog.error("Could not load on loadData." + fileName, e);
					throw e;
				} finally {
					loader.close();
				}
			}
		}
			
		return ret;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static boolean deleteData(final Serializable data) {
		boolean deleted = false;

		synchronized (SYNC_OBJECT) {
			
			final File file = new File(Serialization.getFolderPath(), Serialization.getFileName(data));
			
			if (file.delete()) {
				deleted = true;
			}
		}
	
		return deleted;
	}
	
	/**
	 *
	 * @param fileName
	 * @param folderPath
	 * @return
	 */
	public static boolean existData(final String fileName, final String folderPath) {
		
		// get file pointer
		File data = new File(folderPath, fileName);

		return data.exists();
	}
}
