package br.com.aspprev.uteis;

import java.io.File;
import java.util.List;

public class FilesFound {
	public List<File> filePath;

	public static boolean isTheFile(List<File> filePath) {
		if (filePath != null && !filePath.isEmpty()) {
			for (File oneFound : filePath) {
				if (!oneFound.exists()) {
					return false;
				}
			}
		}
		return true;
	}
}
