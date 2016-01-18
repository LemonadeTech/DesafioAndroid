/*
 * Copyright (c) 2014 Samsung Electronics Co., Ltd.
 * All rights reserved.
 *
 * This software is a confidential and proprietary information of Samsung
 * Electronics, Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Samsung Electronics.
 */
package br.com.rr.deslemtech.model.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.CitiesData;
import br.com.rr.deslemtech.model.domain.CitiesInfo;
import br.com.rr.deslemtech.model.domain.City;
import br.com.rr.deslemtech.model.domain.SelectedCities;
import br.com.rr.deslemtech.utils.WrapperLog;

public final class Serialization {
	
	
	public static final String EXTENSION = ".data";
	
	private Serialization() {
		// do nothing
	}

	public static String getFolderPath() {
		return LemTechBO.context.getFilesDir().getPath();
	}

	/**
	 * Method to create the object ObjectOutputStream to read the file
	 * @param fileName - name of folder
	 * @param folderPath - path of the file
	 * @return saver - the object where is the path to file
	 * @throws IOException - catch the error of IOException
	 */
	public static ObjectOutputStream createDataOutputStream(final String fileName, final String folderPath) throws IOException {

		ObjectOutputStream saver = null;

		try {

			final File folder = new File(folderPath);

			if (folder.exists() || folder.mkdirs()) {

				saver = new ObjectOutputStream(new FileOutputStream(new File(folder, fileName)));
			}

		} catch (IOException e) {
			WrapperLog.error("Access to folder could not be established.", e);
			throw e;
		}

		return saver;
	}


	/**
	 * Method to create the object ObjectOutputStream to save the file
	 * @param fileName - name of folder
	 * @param folderPath - path of the file
	 * @return saver - the object where is the path to file
	 * @throws IOException - catch the error of IOException
	 */
	public static ObjectInputStream createDataInputStream(final String fileName, final String folderPath) throws IOException {

		ObjectInputStream  loader = null;

		try {

			final File folder = new File(folderPath);

			if (folder.exists() || folder.mkdirs()) {

				final File dataFile = new File(folder, fileName);

				if(dataFile.exists()) {
					loader = new ObjectInputStream(new FileInputStream(dataFile));
				}
			}

		} catch (IOException e) {
			WrapperLog.error("Access to folder could not be established.", e);
			throw e;
		}

		return loader;
	}

	public static String getFileName(final Serializable data) {
		return getFileName(data, "");
	}
	public static String getFileName(final Serializable data, final String filenameAppend) {
		
		String ret = EXTENSION;

        if(data instanceof CitiesData) {
            ret = "citiesData" + filenameAppend + ret;
        }
        else if(data instanceof CitiesInfo) {
            ret = "citiesInfo" + filenameAppend + ret;
        }
        else if(data instanceof SelectedCities) {
            ret = "selectedCities" + filenameAppend + ret;
        }
        else if(data instanceof City) {
			ret = "city" + filenameAppend + ret;
		}

		return ret;
	}
}
