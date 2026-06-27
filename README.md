# ImageHotspot

Project which adds support for clickable hitspots for ImageView.

## Installation
To install the plugin use:

```
cordova plugin add https://github.com/AsheraCordova/ImageHotspot.git
```

## Important Links
https://asheracordova.github.io/

https://asheracordova.github.io/doc/help-doc.html

https://asheracordova.github.io/doc/com/ashera/plugin/ImageHotspot.html

## Usage
The following is simple example of configuring a ImageView with hotspots:
```
  <FrameLayout android:layout_width="match_parent" android:layout_height="wrap_content">
     <ImageView
        android:id="@+id/hotspotOverlayDrawable0"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:src="@drawable/cathedral_rock"
        onTouch-android="+hotspot#onTouch"
        onAndroidTouch="+hotspot#onTouch"
        onhotspotClick='logHotspot'
        hotspots="id: test; type:target; imageWidth : 200; imageHeight : 150; percX: 0; percY:0; percWidth: 0.1; percHeight: 0.1333, id: test1; type: decoy; imageWidth : 200; imageHeight : 150; percX: 0.9; percY : 0.86666; percWidth: 0.1; percHeight: 0.1333"
        hotspotOverlayDrawable="@drawable/calatrava_cross"></ImageView>
</FrameLayout>
```
The above example creates 2 hotspots on the cathedral_rock image. The hotspots are trained on a image which is 200X150. When the hotspot is clicked, a overlay image is drawn on the hotspot.

## Attributes supported
Name                		      | Description
-------------       		      | -------------
hotspots *	                  | Simple CSS array of expressions supporting the following attributes: id, type, imageWidth, imageHeight, percX, percY, percWidth, percHeight
onhotspotClick *        		  | Call back to be called on click of the hotspot
hotspotOverlayDrawable 	      | Overlay drawable to be drawn when we click on the hotspot
hotspotOverlayDrawablesByType | If Overlay drawable needs to be drawn only for a particular type
hotspotDetectOnlyOnce         | Detect the hotspot only once. Once a hotspot is found dont call the callback again.
hotspotDebug                  | Show debug hotspots i.e rectangles with border green. Only works in android.

Note: * indicates mandatory. Also onTouch-android and onAndroidTouch are mandatory as touch events are directed to onhotspotClick internally. id for ImageView is needed for the working of the hotspot. 
