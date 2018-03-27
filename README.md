# TouchController
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
### Installing the JDK
Installing the Java Development Kit is only a requirement for developers. Users who only wish to run the JAR file can download the Java Runtime Environment (JRE) instead. If you are unsure how to do this, follow the tutorial for your operating system [here](https://docs.oracle.com/javase/9/install/overview-jdk-9-and-jre-9-installation.htm#JSJIG-GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A).
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
* [Volume Control](http://www.nirsoft.net/utils/nircmd.html) (Windows only)
