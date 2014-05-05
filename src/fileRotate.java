import java.io.*;
import java.nio.channels.*;

public class fileRotate {

	public static void main(String[] args) {
		// check args
		if (args.length < 2)	{	System.out.printf("USAGE: fileRotate.jar <fileName> <numberToRotate>\n" +
													  "fileRotate is a log / file redundancy application by Wayne Simmerson\n" +
													  "9 April 2012\n");
									System.exit(0);																	}		
		// Define variables
		String fileName = args[0];
		String curFileName = ""; String oldFileName = "";
		int numberToRotate = Integer.parseInt(args[1]);
		File file = new File(fileName);
		File curFile = null; File oldFile = null;
		
		// verify file
		if (!file.exists()) {	System.out.printf("%s does not exist, please check path and name\n", fileName);
									System.exit(0);																	}
		
		// move files
		do{
			// take one off the top
			numberToRotate--;
			
			//Create new numbered filename
			curFileName = fileName + "." + Integer.toString(numberToRotate);
			oldFileName = fileName + "." + Integer.toString(numberToRotate + 1);	
		
			// don't forget the original file
			if (numberToRotate == 0)	{	curFileName = fileName;		}
		
			// create files
			curFile = new File(curFileName);
			oldFile = new File(oldFileName);
		
			// copy the file
			if (curFile.exists())
				try {
					copyFile(curFile, oldFile);
				} catch (IOException e)		{	e.printStackTrace();	}
			
		}while(numberToRotate != 0 );
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		// create destination if it doesn't exist
	    if(!destFile.exists()) {	destFile.createNewFile();	}

	    // define channels
	    FileChannel source = null;
	    FileChannel destination = null;

	    // copy the file
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();			// Clean Up!
	        }
	        if(destination != null) {
	            destination.close();	// Clean Up!
	        }
	    }
	}

}
