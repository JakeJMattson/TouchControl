/**
 * Class Description:
 * Load the OpenCV library (used when program is run as JAR)
 */

package touchcontrol.utils;

import java.io.*;

import javax.swing.*;

import org.opencv.core.Core;

public final class LibLoader
{
	//Class constants (load types)
	public static final int IDE = 0;
	public static final int JAR = 1;

	public static boolean loadLibrary(int loadType)
	{
		//Control flow
		boolean isSuccessful = false;

		try
		{
			if (loadType == IDE)
			{
				//Load OpenCV from user library
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

				//Mark load as successful
				isSuccessful = true;
			}
			else if (loadType == JAR)
			{
				//Path to library
				String resourcePath;

				//File to store OpenCV path
				File pathFile = new File("OpenCV Path.txt");

				if (pathFile.exists())
					//Get path from file
					resourcePath = readFile(pathFile);
				else
				{
					//Get path
					resourcePath = getLibraryPath();

					//Create file with path
					if (resourcePath != null)
						createFile(pathFile, resourcePath);
				}

				if (resourcePath != null)
				{
					//Load resource from file
					loadResource(resourcePath);

					//Mark load as successful
					isSuccessful = true;
				}
			}
		}
		catch (Exception e)
		{
			//Report error
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load OpenCV!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
		}

		return isSuccessful;
	}

	private static String readFile(File pathFile) throws IOException
	{
		//Create reader
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));

		//Get path from file
		String opencvPath = reader.readLine().trim();

		//Close reader
		reader.close();

		return opencvPath;
	}

	private static String getLibraryPath()
	{
		//Get OpenCV path from user
		String opencvPath = createChooser();

		//OS-specific variables
		String libraryPath = null, javaDir = "", extension = "";
		boolean isValidOS = false;

		//Get name of operating system
		String osName = System.getProperty("os.name");

		if (osName.startsWith("Windows"))
		{
			//Get system architecture
			int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));

			//Get library path
			javaDir = opencvPath + "\\build\\java\\" + (bitness == 64 ? "x64" : "x86");

			//Set OS-specific library extension
			extension = ".dll";

			//Mark OS as valid
			isValidOS = true;
		}
		else if (osName.equals("Mac OS X"))
		{
			//OpenCV version folder
			String verison = new File(opencvPath).listFiles()[1].getAbsolutePath(); //Ignore .DS_Store

			//Get library path
			javaDir = verison + "/Share/OpenCV/Java/";

			//Set OS-specific library extension
			extension = ".dylib";

			//Mark OS as valid
			isValidOS = true;
		}
		else
			JOptionPane.showMessageDialog(null, "Unsupported OS!", "Fatal Error", JOptionPane.INFORMATION_MESSAGE);

		if (isValidOS)
		{
			//Get library file
			File[] resources = new File(javaDir).listFiles();

			//Get file with extension
			for (File file : resources)
				if (file.getName().endsWith(extension))
					libraryPath = file.getAbsolutePath();
		}

		return libraryPath;
	}

	private static String createChooser()
	{
		//Create file chooser
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Select the OpenCV folder");

		//Ask for directory until one is selected
		while (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			;

		//Return selected path
		return chooser.getSelectedFile().getAbsolutePath();
	}

	private static void createFile(File pathFile, String resourcePath)
			throws FileNotFoundException, UnsupportedEncodingException
	{
		//Create file writer
		PrintWriter writer = new PrintWriter(pathFile, "UTF-8");

		//Write path to file
		writer.println(resourcePath);

		//Save file and stop writing
		writer.close();
	}

	private static String loadResource(String resourcePath) throws IOException
	{
		//Get resource file
		File resource = new File(resourcePath);

		//Create temporary file
		String name = resource.getName();
		String extension = name.substring(name.indexOf("."));
		File fileOut = File.createTempFile("lib", extension);

		//Create streams
		FileInputStream in = new FileInputStream(resource.getAbsolutePath());
		OutputStream out = new FileOutputStream(fileOut);

		//Write resource data to file
		in.transferTo(out);

		//Close streams
		in.close();
		out.close();

		//Load library
		System.load(fileOut.toString());

		return fileOut.toString();
	}
}