# ClearScreenHelper

［[中文](https://github.com/Yellow5A5/ClearScreenHelper/blob/master/README_CN.md)］［[English](#)］

In our daily work, we may need to clean the UI from screen occasionally. The main way is through a Event of Button-Click, 
but some application can clean the UI by sliding the screen. In my mind, cleaning the screen by sliding-mode will be more favored by the user. The ClearScreenHelper will make this feature easily to implement. I hope it will be helpful to you.

## Import

Step 1. Add the JitPack repository to your build file

```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

Step 2. Add the dependency

```
	dependencies {
	        compile 'com.github.Yellow5A5:ClearScreenHelper:1.0.0'
	}
```

## Introduction

ClearScreenHelper has the following advantages:

> * **Minimal changes to the original code**
> * **Dynamic settings the UI which need to be removed**
> * **Simple and Convenient in maintenance**

Demo Show here:

<img src="image/demo_image1.gif" width=300></img>
<img src="image/demo_image2.gif" width=300></img>

## Usage

### First Mode(Recomment)

#### Step 1: replace outermost layout in xml

| Original Layout | Replace To   | 
| :------:   | :-----:  |
| RelativeLayout | RelativeRootView |
| LinearLayout| LinearRootView|
| FrameLayout | FrameRootView|

eg.

* **Original Layout：**

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout 
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/sample_clear_root_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="yellow5a5.sample.SampleFirActivity">

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        .......
        .......
        .......
```

* **After Be Replaced：**

```
<?xml version="1.0" encoding="utf-8"?>
<yellow5a5.clearscreenhelper.View.RelativeRootView
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/sample_clear_root_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="yellow5a5.sample.SampleFirActivity">

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        .......
        .......
        .......
```



#### Step 2: Init in Java.

```java
mClearRootLayout = (RelativeRootView) findViewById(R.id.sample_clear_root_layout);
mClearScreenHelper = new ClearScreenHelper(this, mClearRootLayout);
mClearScreenHelper.bind(mLeftBottomBtn, mRightBottomBtn, mRightTopTextV, mFansTextV, mInfoTextV);

//Add the callback if you want.
mClearScreenHelper.setIClearEvent(new IClearEvent() {
    @Override
    public void onClearEnd() {
        Toast.makeText(SampleFirActivity.this, "Clear End...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecovery() {
        Toast.makeText(SampleFirActivity.this, "Recovery Now...", Toast.LENGTH_SHORT).show();
    }
});
```


### Second Mode（Not recommended for use.）

* Not recommended for use beacause we must move from side of screen in this mode, and it will cover the event of touch. 


```java
mClearScreenHelper = new ClearScreenHelper(this);
mClearScreenHelper.bind(mLeftBottomBtn, mRightBottomBtn, mRightTopTextV, mFansTextV, mInfoTextV);

//Add the callback if you want.
mClearScreenHelper.setIClearEvent(new IClearEvent() {
    @Override
    public void onClearEnd() {
        Toast.makeText(SampleFirActivity.this, "Clear End...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecovery() {
        Toast.makeText(SampleFirActivity.this, "Recovery Now...", Toast.LENGTH_SHORT).show();
    }
}
```



## License

    Copyright 2016 Yellow5A5
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 

