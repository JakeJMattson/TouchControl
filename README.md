# TouchControl
<p align="justify">
This project is intended to be a touch screen without the screen. By using only a camera and some code, you can perform various actions just by waving around your hand. Below you can find some example applications of this concept.
</p>

## Demos
### MousePad

<div style="position:relative;padding-top:56.25%;">
  <iframe src="https://www.youtube.com/embed/2-ApniOaKHs?rel=0" frameborder="0" allowfullscreen
    style="position:absolute;top:0;left:0;width:100%;height:100%;"></iframe>
</div>

### PianoKey

<div style="position:relative;padding-top:56.25%;">
  <iframe src="https://www.youtube.com/embed/-dA1ajjv6EE?rel=0" frameborder="0" allowfullscreen
    style="position:absolute;top:0;left:0;width:100%;height:100%;"></iframe>
</div>

### VolumeSlider

<div style="position:relative;padding-top:56.25%;">
  <iframe src="https://www.youtube.com/embed/Fd5ODeewL9c?rel=0" frameborder="0" allowfullscreen
    style="position:absolute;top:0;left:0;width:100%;height:100%;"></iframe>
</div>

## Prerequisites

### Operating Systems Supported
* Windows
* Mac OS
* Linux

### Java
* [Java Downloads](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

### Libraries
* [OpenCV 3.4.1](https://opencv.org/releases.html)

## Getting Started (Software)

### Installing Java
<p align="justify">
Installing the Java Development Kit (JDK) is only a requirement for developers. Users who plan to solely use the program (run the pre-built JAR file) are only required to install the Java Runtime Environment (JRE). The minimum compiler compliance is Java 9, meaning that systems with installations of Java 9 and above can run the JAR. Simply install the version of your choice from the link above and run the installer.
</p>

### Building OpenCV
<p align="justify">
<a href="https://opencv.org/">OpenCV</a> is required for both developers and users in order to run this code. Please read the tutorial on <a href="https://github.com/opencv-java/opencv-java-tutorials/blob/master/docs/source/01-installing-opencv-for-java.rst">Installing OpenCV for Java</a> in order to correctly build OpenCV for your operating system.
</p>

### Downloading NirCmd
<p align="justify">
"<a href="http://www.nirsoft.net/utils/nircmd.html">NirCmd</a> is a small command-line utility that allows you to do some useful tasks without displaying any user interface." This program is used by the VolumeSlider demo to control system volume on Windows. As there are no runnable versions of TouchControl with this functionality, NirCmd is optional and only for developers who wish to work with this part of the code.
</p>

## Getting Started (Hardware)

### Camera
<p align="justify">
Position a camera (rotated at 180 degrees) above a dark surface. The camera should remain stationary while using the program and the background should remain unchanged. A darker surface will lead to better detection.
</p>

### Operation
<p align="justify">
Move your finger (or any object) through the area covered by the camera. The program will detect the location of the object and perform an action depending on the Touchable object being used.
</p>

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* [OS Compatibility](https://stackoverflow.com/a/18780559)
* [Foreground Extraction](https://stackoverflow.com/a/27036614)
