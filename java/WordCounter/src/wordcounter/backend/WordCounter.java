/**
 * Copyright Brandon Ragsdale (c) 2015 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package wordcounter.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordCounter {
    private File directoryOrFile;
    private String word;

    public File getDirectoryOrFile() {
        return directoryOrFile;
    }

    public void setDirectoryOrFile(File directoryOrFile) {
        this.directoryOrFile = directoryOrFile;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public boolean wordCounterFieldsAreSet() {
    	if (word.equals("")) {
    		return false;
    	}
    	
    	if (directoryOrFile.equals(null)) {
    		return false;
    	}
    	
    	return true;
    }
    
    public List<String> getWordOccurences() throws IOException {
    	if (directoryOrFile.isFile()) {
    		return getSingleFileOutput(directoryOrFile);	
    	}
    	
    	File[] files = directoryOrFile.listFiles();
    	return getMultipleFileOutput(files);
    }
    
    private List<String> getSingleFileOutput(File file) throws IOException {
    	List<String> singleFileWordOccurences = new ArrayList<>();
    	
    	FileReader fr = null;
    	BufferedReader br = null;
    	String line;
    	int lineCount = 0;
    	int wordOccurenceCount = 0;
    	try {
        	fr = new FileReader(file);
        	br = new BufferedReader(fr);
        	
        	singleFileWordOccurences.add(file.getAbsolutePath() + ":\n");
        	
        	while ((line = br.readLine()) != null) {
        		lineCount++;
        		
        		//TODO: this only checks for words between two spaces
        		//so we must account for words that begin at the beginning
        		//and end of lines as well
    			if (line.contains(" " + word + " ")) {
    				singleFileWordOccurences.add("\"" + word + "\"" +  " found on line " + lineCount + ": " + line + "\n");
    				wordOccurenceCount++;
    			}
    		}
        	
        	singleFileWordOccurences.add("\"" + word + "\"" + " found " + wordOccurenceCount + " times.\n");	
		} finally {
	    	fr.close();
	    	br.close();	
		}
    	
    	return singleFileWordOccurences;
    }
    
    private List<String> getMultipleFileOutput(File[] files) throws IOException {
    	List<String> multipleFileWordOccurences = new ArrayList<>();
    	
    	for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			
			BufferedReader br = null;
			FileReader fr = null;
	    	String line;
	    	int lineCount = 0;
	    	int wordOccurenceCount = 0;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);

		    	multipleFileWordOccurences.add(file.getAbsolutePath() + ":\n");
		    	
		    	while ((line = br.readLine()) != null) {
		    		lineCount++;
		    		
	        		//TODO: this only checks for words between two spaces
	        		//so we must account for words that begin at the beginning
	        		//and end of lines as well
					if (line.contains(" " + word + " ")) {
						multipleFileWordOccurences.add("\"" + word + "\"" +  " found on line " + lineCount + ": " + line + "\n");
						wordOccurenceCount++;
					}
				}
		    	
		    	multipleFileWordOccurences.add("\"" + word + "\"" + " found " + wordOccurenceCount + " times.\n");
			} finally {
		    	fr.close();
		    	br.close();
			}
		}
    	
    	return multipleFileWordOccurences;
    }
}
