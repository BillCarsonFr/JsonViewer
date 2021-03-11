# JsonViewer
An android JsonViewer to display formatted json with fold/unfold options

<img src="https://github.com/BillCarsonFr/JsonViewer/blob/master/screenshots/jsonviewer.gif" width="270"/>

## Dependencies

Add this in your root build.gradle at the end of repositories:

````
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
````

````
    implementation 'com.github.BillCarsonFr:JsonViewer:0.6'
````

This library depends on epoxy
````
    implementation("com.airbnb.android:epoxy:$epoxy_version")
    kapt "com.airbnb.android:epoxy-processor:$epoxy_version"
    implementation 'com.airbnb.android:mvrx:1.5.1'
````


## Usage

As a dialog

````kotlin
  JSonViewerDialog.newInstance(
                jsonString,
                -1, // open All
                true,
                JSonViewerStyleProvider(
                        keyColor = ...,
                        secondaryColor = ...,
                        stringColor = ...,
                        baseColor = ...,
                        booleanColor = ...,
                        numberColor = ...
                )
        ).show(childFragmentManager, "JSON_VIEWER")
````

As a fragment in your activity
````kotlin
 val sample = <Your JSON string>
 
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                JSonViewerFragment.newInstance(
                    sample,
                    initialOpenDepth = -1,
                    wrap = wrap
                ),
                "JSONVIEWER"
            )
            .addToBackStack(null)
            .commit()
            
````


