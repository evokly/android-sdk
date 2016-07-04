![logo](https://evok.ly/wp-content/uploads/2016/06/evo-ost.png)

#Android SDK
*Beacons Proximity Marketing Platform*

##Public API Key
Evokly Public API Key is required. Get one from [https://evok.ly](https://evok.ly)


##Getting started

Copy `evokly-framework.aar` to your Android Studio project `libs` folder.

In your `build.gradle`:

```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile (name: 'evokly-framework', ext:'aar')
}
```

In your `AndroidManifest.xml`:

```xml
<activity
	android:name="com.evokly.sdk.actions.activity.ActionActivity"
	android:theme="@style/Theme.Evokly.Translucent"
	android:configChanges="orientation|uiMode|screenSize"
	android:screenOrientation="fullSensor"
	android:excludeFromRecents="true">
	<intent-filter>
		<action android:name="android.intent.action.VIEW" />
		<category android:name="android.intent.category.DEFAULT" />
		<category android:name="android.intent.category.BROWSABLE" />
		<data android:scheme="evokly-subdomain" android:host="action" />
	</intent-filter>
</activity>
```

Replace `evokly-subdomain` with your Evokly Subdomain using format `evokly-yoursubdomain`.

In your main `Activity` class:

```java
import com.evokly.sdk.Evokly;
import com.evokly.sdk.ServiceSettings;
```

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
   	setContentView(R.layout.activity_main);
   	
   	// initialize Evokly
	ServiceSettings serviceSettings = new ServiceSettings();
	serviceSettings.subdomain = "your_subdomain";
	serviceSettings.apiKey = "your_public_apikey";

	Evokly.initialize(this, serviceSettings);
}
```

```java
@Override
protected void onDestroy() {
	Evokly.dispose(this);
	super.onDestroy();
}
```

##Debuging
There is a debug screen to help you see what's going on in Evokly. Present it with:

```java
Evokly.openDebug(Context context);
```
##Android 6.x Marshmallow Permissions
Beginning in Android 6.0 (API level 23), users grant permissions to apps while the app is running, not when they install the app. Keep in mind Evokly framework requires `Manifest.permission.ACCESS_COARSE_LOCATION` permission granted to operate correctly.

```java
private static final int REQUEST_COARSE_LOCATION_PERMISSIONS = 0x21;  

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	// check permission
	int permissionCheck;
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		permissionCheck = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_COARSE_LOCATION);
	} else {
		permissionCheck = PackageManager.PERMISSION_GRANTED;
	}

	if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
		/*
		...
		initialize Evokly framework
		...
		*/
	} else {
		if (ActivityCompat.shouldShowRequestPermissionRationale(this,
				Manifest.permission.ACCESS_COARSE_LOCATION)) {
		} else {
			ActivityCompat.requestPermissions(this,
					new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
					REQUEST_COARSE_LOCATION_PERMISSIONS);
		}
	}
}

@Override
public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
	switch (requestCode) {
		case REQUEST_COARSE_LOCATION_PERMISSIONS: {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				/*
				...
				initialize Evokly framework
				...
				*/
			} else {
				/*
				...
				convince user to grant this permission
				...
				*/
			}
		}
	}
}
```

##License

```
The MIT License (MIT)

Copyright (c) 2016 Evokly S.A.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```