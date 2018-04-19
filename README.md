# TouchControl
This project is intended to be a touch screen without the screen by using only a camera and some code. 
Current demos include: MousePad, PianoKey, and VolumeSlider.

## Prerequisites

### Operating Systems Supported
* Windows
* Mac OS
* Linux

### Java Development Kit (minimum)
* [Java SE Development Kit 9](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)

### Libraries
* [OpenCV 3.4.1](https://opencv.org/releases.html)

## Getting Started (Software)

### Installing Java
Installing the Java Development Kit (JDK) is only a requirement for developers. Users who plan to solely use the program (run the pre-built JAR file) are only required to install the Java Runtime Environment (JRE). The minimum compiler compliance is Java 9, meaning that systems with installations of Java 9 and above can run the JAR. Simply install the version of your choice from the link above and run the installer.

### NirCmd
"[NirCmd](http://www.nirsoft.net/utils/nircmd.html) is a small command-line utility that allows you to do some useful tasks without displaying any user interface." This program is used to control system volume. It is used in the VolumeSlider demo and is only for Windows operating systems. As there are no runnable versions of TouchControl with this functionality, NirCmd is optional and only for developers who wish to work with the VolumeSlider demo.

### Building OpenCV
[OpenCV](https://opencv.org/) is required for both developers and users in order to run this code. Please read the tutorial on [Installing OpenCV for Java](https://github.com/opencv-java/opencv-java-tutorials/blob/master/docs/source/01-installing-opencv-for-java.rst) in order to correctly build OpenCV for your operating system.

## Getting Started (Hardware)

### Camera
Position a camera (rotated at 180 degrees) above a dark surface. The camera should remain stationary while using the program and the background should remain unchanged. A darker surface will lead to better detection.

### Operation
Move your finger (or any object) through the area covered by the camera. The program will detect the location of the object and perform an action depending on the Touchable object being used.

## Acknowledgments
* [OS Compatibility](https://stackoverflow.com/a/18780559)
* [Foreground Extraction](https://stackoverflow.com/a/27036614)
