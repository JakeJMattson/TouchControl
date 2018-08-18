<p align="center">
  <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html">
    <img src="https://img.shields.io/badge/Java-10-blue.svg" alt="Java 10">
  </a>
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-1.2.60-blue.svg" alt="Kotlin 1.2.60">
  </a>
  <a href="https://sourceforge.net/projects/opencvlibrary/files/opencv-win/3.4.2/opencv-3.4.2-vc14_vc15.exe/download">
    <img src="https://img.shields.io/badge/OpenCV-3.4.2-blue.svg">
  </a>
  <a href="https://GitHub.com/JakeJMattson/TouchControl/releases/">
    <img src="https://img.shields.io/github/release/JakeJMattson/TouchControl.svg" alt="release">
  </a>
  <a href="LICENSE.md">
    <img src="https://img.shields.io/github/license/JakeJMattson/TouchControl.svg">
  </a>
</p>

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

### Languages
* [Java](https://go.java/index.html?intcmp=gojava-banner-java-com)
* [Kotlin](https://kotlinlang.org/)

### Libraries
* [OpenCV](https://opencv.org/about.html)
* [NirCmd](http://www.nirsoft.net/utils/nircmd.html)

## Getting Started (Software)
<p align="justify">
The minimum version requirements for each additional software are listed in the badges at the top of the readme.
It is recommended that you install the most updated version at your time of installation.
</p>

### Installing Java
<p align="justify">
Visit the <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html">Java Downloads</a> 
page and select the version of your choice.
Run the installer and follow the instructions provided.
</p>

### Installing Kotlin
**Note:** Included as Maven dependency. Below are manual instructions.
<br /><br />
Kotlin can be added to your project manually by following the instructions on the Kotlin website. It supports [IntelliJ](https://kotlinlang.org/docs/tutorials/getting-started.html), [Eclipse](https://kotlinlang.org/docs/tutorials/getting-started-eclipse.html), or a simple [command line compiler](https://kotlinlang.org/docs/tutorials/command-line.html).

### Building OpenCV
**Note:** Included as Maven dependency. Below are manual instructions.
<br/>
<p align="justify">
Visit the <a href="https://opencv.org/releases.html">OpenCV Releases</a> page and select the version of your choice.
Note: older versions of this library may work with this code, but this is not gauranteed.
Please read the tutorial on
<a href="https://github.com/opencv-java/opencv-java-tutorials/blob/master/docs/source/01-installing-opencv-for-java.rst">Installing OpenCV for Java</a> 
in order to correctly build OpenCV for your operating system.
</p>

### Downloading NirCmd
<p align="justify">
NirCmd is used by the VolumeSlider demo to control system volume on Windows. As there are no released versions of TouchControl with this functionality, NirCmd is optional and only for developers who wish to work with this part of the code. You can download either the <a href="http://www.nirsoft.net/utils/nircmd-x64.zip">64 bit</a> or the <a href="http://www.nirsoft.net/utils/nircmd.zip">32 bit</a> version.
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

## Building
TouchControl is built with Maven. To build the `pom.xml`, please follow the import instructions for your IDE.
* [IntelliJ](https://www.tutorialspoint.com/maven/maven_intellij_idea.htm)
* [Eclipse](https://www.tutorialspoint.com/maven/maven_eclispe_ide.htm)
* [NetBeans](https://www.tutorialspoint.com/maven/maven_netbeans.htm)

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* [OpenCV binaries (Maven)](https://github.com/bytedeco/javacpp-presets)
* [Foreground Extraction](https://stackoverflow.com/a/27036614)
