# Bitmaptic-Android
A library to do a lot more with your bitmaps, This library puts blur effect fast and efficiently, rotate it with the same quality, manage the quality with pixel range, scale to you requirements.


# Installing

For a working implementation of this project see the `app/` folder.

1. Add it in your root 'build.gradle' at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Include the following dependency.

```
compile 'com.github.AmeerHamzaaa:Bitmaptic-Android:0.1.2'
```

# Usage

3. Just call the functions anywhere you want, all the functions return a bitmap:

Just get them like this

```java
Bitmap bitmap = null:

//Blur your bitmaps. Radius should not be more than 25. I am trying to optimize it.
bitmap = Bitmaptic.blurBitmap(bitmap, this, 20);

//Rotate Bitmap by 90 degree
bitmap = Bitmaptic.rotateBitmap(bitmap, 90);

//this will scale the bitmap to the required size by keeping the aspect ratio
bitmap = Bitmaptic.scaleToSizeKeepingAspectRatio(bitmap, 1000, true);

//this will reduce or increase the number of pixels depending upon the height and width it will compress the image with less values
bitmap = Bitmaptic.scaleToPixelQuality(bitmap, 400, 400);
```

For ease of making bitmaps from imageview or uri use:

```java

//Convert your ImageView to Bitmap
bitmap = Bitmaptic.getBitmapFromImageView(imageView);

//Convert Uri to Bitmap
bitmap = Bitmaptic.convertUritoBitmap(imageUri, context) //This might create some problems

```


### That's all you need to do.

If you think this library is useful, please press star button at upside. 
<br/>
<img src="https://phaser.io/content/news/2015/09/10000-stars.png" width="200">
<br/><br/>


Please open an issue if you find things, that are missing.

# Developed By

 * Ameer Hamza
 
 * [StackOverFlow](https://stackoverflow.com/story/ameer_hamza)
 * [Facebook](https://www.facebook.com/hamzabhatti20)
 * mailto - <aha1475@gmail.com>

# Contributions

 * Please, read the README file before opening an issue, thanks.
 * Please, all the Pull Request must be sent to the dev branch, thanks..
 
 ## License

```
MIT License

Copyright (c) 2017 Ameer Hamza

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
