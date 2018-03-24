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
	//Load types
	public static final int IDE = 0;
	public static final int JAR = 1;

	public static boolean loadLibrary(int loadType)
	{
		boolean isSuccessful = false;
		try
		{
			if (loadType == IDE)
			{
				//Load OpenCV from user library
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				isSuccessful = true;
			}
			else if (loadType == JAR)
			{
				//File to store OpenCV path
				File pathFile = new File("./OpenCV Path.txt");
				String resourcePath;

				if (pathFile.exists())
					//Get path from file
					resourcePath = readFile(pathFile);
				else
				{
					//Get path
					resourcePath = getLibraryPath();

					//Create file with path
					createFile(pathFile, resourcePath);
				}

				//Load resource from file
				loadResource(resourcePath);

				isSuccessful = true;
			}
		}
		catch (Exception e)
		{
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
		String libraryPath = "";

		//Get name of operating system
		String osName = System.getProperty("os.name");

		if (osName.startsWith("Windows"))
		{
			//Get system architecture
			int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));

			//Get library path
			String javaDir = opencvPath + "\\build\\java\\" + (bitness == 64 ? "x64" : "x86");

			//Get library file
			File lib = new File(javaDir).listFiles()[0];

			libraryPath = lib.getAbsolutePath();
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

		//Create streams
		FileInputStream in = new FileInputStream(resource.getAbsolutePath());
		File fileOut = File.createTempFile("lib", ".dll");
		OutputStream out = new FileOutputStream(fileOut);

		//Write resource data to file
		in.transferTo(out);

		//Close streams
		in.close();
		out.close();

		System.load(fileOut.toString());

		return fileOut.toString();
	}
}