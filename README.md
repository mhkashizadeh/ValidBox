# ValidBox
ValidBox is EditText with validation for android

### Setup 

##### 1.First import project as android application.
##### 2.Then copy ```icomoon.ttf``` in assets folder.

----

### Usage

##### JAVA CODE

``` java
    ValidBox validBox = (ValidBox) findViewById(R.id.validBox);
        validBox.setPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                .setTextColor(Color.Red)
                .setTextSize(14)
                .setIconType(ValidBox.ICON_RADIOCHECK);
```

##### XML CODE

``` xml
    <com.kashizadeh.library.validbox.ValidBox
        xmlns:customAttr="http://schemas.android.com/apk/res/com.kashizadeh.library.validbox"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        customAttr:textSize="18"
        customAttr:textColor="@color/red"
        customAttr:iconType="2"
        customAttr:pattern="(.*)(\\d+)(.*)"
   />
```

----

### Developer
#### [Mohammad Hossein Kashizadeh](mailto:mh.kashizadeh@gmail.com)

  
